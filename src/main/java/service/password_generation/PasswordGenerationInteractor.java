package service.password_generation;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The interactor for the password generation use case. Implements the
 * business logic for generating passwords by communicating directly with the Random.org API.
 */
public class PasswordGenerationInteractor implements PasswordGenerationInputBoundary {

    private final PasswordGenerationOutputBoundary presenter;
    private final String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    /**
     * Constructs a new PasswordGenerationInteractor with the specified presenter and API key.
     *
     * @param presenter The presenter to handle the response.
     * @param apiKey    The Random.org API key.
     */
    public PasswordGenerationInteractor(PasswordGenerationOutputBoundary presenter, String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("Random.org API key must be provided.");
        }
        this.presenter = presenter;
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Generates a password by communicating with the Random.org API.
     */
    @Override
    public void generate() {
        try {
            int passwordLength = 12;
            String password = generatePassword(passwordLength);

            final PasswordGenerationResponseModel responseModel = new PasswordGenerationResponseModel(
                    password,
                    true,
                    null
            );

            presenter.present(responseModel);
        } catch (IOException | InterruptedException e) {
            final PasswordGenerationResponseModel responseModel = new PasswordGenerationResponseModel(
                    null,
                    false,
                    e.getMessage()
            );
            presenter.present(responseModel);
        }
    }

    /**
     * Generates a random password of the specified length using the Random.org API.
     *
     * @param length The desired length of the password.
     * @return The generated password.
     * @throws IOException          If an I/O error occurs.
     * @throws InterruptedException If the operation is interrupted.
     */
    private String generatePassword(int length) throws IOException, InterruptedException {

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_=+[]{}|;:',.<>/?";

        String allChars = upper + lower + digits + special;

        ObjectNode mainNode = objectMapper.createObjectNode();
        mainNode.put("jsonrpc", "2.0");
        mainNode.put("method", "generateIntegers");
        mainNode.put("id", 42);

        ObjectNode paramsNode = objectMapper.createObjectNode();
        paramsNode.put("apiKey", apiKey);
        paramsNode.put("n", length);
        paramsNode.put("min", 0);
        paramsNode.put("max", allChars.length() - 1);
        paramsNode.put("replacement", true);

        mainNode.set("params", paramsNode);

        String requestBody = objectMapper.writeValueAsString(mainNode);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.random.org/json-rpc/4/invoke"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode jsonResponse = objectMapper.readTree(response.body());

        if (jsonResponse.has("error")) {
            String errorMessage = jsonResponse.get("error").get("message").asText();
            throw new IOException("Random.org API error: " + errorMessage);
        }

        List<Integer> randomNumbers = objectMapper.convertValue(
                jsonResponse.at("/result/random/data"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Integer.class)
        );

        StringBuilder passwordBuilder = new StringBuilder();
        for (Integer number : randomNumbers) {
            passwordBuilder.append(allChars.charAt(number));
        }

        return passwordBuilder.toString();
    }
}

package service.password_generation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import exception.HttpRequestException;
import interface_adapter.net.http.HttpClient;
import interface_adapter.net.http.HttpResponse;

/**
 * The interactor for the password generation use case. Implements the
 * business logic for generating passwords by communicating directly with the Random.org API.
 */
public class PasswordGenerationInteractor implements PasswordGenerationInputBoundary {
    private final PasswordGenerationOutputBoundary presenter;
    private final HttpClient httpClient;
    private final String apiKey;

    private static final String RANDOM_ORG_API_URL = "https://api.random.org/json-rpc/4/invoke";

    /**
     * Constructs a new PasswordGenerationInteractor with the specified presenter and API key.
     *
     * @param httpClient The HTTP client to use for making requests.
     * @param presenter The presenter to handle the response.
     * @param apiKey    The Random.org API key.
     */
    public PasswordGenerationInteractor(
        HttpClient httpClient,
        PasswordGenerationOutputBoundary presenter,
        String apiKey
    ) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("Random.org API key must be provided.");
        }
        this.presenter = presenter;
        this.apiKey = apiKey;
        this.httpClient = httpClient;
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
                    ""
            );
    
            presenter.present(responseModel);
        } catch (JSONException | IOException | InterruptedException e) {
            final PasswordGenerationResponseModel responseModel;
            responseModel = new PasswordGenerationResponseModel(
                    "",
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

        String requestBody = getRequestBody(length, allChars.length());

        try {
            HttpResponse response = httpClient.post(
                RANDOM_ORG_API_URL,
                requestBody,
                new HashMap<String, String>() {{
                    put("Content-Type", "application/json");
                }}
            );

            JSONObject jsonResponse = response.bodyToJsonObject();

            if (jsonResponse.has("error")) {
                String errorMessage = jsonResponse.getJSONObject("error").getString("message");
                throw new IOException("Random.org API error: " + errorMessage);
            }

            List<Object> randomObjectNumbers = jsonResponse.getJSONObject("result")
                .getJSONObject("random")
                .getJSONArray("data")
                .toList();

            List<Integer> randomNumbers = randomObjectNumbers.stream()
                .map(number -> (int) number)
                .toList();

            StringBuilder passwordBuilder = new StringBuilder();
            for (Integer number : randomNumbers) {
                passwordBuilder.append(allChars.charAt(number));
            }

            return passwordBuilder.toString();
        } catch (HttpRequestException httpRequestException) {
            throw new IOException("Failed to make a request to the Random.org API: " + httpRequestException.getMessage());
        } catch (NullPointerException nullPointerException) {
            throw new InterruptedException("Thread was interrupted");
        }
    }

    private String getRequestBody(int length, int allCharsLength) {
        JSONObject mainObject = new JSONObject();
        mainObject.put("jsonrpc", "2.0");
        mainObject.put("method", "generateIntegers");
        mainObject.put("id", 42);

        JSONObject paramsObject = new JSONObject();
        paramsObject.put("apiKey", apiKey);
        paramsObject.put("n", length);
        paramsObject.put("min", 0);
        paramsObject.put("max", allCharsLength - 1);
        paramsObject.put("replacement", true);

        mainObject.put("params", paramsObject);

        return mainObject.toString();
    }
}
package interface_adapter.net.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import exception.HttpRequestException;

/**
 * Implements the HttpClient interface providing GET and POST methods.
 */
public class CommonHttpClient implements HttpClient {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PATCH = "PATCH";

    @Override
    public HttpResponse get(String url, Map<String, String> headers) throws HttpRequestException {
        final HttpURLConnection conn = getHttpUrlConnection(url, headers, GET);

        try {
            return readStream(conn);
        }
        catch (IOException ioException) {
            throw new HttpRequestException("Failed to get response: " + ioException);
        }
    }

    @Override
    public HttpResponse post(
            String url,
            String body,
            Map<String, String> headers
    ) throws HttpRequestException {
        final HttpURLConnection conn = getHttpUrlConnection(url, headers, POST);

        try {
            final OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();

            return readStream(conn);
        }
        catch (IOException ioException) {
            throw new HttpRequestException("Failed to write to output stream: " + ioException);
        }
    }

    @Override
    public HttpResponse patch(
            String url,
            String body,
            Map<String, String> headers
    ) throws HttpRequestException {
        try {
            // HttpURLConnection does not support PATCH requests,
            // so we have to use the Java 11 HttpClient instead.
            final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method(PATCH, HttpRequest.BodyPublishers.ofString(body));

            if (headers != null) {
                headers.forEach(requestBuilder::header);
            }

            final HttpRequest request = requestBuilder.build();

            final java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            final java.net.http.HttpResponse<String> response = client.send(
                    request,
                    java.net.http.HttpResponse.BodyHandlers.ofString()
            );

            return new HttpResponse(
                    response.statusCode(),
                    response.body()
            );
        }
        catch (IOException | InterruptedException ioOrInterruptedException) {
            throw new HttpRequestException("Failed to send PATCH request: " + ioOrInterruptedException);
        }
    }

    @NotNull
    private static HttpResponse readStream(HttpURLConnection conn) throws IOException {
        final int responseCode = conn.getResponseCode();
        final BufferedReader reader;
        // If the response starts with 2**, then it means success
        // So we should be reading the input stream:
        if (200 <= responseCode && responseCode <= 299) {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        }
        else {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
        }

        final StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        return new HttpResponse(responseCode, response.toString());
    }

    @NotNull
    private static HttpURLConnection getHttpUrlConnection(
            String url, Map<String, String> headers, String requestMethod) throws HttpRequestException {
        final HttpURLConnection conn;

        try {
            final URL obj = new URL(url);
            conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);

            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    conn.setRequestProperty(header.getKey(), header.getValue());
                }
            }
        }
        catch (IOException ioException) {
            throw new HttpRequestException("Failed to create connection: " + ioException);
        }
        return conn;
    }
}

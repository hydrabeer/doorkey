package interface_adapter.net.http;

import java.util.Map;

import exception.HttpRequestException;

/**
 * Utility for making HTTP requests.
 */
public interface HttpClient {
    /**
     * Sends a GET request to the specified URL.
     *
     * @param url     The URL to send the request to.
     * @param headers The headers to include in the request.
     * @return The response from the server.
     * @throws HttpRequestException If the request fails.
     */
    HttpResponse get(String url, Map<String, String> headers) throws HttpRequestException;

    /**
     * Sends a POST request to the specified URL.
     *
     * @param url     The URL to send the request to.
     * @param body    The body of the request.
     * @param headers The headers to include in the request.
     * @return The response from the server.
     * @throws HttpRequestException If the request fails.
     */
    HttpResponse post(String url, String body, Map<String, String> headers) throws HttpRequestException;

    /**
     * Sends a PATCH request to the specified URL.
     *
     * @param url     The URL to send the request to.
     * @param body    The body of the request.
     * @param headers The headers to include in the request.
     * @return The response from the server.
     * @throws HttpRequestException If the request fails.
     */
    HttpResponse patch(String url, String body, Map<String, String> headers) throws HttpRequestException;
}

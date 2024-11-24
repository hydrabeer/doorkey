package exception;

/**
 * Represents an HTTP request exception.
 */
public class HttpRequestException extends Exception {
    private final int responseCode;
    private final String responseBody;

    public HttpRequestException(int responseCode, String responseBody) {
        super("HTTP request failed with response code " + responseCode + " and response body " + responseBody);
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public HttpRequestException(String message) {
        super(message);
        this.responseCode = -1;
        this.responseBody = null;
    }
}

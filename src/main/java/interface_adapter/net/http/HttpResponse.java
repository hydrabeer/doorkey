package interface_adapter.net.http;

import org.json.JSONObject;

/**
 * Represents an HTTP response.
 */
public class HttpResponse {
    private final int responseCode;
    private final String responseBody;

    public HttpResponse(int responseCode, String responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    /**
     * Convert the body to JSON Object.
     * @return A JSON Object representation of the body.
     */
    public JSONObject bodyToJsonObject() {
        return new JSONObject(responseBody);
    }
}

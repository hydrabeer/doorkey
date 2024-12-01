package service.validate_url;

import java.util.Map;

/**
 * Input data for validating URLs.
 */
public class ValidateUrlInputData {
    private String apiUrl;
    private Map<String, String> headers;
    private String url;

    public ValidateUrlInputData(String apiUrl, Map<String, String> headers, String url) {
        this.apiUrl = apiUrl;
        this.headers = headers;
        this.url = url;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getUrl() {
        return url;
    }
}

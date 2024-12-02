package interface_adapter.validate_url;

import java.net.URL;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import exception.HttpRequestException;
import interface_adapter.net.http.HttpClient;
import interface_adapter.net.http.HttpResponse;

/**
 * Common phishing URL validator.
 */
public class PhishtankPhishingUrlValidator implements PhishingUrlValidator {
    private static final String PHISHTANK_API_URL = "https://checkurl.phishtank.com/checkurl/";
    private static final Map<String, String> PHISHTANK_HEADERS = Map.of(
        "User-Agent", "phishtank/doorkey"
    );

    private final HttpClient client;

    public PhishtankPhishingUrlValidator(HttpClient client) {
        this.client = client;
    }

    @Override
    public boolean isPhishingUrl(URL url) {
        try {
            String urlString = url.toString();
            if (!urlString.endsWith("/")) {
                urlString += '/';
            }

            final HttpResponse response = client.post(
                    PHISHTANK_API_URL,
                    "url=" + urlString + "&format=json",
                    PHISHTANK_HEADERS
            );
            final JSONObject jsonResponse = response.bodyToJsonObject();
            final JSONObject results = jsonResponse.getJSONObject("results");
            final boolean isInDatabase = results.getBoolean("in_database");
            if (isInDatabase) {
                return results.getBoolean("valid");
            }
            return false;
        }
        catch (HttpRequestException | JSONException phishtankException) {
            return false;
        }
    }
}

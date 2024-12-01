package interface_adapter.validate_url;

import java.net.URL;

/**
 * Interface for validating the URL.
 */
public interface PhishingUrlValidator {
    /**
     * Detect whether the given URL is a phishing link.
     *
     * @param url the URL to validate
     * @return True if the URL is a phishing link, false otherwise. Return false if an error occurs.
    */
    boolean isPhishingUrl(URL url);
}

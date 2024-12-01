package service.validate_url;

/**
 * Output data for validating URLs.
 */
public class ValidateUrlOutputData {
    private final boolean isPhishingUrl;

    public ValidateUrlOutputData(boolean isPhishingUrl) {
        this.isPhishingUrl = isPhishingUrl;
    }

    public boolean isPhishingUrl() {
        return isPhishingUrl;
    }

}

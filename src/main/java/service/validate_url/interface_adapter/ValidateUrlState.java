package service.validate_url.interface_adapter;

/**
 * State for validating URLs.
 */
public class ValidateUrlState {

    private boolean isPhishingUrl;
    private String message;

    public boolean isPhishingUrl() {
        return isPhishingUrl;
    }

    public void setPhishingUrl(boolean phishingUrl) {
        isPhishingUrl = phishingUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

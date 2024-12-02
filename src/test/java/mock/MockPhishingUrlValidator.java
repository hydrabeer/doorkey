package mock;

import java.net.URL;

import interface_adapter.validate_url.PhishingUrlValidator;

public class MockPhishingUrlValidator implements PhishingUrlValidator {
    private boolean isPhishing;

    public MockPhishingUrlValidator(boolean isPhishing) {
        this.isPhishing = isPhishing;
    }

    @Override
    public boolean isPhishingUrl(URL url) {
        return isPhishing;
    }

    public void setIsPhishing(boolean isPhishing) {
        this.isPhishing = isPhishing;
    }

    public boolean getIsPhishing() {
        return isPhishing;
    }
}
package service.login.url_redirect;

import java.awt.*;
import java.net.URI;

/**
 * The Open URL Interactor.
 * Opens the URL
 */
public class UrlRedirectInteractor implements UrlRedirectInputBoundary {
    private final UrlRedirectOutputBoundary boundary;

    public UrlRedirectInteractor(UrlRedirectOutputBoundary boundary) {
        this.boundary = boundary;
    }

    /**
     * Attempts to open urlInputData.getUrl in the default browser.
     * @param urlInputData object that stores URL
     */
    public void openUrl(UrlInputData urlInputData) {
        try {
            final Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new URI(urlInputData.getUrl()));
            }
            else {
                boundary.displayError("Opening URLs is not supported on this system.");
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            boundary.displayError("Failed to open the link: " + urlInputData.getUrl());
        }
    }
}

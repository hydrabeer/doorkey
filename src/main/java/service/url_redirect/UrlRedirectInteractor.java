package service.url_redirect;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The Open URL Interactor.
 * Opens the URL
 */
public class UrlRedirectInteractor implements UrlRedirectInputBoundary {
    private final DesktopWrapper desktopWrapper;
    private final UrlRedirectOutputBoundary boundary;

    public UrlRedirectInteractor(DesktopWrapper desktopWrapper, UrlRedirectOutputBoundary boundary) {
        this.desktopWrapper = desktopWrapper;
        this.boundary = boundary;
    }

    /**
     * Attempts to open urlInputData.getUrl in the default browser.
     * @param urlInputData object that stores URL
     */
    public void openUrl(UrlInputData urlInputData) {
        try {
            if (desktopWrapper.isSupported(Desktop.Action.BROWSE)) {
                desktopWrapper.browse(new URI(urlInputData.getUrl()));
            }
            else {
                System.out.println("a");
                boundary.displayError("Opening URLs is not supported on this system.");
            }
        }
        catch (URISyntaxException exception) {
            System.out.println("b");
            boundary.displayError("Invalid URL: " + exception.getMessage());
        }
        catch (IOException exception) {
            System.out.println("c");
            boundary.displayError("I/O Error while trying to open the URL: " + exception.getMessage());
        }
        catch (SecurityException exception) {
            System.out.println("d");
            boundary.displayError("Insufficient permissions to open the URL: " + exception.getMessage());
        }
    }
}

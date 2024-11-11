package url_redirect;

/**
 * The Open URL Interactor.
 * Opens the URL
 */
public class UrlRedirectInteractor implements UrlRedirectInputBoundary {
    private final UrlRedirectInputBoundary boundary;

    public UrlRedirectInteractor(UrlRedirectInputBoundary boundary) {
        this.boundary = boundary;
    }

    /**
     * Attempts to open urlInputData.getUrl in the default browser.
     * @param urlInputData object that stores URL
     */
    public void openUrl(UrlInputData urlInputData) {

    }
}

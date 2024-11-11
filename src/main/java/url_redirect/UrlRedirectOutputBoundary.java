package url_redirect;

/**
 * Interface that Presenter implements.
 * Used as layer in between interactor and presenter
 */
public interface UrlRedirectOutputBoundary {

    /**
     * Shows error messages if opening the URL fails.
     * @param message message to be displayed
     */
    void displayError(String message);
}

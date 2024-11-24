package service.url_redirect;

/**
 * Interface that interactor implements.
 * Used as layer inbetween the controller and interactor.
 */
public interface UrlRedirectInputBoundary {

    /**
     * Opens URL in web browser.
     * @param urlInputData contains URL that will be opened
     */
    void openUrl(UrlInputData urlInputData);
}

package service.login.url_redirect.interface_adapter;

import service.login.url_redirect.UrlInputData;
import service.login.url_redirect.UrlRedirectInputBoundary;

/**
 * URL redirect controller
 * Calls interactor methods indrectly.
 */
public class UrlRedirectController {
    private UrlRedirectInputBoundary inputBoundary;

    public UrlRedirectController(UrlRedirectInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Calls interactors url method using inputBoundary as an abstraction layer.
     * @param url url to passed in input data object to interactor method.
     */
    public void openUrl(String url) {
        final UrlInputData urlInputData = new UrlInputData(url);
        inputBoundary.openUrl(urlInputData);
    }
}

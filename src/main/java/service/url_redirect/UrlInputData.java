package service.url_redirect;

/**
 * Object that passes info from UrlRedirectController to interactor.
 */
public class UrlInputData {
    private String url;

    public UrlInputData(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

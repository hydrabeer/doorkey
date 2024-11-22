package url_redirect;

import org.junit.jupiter.api.Test;
import service.url_redirect.UrlInputData;
import service.url_redirect.UrlRedirectInputBoundary;
import service.url_redirect.UrlRedirectInteractor;
import service.url_redirect.UrlRedirectOutputBoundary;

public class UrlRedirectInteractorTest {

    @Test
    public void UrlRedirectTest(){
        UrlInputData urlInputData = new UrlInputData("https://www.hltv.org/");
        UrlRedirectOutputBoundary urlRedirectOutputBoundary = new UrlRedirectOutputBoundary() {
            @Override
            public void displayError(String message) {

            }
        };
        UrlRedirectInputBoundary urlRedirectInputBoundary = new UrlRedirectInteractor(urlRedirectOutputBoundary);
        urlRedirectInputBoundary.openUrl(urlInputData);
    }
}

package url_redirect;

import org.junit.jupiter.api.Test;

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

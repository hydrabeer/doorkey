package url_redirect;

import mock.MockDesktopWrapper;
import mock.MockUrlRedirectPresenter;
import org.junit.jupiter.api.Test;
import service.password_vault_item.interface_adapter.PasswordVaultItemViewModel;
import service.url_redirect.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UrlRedirectInteractorTest {
    private String url = "https://www.example.com/";

    @Test
    public void testOpenUrlWhenSupported(){
        UrlInputData urlInputData = new UrlInputData(url);
        UrlRedirectOutputBoundary urlRedirectOutputBoundary = new UrlRedirectOutputBoundary() {
            @Override
            public void displayError(String message) {

            }
        };
        DesktopWrapper desktopWrapper = new MockDesktopWrapper(Boolean.TRUE);
        UrlRedirectInputBoundary urlRedirectInputBoundary = new UrlRedirectInteractor(
                desktopWrapper,urlRedirectOutputBoundary);
        urlRedirectInputBoundary.openUrl(urlInputData);
        // will throw an exception if it fails causing the test to fail
    }

    @Test
    public void testOpenUrlWhenNotSupported() {
        UrlInputData urlInputData = new UrlInputData(url);
        MockUrlRedirectPresenter mockPresenter = new MockUrlRedirectPresenter();
        DesktopWrapper desktopWrapper = new MockDesktopWrapper(Boolean.FALSE) ;
        UrlRedirectInputBoundary urlRedirectInputBoundary = new UrlRedirectInteractor(
                desktopWrapper, mockPresenter);
        urlRedirectInputBoundary.openUrl(urlInputData);
        assert(mockPresenter.hasGeneralError("Opening URLs is not supported on this system."));
    }

    @Test
    public void testOpenUrlIOException() {
        UrlInputData urlInputData = new UrlInputData(url);
        MockUrlRedirectPresenter mockPresenter = new MockUrlRedirectPresenter();
        DesktopWrapper desktopWrapper = new MockDesktopWrapper(Boolean.TRUE, new IOException()) ;
        UrlRedirectInputBoundary urlRedirectInputBoundary = new UrlRedirectInteractor(
                desktopWrapper, mockPresenter);
        urlRedirectInputBoundary.openUrl(urlInputData);
        assert(mockPresenter.hasGeneralError("I/O Error while trying to open the URL: "));

    }

    @Test
    public void testOpenUrlSecurityException() {
        UrlInputData urlInputData = new UrlInputData(url);
        MockUrlRedirectPresenter mockPresenter = new MockUrlRedirectPresenter();
        DesktopWrapper desktopWrapper = new MockDesktopWrapper(Boolean.TRUE, new SecurityException()) ;
        UrlRedirectInputBoundary urlRedirectInputBoundary = new UrlRedirectInteractor(
                desktopWrapper, mockPresenter);
        urlRedirectInputBoundary.openUrl(urlInputData);
        assert(mockPresenter.hasGeneralError("Insufficient permissions to open the URL: "));

    }

    @Test
    public void testOpenUrlURISyntaxException() {
        UrlInputData urlInputData = new UrlInputData("://example.com");
        MockUrlRedirectPresenter mockPresenter = new MockUrlRedirectPresenter();
        DesktopWrapper desktopWrapper = new MockDesktopWrapper(Boolean.TRUE) ;
        UrlRedirectInputBoundary urlRedirectInputBoundary = new UrlRedirectInteractor(
                desktopWrapper, mockPresenter);
        urlRedirectInputBoundary.openUrl(urlInputData);
        assert(mockPresenter.hasGeneralError("Invalid URL: "));
    }

    @Test
    public void testOpenUrlWithRealDeskTop(){
        UrlInputData urlInputData = new UrlInputData(url);
        UrlRedirectOutputBoundary urlRedirectOutputBoundary = new UrlRedirectOutputBoundary() {
            @Override
            public void displayError(String message) {

            }
        };
        DesktopWrapper desktopWrapper = new RealDesktopWrapper();
        UrlRedirectInputBoundary urlRedirectInputBoundary = new UrlRedirectInteractor(
                desktopWrapper,urlRedirectOutputBoundary);
        urlRedirectInputBoundary.openUrl(urlInputData);
        // will throw an exception if it fails causing the test to fail
    }
}

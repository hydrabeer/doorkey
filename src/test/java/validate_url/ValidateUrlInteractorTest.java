package validate_url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.validate_url.ValidateUrlInteractor;
import service.validate_url.ValidateUrlInputData;
import service.validate_url.ValidateUrlOutputBoundary;
import service.validate_url.ValidateUrlOutputData;
import interface_adapter.net.http.HttpClient;
import interface_adapter.net.http.HttpResponse;
import exception.HttpRequestException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValidateUrlInteractorTest {

    private ValidateUrlInteractor interactor;
    private TestHttpClient testHttpClient;
    private TestValidateUrlOutputBoundary testPresenter;

    @BeforeEach
    void setUp() {
        testHttpClient = new TestHttpClient();
        testPresenter = new TestValidateUrlOutputBoundary();
        interactor = new ValidateUrlInteractor(testPresenter, testHttpClient);
    }

    @Test
    void testValidateUrl_phishingUrl() throws HttpRequestException {
        testHttpClient.setResponse(new HttpResponse(200, "{\"in_database\":\"true\"}"));
        ValidateUrlInputData inputData = new ValidateUrlInputData(
                "https://api.example.com",
                new HashMap<>(),
                "https://example.com"
        );

        interactor.validateUrl(inputData);

        assertTrue(testPresenter.getOutputData().isPhishingUrl());
    }

    @Test
    void testValidateUrl_nonPhishingUrl() throws HttpRequestException {
        testHttpClient.setResponse(new HttpResponse(200, "{\"in_database\":\"false\"}"));
        ValidateUrlInputData inputData = new ValidateUrlInputData(
                "https://api.example.com",
                new HashMap<>(),
                "https://example.com"
        );

        interactor.validateUrl(inputData);

        assertFalse(testPresenter.getOutputData().isPhishingUrl());
    }

    private static class TestHttpClient implements HttpClient {
        private HttpResponse response;

        void setResponse(HttpResponse response) {
            this.response = response;
        }

        @Override
        public HttpResponse post(String url, String body, Map<String, String> headers) {
            return response;
        }

        @Override
        public HttpResponse get(String url, Map<String, String> headers) {
            return response;
        }

        @Override
        public HttpResponse patch(String url, String body, Map<String, String> headers) {
            return response;
        }
    }

    private static class TestValidateUrlOutputBoundary implements ValidateUrlOutputBoundary {
        private ValidateUrlOutputData outputData;

        @Override
        public void prepareView(ValidateUrlOutputData outputData) {
            this.outputData = outputData;
        }

        ValidateUrlOutputData getOutputData() {
            return outputData;
        }
    }
}
package interface_adapter.validate_url;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

import exception.HttpRequestException;
import interface_adapter.net.http.HttpResponse;
import mock.MockHttpClient;

public class PhishtankPhishingUrlValidatorTest {

    @Test
    public void testIsPhishingUrl_ValidPhishingUrl() throws MalformedURLException {
        String jsonResponse = "{\"results\":{\"in_database\":true,\"valid\":true}}";
        MockHttpClient mockClient = new MockHttpClient();
        mockClient.setMockResponse(new HttpResponse(200, jsonResponse));
        PhishtankPhishingUrlValidator validator = new PhishtankPhishingUrlValidator(mockClient);

        URL url = new URL("https://phishing.com");
        assertTrue(validator.isPhishingUrl(url));
    }

    @Test
    public void testIsPhishingUrl_NotPhishingUrl() throws MalformedURLException {
        String jsonResponse = "{\"results\":{\"in_database\":true,\"valid\":false}}";
        MockHttpClient mockClient = new MockHttpClient();
        mockClient.setMockResponse(new HttpResponse(200, jsonResponse));
        PhishtankPhishingUrlValidator validator = new PhishtankPhishingUrlValidator(mockClient);

        URL url = new URL("https://safe.com");
        assertFalse(validator.isPhishingUrl(url));
    }

    @Test
    public void testIsPhishingUrl_NotInDatabase() throws MalformedURLException {
        String jsonResponse = "{\"results\":{\"in_database\":false}}";
        MockHttpClient mockClient = new MockHttpClient();
        mockClient.setMockResponse(new HttpResponse(200, jsonResponse));
        PhishtankPhishingUrlValidator validator = new PhishtankPhishingUrlValidator(mockClient);

        URL url = new URL("https://unknown.com");
        assertFalse(validator.isPhishingUrl(url));
    }

    @Test
    public void testIsPhishingUrl_HttpRequestException() throws MalformedURLException {
        MockHttpClient mockClient = new MockHttpClient();
        mockClient.setException(new HttpRequestException("Simulated exception"));
        PhishtankPhishingUrlValidator validator = new PhishtankPhishingUrlValidator(mockClient);

        URL url = new URL("https://error.com");
        assertFalse(validator.isPhishingUrl(url));
    }

    @Test
    public void testIsPhishingUrl_UrlWithTrailingSlash() throws MalformedURLException {
        String jsonResponse = "{\"results\":{\"in_database\":true,\"valid\":true}}";
        MockHttpClient mockClient = new MockHttpClient();
        mockClient.setMockResponse(new HttpResponse(200, jsonResponse));
        PhishtankPhishingUrlValidator validator = new PhishtankPhishingUrlValidator(mockClient);

        URL url = new URL("https://phishing.com/");
        assertTrue(validator.isPhishingUrl(url));
    }
}
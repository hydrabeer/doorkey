package password_generation;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.HttpRequestException;
import interface_adapter.net.http.HttpResponse;
import mock.MockHttpClient;
import mock.MockPasswordGenerationPresenter;
import service.password_generation.PasswordGenerationInteractor;
import service.password_generation.PasswordGenerationResponseModel;

public class PasswordGenerationInteractorTest {
    private MockHttpClient mockHttpClient;
    private MockPasswordGenerationPresenter mockPresenter;
    private PasswordGenerationInteractor interactor;
    private final String validApiKey = "valid-api-key";

    @BeforeEach
    public void setUp() {
        mockHttpClient = new MockHttpClient();
        mockPresenter = new MockPasswordGenerationPresenter();
        interactor = new PasswordGenerationInteractor(mockHttpClient, mockPresenter, validApiKey);
    }

    @Test
    public void testGenerateSuccess() throws Exception {
        String jsonResponse = "{ \"result\": { \"random\": { \"data\": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11] } } }";
        mockHttpClient.setMockResponse(new HttpResponse(200, jsonResponse));

        interactor.generate();

        PasswordGenerationResponseModel response = mockPresenter.getPresentedModel();
        assertTrue(response.isGenerationSuccess());
        assertTrue("ABCDEFGHIJKL".equals(response.getGeneratedPassword()));
        assertTrue(response.getErrorMessage().isEmpty());
    }

    @Test
    public void testGenerateRandomOrgError() throws Exception {
        String jsonResponse = "{ \"error\": { \"message\": \"Invalid API key.\" } }";
        mockHttpClient.setMockResponse(new HttpResponse(400, jsonResponse));

        interactor.generate();

        PasswordGenerationResponseModel response = mockPresenter.getPresentedModel();
        assertFalse(response.isGenerationSuccess());
        assertTrue(response.getGeneratedPassword().isEmpty());
        assertEquals("Random.org API error: Invalid API key.", response.getErrorMessage());
    }

    @Test
    public void testGenerateHttpRequestException() throws Exception {
        mockHttpClient.setException(new HttpRequestException("Network error"));

        interactor.generate();

        PasswordGenerationResponseModel response = mockPresenter.getPresentedModel();
        assertFalse(response.isGenerationSuccess());
        assertTrue(response.getGeneratedPassword().isEmpty());
        assertEquals("Failed to make a request to the Random.org API: Network error", response.getErrorMessage());
    }

    @Test
    public void testGenerateIOException() {
        mockHttpClient.setMockResponse(new HttpResponse(200, "invalid json"));

        interactor.generate();

        PasswordGenerationResponseModel response = mockPresenter.getPresentedModel();
        assertFalse(response.isGenerationSuccess());
        assertTrue(response.getGeneratedPassword().isEmpty());
    }

    @Test
    public void testGenerateInterruptedException() throws Exception {
        MockHttpClient interruptingHttpClient = new MockHttpClient() {
            @Override
            public HttpResponse post(String url, String body, Map<String, String> headers) throws HttpRequestException {
                Thread.currentThread().interrupt();
                return super.post(url, body, headers);
            }
        };
        interactor = new PasswordGenerationInteractor(interruptingHttpClient, mockPresenter, validApiKey);

        interactor.generate();

        PasswordGenerationResponseModel response = mockPresenter.getPresentedModel();
        assertFalse(response.isGenerationSuccess());
        assertTrue(response.getGeneratedPassword().isEmpty());
        assertEquals("Thread was interrupted", response.getErrorMessage());
    }

    @Test
    public void testConstructorWithNullApiKey() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new PasswordGenerationInteractor(mockHttpClient, mockPresenter, null);
        });
        assertEquals("Random.org API key must be provided.", exception.getMessage());
    }

    @Test
    public void testConstructorWithEmptyApiKey() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new PasswordGenerationInteractor(mockHttpClient, mockPresenter, "");
        });
        assertEquals("Random.org API key must be provided.", exception.getMessage());
    }
}

package mock;

import java.util.Map;

import exception.HttpRequestException;
import interface_adapter.net.http.HttpClient;
import interface_adapter.net.http.HttpResponse;

public class MockHttpClient implements HttpClient {
    private HttpResponse mockResponse;
    private HttpRequestException exceptionToThrow;

    public void setMockResponse(HttpResponse response) {
        this.mockResponse = response;
        this.exceptionToThrow = null;
    }

    public void setException(HttpRequestException exception) {
        this.exceptionToThrow = exception;
        this.mockResponse = null;
    }

    @Override
    public HttpResponse get(String url, Map<String, String> headers) throws HttpRequestException {
        throw new UnsupportedOperationException("GET not supported in mock yet.");
    }

    @Override
    public HttpResponse post(String url, String body, Map<String, String> headers) throws HttpRequestException {
        if (exceptionToThrow != null) {
            throw exceptionToThrow;
        }
        return mockResponse;
    }

    @Override
    public HttpResponse patch(String url, String body, Map<String, String> headers) throws HttpRequestException {
        throw new UnsupportedOperationException("PATCH not supported in mock yet.");
    }
}

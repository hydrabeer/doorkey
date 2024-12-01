package service.validate_url;

import org.json.JSONObject;

import exception.HttpRequestException;
import interface_adapter.net.http.HttpClient;
import interface_adapter.net.http.HttpResponse;

/**
 * Interactor to validate URLs.
 */
public class ValidateUrlInteractor implements ValidateUrlInputBoundary {
    private final ValidateUrlOutputBoundary presenter;
    private final HttpClient client;

    public ValidateUrlInteractor(ValidateUrlOutputBoundary presenter, HttpClient client) {
        this.client = client;
        this.presenter = presenter;
    }

    @Override
    public void validateUrl(ValidateUrlInputData inputData) throws HttpRequestException {
        final HttpResponse response = client.post(
                inputData.getApiUrl(),
                "?url=" + inputData.getUrl() + "&format=json",
                inputData.getHeaders()
        );
        final JSONObject jsonResponse = response.bodyToJsonObject();
        final ValidateUrlOutputData outputData = new ValidateUrlOutputData(
                jsonResponse.get("in_database").equals("true")
        );
        presenter.prepareView(outputData);
    }
}

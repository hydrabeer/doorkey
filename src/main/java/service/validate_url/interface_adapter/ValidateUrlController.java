package service.validate_url.interface_adapter;

import java.util.Map;

import exception.HttpRequestException;
import service.validate_url.ValidateUrlInputBoundary;
import service.validate_url.ValidateUrlInputData;

/**
 * Controller for validating URLs.
 */
public class ValidateUrlController {

    private final ValidateUrlInputBoundary interactor;

    public ValidateUrlController(ValidateUrlInputBoundary interactor) {
        this.interactor = interactor;

    }

    /**
     * Validates a URL.
     *
     * @param apiUrl  API URL
     * @param headers Headers
     * @param url     URL to validate
     * @throws HttpRequestException if an error occurs while validating the URL
     */
    public void validateUrl(String apiUrl,
                            Map<String, String> headers,
                            String url
    ) throws HttpRequestException {
        final ValidateUrlInputData inputData = new ValidateUrlInputData(apiUrl, headers, url);
        interactor.validateUrl(inputData);
    }
}

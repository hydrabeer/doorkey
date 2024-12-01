package service.validate_url;

import exception.HttpRequestException;

/**
 * Input boundary for validating URLs.
 */
public interface ValidateUrlInputBoundary {
    /**
     * Validates a URL to determine if it is a phishing URL.
     *
     * @param validateUrlInputData Input data containing the API URL, headers, and URL to validate
     * @throws HttpRequestException If an error occurs while making the HTTP request
     */
    void validateUrl(ValidateUrlInputData validateUrlInputData) throws HttpRequestException;
}

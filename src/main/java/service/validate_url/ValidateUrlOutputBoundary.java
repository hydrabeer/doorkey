package service.validate_url;

/**
 * Output boundary for validating URLs.
 */
public interface ValidateUrlOutputBoundary {
    /**
     * Prepares the view for the result of the URL validation.
     *
     * @param outputData the output data
     */
    void prepareView(ValidateUrlOutputData outputData);
}

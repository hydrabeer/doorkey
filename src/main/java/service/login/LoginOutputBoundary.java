package service.login;

/**
 * The log in output boundary for the view.
 */
public interface LoginOutputBoundary {
    /**
     * Prepares the success view.
     *
     * @param loginOutputData The login output data.
     */
    void prepareSuccessView(LoginOutputData loginOutputData);

    /**
     * Prepares the fail view.
     *
     * @param error The error message.
     */
    void prepareErrorView(String error);

    /**
     * Prepares the fail view for a specific field.
     *
     * @param field The field that has an error.
     * @param error The error message.
     */
    void prepareErrorView(String field, String error);

    /**
     * Clears an error at a given field.
     *
     * @param field The field to clear the error from.
     */
    void clearError(String field);
}

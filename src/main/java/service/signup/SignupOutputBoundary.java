package service.signup;

/**
 * The sign up output boundary for the view.
 */
public interface SignupOutputBoundary {
    /**
     * Prepares the success view.
     *
     * @param signupOutputData The sign up output data.
     */
    void prepareSuccessView(SignupOutputData signupOutputData);

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
     * Clears the error view for a field.
     *
     * @param field The field to clear.
     */
    void clearError(String field);
}

package service.login;

/**
 * The log in output boundary for the view.
 */
public interface LoginOutputBoundary {
    /**
     * Prepares the success view.
     * @param loginOutputData The login output data.
     */
    void prepareSuccessView(LoginOutputData loginOutputData);

    /**
     * Prepares the fail view.
     *
     * @param error The error message.
     */
    void prepareErrorView(String error);
}

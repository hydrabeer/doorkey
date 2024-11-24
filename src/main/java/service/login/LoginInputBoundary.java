package service.login;

/**
 * The Login use case.
 */
public interface LoginInputBoundary {
    /**
     * Log in the user to remote sync.
     * @param loginInputData The login input data.
     */
    void login(LoginInputData loginInputData);
}

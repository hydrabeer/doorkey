package service.login;

/**
 * The Login use case.
 */
public interface LoginInputBoundary {
    /**
     * Log in the user to remote sync.
     */
    void login(LoginInputData loginInputData);

    /**
     * Switch to home view.
     *
     * @param email The user's email
     * @param password The user's master password
     */
    void switchToHomeView(String email, String password);
}

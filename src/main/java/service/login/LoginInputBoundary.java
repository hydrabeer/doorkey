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

    /**
     * Switch to home view.
     *
     * @param email The user's email
     * @param password The user's master password
     */
    void switchToHomeView(String email, String password);

    /**
     * Switch to the local vault view.
     */
    void switchToLocalVaultView();
}

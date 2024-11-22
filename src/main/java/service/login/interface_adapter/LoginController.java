package service.login.interface_adapter;

import service.login.LoginInputBoundary;
import service.login.LoginInputData;

/**
 * Controller for signing in.
 */
public class LoginController {
    private final LoginInputBoundary interactor;

    public LoginController(LoginInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the login.
     * @param email The user's email.
     * @param password The user's password.
     */
    public void login(String email, String password) {
        final LoginInputData inputData = new LoginInputData(email, password);
        interactor.login(inputData);
    }

    /**
     * Switch to home view.
     *
     * @param email The user's email
     * @param password The user's master password
     */
    public void switchToHomeView(String email, String password) {
        interactor.switchToHomeView(email, password);
    }

    /**
     * Switch to the local vault view.
     */
    public void switchToLocalVaultView() {
        interactor.switchToLocalVaultView();
    }
}

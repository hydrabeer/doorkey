package service.login;

import views.TestView;

/**
 * The log in output boundary for the view.
 */
public interface LoginOutputBoundary {
    /**
     * Prepares the success view.
     */
    void prepareSuccessView(LoginOutputData loginOutputData);

    /**
     * Prepares the fail view.
     *
     * @param error The error message.
     */
    void prepareErrorView(String error);

    /**
     * Switch to home view.
     *
     * @param email The user's email
     * @param password The user's master password
     */
    void switchToHomeView(String email, String password);
}

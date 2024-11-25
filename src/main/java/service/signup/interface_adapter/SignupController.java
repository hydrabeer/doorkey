package service.signup.interface_adapter;

import service.signup.SignupInputBoundary;
import service.signup.SignupInputData;

/**
 * Controller for signing up.
 */
public class SignupController {
    private final SignupInputBoundary interactor;

    public SignupController(SignupInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the signup.
     *
     * @param email            The user's email.
     * @param password         The user's password.
     * @param repeatedPassword The user's repeated password.
     */
    public void signup(String email, String password, String repeatedPassword) {
        final SignupInputData signupData = new SignupInputData(email, password, repeatedPassword);
        interactor.signup(signupData);
    }
}

package service.signup;

import entity.AbstractUser;
import exception.AuthException;
import repository.UserRepository;

/**
 * The signup interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";
    private static final String REPEATED_PASSWORD_FIELD = "repeatedPassword";

    private final UserRepository userRepository;
    private final SignupOutputBoundary signupPresenter;

    public SignupInteractor(UserRepository userRepository, SignupOutputBoundary outputPresenter) {
        this.userRepository = userRepository;
        this.signupPresenter = outputPresenter;
    }

    @Override
    public void signup(SignupInputData signupInputData) {
        if (inputDataIsInvalid(signupInputData)) {
            return;
        }

        signupPresenter.clearError(EMAIL_FIELD);
        signupPresenter.clearError(PASSWORD_FIELD);
        signupPresenter.clearError(REPEATED_PASSWORD_FIELD);

        try {
            final AbstractUser user = this.userRepository.signupUser(
                    signupInputData.getEmail(),
                    signupInputData.getPassword()
            );

            final SignupOutputData outputData = new SignupOutputData(user, this.userRepository);
            signupPresenter.prepareSuccessView(outputData);
        }
        catch (AuthException authException) {
            signupPresenter.prepareErrorView(authException.getViewMessage());
        }
    }

    private boolean inputDataIsInvalid(SignupInputData signupInputData) {
        final boolean passwordsDontMatch = !signupInputData.getPassword().equals(signupInputData.getRepeatedPassword());
        final boolean emailIsInvalid = signupInputData.getEmail().split("@").length != 2;
        final boolean passwordIsEmpty = signupInputData.getPassword().isEmpty();

        // Note: repeatedPasswordIsEmpty case is not needed in OR (which was revealed by code coverage).
        // If password is empty, then repeated password is also empty. So, passwordIsEmpty is enough.
        // Otherwise, only empty repeated password will be caught by the passwordsDontMatch check.
        if (passwordsDontMatch || emailIsInvalid || passwordIsEmpty) {
            final boolean repeatedPasswordIsEmpty = signupInputData.getRepeatedPassword().isEmpty();
            validateFields(passwordsDontMatch, emailIsInvalid, passwordIsEmpty, repeatedPasswordIsEmpty);
            return true;
        }
        return false;
    }

    private void validateFields(
            boolean passwordsDontMatch,
            boolean emailIsInvalid,
            boolean passwordIsEmpty,
            boolean repeatedPasswordIsEmpty
    ) {
        if (passwordsDontMatch) {
            signupPresenter.prepareErrorView(REPEATED_PASSWORD_FIELD, "Passwords don't match.");
        }

        if (emailIsInvalid) {
            signupPresenter.prepareErrorView(EMAIL_FIELD, "Invalid email.");
        }
        else {
            signupPresenter.clearError(EMAIL_FIELD);
        }

        if (passwordIsEmpty) {
            signupPresenter.prepareErrorView(PASSWORD_FIELD, "Cannot be empty.");
        }
        else {
            signupPresenter.clearError(PASSWORD_FIELD);
        }

        if (repeatedPasswordIsEmpty) {
            signupPresenter.prepareErrorView(REPEATED_PASSWORD_FIELD, "Cannot be empty.");
        }

        if (!repeatedPasswordIsEmpty && !passwordsDontMatch) {
            signupPresenter.clearError(REPEATED_PASSWORD_FIELD);
        }
    }
}

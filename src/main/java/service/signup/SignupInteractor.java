package service.signup;

import java.io.IOException;

import exception.AuthException;
import repository.RepositoryProvider;
import repository.UserRepository;

/**
 * The signup interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";
    private static final String REPEATED_PASSWORD_FIELD = "repeatedPassword";

    private final RepositoryProvider repositoryProvider;
    private final UserRepository remoteUserRepository;
    private final SignupOutputBoundary signupPresenter;

    public SignupInteractor(
            RepositoryProvider repositoryProvider,
            UserRepository remoteUserRepository,
            SignupOutputBoundary outputPresenter
    ) {
        this.repositoryProvider = repositoryProvider;
        this.remoteUserRepository = remoteUserRepository;
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
            this.remoteUserRepository.signupUser(
                    signupInputData.getEmail(),
                    signupInputData.getPassword()
            );
            repositoryProvider.setRepository(remoteUserRepository);
            signupPresenter.prepareSuccessView(new SignupOutputData(remoteUserRepository));
        }
        catch (AuthException authException) {
            signupPresenter.prepareErrorView(authException.getViewMessage());
        }
        catch (IOException ioException) {
            signupPresenter.prepareErrorView(ioException.getMessage());
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

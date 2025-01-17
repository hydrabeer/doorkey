package service.login;

import java.io.IOException;

import exception.AuthException;
import repository.RepositoryProvider;
import repository.UserRepository;

/**
 * The login interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";

    private final RepositoryProvider repositoryProvider;
    private final UserRepository remoteLoginUserRepository;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(
            RepositoryProvider repositoryProvider,
            UserRepository remoteLoginUserRepository,
            LoginOutputBoundary outputPresenter
    ) {
        this.repositoryProvider = repositoryProvider;
        this.remoteLoginUserRepository = remoteLoginUserRepository;
        this.loginPresenter = outputPresenter;
    }

    @Override
    public void login(LoginInputData loginInputData) {
        // First, we have to clear the error states because we have to
        // re-detect the current errors that might occur.
        // Validate email: If the input isn't a valid email, just present an error notifying them.
        final boolean emailIsInvalid = loginInputData.getEmail().split("@").length != 2;
        final boolean passwordIsEmpty = loginInputData.getPassword().isEmpty();

        if (emailIsInvalid || passwordIsEmpty) {
            if (emailIsInvalid) {
                loginPresenter.prepareErrorView(EMAIL_FIELD, "Invalid email.");
            }
            else {
                loginPresenter.clearError(EMAIL_FIELD);
            }
            if (passwordIsEmpty) {
                loginPresenter.prepareErrorView(PASSWORD_FIELD, "Cannot be empty.");
            }
            else {
                loginPresenter.clearError(PASSWORD_FIELD);
            }
            return;
        }

        loginPresenter.clearError(EMAIL_FIELD);
        loginPresenter.clearError(PASSWORD_FIELD);

        try {
            this.remoteLoginUserRepository.signInUser(
                    loginInputData.getEmail(),
                    loginInputData.getPassword()
            );
            repositoryProvider.setRepository(remoteLoginUserRepository);
            loginPresenter.prepareSuccessView(new LoginOutputData(remoteLoginUserRepository));
        }
        catch (AuthException authException) {
            loginPresenter.prepareErrorView(authException.getViewMessage());
        }
        catch (IOException ioException) {
            loginPresenter.prepareErrorView(ioException.getMessage());
        }
    }
}

package service.login;

/**
 * The login interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginOutputBoundary outputPresenter) {
        this.loginPresenter = outputPresenter;
    }

    @Override
    public void login(LoginInputData loginInputData) {
        // TODO: Auth logic
        if (1 + 1 == 2 /* how surprising */) {
            LoginOutputData outputData = new LoginOutputData(
                    loginInputData.getEmail(),
                    loginInputData.getPassword()
            );
            loginPresenter.prepareSuccessView(outputData);
        }
    }

    @Override
    public void switchToHomeView(String email, String password) {
        loginPresenter.switchToHomeView(email, password);
    }
}

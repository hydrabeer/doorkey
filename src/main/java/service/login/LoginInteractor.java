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
        final LoginOutputData outputData = new LoginOutputData(
                loginInputData.getEmail(),
                loginInputData.getPassword()
        );

        // TODO: Auth logic
        if (loginInputData.getPassword().equals("password")) {
            loginPresenter.prepareSuccessView(outputData);
        }
        else {
            loginPresenter.prepareErrorView("Invalid password.");
        }
    }
}

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
        // if (true) {
        final LoginOutputData outputData = new LoginOutputData(
                loginInputData.getEmail(),
                loginInputData.getPassword()
        );
        loginPresenter.prepareSuccessView(outputData);
        // }
    }

    @Override
    public void switchToHomeView(String email, String password) {
        loginPresenter.switchToHomeView(email, password);
    }

    @Override
    public void switchToLocalVaultView() {
        loginPresenter.switchToLocalVaultView();
    }
}

package service.login.interface_adapter;

import service.login.LoginOutputBoundary;
import service.login.LoginOutputData;
import views.TestView;
import views.ViewConstants;
import views.ViewManager;

/**
 * Presents the login view model.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManager viewManager;

    public LoginPresenter(LoginViewModel loginViewModel, ViewManager viewManager) {
        this.loginViewModel = loginViewModel;
        this.viewManager = viewManager;
    }

    @Override
    public void prepareSuccessView(LoginOutputData loginOutputData) {
        LoginState loginState = new LoginState(
                loginOutputData.getEmail(),
                loginOutputData.getPassword(),
                true
        );
        loginViewModel.setState(loginState);
    }

    @Override
    public void prepareErrorView(String error) {
        // TODO
    }

    @Override
    public void switchToHomeView(String email, String password) {
        TestView testView = new TestView(email, password, viewManager);
        viewManager.addView(testView);
        viewManager.showView(ViewConstants.TEST_VIEW);
    }
}

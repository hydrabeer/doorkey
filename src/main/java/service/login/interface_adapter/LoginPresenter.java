package service.login.interface_adapter;

import service.ViewManagerModel;
import service.login.LoginOutputBoundary;
import service.login.LoginOutputData;
import views.TestViewModel;
import views.TestViewState;
import views.ViewConstants;

/**
 * Presents the login view model.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final TestViewModel testViewModel;

    public LoginPresenter(
            LoginViewModel loginViewModel,
            TestViewModel testViewModel,
            ViewManagerModel viewManagerModel
    ) {
        this.loginViewModel = loginViewModel;
        this.testViewModel = testViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData loginOutputData) {
        final TestViewState currentTestViewState = testViewModel.getState();
        currentTestViewState.setEmail(loginOutputData.getEmail());
        currentTestViewState.setPassword(loginOutputData.getPassword());
        testViewModel.setState(currentTestViewState);
        testViewModel.onStateChanged();

        this.viewManagerModel.setState(ViewConstants.TEST_VIEW);
        this.viewManagerModel.onStateChanged();
    }

    @Override
    public void prepareErrorView(String error) {
        final LoginState loginState = loginViewModel.getState();
        // TODO: More error messages? Optional
        loginState.setIsSuccess(false);
        loginViewModel.onStateChanged();
    }
}

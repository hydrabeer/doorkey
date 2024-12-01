package service.login.interface_adapter;

import service.ViewManagerModel;
import service.home.interface_adapter.HomeState;
import service.home.interface_adapter.HomeViewModel;
import service.login.LoginOutputBoundary;
import service.login.LoginOutputData;
import views.ViewConstants;

/**
 * Presents the login view model.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final HomeViewModel homeViewModel;

    public LoginPresenter(
            LoginViewModel loginViewModel,
            HomeViewModel homeViewModel,
            ViewManagerModel viewManagerModel
    ) {
        this.loginViewModel = loginViewModel;
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData loginOutputData) {
        final HomeState currentHomeViewState = homeViewModel.getState();
        currentHomeViewState.setUser(loginOutputData.getUserRepository().getCurrentUser());
        currentHomeViewState.setUserRepository(loginOutputData.getUserRepository());

        homeViewModel.setState(currentHomeViewState);
        homeViewModel.onStateChanged();

        this.viewManagerModel.setState(ViewConstants.HOME_VIEW);
        this.viewManagerModel.onStateChanged();
    }

    @Override
    public void prepareErrorView(String error) {
        // We have to clear other errors, as current errors will be re-reported.
        final LoginState loginState = loginViewModel.getState();
        loginState.setError(error);
        loginViewModel.onStateChanged();
    }

    @Override
    public void prepareErrorView(String field, String error) {
        // Again, we have to clear all current errors.
        final LoginState loginState = loginViewModel.getState();
        loginState.setFieldError(field, error);
        loginViewModel.onStateChanged();
    }

    @Override
    public void clearError(String field) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setFieldError(field, "");
        loginViewModel.onStateChanged();
    }
}

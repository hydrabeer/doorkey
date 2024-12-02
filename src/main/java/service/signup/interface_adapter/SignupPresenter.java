package service.signup.interface_adapter;

import service.ViewManagerModel;
import service.home.interface_adapter.HomeState;
import service.home.interface_adapter.HomeViewModel;
import service.signup.SignupOutputBoundary;
import service.signup.SignupOutputData;
import views.ViewConstants;

/**
 * Presents the signup view model.
 */
public class SignupPresenter implements SignupOutputBoundary {
    private final SignupViewModel signupViewModel;
    private final HomeViewModel homeViewModel;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(
            SignupViewModel signupViewModel,
            HomeViewModel homeViewModel,
            ViewManagerModel viewManagerModel
    ) {
        this.signupViewModel = signupViewModel;
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData signupOutputData) {
        final HomeState currentHomeViewState = homeViewModel.getState();
        currentHomeViewState.setUser(signupOutputData.getUserRepository().getCurrentUser());
        currentHomeViewState.setUserRepository(signupOutputData.getUserRepository());

        homeViewModel.setState(currentHomeViewState);
        homeViewModel.onStateChanged();

        this.viewManagerModel.setState(ViewConstants.HOME_VIEW);
        this.viewManagerModel.onStateChanged();
    }

    @Override
    public void prepareErrorView(String error) {
        final SignupState signupState = new SignupState(
                signupViewModel.getState().getEmail(),
                signupViewModel.getState().getPassword(),
                signupViewModel.getState().getRepeatedPassword(),
                error,
                false
        );
        signupViewModel.setState(signupState);
        signupViewModel.onStateChanged();
    }

    @Override
    public void prepareErrorView(String field, String error) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setFieldError(field, error);
        signupViewModel.setState(signupState);
        signupViewModel.onStateChanged();
    }

    @Override
    public void clearError(String field) {
        final SignupState signupState = signupViewModel.getState();
        signupState.setFieldError(field, "");
        signupViewModel.setState(signupState);
        signupViewModel.onStateChanged();
    }
}

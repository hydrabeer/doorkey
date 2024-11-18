package service.login.interface_adapter;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * The login view model.
 */
public class LoginViewModel extends ViewModel<LoginState> {
    private LoginController loginController;

    public LoginViewModel() {
        super(ViewConstants.LOGIN_VIEW);
        setState(new LoginState());
    }

    @Override
    protected void onStateChanged() {
        if (state.getIsSuccess()) {
            loginController.switchToHomeView(state.getEmail(), state.getPassword());
        }
        // TODO: Other Navigation
    }

    public void setLoginInteractor(LoginController newLoginController) {
        this.loginController = newLoginController;
    }
}

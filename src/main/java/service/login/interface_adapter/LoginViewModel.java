package service.login.interface_adapter;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * The login view model.
 */
public class LoginViewModel extends ViewModel<LoginState> {
    public LoginViewModel() {
        super(ViewConstants.LOGIN_VIEW);
        setState(new LoginState());
    }
}

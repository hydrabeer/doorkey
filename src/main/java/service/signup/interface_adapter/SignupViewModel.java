package service.signup.interface_adapter;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * The sign up view model.
 */
public class SignupViewModel extends ViewModel<SignupState> {
    public SignupViewModel() {
        super(ViewConstants.SIGNUP_VIEW);
        setState(new SignupState());
    }
}

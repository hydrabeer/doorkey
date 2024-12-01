package service.validate_url.interface_adapter;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * View model for validating URLs.
 */
public class ValidateUrlViewModel extends ViewModel<ValidateUrlState> {

    public ValidateUrlViewModel() {
        super(ViewConstants.VALIDATE_URL_VIEW);
        setState(new ValidateUrlState());
    }
}

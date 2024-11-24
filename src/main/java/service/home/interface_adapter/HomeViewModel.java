package service.home.interface_adapter;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * The home view model.
 */
public class HomeViewModel extends ViewModel<HomeState> {
    public HomeViewModel() {
        super(ViewConstants.HOME_VIEW);
        setState(new HomeState());
    }
}

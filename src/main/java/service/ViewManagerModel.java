package service;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * Model for the view manager with the view name.
 * Listens to changes in the view name.
 */
public class ViewManagerModel extends ViewModel<String> {
    public ViewManagerModel() {
        super("ViewManager");
        setState(ViewConstants.LOGIN_VIEW);
    }
}

package service.local.load.interface_adapter;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * The load local vault view model.
 */
public class LoadLocalVaultViewModel extends ViewModel<LoadLocalVaultState> {
    public LoadLocalVaultViewModel() {
        super(ViewConstants.LOAD_LOCAL_VAULT_VIEW);
        setState(new LoadLocalVaultState());
    }
}

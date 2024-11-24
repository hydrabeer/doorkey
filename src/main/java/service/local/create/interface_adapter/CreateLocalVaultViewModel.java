package service.local.create.interface_adapter;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * The create local vault view model.
 */
public class CreateLocalVaultViewModel extends ViewModel<CreateLocalVaultState> {
    public CreateLocalVaultViewModel() {
        super(ViewConstants.CREATE_LOCAL_VAULT_VIEW);
        setState(new CreateLocalVaultState());
    }
}

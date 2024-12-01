package service.create_vault_item.interface_adapter;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * The CreateVaultItem view model.
 */
public class CreateVaultItemViewModel extends ViewModel<CreateVaultItemState> {
    public CreateVaultItemViewModel() {
        super(ViewConstants.CREATE_VAULT_ITEM_VIEW);
        setState(new CreateVaultItemState());
    }
}

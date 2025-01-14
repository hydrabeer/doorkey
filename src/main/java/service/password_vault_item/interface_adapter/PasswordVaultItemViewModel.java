package service.password_vault_item.interface_adapter;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * The password vault item view model.
 */
public class PasswordVaultItemViewModel extends ViewModel<PasswordVaultItemState> {
    public PasswordVaultItemViewModel() {
        super(ViewConstants.PASSWORD_VAULT_ITEM_VIEW);
        setState(new PasswordVaultItemState());
    }
}

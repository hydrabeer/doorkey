package views;

import interface_adapter.ViewModel;

/**
 * The password vault item view model.
 */
public class PasswordVaultItemViewModel extends ViewModel<PasswordVaultItemState> {
    public PasswordVaultItemViewModel() {
        super(ViewConstants.PASSWORD_VAULT_ITEM_VIEW);
        setState(new PasswordVaultItemState());
    }
}

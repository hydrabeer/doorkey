package views;

import entity.AbstractVaultItem;
import entity.PasswordVaultItem;
import interface_adapter.ViewModel;
import java.util.Optional;

public class PasswordVaultItemViewModel extends ViewModel<PasswordVaultItemState> {
    public PasswordVaultItemViewModel() {
        super(ViewConstants.PASSWORD_VAULT_ITEM_VIEW);
        setState(new PasswordVaultItemState());
    }
}

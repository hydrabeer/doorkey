package views;

import entity.PasswordVaultItem;
import interface_adapter.ViewModel;

public class PasswordVaultItemViewModel extends ViewModel<PasswordVaultItemState> {
    public PasswordVaultItemViewModel(PasswordVaultItem passwordVaultItem) {
        super(ViewConstants.TEST_VIEW);
        setState(new PasswordVaultItemState(passwordVaultItem));
    }
}

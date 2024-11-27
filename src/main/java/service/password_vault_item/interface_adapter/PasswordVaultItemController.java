package service.password_vault_item.interface_adapter;

import service.password_vault_item.PasswordVaultItemInputBoundary;

/**
 * Controller class calls interactor methods.
 * Accessed by PasswordVaultItemView.
 */
public class PasswordVaultItemController {
    private PasswordVaultItemInputBoundary vaultItemInteractor;

    public PasswordVaultItemController(PasswordVaultItemInputBoundary vaultItemInteractor) {
        this.vaultItemInteractor = vaultItemInteractor;

    }

    /**
     * Calls interactor to display home view.
     */
    public void displayHomeView() {
        vaultItemInteractor.displayHomeView();
    }
}

package service.password_vault_item.interface_adapter;

import java.io.IOException;

import entity.PasswordVaultItem;
import exception.AuthException;
import service.password_vault_item.PasswordVaultItemInputBoundary;
import service.password_vault_item.PasswordVaultItemInputData;

/**
 * Controller class calls interactor methods.
 * Accessed by PasswordVaultItemView.
 */
public class PasswordVaultItemController {
    private final PasswordVaultItemInputBoundary vaultItemInteractor;

    public PasswordVaultItemController(PasswordVaultItemInputBoundary vaultItemInteractor) {
        this.vaultItemInteractor = vaultItemInteractor;

    }

    /**
     * Calls interactor to display home view.
     */
    public void displayHomeView() {
        vaultItemInteractor.displayHomeView();
    }

    /**
     * Calls interactor to delete item.
     * @param item vault item to be deleted.
     * @throws AuthException if no authorization.
     * @throws IOException if there is an error.
     */
    public void deleteItem(PasswordVaultItem item) throws AuthException, IOException {
        final PasswordVaultItemInputData passwordVaultItemInputData = new PasswordVaultItemInputData(item);
        vaultItemInteractor.deleteItem(passwordVaultItemInputData);
    }

    /**
     * Calls interactor to display delete message.
     */
    public void displayDeleteMessage() {
        vaultItemInteractor.displayDeleteMessage();
    }
}


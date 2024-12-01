package service.password_vault_item;

import java.io.IOException;

import exception.AuthException;

/**
 * PasswordVaultItem Input Boundary.
 */
public interface PasswordVaultItemInputBoundary {

    /**
     * Display the home view.
     */
    void displayHomeView();

    /**
     * Deletes item.
     * @param passwordVaultItemInputData input data object.
     * @throws AuthException if no authorization.
     * @throws IOException if there is an error.
     */
    void deleteItem(PasswordVaultItemInputData passwordVaultItemInputData) throws AuthException, IOException;

    /**
     * Display the delete message.
     */
    void displayDeleteMessage();
}


package service.password_vault_item;

/**
 * PasswordVaultItem Output Boundary.
 */
public interface PasswordVaultItemOutputBoundary {

    /**
     * Displays the home view.
     */
    void displayHomeView();

    /**
     * Updates after the delete item view.
     */
    void updateDeleteItem();

    /**
     * Displays the delete view.
     */
    void displayDeleteMessage();
}


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
     * Displays the delete view.
     *
     * @param outputData output data object
     */
    void deleteItem(PasswordVaultItemOutputData outputData);

    /**
     * Displays the delete view.
     */
    void displayDeleteMessage();
}


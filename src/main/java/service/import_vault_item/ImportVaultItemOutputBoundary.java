package service.import_vault_item;

/**
 * The import vault item output boundary.
 */
public interface ImportVaultItemOutputBoundary {
    /**
     * Display the home view.
     */
    void displayHomeView();

    /**
     * Display an error message.
     * @param error The error message to display.
     */
    void displayError(String error);
}

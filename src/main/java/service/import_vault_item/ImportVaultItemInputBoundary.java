package service.import_vault_item;

/**
 * The import vault item input boundary.
 */
public interface ImportVaultItemInputBoundary {
    /**
     * Import a vault item.
     * @param importVaultItemInputData The import vault item input data.
     */
    void importVaultItems(ImportVaultItemInputData importVaultItemInputData);

    /**
    * Display the home view.
    */
    void displayHomeView();
}

package service.create_vault_item;

/**
 * The CreateVaultItem Input Boundary.
 */
public interface CreateVaultItemInputBoundary {

    /**
     * The CreateVaultItem request model createVaultItem method.
     *
     * @param requestModel the request model for Create Vault Item usecase
     */
    void createVaultItem(CreateVaultItemRequestModel requestModel);

    /**
     * Display the home view.
     */
    void displayHomeView();
}

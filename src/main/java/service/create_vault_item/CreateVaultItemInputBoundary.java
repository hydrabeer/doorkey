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
     * Cancel the create vault item use case.
     */
    void cancel();

    /**
     * Display the home view.
     */
    void displayHomeView();
}

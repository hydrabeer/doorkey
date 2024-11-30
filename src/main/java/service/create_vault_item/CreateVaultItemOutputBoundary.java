package service.create_vault_item;

/**
 * The CreateVaultItem Output Boundary.
 */
public interface CreateVaultItemOutputBoundary {

    /**
     * The CreateVaultItem response model presenter method.
     *
     * @param responseModel the response model for Create Vault Item usecase
     */
    void presentCreateVaultItemResponse(CreateVaultItemResponseModel responseModel);

    /**
     * Displays the Home view.
     */
    void displayHomeView();
}

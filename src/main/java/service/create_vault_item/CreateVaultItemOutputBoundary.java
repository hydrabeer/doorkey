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
     * Cancels the creation of a vault item.
     */
    void cancel();

    /**
     * Displays the Home view.
     */
    void displayHomeView();

    /**
     * Displays an error message.
     * @param errorMessage the error message
     */
    void displayErrorMessage(String errorMessage);
}

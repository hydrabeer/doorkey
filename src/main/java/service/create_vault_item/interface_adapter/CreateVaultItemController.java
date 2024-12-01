package service.create_vault_item.interface_adapter;

import service.create_vault_item.CreateVaultItemInputBoundary;
import service.create_vault_item.CreateVaultItemRequestModel;

/**
 * The CreateVaultItem Controller.
 */
public class CreateVaultItemController {
    private final CreateVaultItemInputBoundary interactor;

    public CreateVaultItemController(CreateVaultItemInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * The CreateVaultItem controller createVaultItem method.
     *
     * @param url the url for the Vault item we are creating.
     * @param vaultTitle the title for the Vault item we are creating.
     * @param username the username for the Vault item we are creating.
     * @param password the password for the Vault item we are creating.
     * @param repeatedPassword the repeated password.
     */
    public void createVaultItem(
        String url,
        String vaultTitle,
        String username,
        String password,
        String repeatedPassword
    ) {
        final CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            url,
            vaultTitle,
            username,
            password,
            repeatedPassword
        );
        interactor.createVaultItem(requestModel);
    }

    /**
     * Cancel the creation of the Vault item.
     */
    public void cancel() {
        interactor.cancel();
    }

    /**
     * Display CreateVaultItem view.
     */
    public void displayHomeView() {
        interactor.displayHomeView();
    }
}

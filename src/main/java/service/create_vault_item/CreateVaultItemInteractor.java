package service.create_vault_item;

import java.io.IOException;

import entity.AbstractUser;
import entity.PasswordVaultItem;
import exception.AuthException;
import repository.UserRepository;

/**
 * The CreateVaultItem Interactor.
 */
public class CreateVaultItemInteractor implements CreateVaultItemInputBoundary {
    private final CreateVaultItemOutputBoundary presenter;

    public CreateVaultItemInteractor(CreateVaultItemOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void createVaultItem(CreateVaultItemRequestModel requestModel) {
        final String password = requestModel.getPassword();
        final String repeatedPassword = requestModel.getRepeatedPassword();
        final boolean isPasswordValid = password.equals(repeatedPassword);

        if (
            !isPasswordValid
        ) {
            presenter.displayErrorMessage("Passwords must match");
            return;
        }

        final AbstractUser user = requestModel.getUser();
        final UserRepository userRepository = requestModel.getUserRepository();
        
        try {
            final PasswordVaultItem vaultItem = new PasswordVaultItem(
                    requestModel.getVaultTitle(),
                    requestModel.getUsername(),
                    requestModel.getPassword(),
                    requestModel.getUrl()
            );

            userRepository.addVaultItem(user, vaultItem);

            final CreateVaultItemResponseModel responseModel = new CreateVaultItemResponseModel(
                true,
                "Vault item created successfully"
            );

            presenter.presentCreateVaultItemResponse(responseModel);
        }
        catch (AuthException | IOException exception) { 
            presenter.displayErrorMessage("Error occured saving vault item");
        }
    }

    @Override
    public void cancel() {
        presenter.cancel();
    }

    @Override
    public void displayHomeView() {
        presenter.displayHomeView();
    }
}


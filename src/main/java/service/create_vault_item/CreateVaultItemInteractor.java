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
    private final UserRepository userRepository;
    private final AbstractUser user;

    public CreateVaultItemInteractor(
            CreateVaultItemOutputBoundary presenter,
            UserRepository userRepository,
            AbstractUser user) {
        this.presenter = presenter;
        this.userRepository = userRepository;
        this.user = user;
    }

    @Override
    public void createVaultItem(CreateVaultItemRequestModel requestModel) {
        try {
            final PasswordVaultItem vaultItem = new PasswordVaultItem(
                    requestModel.getVaultTitle(),
                    requestModel.getUsername(),
                    requestModel.getPassword(),
                    requestModel.getUrl()
            );

            userRepository.addVaultItem(user, vaultItem);
            final CreateVaultItemResponseModel responseModel =
                    new CreateVaultItemResponseModel(true, "Vault item created successfully");
            presenter.presentCreateVaultItemResponse(responseModel);
        }
        catch (AuthException | IOException ex) { 
            // This should never run
        }
    }

    @Override
    public void displayHomeView() {
        presenter.displayHomeView();
    }
}


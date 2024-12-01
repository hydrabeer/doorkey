package service.create_vault_item;

import java.io.IOException;

import entity.PasswordVaultItem;
import exception.AuthException;
import repository.RepositoryProvider;
import repository.UserRepository;

/**
 * The CreateVaultItem Interactor.
 */
public class CreateVaultItemInteractor implements CreateVaultItemInputBoundary {
    private final RepositoryProvider repositoryProvider;
    private final CreateVaultItemOutputBoundary presenter;

    public CreateVaultItemInteractor(
        RepositoryProvider repositoryProvider,
        CreateVaultItemOutputBoundary presenter
    ) {
        this.repositoryProvider = repositoryProvider;
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

        // Why safe: at this point, user must've already logged in.
        final UserRepository userRepository = repositoryProvider.getRepositoryUnchecked();
        
        try {
            final PasswordVaultItem vaultItem = new PasswordVaultItem(
                    requestModel.getVaultTitle(),
                    requestModel.getUsername(),
                    requestModel.getPassword(),
                    requestModel.getUrl()
            );

            userRepository.addVaultItem(vaultItem);

            final CreateVaultItemResponseModel responseModel = new CreateVaultItemResponseModel(
                true,
                "Vault item created successfully"
            );

            presenter.presentCreateVaultItemResponse(responseModel);
        }
        catch (AuthException | IOException exception) { 
            presenter.displayErrorMessage("Error occurred saving vault item");
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


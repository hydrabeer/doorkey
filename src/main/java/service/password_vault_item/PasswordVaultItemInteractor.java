package service.password_vault_item;

import java.io.IOException;

import exception.AuthException;
import repository.RepositoryProvider;
import repository.UserRepository;

/**
 * The Password Vault item Interactor.
 * Calls Presenter
 */
public class PasswordVaultItemInteractor implements PasswordVaultItemInputBoundary {
    private final RepositoryProvider repositoryProvider;
    private final PasswordVaultItemOutputBoundary passwordVaultItemPresenter;

    public PasswordVaultItemInteractor(
            RepositoryProvider repositoryProvider,
            PasswordVaultItemOutputBoundary passwordVaultItemPresenter
    ) {
        this.repositoryProvider = repositoryProvider;
        this.passwordVaultItemPresenter = passwordVaultItemPresenter;
    }

    @Override
    public void displayHomeView() {
        passwordVaultItemPresenter.displayHomeView();
    }

    @Override
    public void deleteItem(PasswordVaultItemInputData passwordVaultItemInputData) throws AuthException, IOException {
        // Why it is safe: at this point, the user must have already logged in.
        final UserRepository userRepository = repositoryProvider.getRepositoryUnchecked();
        userRepository.removeVaultItem(passwordVaultItemInputData.getPasswordVaultItem());
        passwordVaultItemPresenter.updateDeleteItem();
    }

    @Override
    public void displayDeleteMessage() {
        passwordVaultItemPresenter.displayDeleteMessage();
    }
}

package service.password_vault_item;

import java.io.IOException;

import exception.AuthException;

/**
 * The Password Vault item Interactor.
 * Calls Presenter
 */
public class PasswordVaultItemInteractor implements PasswordVaultItemInputBoundary {
    private final PasswordVaultItemOutputBoundary passwordVaultItemPresenter;

    public PasswordVaultItemInteractor(PasswordVaultItemOutputBoundary passwordVaultItemPresenter) {
        this.passwordVaultItemPresenter = passwordVaultItemPresenter;
    }

    @Override
    public void displayHomeView() {
        passwordVaultItemPresenter.displayHomeView();
    }

    @Override
    public void deleteItem(PasswordVaultItemInputData passwordVaultItemInputData) throws AuthException, IOException {
        final PasswordVaultItemOutputData passwordVaultItemOutputData = new PasswordVaultItemOutputData(
                passwordVaultItemInputData.getUser(), passwordVaultItemInputData.getUserRepository());
        passwordVaultItemInputData.getUserRepository().removeVaultItem(
                passwordVaultItemInputData.getUser(), passwordVaultItemInputData.getPasswordVaultItem());
        passwordVaultItemPresenter.deleteItem(passwordVaultItemOutputData);
    }

    @Override
    public void displayDeleteMessage() {
        passwordVaultItemPresenter.displayDeleteMessage();
    }
}

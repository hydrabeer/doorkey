package service.password_vault_item;

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
    public void displayEditView() {

    }

    @Override
    public void displayDeleteView() {

    }
}

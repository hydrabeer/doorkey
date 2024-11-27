package service.password_vault_item.interface_adapter;

import service.ViewManagerModel;
import service.password_vault_item.PasswordVaultItemOutputBoundary;
import views.ViewConstants;

/**
 * Presenter class calls view model changes that are reflected by View.
 * Accessed by PasswordVaultItemInteractors.
 */
public class PasswordVaultItemPresenter implements PasswordVaultItemOutputBoundary {
    private final PasswordVaultItemViewModel vaultItemViewModel;
    private final ViewManagerModel viewManagerModel;

    public PasswordVaultItemPresenter(PasswordVaultItemViewModel vaultItemViewModel, ViewManagerModel viewManagerModel) {
        this.vaultItemViewModel = vaultItemViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void displayHomeView() {
        this.viewManagerModel.setState(ViewConstants.HOME_VIEW);
        this.viewManagerModel.onStateChanged();
    }

    @Override
    public void displayEditView() {

    }

    @Override
    public void displayDeleteView() {

    }

}

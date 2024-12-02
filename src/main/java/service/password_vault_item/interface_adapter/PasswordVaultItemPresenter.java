package service.password_vault_item.interface_adapter;

import service.ViewManagerModel;
import service.home.interface_adapter.HomeViewModel;
import service.password_vault_item.PasswordVaultItemOutputBoundary;
import views.ViewConstants;

/**
 * Presenter class calls view model changes that are reflected by View.
 * Accessed by PasswordVaultItemInteractors.
 */
public class PasswordVaultItemPresenter implements PasswordVaultItemOutputBoundary {
    private final PasswordVaultItemViewModel vaultItemViewModel;
    private final ViewManagerModel viewManagerModel;
    private final HomeViewModel homeViewModel;

    public PasswordVaultItemPresenter(
            PasswordVaultItemViewModel vaultItemViewModel,
            ViewManagerModel viewManagerModel,
            HomeViewModel homeViewModel
    ) {
        this.vaultItemViewModel = vaultItemViewModel;
        this.viewManagerModel = viewManagerModel;
        this.homeViewModel = homeViewModel;
    }

    @Override
    public void displayHomeView() {
        this.viewManagerModel.setState(ViewConstants.HOME_VIEW);
        this.viewManagerModel.onStateChanged();
    }

    @Override
    public void updateDeleteItem() {
        // Update HomeViewModel state as an item was just deleted.
        this.homeViewModel.onStateChanged();
        this.viewManagerModel.setState(ViewConstants.HOME_VIEW);
        this.viewManagerModel.onStateChanged();
    }

    @Override
    public void displayDeleteMessage() {
        vaultItemViewModel.getState().setMessage(
                "Press delete again to confirm. Press copy button to reset"
        );
    }
}

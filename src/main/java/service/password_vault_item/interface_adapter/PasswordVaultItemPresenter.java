package service.password_vault_item.interface_adapter;

import service.ViewManagerModel;
import service.home.interface_adapter.HomeState;
import service.home.interface_adapter.HomeViewModel;
import service.password_vault_item.PasswordVaultItemOutputBoundary;
import service.password_vault_item.PasswordVaultItemOutputData;
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
            PasswordVaultItemViewModel vaultItemViewModel, ViewManagerModel viewManagerModel,
            HomeViewModel homeViewModel) {
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
    public void deleteItem(PasswordVaultItemOutputData passwordVaultItemOutputData) {
        this.homeViewModel.setState(new HomeState(
                passwordVaultItemOutputData.getUser(), passwordVaultItemOutputData.getUserRepository()));
        this.homeViewModel.onStateChanged();
        this.viewManagerModel.setState(ViewConstants.HOME_VIEW);
        this.viewManagerModel.onStateChanged();
    }

    @Override
    public void displayDeleteMessage() {
        vaultItemViewModel.getState().setMessage(
                "Press delete again to confirm vault item deletion. \n Press either copy button to reset");
    }

}

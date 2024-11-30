package service.home.interface_adapter;

import entity.AbstractVaultItem;
import entity.PasswordVaultItem;
import exception.InvalidVaultItemException;
import service.ViewManagerModel;
import service.home.HomeOutputBoundary;
import service.home.HomeOutputData;
import service.password_vault_item.interface_adapter.PasswordVaultItemState;
import service.password_vault_item.interface_adapter.PasswordVaultItemViewModel;
import views.ViewConstants;

/**
 * Presents the home view.
 */
public class HomePresenter implements HomeOutputBoundary {

    private final HomeViewModel homeViewModel;
    private final PasswordVaultItemViewModel passwordVaultItemViewModel;
    private final ViewManagerModel viewManagerModel;

    public HomePresenter(
            HomeViewModel homeViewModel,
            PasswordVaultItemViewModel passwordVaultItemViewModel,
            // note vault item view model
            ViewManagerModel viewManagerModel
    ) {
        this.homeViewModel = homeViewModel;
        this.passwordVaultItemViewModel = passwordVaultItemViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareShowVaultView(HomeOutputData homeOutputData) throws InvalidVaultItemException {
        final AbstractVaultItem item = homeOutputData.getVaultItem();
        switch (item.getType()) {
            case "passwordItem" -> {
                this.passwordVaultItemViewModel.setState(new PasswordVaultItemState((PasswordVaultItem) item));
                this.passwordVaultItemViewModel.onStateChanged();
                this.viewManagerModel.setState(ViewConstants.PASSWORD_VAULT_ITEM_VIEW);
                this.viewManagerModel.onStateChanged();
            }
            //            case "noteItem" -> {
            //                noteVaultItemViewModel.setState(
            //                        new NoteVaultItemState((NoteVaultItem) item)
            //                );
            //                noteVaultItemViewModel.onStateChanged();
            //            }
            default ->
                throw new InvalidVaultItemException("Unhandled vault item type");
        }
    }

    @Override
    public void displayLoginView() {
        homeViewModel.setState(new HomeState());
        homeViewModel.onStateChanged();
        this.viewManagerModel.setState(ViewConstants.LOGIN_VIEW);
        this.viewManagerModel.onStateChanged();
    }

    @Override
    public void displayCreateVaultItemView() {
        homeViewModel.setState(new HomeState());
        homeViewModel.onStateChanged();
        this.viewManagerModel.setState(ViewConstants.CREATE_VAULT_ITEM_VIEW);
        this.viewManagerModel.onStateChanged();
    }
}

package service.home.interface_adapter;

import entity.AbstractVaultItem;
import entity.NoteVaultItem;
import entity.PasswordVaultItem;
import service.ViewManagerModel;
import service.home.HomeOutputBoundary;
import service.home.HomeOutputData;
import views.PasswordVaultItemState;
import views.PasswordVaultItemViewModel;
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
    public void prepareShowVaultView(HomeOutputData homeOutputData) {
        final AbstractVaultItem item = homeOutputData.getVaultItem();
        switch (item.getType()) {
            case "passwordItem" -> {
                passwordVaultItemViewModel.setState(new PasswordVaultItemState((PasswordVaultItem) item));
                passwordVaultItemViewModel.onStateChanged();
                this.viewManagerModel.setState(ViewConstants.PASSWORD_VAULT_ITEM_VIEW);
                this.viewManagerModel.onStateChanged();
            }
//            case "noteItem" -> {
//                noteVaultItemViewModel.setState(
//                        new NoteVaultItemState((NoteVaultItem) item)
//                );
//                noteVaultItemViewModel.onStateChanged();
//            }
        }
    }
}

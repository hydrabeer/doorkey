package service.import_vault_item.interface_adapter;

import service.ViewManagerModel;
import service.home.interface_adapter.HomeViewModel;
import service.import_vault_item.ImportVaultItemOutputBoundary;
import views.ViewConstants;

/**
 * The import vault item presenter.
 */
public class ImportVaultItemPresenter implements ImportVaultItemOutputBoundary {
    private final ImportVaultItemViewModel importVaultItemViewModel;
    private final HomeViewModel homeViewModel;
    private final ViewManagerModel viewManagerModel;

    public ImportVaultItemPresenter(
            ImportVaultItemViewModel importVaultItemViewModel,
            HomeViewModel homeViewModel,
            ViewManagerModel viewManagerModel
    ) {
        this.importVaultItemViewModel = importVaultItemViewModel;
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void displayHomeView() {
        // We're calling homeViewModel.onStateChanged() regardless since
        // import is likely to create an update. If there were no updates, this doesn't have any effect.
        homeViewModel.onStateChanged();
        viewManagerModel.setState(ViewConstants.HOME_VIEW);
        viewManagerModel.onStateChanged();
    }

    @Override
    public void displayError(String message) {
        importVaultItemViewModel.setState(message);
        importVaultItemViewModel.onStateChanged();
    }
}

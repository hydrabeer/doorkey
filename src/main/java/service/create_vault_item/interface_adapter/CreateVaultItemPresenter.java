package service.create_vault_item.interface_adapter;

import service.ViewManagerModel;
import service.create_vault_item.CreateVaultItemOutputBoundary;
import service.create_vault_item.CreateVaultItemResponseModel;
import views.ViewConstants;

/**
 * The CreateVaultItem Presenter.
 */
public class CreateVaultItemPresenter implements CreateVaultItemOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final CreateVaultItemViewModel viewModel;

    public CreateVaultItemPresenter(CreateVaultItemViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewModel = viewModel;
    }

    @Override
    public void presentCreateVaultItemResponse(CreateVaultItemResponseModel responseModel) {
        if (responseModel.isSuccess()) {
            // Navigate back to the previous view or update the view as needed
            viewManagerModel.setState(ViewConstants.HOME_VIEW);
            viewManagerModel.onStateChanged();
        }
        else {
            // Potential Error Update
        }
    }

    @Override
    public void displayHomeView() {
        viewModel.setState(new CreateVaultItemState());
        viewModel.onStateChanged();
        this.viewManagerModel.setState(ViewConstants.HOME_VIEW);
        this.viewManagerModel.onStateChanged();
    }
}

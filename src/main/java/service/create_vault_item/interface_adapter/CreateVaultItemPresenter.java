package service.create_vault_item.interface_adapter;

import service.ViewManagerModel;
import service.create_vault_item.CreateVaultItemOutputBoundary;
import service.create_vault_item.CreateVaultItemResponseModel;
import service.home.interface_adapter.HomeViewModel;
import views.ViewConstants;

/**
 * Presenter class for CreateVaultItem use case.
 */
public class CreateVaultItemPresenter implements CreateVaultItemOutputBoundary {
    private final CreateVaultItemViewModel viewModel;
    private final HomeViewModel homeViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateVaultItemPresenter(
        CreateVaultItemViewModel viewModel,
        HomeViewModel homeViewModel,
        ViewManagerModel viewManagerModel
    ) {
        this.viewModel = viewModel;
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentCreateVaultItemResponse(CreateVaultItemResponseModel responseModel) {
        if (responseModel.isSuccess()) {
            viewModel.getState().setErrorMessage("");
            viewModel.getState().setSuccessMessage(responseModel.getMessage());
            viewModel.getState().setClearFields(true);
            viewModel.onStateChanged();
            viewModel.getState().setClearFields(false);

            // Fire home view state change as a new vault item was just added
            homeViewModel.onStateChanged();
            displayHomeView();
        }
        else {
            viewModel.getState().setErrorMessage(responseModel.getMessage());
            viewModel.getState().setSuccessMessage("");
            viewModel.onStateChanged();
        }
    }

    @Override
    public void displayErrorMessage(String error) {
        viewModel.getState().setErrorMessage(error);
        viewModel.getState().setSuccessMessage("");
        viewModel.onStateChanged();
    }

    @Override
    public void cancel() {
        viewModel.getState().setClearFields(true);
        viewModel.getState().setErrorMessage("");
        viewModel.getState().setSuccessMessage("");
        viewModel.onStateChanged();
        viewModel.getState().setClearFields(false);
        displayHomeView();
    }

    @Override
    public void displayHomeView() {
        viewModel.getState().setSuccessMessage("");
        viewModel.getState().setErrorMessage("");
        viewModel.getState().setClearFields(true);
        viewModel.onStateChanged();
        viewModel.getState().setClearFields(false);
        viewManagerModel.setState(ViewConstants.HOME_VIEW);
        viewManagerModel.onStateChanged();
    }
}

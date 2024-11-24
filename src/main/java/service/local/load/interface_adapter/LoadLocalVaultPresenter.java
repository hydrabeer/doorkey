package service.local.load.interface_adapter;

import service.ViewManagerModel;
import service.local.load.LoadLocalVaultOutputBoundary;
import service.local.load.LoadLocalVaultOutputData;

/**
 * Presents the load local vault view model.
 */
public class LoadLocalVaultPresenter implements LoadLocalVaultOutputBoundary {
    private final LoadLocalVaultViewModel loadLocalVaultViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoadLocalVaultPresenter(
        LoadLocalVaultViewModel loadLocalVaultViewModel,
        ViewManagerModel viewManagerModel
    ) {
        this.loadLocalVaultViewModel = loadLocalVaultViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LoadLocalVaultOutputData loadLocalVaultOutputData) {
        System.out.println("Got data:" + loadLocalVaultOutputData.getVaultAsString());
        // TODO @Evan: go to main view
        System.out.println("TODO: GO TO MAIN VIEW ONCE IMPL.");
    }

    @Override
    public void prepareErrorView(String error) {
        final LoadLocalVaultState loadLocalVaultState = loadLocalVaultViewModel.getState();
        loadLocalVaultState.setSuccess(false);
        loadLocalVaultViewModel.onStateChanged();
    }
    
}

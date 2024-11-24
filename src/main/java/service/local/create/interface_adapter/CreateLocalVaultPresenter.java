package service.local.create.interface_adapter;

import service.ViewManagerModel;
import service.local.create.CreateLocalVaultOutputBoundary;
import service.local.create.CreateLocalVaultOutputData;

/**
 * Presents the create local vault view model.
 */
public class CreateLocalVaultPresenter implements CreateLocalVaultOutputBoundary {
    private final CreateLocalVaultViewModel createLocalVaultViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateLocalVaultPresenter(
        CreateLocalVaultViewModel createLocalVaultViewModel,
        ViewManagerModel viewManagerModel
    ) {
        this.createLocalVaultViewModel = createLocalVaultViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    
    @Override
    public void prepareSuccessView(CreateLocalVaultOutputData createLocalVaultOutputData) {
        System.out.println("Got data:" + createLocalVaultOutputData.getPath());
        // TODO @Evan: go to main view
        System.out.println("TODO: GO TO MAIN VIEW ONCE IMPL.");
    }

    @Override
    public void prepareErrorView(String error) {
        final CreateLocalVaultState createLocalVaultState = createLocalVaultViewModel.getState();
        createLocalVaultState.setSuccess(false);
        createLocalVaultViewModel.onStateChanged();
    }
    
}

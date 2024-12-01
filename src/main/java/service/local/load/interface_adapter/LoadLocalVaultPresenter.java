package service.local.load.interface_adapter;

import repository.UserRepository;
import service.ViewManagerModel;
import service.home.interface_adapter.HomeState;
import service.home.interface_adapter.HomeViewModel;
import service.local.load.LoadLocalVaultOutputBoundary;
import service.local.load.LoadLocalVaultOutputData;
import views.ViewConstants;

/**
 * Presents the load local vault view model.
 */
public class LoadLocalVaultPresenter implements LoadLocalVaultOutputBoundary {
    private final LoadLocalVaultViewModel loadLocalVaultViewModel;
    private final ViewManagerModel viewManagerModel;
    private final HomeViewModel homeViewModel;

    public LoadLocalVaultPresenter(
        LoadLocalVaultViewModel loadLocalVaultViewModel,
        HomeViewModel homeViewModel,
        ViewManagerModel viewManagerModel
    ) {
        this.loadLocalVaultViewModel = loadLocalVaultViewModel;
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LoadLocalVaultOutputData loadLocalVaultOutputData) {
        final UserRepository repository = loadLocalVaultOutputData.getRepository();

        final HomeState currentHomeViewState = homeViewModel.getState();

        currentHomeViewState.setUser(repository.getCurrentUser());
        currentHomeViewState.setUserRepository(repository);

        homeViewModel.setState(currentHomeViewState);
        homeViewModel.onStateChanged();

        this.viewManagerModel.setState(ViewConstants.HOME_VIEW);
        this.viewManagerModel.onStateChanged();
    }

    @Override
    public void prepareErrorView(String error) {
        final LoadLocalVaultState loadLocalVaultState = loadLocalVaultViewModel.getState();
        loadLocalVaultState.setSuccess(false);
        loadLocalVaultViewModel.onStateChanged();
    }
    
}

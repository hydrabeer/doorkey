package service.local.load.interface_adapter;

import data_access.LocalVaultUserDataAccessObject;
import entity.AbstractUser;
import exception.AuthException;
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
        final HomeState currentHomeViewState = homeViewModel.getState();

        UserRepository repository = new LocalVaultUserDataAccessObject();
        try {
            AbstractUser user = repository.signInUser(loadLocalVaultOutputData.getPath(), loadLocalVaultOutputData.getPassword());

            currentHomeViewState.setUser(user);
            currentHomeViewState.setUserRepository(new LocalVaultUserDataAccessObject());

            homeViewModel.setState(currentHomeViewState);
            homeViewModel.onStateChanged();

            this.viewManagerModel.setState(ViewConstants.HOME_VIEW);
            this.viewManagerModel.onStateChanged();
        } catch (AuthException e) {
            prepareErrorView(e.getMessage());
            return;
        }
    }

    @Override
    public void prepareErrorView(String error) {
        final LoadLocalVaultState loadLocalVaultState = loadLocalVaultViewModel.getState();
        loadLocalVaultState.setSuccess(false);
        loadLocalVaultViewModel.onStateChanged();
    }
    
}

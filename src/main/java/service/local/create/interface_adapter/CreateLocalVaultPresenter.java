package service.local.create.interface_adapter;

import data_access.LocalVaultUserDataAccessObject;
import entity.AbstractUser;
import exception.AuthException;
import repository.UserRepository;
import service.ViewManagerModel;
import service.home.interface_adapter.HomeState;
import service.home.interface_adapter.HomeViewModel;
import service.local.create.CreateLocalVaultOutputBoundary;
import service.local.create.CreateLocalVaultOutputData;
import views.ViewConstants;

/**
 * Presents the create local vault view model.
 */
public class CreateLocalVaultPresenter implements CreateLocalVaultOutputBoundary {
    private final CreateLocalVaultViewModel createLocalVaultViewModel;
    private final ViewManagerModel viewManagerModel;
    private final HomeViewModel homeViewModel;

    public CreateLocalVaultPresenter(
        CreateLocalVaultViewModel createLocalVaultViewModel,
        HomeViewModel homeViewModel,
        ViewManagerModel viewManagerModel
    ) {
        this.createLocalVaultViewModel = createLocalVaultViewModel;
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;
    }
    
    @Override
    public void prepareSuccessView(CreateLocalVaultOutputData createLocalVaultOutputData) {
        final HomeState currentHomeViewState = homeViewModel.getState();

        UserRepository repository = new LocalVaultUserDataAccessObject();
        try {
            AbstractUser user = repository.signupUser(createLocalVaultOutputData.getPath(), createLocalVaultOutputData.getPassword());
        
            currentHomeViewState.setUser(user);
            currentHomeViewState.setUserRepository(repository);

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
        final CreateLocalVaultState createLocalVaultState = createLocalVaultViewModel.getState();
        createLocalVaultState.setSuccess(false);
        createLocalVaultViewModel.onStateChanged();
    }
    
}

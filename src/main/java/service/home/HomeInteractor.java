package service.home;

import exception.InvalidVaultItemException;
import repository.RepositoryProvider;

/**
 * The Home interactor.
 */
public class HomeInteractor implements HomeInputBoundary {
    private final RepositoryProvider repositoryProvider;
    private final HomeOutputBoundary homePresenter;

    public HomeInteractor(
            RepositoryProvider repositoryProvider,
            HomeOutputBoundary homePresenter
    ) {
        this.repositoryProvider = repositoryProvider;
        this.homePresenter = homePresenter;
    }

    @Override
    public void displayVaultItem(HomeInputData homeInputData) throws InvalidVaultItemException {
        homePresenter.prepareShowVaultView(
                new HomeOutputData(homeInputData.getChosenVaultItem())
        );
    }

    @Override
    public void signOut() {
        repositoryProvider.getRepositoryUnchecked().signOutUser();
        repositoryProvider.clearRepository();

        homePresenter.displayLoginView();
    }

    @Override
    public void displayCreateVaultItemView() {
        homePresenter.displayCreateVaultItemView();
    }
    
    @Override
    public void displayImportView() {
        homePresenter.displayImportView();
    }
}

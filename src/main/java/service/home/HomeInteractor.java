package service.home;

import exception.InvalidVaultItemException;

/**
 * The Home interactor.
 */
public class HomeInteractor implements HomeInputBoundary {
    private final HomeOutputBoundary homePresenter;

    public HomeInteractor(HomeOutputBoundary homePresenter) {
        this.homePresenter = homePresenter;
    }

    @Override
    public void displayVaultItem(HomeInputData homeInputData) throws InvalidVaultItemException {
        homePresenter.prepareShowVaultView(new HomeOutputData(
                homeInputData.getChosenVaultItem(), homeInputData.getChosenUser(), homeInputData.getUserRepository()));
    }

    @Override
    public void displayLoginView() {
        homePresenter.displayLoginView();
    }

    @Override
    public void displayImportView() {
        homePresenter.displayImportView();
    }
}

package service.home;

/**
 * The Home interactor.
 */
public class HomeInteractor implements HomeInputBoundary {
    private final HomeOutputBoundary homePresenter;

    public HomeInteractor(HomeOutputBoundary homePresenter) {
        this.homePresenter = homePresenter;
    }

    @Override
    public void displayVaultItem(HomeInputData homeInputData) {

    }
}

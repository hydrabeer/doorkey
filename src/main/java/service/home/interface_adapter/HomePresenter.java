package service.home.interface_adapter;

import service.ViewManagerModel;
import service.home.HomeOutputBoundary;
import service.home.HomeOutputData;

/**
 * Presents the home view.
 */
public class HomePresenter implements HomeOutputBoundary {
    private final HomeViewModel homeViewModel;
    private final ViewManagerModel viewManagerModel;

    public HomePresenter(HomeViewModel homeViewModel, ViewManagerModel viewManagerModel) {
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareShowVaultView(HomeOutputData homeOutputData) {
        // TODO
    }
}

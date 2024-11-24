package service.home.interface_adapter;

import entity.AbstractVaultItem;
import service.home.HomeInputBoundary;
import service.home.HomeInputData;

/**
 * The Home controller.
 */
public class HomeController {
    private final HomeInputBoundary interactor;

    public HomeController(HomeInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Display a vault item.
     * @param vaultItem The vault item to display.
     */
    public void displayVaultItem(AbstractVaultItem vaultItem) {
        final HomeInputData homeInputData = new HomeInputData(vaultItem);
        interactor.displayVaultItem(homeInputData);
    }
}

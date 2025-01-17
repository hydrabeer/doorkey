package service.home.interface_adapter;

import entity.AbstractVaultItem;
import exception.InvalidVaultItemException;
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
     *
     * @param vaultItem The vault item to display.
     * @throws InvalidVaultItemException If the vault item is invalid.
     */
    public void displayVaultItem(AbstractVaultItem vaultItem) throws InvalidVaultItemException {
        final HomeInputData homeInputData = new HomeInputData(vaultItem);
        interactor.displayVaultItem(homeInputData);
    }

    /**
     * Display login view.
     */
    public void signOut() {
        interactor.signOut();
    }

    /**
     * Display CreateVaultItem view.
     */
    public void displayCreateVaultItemView() {
        interactor.displayCreateVaultItemView();
    }

    /**
     * Display the import item view.
     */
    public void displayImportView() {
        interactor.displayImportView();
    }
}

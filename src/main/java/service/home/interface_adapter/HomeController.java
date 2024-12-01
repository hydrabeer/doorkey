package service.home.interface_adapter;

import entity.AbstractUser;
import entity.AbstractVaultItem;
import exception.InvalidVaultItemException;
import repository.UserRepository;
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
     * @param user The user of vault item.
     * @param repository The user repository.
     * @throws InvalidVaultItemException If the vault item is invalid.
     */
    public void displayVaultItem(AbstractVaultItem vaultItem,
                                 AbstractUser user, UserRepository repository) throws InvalidVaultItemException {
        final HomeInputData homeInputData = new HomeInputData(vaultItem, user, repository);
        interactor.displayVaultItem(homeInputData);
    }

    /**
     * Display login view.
     */
    public void displayLoginView() {
        interactor.displayLoginView();
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

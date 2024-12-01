package service.home;

import exception.InvalidVaultItemException;

/**
 * The Home use case.
 */
public interface HomeInputBoundary {

    /**
     * Display a vault item.
     *
     * @param homeInputData The home input data.
     * @throws InvalidVaultItemException If the vault item is invalid.
     */
    void displayVaultItem(HomeInputData homeInputData) throws InvalidVaultItemException;

    /**
     * Sign out from the application.
     */
    void signOut();

    /**
     * Display the CreateVaultItem view.
     */
    void displayCreateVaultItemView();

    /**
     * Display the import view.
     */
    void displayImportView();
}

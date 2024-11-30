package service.home;

import exception.InvalidVaultItemException;

/**
 * The Home output boundary.
 */
public interface HomeOutputBoundary {

    /**
     * Prepares the show vault view.
     *
     * @param homeOutputData The home output data.
     * @throws InvalidVaultItemException If the vault item is invalid.
     */
    void prepareShowVaultView(HomeOutputData homeOutputData) throws InvalidVaultItemException;

    /**
     * Displays the login view.
     */
    void displayLoginView();

    /**
     * Displays the CreateVaultItem view.
     */
    void displayCreateVaultItemView();
}

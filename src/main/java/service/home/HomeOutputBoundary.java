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
     */
    void prepareShowVaultView(HomeOutputData homeOutputData) throws InvalidVaultItemException;

    /**
     * Displays the login view.
     */
    void displayLoginView();
}

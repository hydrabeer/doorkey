package service.home;

/**
 * The Home output boundary.
 */
public interface HomeOutputBoundary {
    /**
     * Prepares the show vault view.
     *
     * @param homeOutputData The home output data.
     */
    void prepareShowVaultView(HomeOutputData homeOutputData);

    /**
     * Displays the login view.
     */
    void displayLoginView();
}

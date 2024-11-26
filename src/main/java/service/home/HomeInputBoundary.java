package service.home;

/**
 * The Home use case.
 */
public interface HomeInputBoundary {
    /**
     * Display a vault item.
     * @param homeInputData The home input data.
     */
    void displayVaultItem(HomeInputData homeInputData);

    /**
     * Display the home view.
     */
    void displayHomeView();
}

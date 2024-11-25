package service.local.load;

/**
 * The load local vault output boundary for the view.
 */
public interface LoadLocalVaultOutputBoundary {
    /**
     * Prepares the success view.
     * @param loadLocalVaultOutputData The login output data.
     */
    void prepareSuccessView(LoadLocalVaultOutputData loadLocalVaultOutputData);

    /**
     * Prepares the fail view.
     *
     * @param error The error message.
     */
    void prepareErrorView(String error);
}

package service.local.create;

/**
 * The create local vault output boundary for the view.
 */
public interface CreateLocalVaultOutputBoundary {
    /**
     * Prepares the success view.
     * @param createLocalVaultOutputData The login output data.
     */
    void prepareSuccessView(CreateLocalVaultOutputData createLocalVaultOutputData);

    /**
     * Prepares the fail view.
     *
     * @param error The error message.
     */
    void prepareErrorView(String error);
}

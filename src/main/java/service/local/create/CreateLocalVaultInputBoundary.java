package service.local.create;

/**
 * The create local vault use case.
 */
public interface CreateLocalVaultInputBoundary {
    /**
     * Create a local .doorkey vault.
     * @param createLocalVaultInputData
     */
    void createLocalVault(CreateLocalVaultInputData createLocalVaultInputData);
}

package service.local.create;

/**
 * The create local vault use case.
 */
public interface CreateLocalVaultInputBoundary {
    /**
     * Create a local .doorkey vault.
     * @param createLocalVaultInputData the vault creation input data
     */
    void createLocalVault(CreateLocalVaultInputData createLocalVaultInputData);
}

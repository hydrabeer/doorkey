package service.local.load;

/**
 * The load local vault use case.
 */
public interface LoadLocalVaultInputBoundary {
    /**
     * Load and decrypt the local .doorkey vault.
     * @param loadLocalVaultInputData the load vault input data
     */
    void loadLocalVault(LoadLocalVaultInputData loadLocalVaultInputData);
}

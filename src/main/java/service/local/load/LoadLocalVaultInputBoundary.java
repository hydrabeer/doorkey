package service.local.load;

/**
 * The load local vault use case.
 */
public interface LoadLocalVaultInputBoundary {
    /**
     * Load and decrypt the local .doorkey vault.
     * @param loadLocalVaultInputData
     */
    void loadLocalVault(LoadLocalVaultInputData loadLocalVaultInputData);
}

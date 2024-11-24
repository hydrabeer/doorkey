package service.local.load;

/**
 * Output data from loading a local .doorkey vault.
 */
public class LoadLocalVaultOutputData {
    private final String vaultAsString;

    public LoadLocalVaultOutputData(String vaultAsString) {
        this.vaultAsString = vaultAsString;
    }

    public String getVaultAsString() {
        return vaultAsString;
    }
}

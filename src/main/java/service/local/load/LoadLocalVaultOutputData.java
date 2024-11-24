package service.local.load;

/**
 * Output data from loading a local .doorkey vault.
 */
public class LoadLocalVaultOutputData {
    private final String vaultAsString;
    private final String path;
    private final String password;

    public LoadLocalVaultOutputData(String vaultAsString, String path, String password) {
        this.vaultAsString = vaultAsString;
        this.path = path;
        this.password = password;
    }

    public String getVaultAsString() {
        return vaultAsString;
    }

    public String getPath() {
        return path;
    }

    public String getPassword() {
        return password;
    }
}

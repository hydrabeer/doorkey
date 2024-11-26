package service.local.load;

/**
 * Output data from loading a local .doorkey vault.
 */
public class LoadLocalVaultOutputData {
    private final String path;
    private final String password;

    public LoadLocalVaultOutputData(String path, String password) {
        this.path = path;
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public String getPassword() {
        return password;
    }
}

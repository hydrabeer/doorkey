package service.local.create;

/**
 * Output data from creating a local .doorkey vault.
 */
public class CreateLocalVaultOutputData {
    private final String path;
    private final String password;

    public CreateLocalVaultOutputData(String path, String password) {
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

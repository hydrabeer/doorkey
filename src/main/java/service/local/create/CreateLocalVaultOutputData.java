package service.local.create;

/**
 * Output data from creating a local .doorkey vault.
 */
public class CreateLocalVaultOutputData {
    private final String path;

    public CreateLocalVaultOutputData(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

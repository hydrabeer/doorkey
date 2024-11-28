package entity;

import java.util.List;

/**
 * LocalVault is a vault that stores items in JSON format.
 */
public class LocalVault extends AbstractVault {
    private String path;
    private String password;

    public LocalVault(List<AbstractVaultItem> items) {
        super(items);
    }

    public LocalVault(List<AbstractVaultItem> items, String path, String password) {
        super(items);
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

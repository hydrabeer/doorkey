package service.password_vault_item.interface_adapter;

import java.util.Optional;

import entity.PasswordVaultItem;

/**
 * The state of a password vault item.
 */
public class PasswordVaultItemState {
    private Optional<PasswordVaultItem> vaultItem;
    private String error = "";
    private String message = "";

    public PasswordVaultItemState() {
        this.vaultItem = Optional.empty();
    }

    public PasswordVaultItemState(PasswordVaultItem item) {
        this.vaultItem = Optional.of(item);
    }

    public Optional<PasswordVaultItem> getVaultItem() {
        return vaultItem;
    }

    /**
     * Gets the title of the vault item.
     * @return The title of the vault item, if present. Otherwise, an empty string.
     */
    public String getTitle() {
        if (vaultItem.isPresent()) {
            return vaultItem.get().getTitle();
        }
        return "";
    }

    /**
     * Gets the username of the vault item.
     * @return The username of the vault item, if present. Otherwise, an empty string.
     */
    public String getUsername() {
        if (vaultItem.isPresent()) {
            return vaultItem.get().getUsername();
        }
        return "";
    }

    /**
     * Gets the password of the vault item.
     * @return The password of the vault item, if present. Otherwise, an empty string.
     */
    public String getPassword() {
        if (vaultItem.isPresent()) {
            return vaultItem.get().getPassword();
        }
        return "";
    }

    /**
     * Gets the url of the vault item.
     * @return The url of the vault item, if present. Otherwise, an empty string.
     */
    public String getUrl() {
        if (vaultItem.isPresent()) {
            return vaultItem.get().getUrl();
        }
        return "";
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

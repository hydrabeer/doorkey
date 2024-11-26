package views;

import entity.AbstractVaultItem;
import entity.PasswordVaultItem;

import java.util.Optional;

public class PasswordVaultItemState {
    private Optional<PasswordVaultItem> vaultItem;
    private String error;
    private String message;

    public PasswordVaultItemState() {
        this.vaultItem = Optional.empty();
        this.error = "";
        this.message = "";
    }

    public PasswordVaultItemState(PasswordVaultItem item) {
        this.vaultItem = Optional.of(item);
        this.error = "";
        this.message = "";
    }

    public Optional<PasswordVaultItem> getVaultItem() {
        return vaultItem;
    }

    public String getTitle() {
        if (vaultItem.isPresent()) {
            return vaultItem.get().getTitle();
        }
        return "";
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }

    public String getUsername() {
        if (vaultItem.isPresent()) {
            return vaultItem.get().getUsername();
        }
        return "";
    }

//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getPassword() {
        if (vaultItem.isPresent()) {
            return vaultItem.get().getPassword();
        }
        return "";
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getUrl() {
        if (vaultItem.isPresent()) {
            return vaultItem.get().getUrl();
        }
        return "";
    }

//    public void setUrl(String url) {
//        this.url = url;
//    }

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

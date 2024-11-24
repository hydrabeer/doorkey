package views;

import entity.PasswordVaultItem;

public class PasswordVaultItemState {
    private String title;
    private String username;
    private String password;
    private String url;
    private String error;
    private String message;

    public PasswordVaultItemState(PasswordVaultItem item) {
        this.title = item.getTitle();
        this.username = item.getUsername();
        this.password = item.getPassword();
        this.url = item.getUrl();
        this.error = "";
        this.message = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

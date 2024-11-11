package entity;

import org.json.JSONObject;

/**
 * PasswordVaultItem entity extends AbstractVaultItem entity.
 * PasswordVaultItem is the standard VaultItem type that can be stored in a Vault with username, password and URL.
 */
public class PasswordVaultItem extends AbstractVaultItem {
    private String username;
    private String password;
    private String url;

    public PasswordVaultItem(String title, String username, String password, String url) {
        super(title);
        this.username = username;
        this.password = password;
        this.url = url;
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

    public String toJSON() {
        JSONObject json = new JSONObject();
        json.put("title", this.getTitle());
        json.put("username", this.getUsername());
        json.put("password", this.getPassword());
        json.put("url", this.getUrl());
        return json.toString();
    }
}

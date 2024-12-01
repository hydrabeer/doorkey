package entity;

import org.json.JSONObject;

/**
 * PasswordVaultItem is a vault item that stores a title, username, password,
 * and url.
 */
public class PasswordVaultItem extends AbstractVaultItem {

    private String username;
    private String password;
    private String url;

    public PasswordVaultItem(String title, String username, String password, String url) {
        super(title, "passwordItem");
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public PasswordVaultItem(JSONObject json) {
        super(json.getString("title"), "passwordItem");
        this.username = json.getString("username");
        this.password = json.getString("password");
        this.url = json.getString("url");
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

    /**
     * Export the AbstractVaultItem as a JSONObject.
     *
     * @return a JSON object representing the vault item
     */
    @Override
    public JSONObject toJSONObject() {
        final JSONObject json = new JSONObject();
        json.put("title", this.getTitle());
        json.put("username", this.getUsername());
        json.put("password", this.getPassword());
        json.put("url", this.getUrl());
        json.put("type", this.getType());
        return json;
    }
}

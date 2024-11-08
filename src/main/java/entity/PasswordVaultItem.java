package entity;

public class PasswordVaultItem extends VaultItem {
    String username;
    String password;
    String url;

    public PasswordVaultItem(String title, String username, String password, String url) {
        super(title);
        this.username = username;
        this.password = password;
        this.url = url;
    }
}

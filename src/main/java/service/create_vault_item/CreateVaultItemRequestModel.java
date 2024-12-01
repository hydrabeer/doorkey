package service.create_vault_item;

/**
 * The CreateVaultItem Request Model.
 */
public class CreateVaultItemRequestModel {
    private final String url;
    private final String vaultTitle;
    private final String username;
    private final String password;
    private final String repeatedPassword;

    public CreateVaultItemRequestModel(
        String url,
        String vaultTitle,
        String username,
        String password,
        String repeatedPassword
    ) {
        this.url = url;
        this.vaultTitle = vaultTitle;
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public String getUrl() {
        return url;
    }

    public String getVaultTitle() {
        return vaultTitle;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }
}

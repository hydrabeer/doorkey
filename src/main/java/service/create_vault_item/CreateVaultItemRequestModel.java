package service.create_vault_item;

import entity.AbstractUser;
import repository.UserRepository;

/**
 * The CreateVaultItem Request Model.
 */
public class CreateVaultItemRequestModel {
    private final AbstractUser user;
    private final UserRepository userRepository;
    private final String url;
    private final String vaultTitle;
    private final String username;
    private final String password;
    private final String repeatedPassword;

    public CreateVaultItemRequestModel(
        AbstractUser user,
        UserRepository userRepository,
        String url,
        String vaultTitle,
        String username,
        String password,
        String repeatedPassword
    ) {
        this.user = user;
        this.userRepository = userRepository;
        this.url = url;
        this.vaultTitle = vaultTitle;
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public AbstractUser getUser() {
        return user;
    }

    public UserRepository getUserRepository() {
        return userRepository;
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

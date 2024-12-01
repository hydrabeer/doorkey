package service.password_vault_item;

import entity.AbstractUser;
import entity.PasswordVaultItem;
import repository.UserRepository;

/**
 * Input data class for Password Vault Item.
 */
public class PasswordVaultItemInputData {
    private AbstractUser user;
    private UserRepository userRepository;
    private PasswordVaultItem passwordVaultItem;

    public PasswordVaultItemInputData(
            AbstractUser user, UserRepository userRepository, PasswordVaultItem passwordVaultItem) {
        this.user = user;
        this.userRepository = userRepository;
        this.passwordVaultItem = passwordVaultItem;
    }

    public AbstractUser getUser() {
        return user;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public PasswordVaultItem getPasswordVaultItem() {
        return passwordVaultItem;
    }
}

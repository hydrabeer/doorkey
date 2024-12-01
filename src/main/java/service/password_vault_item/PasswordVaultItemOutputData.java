package service.password_vault_item;

import entity.AbstractUser;
import repository.UserRepository;

/**
 * Output data class for PasswordVaultItem.
 */
public class PasswordVaultItemOutputData {
    private AbstractUser user;
    private UserRepository userRepository;

    public PasswordVaultItemOutputData(AbstractUser user, UserRepository userRepository) {
        this.user = user;
        this.userRepository = userRepository;
    }

    public AbstractUser getUser() {
        return user;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}

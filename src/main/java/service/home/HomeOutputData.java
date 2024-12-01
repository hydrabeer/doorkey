package service.home;

import entity.AbstractUser;
import entity.AbstractVaultItem;
import repository.UserRepository;

/**
 * Output data from home.
 */
public class HomeOutputData {
    private final AbstractVaultItem vaultItem;
    private final AbstractUser user;
    private final UserRepository userRepository;

    public HomeOutputData(AbstractVaultItem vaultItem, AbstractUser user, UserRepository userRepository) {
        this.vaultItem = vaultItem;
        this.user = user;
        this.userRepository = userRepository;
    }

    public AbstractVaultItem getVaultItem() {
        return vaultItem;
    }

    public AbstractUser getUser() {
        return user;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}

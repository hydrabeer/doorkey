package service.home;

import entity.AbstractUser;
import entity.AbstractVaultItem;
import repository.UserRepository;

/**
 * The home input data.
 */
public class HomeInputData {
    private final AbstractVaultItem chosenVaultItem;
    private final AbstractUser chosenUser;
    private final UserRepository userRepository;

    public HomeInputData(AbstractVaultItem chosenVaultItem, AbstractUser chosenUser, UserRepository userRepository) {
        this.chosenVaultItem = chosenVaultItem;
        this.chosenUser = chosenUser;
        this.userRepository = userRepository;
    }

    public AbstractVaultItem getChosenVaultItem() {
        return chosenVaultItem;
    }

    public AbstractUser getChosenUser() {
        return chosenUser;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}

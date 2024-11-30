package service.create_vault_item.interface_adapter;

import java.util.Optional;

import entity.AbstractUser;
import repository.UserRepository;

/**
 * The CreateVaultItem state.
 */
public class CreateVaultItemState {

    private final Optional<AbstractUser> user;
    private final Optional<UserRepository> userRepository;

    /**
     * Constructor with user and userRepository.
     *
     * @param user The current user.
     * @param userRepository The user repository.
     */
    public CreateVaultItemState(AbstractUser user, UserRepository userRepository) {
        this.user = Optional.of(user);
        this.userRepository = Optional.of(userRepository);
    }

    /**
     * No-argument constructor initializing fields to empty Optionals.
     */
    public CreateVaultItemState() {
        this.user = Optional.empty();
        this.userRepository = Optional.empty();
    }

    public Optional<AbstractUser> getUser() {
        return user;
    }

    public Optional<UserRepository> getUserRepository() {
        return userRepository;
    }

}

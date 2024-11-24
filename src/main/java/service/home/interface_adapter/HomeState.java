package service.home.interface_adapter;

import java.util.Optional;

import entity.AbstractUser;
import repository.UserRepository;

/**
 * The Home state.
 */
public class HomeState {
    private Optional<AbstractUser> user;
    private Optional<UserRepository> userRepository;

    public HomeState(AbstractUser user, UserRepository userRepository) {
        this.user = Optional.of(user);
        this.userRepository = Optional.of(userRepository);
    }

    public HomeState() {
        this.user = Optional.empty();
        this.userRepository = Optional.empty();
    }

    public Optional<AbstractUser> getUser() {
        return user;
    }

    public Optional<UserRepository> getUserRepository() {
        return userRepository;
    }

    public void setUser(AbstractUser user) {
        this.user = Optional.of(user);
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = Optional.of(userRepository);
    }
}

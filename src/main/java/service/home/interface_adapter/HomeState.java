package service.home.interface_adapter;

import entity.AbstractUser;
import repository.UserRepository;

/**
 * The Home state.
 */
public class HomeState {
    private AbstractUser user;
    private UserRepository userRepository;

    public HomeState(AbstractUser user, UserRepository userRepository) {
        this.user = user;
        this.userRepository = userRepository;
    }

    public AbstractUser getUser() {
        return user;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUser(AbstractUser user) {
        this.user = user;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

package service.login;

import entity.AbstractUser;
import repository.UserRepository;

/**
 * Output data from logging in operation.
 */
public class LoginOutputData {
    private final AbstractUser user;
    private final UserRepository userRepository;

    public LoginOutputData(AbstractUser user, UserRepository userRepository) {
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

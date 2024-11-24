package service.signup;

import entity.AbstractUser;
import repository.UserRepository;

/**
 * Output data from signing up operation.
 */
public class SignupOutputData {
    private final AbstractUser user;
    private final UserRepository userRepository;

    public SignupOutputData(AbstractUser user, UserRepository userRepository) {
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


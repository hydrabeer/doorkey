package service.login;

import repository.UserRepository;

/**
 * Output data from logging in operation.
 */
public class LoginOutputData {
    private final UserRepository userRepository;

    public LoginOutputData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}

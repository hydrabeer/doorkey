package service.signup;

import repository.UserRepository;

/**
 * Output data from signing up operation.
 */
public class SignupOutputData {
    private final UserRepository userRepository;

    public SignupOutputData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}


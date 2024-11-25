package service.signup;

/**
 * The sign up use case.
 */
public interface SignupInputBoundary {
    /**
     * Log in the user to remote sync.
     *
     * @param signupInputData The login input data.
     */
    void signup(SignupInputData signupInputData);
}

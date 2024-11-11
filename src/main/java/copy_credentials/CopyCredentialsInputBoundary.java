package copy_credentials;

/**
 * Input Boundary for actions which are related to Copying Credentials.
 */
public interface CopyCredentialsInputBoundary {

    /**
     * Copy username to clipboard.
     * @param usernameInputData data object that holds username string.
     */
    void copyUsername(UsernameInputData usernameInputData);

    /**
     * Copy password to clipboard.
     * @param passwordInputData data object that holds username string.
     */
    void copyPassword(PasswordInputData passwordInputData);
}

package copy_credentials;

/**
 * The Login Interactor.
 */

public class CopyCredentialsInteractor {

    private final CopyCredenentialsOutputBoundary outputBoundary;

    public CopyCredentialsInteractor(CopyCredenentialsOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    /**
     * Copy username to clipboard.
     * @param usernameInputData data object with username.
     */
    public static void copyUsername(UsernameInputData usernameInputData) {

    }
    /**
     * Copy password to clipboard.
     * @param passwordInputData data object with password.
     */

    public static void copyPassword(PasswordInputData passwordInputData) {

    }
}

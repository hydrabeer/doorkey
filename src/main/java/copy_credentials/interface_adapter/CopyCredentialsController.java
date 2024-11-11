package copy_credentials.interface_adapter;

import copy_credentials.CopyCredentialsInputBoundary;
import copy_credentials.PasswordInputData;
import copy_credentials.UsernameInputData;

/**
 * The Copy Credentials Interactor.
 */
public class CopyCredentialsController {
    private static CopyCredentialsInputBoundary credentialsInputBoundary;

    public CopyCredentialsController(CopyCredentialsInputBoundary credentialsInputBoundary) {
        this.credentialsInputBoundary = credentialsInputBoundary;
    }

    /**
     * Creates usernameInputData object to call copyUsername interactor method.
     * @param username username to be stored in passwordInputData object.
     */
    public static void copyUsernameClicked(String username) {
        final UsernameInputData usernameInputData = new UsernameInputData(username);
        credentialsInputBoundary.copyUsername(usernameInputData);
    }

    /**
     * Creates passwordInputData object to call copyPassword interactor method.
     * @param password password to be stored in passwordInputData object.
     */
    public static void copyPasswordClicked(String password) {
        final PasswordInputData passwordInputData = new PasswordInputData(password);
        credentialsInputBoundary.copyPassword(passwordInputData);
    }
}

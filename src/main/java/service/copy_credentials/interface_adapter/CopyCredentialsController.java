package service.copy_credentials.interface_adapter;

import service.copy_credentials.CopyCredentialsInputBoundary;
import service.copy_credentials.PasswordInputData;
import service.copy_credentials.TimeInputData;
import service.copy_credentials.UsernameInputData;

/**
 * The Copy Credentials Interactor.
 */
public class CopyCredentialsController {
    private CopyCredentialsInputBoundary interactor;

    public CopyCredentialsController(CopyCredentialsInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Creates usernameInputData object to call copyUsername interactor method.
     * @param username username to be stored in passwordInputData object.
     * @param time time username is copied for
     */
    public void copyUsernameClicked(String username, int time) {
        final UsernameInputData usernameInputData = new UsernameInputData(username, time);
        interactor.copyUsername(usernameInputData);
    }

    /**
     * Creates passwordInputData object to call copyPassword interactor method.
     * @param password password to be stored in passwordInputData object.
     * @param time time password is copied for
     */
    public void copyPasswordClicked(String password, int time) {
        final PasswordInputData passwordInputData = new PasswordInputData(password, time);
        interactor.copyPassword(passwordInputData);
    }

    /**
     * Calls clear clipBoard interactor method.
     * @param time time in milliseconds after which clipboard is cleared
     */
    public void clearClipboard(int time) {
        final TimeInputData timeInputData = new TimeInputData(time);
        interactor.clearClipboard(timeInputData.getTime());
    }
}

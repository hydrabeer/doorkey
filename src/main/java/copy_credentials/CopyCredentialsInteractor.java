package copy_credentials;


import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

/**
 * The Login Interactor.
 */

public class CopyCredentialsInteractor implements CopyCredentialsInputBoundary {

    private final CopyCredenentialsOutputBoundary outputBoundary;

    public CopyCredentialsInteractor(CopyCredenentialsOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    /**
     * Copy username to clipboard.
     * @param usernameInputData data object with username.
     */
    public void copyUsername(UsernameInputData usernameInputData) {
        final StringSelection username = new StringSelection(usernameInputData.getUsername());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(username, null);
        outputBoundary.displayUsernameCopyMessage("Copied to clipboard!");
    }
    /**
     * Copy password to clipboard.
     * @param passwordInputData data object with password.
     */

    public void copyPassword(PasswordInputData passwordInputData) {
        final StringSelection password = new StringSelection(passwordInputData.getPassword());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(password, null);
        outputBoundary.displayUsernameCopyMessage("Copied to clipboard!");
    }
}

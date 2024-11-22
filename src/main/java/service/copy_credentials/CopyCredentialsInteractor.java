package service.copy_credentials;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Timer;
import java.util.TimerTask;

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
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            public void run() {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
                outputBoundary.displayUsernameCopyMessage("Clipboard cleared after " + 10 + " seconds.");
            }
        };
        timer.schedule(timerTask, 10000);
        // Convert seconds to milliseconds
    }
    /**
     * Copy password to clipboard.
     * @param passwordInputData data object with password.
     */

    public void copyPassword(PasswordInputData passwordInputData) {
        final StringSelection password = new StringSelection(passwordInputData.getPassword());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(password, null);
        outputBoundary.displayPasswordCopyMessage("Copied to clipboard!");
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            public void run() {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
                outputBoundary.displayPasswordCopyMessage("Clipboard cleared after " + 10 + " seconds.");
            }
        };
        timer.schedule(timerTask, 10000);
        // Convert seconds to milliseconds
    }
}

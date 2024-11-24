package service.copy_credentials;

import java.awt.Toolkit;
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
        copyString(usernameInputData.getUsername());
    }

    /**
     * Copy password to clipboard.
     * @param passwordInputData data object with password.
     */
    public void copyPassword(PasswordInputData passwordInputData) {
        copyString(passwordInputData.getPassword());
    }

    /**
     * Helper function that copies input text to clip board and clears after 10 seconds.
     * @param copyText text to be copied to clipboard.
     */
    private void copyString(String copyText) {
        final StringSelection text = new StringSelection(copyText);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(text, null);
        outputBoundary.displayPasswordCopyMessage("Copied to clipboard!");
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            public void run() {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
                outputBoundary.displayPasswordCopyMessage("Clipboard cleared after " + 10 + " seconds.");
            }
        };
        timer.schedule(timerTask, 10000);

    }
}

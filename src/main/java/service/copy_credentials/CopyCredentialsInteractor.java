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
     *
     * @param usernameInputData data object with username.
     */
    public void copyUsername(UsernameInputData usernameInputData) {

        copyString(usernameInputData.getUsername());
        outputBoundary.displayCopyMessage(
                "Copied username to clipboard for " + usernameInputData.getTime() / 1000 + " seconds!");
    }

    /**
     * Copy password to clipboard.
     *
     * @param passwordInputData data object with password.
     */
    public void copyPassword(PasswordInputData passwordInputData) {
        copyString(passwordInputData.getPassword());
        outputBoundary.displayCopyMessage(
                "Copied password to clipboard for " + passwordInputData.getTime() / 1000 + " seconds!");
    }

    /**
     * Helper function that copies input text to clip board and clears after 10 seconds.
     *
     * @param copyText text to be copied to clipboard.
     */
    private void copyString(String copyText) {
        final StringSelection text = new StringSelection(copyText);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(text, null);
    }

    /**
     * Clear system clipboard after 10 seconds and display message.
     * @param time time in milliseconds after which clipboard is cleared.
     */
    public void clearClipboard(int time) {
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            public void run() {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
            }
        };
        timer.schedule(timerTask, time);
        outputBoundary.displayCopyMessage("Clipboard cleared after " + time / 1000 + " seconds");
    }
}

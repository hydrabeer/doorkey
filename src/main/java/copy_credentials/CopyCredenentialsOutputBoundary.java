package copy_credentials;

/**
 * Displays Copy Confirmation message.
 */
public interface CopyCredenentialsOutputBoundary {

    /**
     * Displays message when user clicks copy username button.
     * @param message message to be displayed when user clicks copy username button
     */
    void displayUsernameCopyMessage(String message);

    /**
     * Displays message when user clicks copy password button.
     * @param message message to be displayed when user clicks copy password button
     */
    void displayPasswordCopyMessage(String message);
}

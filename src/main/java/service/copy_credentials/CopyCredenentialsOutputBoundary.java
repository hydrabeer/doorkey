package service.copy_credentials;

/**
 * Displays Copy Confirmation message.
 */
public interface CopyCredenentialsOutputBoundary {

    /**
     * Displays message when user clicks copy button.
     * @param message message to be displayed when user clicks copy username button
     */
    void displayCopyMessage(String message);
}


package presenters.controllers;

import javax.swing.JFileChooser;

import views.components.DoorkeyForm;

/**
 * Handles loading (decrypting) a local .doorkey vault.
 */
public class LoadLocalVaultController {
    private final JFileChooser saver;
    private final DoorkeyForm form;

    public LoadLocalVaultController(JFileChooser saver, DoorkeyForm form) {
        this.saver = saver;
        this.form = form;
    }

    /**
     * When the user clicks the "Load" button.
     */
    public void formSubmitted() {
        if (saver.getSelectedFile() == null) {
            form.setError("Please select a valid .doorkey file!");
        }
        else {
            final String path = saver.getSelectedFile().getAbsolutePath();
            if (path.isEmpty()) {
                form.setError("Please select a valid .doorkey file!");
            }
            else {
                final String password = form.getFieldValue("password");
                if (password.isEmpty()) {
                    form.setError("Please enter a password");
                }
                else {
                    openVault(password, path);
                }
            }
        }
    }

    private void openVault(String password, String path) {
        // TODO: TO BE IMPLEMENTED
        // Checks to be implemented:
        // - If path is non-existent
        // - If password is incorrect
        // If no problems, load the main vault view
    }
}

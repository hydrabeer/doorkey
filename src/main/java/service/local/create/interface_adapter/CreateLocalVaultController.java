package service.local.create.interface_adapter;

import javax.swing.JFileChooser;

import views.components.DoorkeyForm;

/**
 * Handles the creation of encrypted .doorkey files.
 */
public class CreateLocalVaultController {
    private final JFileChooser saver;
    private final DoorkeyForm form;

    public CreateLocalVaultController(JFileChooser saver, DoorkeyForm form) {
        this.saver = saver;
        this.form = form;
    }

    /**
     * When the user clicks the "Create" button and provided inputs are valid.
     * Create a local .doorkey vault with the given password and path, and load the main vault view.
     * @param password The user provided vault password
     * @param path The path to save the .doorkey file
     */
    public void vaultCreated(String password, String path) {
        final String filepath = addDoorkeyExtension(path);
        System.out.println(password);
        System.out.println(filepath);
        // TODO: TO BE IMPLEMENTED
    }

    private String addDoorkeyExtension(String path) {
        String filepath = path;
        if (!path.contains(".doorkey")) {
            filepath += ".doorkey";
        }
        return filepath;
    }

    /**
     * When the user clicks on the "Create" button, validate provided inputs.
     */
    public void formSubmitted() {
        if (saver.getSelectedFile() == null) {
            form.setError("Please select a save file!");
        }
        else {
            final String path = saver.getSelectedFile().getAbsolutePath();
            if (path.isEmpty()) {
                form.setError("Please select a save file!");
            }
            else {
                final String password = form.getFieldValue("password");
                final String confirm = form.getFieldValue("confirm");
                if (password.isEmpty()) {
                    form.setError("Please enter a password!");
                }
                else if (!password.equals(confirm)) {
                    form.setError("Passwords do not match!");
                }
                else {
                    vaultCreated(password, path);
                }
            }
        }
    }
}

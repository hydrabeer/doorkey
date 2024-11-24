package service.local.create;

import javax.swing.JFileChooser;

/**
 * The main input data for creating a local .doorkey vault.
 */
public class CreateLocalVaultInputData {
    private final JFileChooser path;
    private final String password;

    public CreateLocalVaultInputData(JFileChooser path, String password) {
        this.path = path;
        this.password = password;
    }

    public JFileChooser getPath() {
        return path;
    }

    public String getPassword() {
        return password;
    }
}

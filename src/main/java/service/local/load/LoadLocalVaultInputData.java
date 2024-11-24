package service.local.load;

import javax.swing.JFileChooser;

/**
 * The main input data for loading a local .doorkey vault.
 */
public class LoadLocalVaultInputData {
    private final JFileChooser path;
    private final String password;

    public LoadLocalVaultInputData(JFileChooser path, String password) {
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

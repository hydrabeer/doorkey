package service.local.load.interface_adapter;

import javax.swing.JFileChooser;

/**
 * The possible view for loading local vault view.
 */
public class LoadLocalVaultState {
    private JFileChooser path;
    private String password = "";
    private boolean isSuccess;

    public LoadLocalVaultState() {
    }
    
    public LoadLocalVaultState(JFileChooser path, String password, boolean isSuccess) {
        this.path = path;
        this.password = password;
        this.isSuccess = isSuccess;
    }

    public JFileChooser getPath() {
        return path;
    }

    public void setPath(JFileChooser path) {
        this.path = path;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }
}

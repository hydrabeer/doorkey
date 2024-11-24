package service.local.create.interface_adapter;

import javax.swing.JFileChooser;

/**
 * The possible view for creating local vault view.
 */
public class CreateLocalVaultState {
    private JFileChooser path;
    private String password = "";
    private boolean isSuccess;

    public CreateLocalVaultState() {
    }
    
    public CreateLocalVaultState(JFileChooser path, String password, boolean isSuccess) {
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

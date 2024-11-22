package service.login.interface_adapter;

/**
 * The possible state for the Login view.
 */
public class LoginState {
    private String email = "";
    private String password = "";
    private boolean isSuccess;

    public LoginState() {
    }

    public LoginState(String email, String password, boolean isSuccess) {
        this.email = email;
        this.password = password;
        this.isSuccess = isSuccess;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setIsSuccess(boolean success) {
        isSuccess = success;
    }
}

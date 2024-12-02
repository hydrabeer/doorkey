package service.copy_credentials;

/**
 * PasswordInputData used to pass information from controller to interactor.
 */
public class PasswordInputData {
    private String password;
    private int time;

    public PasswordInputData(String password, int time) {
        this.time = time;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getTime() {
        return time;
    }
}

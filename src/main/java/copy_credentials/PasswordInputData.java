package copy_credentials;

/**
 * PasswordInputData used to pass information from controller to interactor.
 */
public class PasswordInputData {
    private String password;

    public PasswordInputData(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}

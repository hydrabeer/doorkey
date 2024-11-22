package service.login;

/**
 * Output data from logging in operation.
 */
public class LoginOutputData {
    private final String email;
    private final String password;

    public LoginOutputData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

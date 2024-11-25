package service.signup;

/**
 * The main input data for signing up.
 */
public class SignupInputData {
    private final String email;
    private final String password;
    private final String repeatedPassword;

    public SignupInputData(String email, String password, String repeatedPassword) {
        this.email = email;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }
}

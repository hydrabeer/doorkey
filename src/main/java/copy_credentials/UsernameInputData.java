package copy_credentials;

/**
 * UsernameInputData used to pass information from controller to interactor.
 */
public class UsernameInputData {
    private String username;

    public UsernameInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

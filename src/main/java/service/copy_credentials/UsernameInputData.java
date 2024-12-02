package service.copy_credentials;

/**
 * UsernameInputData used to pass information from controller to interactor.
 */
public class UsernameInputData {
    private String username;
    private int time;

    public UsernameInputData(String username, int time) {
        this.time = time;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getTime() {
        return time;
    }
}

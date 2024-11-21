package views;

public class TestViewState {
    private String email;
    private String password;

    public TestViewState() {
        this.email = "";
        this.password = "";
    }

    public TestViewState(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
package entity;

/**
 * Abstract user entity.
 */

public abstract class AbstractUser {
    private String email;
    private final String password;
    private AbstractVault vault;

    public AbstractUser(String email, String password, AbstractVault vault) {
        this.email = email;
        this.password = password;
        this.vault = vault;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public AbstractVault getVault() {
        return vault;
    }

    public void setVault(AbstractVault vault) {
        this.vault = vault;
    }
}

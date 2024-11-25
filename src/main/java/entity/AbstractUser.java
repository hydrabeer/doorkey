package entity;

/**
 * Abstract user entity.
 */

public abstract class AbstractUser {
    private String email;
    private AbstractVault vault;

    public AbstractUser(String email, AbstractVault vault) {
        this.email = email;
        this.vault = vault;
    }

    public String getEmail() {
        return email;
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

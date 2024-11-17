package entity;

/**
 * Abstract user entity.
 */

public abstract class AbstractUser {
    private String username;
    private AbstractVault vault;

    public AbstractUser(String username, AbstractVault vault) {
        this.username = username;
        this.vault = vault;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AbstractVault getVault() {
        return vault;
    }

    public void setVault(AbstractVault vault) {
        this.vault = vault;
    }
}

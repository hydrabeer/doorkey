package entity;

/**
 * User entity.
 */

public abstract class AbstractUser {
    private String username;
    private AbstractVault vault;

    public AbstractUser(String username, AbstractVault vault) {
        this.username = username;
        this.vault = vault;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public AbstractVault getVault() {
        return vault;
    }

    public void setVault(AbstractVault vault) {
        this.vault = vault;
    }
}

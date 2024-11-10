package entity;

/**
 * User entity.
 */

public abstract class AbstractUser {
    private String name;
    private AbstractVault vault;

    public AbstractUser(String name, AbstractVault vault) {
        this.name = name;
        this.vault = vault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractVault getVault() {
        return vault;
    }

    public void setVault(AbstractVault vault) {
        this.vault = vault;
    }
}

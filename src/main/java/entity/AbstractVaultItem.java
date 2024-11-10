package entity;

/**
 * Abstract VaultItem entity.
 */
public abstract class AbstractVaultItem {
    private String title;

    public AbstractVaultItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}

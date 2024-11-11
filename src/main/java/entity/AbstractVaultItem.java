package entity;

/**
 * Abstract vault item entity.
 */
public abstract class AbstractVaultItem {
    private String title;

    public AbstractVaultItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract String toJSON();
}

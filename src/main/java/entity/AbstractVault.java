package entity;

import java.util.List;

/**
 * Abstract Vault entity.
 */
public abstract class AbstractVault {
    private List<AbstractVaultItem> items;

    public AbstractVault(List<AbstractVaultItem> items) {
        this.items = items;
    }

    public List<AbstractVaultItem> getItems() {
        return items;
    }

    public void setItems(List<AbstractVaultItem> items) {
        this.items = items;
    }

    /**
     * Add VaultItem to Vault.
     * @param item VaultItem to be added to Vault.
     */
    public void addItem(AbstractVaultItem item) {
        items.add(item);
    }

    /**
     * Load and parse each value as JSON.
     * @param values array of values
     */
    public void load(String[] values) {
        // implementation not added yet

    }

}

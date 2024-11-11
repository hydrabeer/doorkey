package entity;

import java.util.List;

/**
 * Abstract vault entity.
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
     * Add a VaultItem to the Vault.
     * @param item the VaultItem to be added to the Vault
     */
    public void addItem(AbstractVaultItem item) {
        items.add(item);
    }

    /**
     * Populate a vault from an existing vault stored in JSON
     * @param json the JSON string containing vault data to load
     */
    public void loadFromJSON(String json) {
        // implementation not added yet
    }

}

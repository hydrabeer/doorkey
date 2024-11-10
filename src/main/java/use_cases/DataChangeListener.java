package use_cases;

import entity.VaultItem;

/**
 * Interface for listening to changes to Vault Items.
 */
public interface DataChangeListener {
    /**
     * Execute when an item is added to the vault.
     *
     * @param item the vault item that was added
     */
    void onItemAdded(VaultItem item);

    /**
     * Execute when an item in the vault is updated.
     *
     * @param item the vault item that was added
     */
    void onItemUpdated(VaultItem item);

    /**
     * Execute when an item is removed from the vault.
     *
     * @param item the vault item that was added
     */
    void onItemRemoved(VaultItem item);
}

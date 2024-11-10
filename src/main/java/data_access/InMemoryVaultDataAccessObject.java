package data_access;

import java.util.ArrayList;
import java.util.List;

import entity.VaultItem;
import use_case.DataChangeListener;

/**
 * In-memory implementation of the DAO for storing vault items. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryVaultDataAccessObject {
    private final List<VaultItem> items = new ArrayList<>();
    private List<DataChangeListener> listeners = new ArrayList<>();

    /**
     * Adds the given item to the vault and notifies listeners.
     *
     * @param item the item to add to the vault
     */
    public void addItem(VaultItem item) {
        items.add(item);
        notifyItemAdded(item);
    }

    /**
     * Removes the given item from the vault and notifies listeners.
     *
     * @param item the item to remove from the vault
     */
    public void removeItem(VaultItem item) {
        items.remove(item);
        notifyItemRemoved(item);
    }

    // Observer pattern methods (this class is a subject)

    /**
     * Registers the given listener.
     *
     * @param listener the listener to register
     */
    public void registerListener(DataChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Unregisters the given listener.
     *
     * @param listener the listener to unregister
     */
    public void unregisterListener(DataChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyItemAdded(VaultItem item) {
        for (DataChangeListener listener : listeners) {
            listener.onItemAdded(item);
        }
    }

    private void notifyItemUpdated(VaultItem item) {
        for (DataChangeListener listener : listeners) {
            listener.onItemUpdated(item);
        }
    }

    private void notifyItemRemoved(VaultItem item) {
        for (DataChangeListener listener : listeners) {
            listener.onItemRemoved(item);
        }
    }
}

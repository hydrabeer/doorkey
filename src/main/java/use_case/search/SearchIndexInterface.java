package use_case.search;

import java.util.List;

import entity.VaultItem;
import use_case.DataChangeListener;

/**
 * Interface for building and maintaining the search index and providing search
 * functionality to retrieve matching VaultItem instances.
 */
public interface SearchIndexInterface extends DataChangeListener {
    /**
     * Builds the search index with a list of VaultItems.
     *
     * @param items List of VaultItem instances.
     */
    void buildIndex(List<VaultItem> items);

    /**
     * Indexes a single VaultItem.
     *
     * @param item The VaultItem to index.
     */
    void indexItem(VaultItem item);

    /**
     * Updates the index when a VaultItem is modified.
     *
     * @param item The updated VaultItem.
     */
    void updateItem(VaultItem item);

    /**
     * Removes a VaultItem from the index.
     *
     * @param item The VaultItem to remove.
     */
    void removeItem(VaultItem item);

    /**
     * Searches the index for a given query string.
     *
     * @param query The search query.
     * @return List of matching VaultItems.
     */
    List<VaultItem> search(String query);

    /**
     * Clears the index, removing all data.
     */
    void clearIndex();
}

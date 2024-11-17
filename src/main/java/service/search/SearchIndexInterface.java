package service.search;

import java.util.List;

import entity.AbstractVaultItem;
import service.DataChangeListener;

/**
 * Interface for building and maintaining the search index and providing search
 * functionality to retrieve matching AbstractVaultItem instances.
 */
public interface SearchIndexInterface extends DataChangeListener {
    /**
     * Builds the search index with a list of AbstractVaultItems.
     *
     * @param items List of AbstractVaultItem instances.
     */
    void buildIndex(List<AbstractVaultItem> items);

    /**
     * Indexes a single AbstractVaultItem.
     *
     * @param item The AbstractVaultItem to index.
     */
    void indexItem(AbstractVaultItem item);

    /**
     * Updates the index when a AbstractVaultItem is modified.
     *
     * @param item The updated AbstractVaultItem.
     */
    void updateItem(AbstractVaultItem item);

    /**
     * Removes a AbstractVaultItem from the index.
     *
     * @param item The AbstractVaultItem to remove.
     */
    void removeItem(AbstractVaultItem item);

    /**
     * Searches the index for a given query string.
     *
     * @param query The search query.
     * @return List of matching AbstractVaultItems.
     */
    List<AbstractVaultItem> search(String query);

    /**
     * Clears the index, removing all data.
     */
    void clearIndex();
}

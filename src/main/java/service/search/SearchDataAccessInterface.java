package service.search;

import java.util.List;

import entity.AbstractVaultItem;

/**
 * DAO for the Search Use Case.
 */
public interface SearchDataAccessInterface {
    /**
     * Returns the items in the vault.
     *
     * @return the items in the vault
     */
    List<AbstractVaultItem> getItems();
}

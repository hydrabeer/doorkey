package service.search.interface_adapter;

import java.util.List;

import entity.AbstractVaultItem;

/**
 * The state for the Search View Model.
 */
public class SearchState {

    private List<AbstractVaultItem> items;

    public List<AbstractVaultItem> getItems() {
        return items;
    }

    public void setItems(List<AbstractVaultItem> items) {
        this.items = items;
    }

}

package service.search.interface_adapter;

import java.util.List;

import entity.AbstractVaultItem;

/**
 * The state for the Search View Model.
 */
public class SearchState {

    private String query;
    private List<AbstractVaultItem> items;
    private String noResultsMessage;

    public List<AbstractVaultItem> getItems() {
        return items;
    }

    public void setItems(List<AbstractVaultItem> items) {
        this.items = items;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getNoResultsMessage() {
        return noResultsMessage;
    }

    public void setNoResultsMessage(String noResultsMessage) {
        this.noResultsMessage = noResultsMessage;
    }
}

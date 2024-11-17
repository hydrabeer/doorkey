package service.search;

import java.util.List;

import entity.AbstractVaultItem;

/**
 * Output data for the Search use case.
 */
public class SearchResponseModel {
    private final List<AbstractVaultItem> results;

    public SearchResponseModel(List<AbstractVaultItem> results) {
        this.results = results;
    }

    public List<AbstractVaultItem> getResults() {
        return results;
    }
}

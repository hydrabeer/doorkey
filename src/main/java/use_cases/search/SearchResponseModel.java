package use_cases.search;

import java.util.List;

import entity.VaultItem;

/**
 * Output data for the Search use case.
 */
public class SearchResponseModel {
    private final List<VaultItem> results;

    public SearchResponseModel(List<VaultItem> results) {
        this.results = results;
    }

    public List<VaultItem> getResults() {
        return results;
    }
}

package service.search;

import java.util.List;

import entity.AbstractVaultItem;

/**
 * Output data for the Search use case.
 */
public class SearchOutputData {
    private final List<AbstractVaultItem> results;

    public SearchOutputData(List<AbstractVaultItem> results) {
        this.results = results;
    }

    public List<AbstractVaultItem> getResults() {
        return results;
    }
}

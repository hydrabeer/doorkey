package use_case.search;

import java.util.List;

import entity.VaultItem;

/**
 * The Search Interactor holds the main logic of the Search use case.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final SearchOutputBoundary searchPresenter;
    private final SearchIndexInterface searchIndex;

    public SearchInteractor(SearchOutputBoundary outputBoundary,
                            SearchIndexInterface searchIndex) {
        this.searchPresenter = outputBoundary;
        this.searchIndex = searchIndex;
    }

    @Override
    public void execute(SearchInputData inputData) {
        final String query = inputData.getQuery();
        final List<VaultItem> results = searchIndex.search(query);

        if (results.isEmpty()) {
            searchPresenter.prepareNoResultsView("No results found for \"" + query + "\".");
        }
        else {
            final SearchResponseModel responseModel = new SearchResponseModel(results);
            searchPresenter.prepareResultsView(responseModel);
        }
    }
}

package service.search.interface_adapter;

import service.search.SearchInputBoundary;
import service.search.SearchInputData;

/**
 * The controller for the Search Use Case.
 */
public class SearchController {

    private final SearchInputBoundary searchInteractor;

    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    /**
     * Executes the Search Use Case.
     *
     * @param query the search query
     */
    public void execute(String query) {
        final SearchInputData searchInputData = new SearchInputData(query);
        searchInteractor.execute(searchInputData);
    }
}

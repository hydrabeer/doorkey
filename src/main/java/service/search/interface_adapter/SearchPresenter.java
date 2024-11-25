package service.search.interface_adapter;

import service.ViewManagerModel;
import service.search.SearchOutputBoundary;
import service.search.SearchOutputData;

/**
 * The presenter for the Search use case.
 */
public class SearchPresenter implements SearchOutputBoundary {

    private final SearchViewModel searchViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(SearchViewModel searchViewModel, ViewManagerModel viewManagerModel) {
        this.searchViewModel = searchViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareResultsView(SearchOutputData searchOutputData) {
        final SearchState searchState = searchViewModel.getState();
        searchState.setItems(searchOutputData.getResults());
        searchViewModel.setState(searchState);
        searchViewModel.onStateChanged();

        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.onStateChanged();
    }

    @Override
    public void prepareNoResultsView(String message) {
        final SearchState searchState = searchViewModel.getState();
        searchState.setItems(null);
        searchState.setNoResultsMessage(message);
        searchViewModel.onStateChanged();
    }
}

package service.search.interface_adapter;

import interface_adapter.ViewModel;

/**
 * The search view model.
 */
public class SearchViewModel extends ViewModel<SearchState> {

    public SearchViewModel() {
        super("search");
        setState(new SearchState());
    }

    @Override
    public void onStateChanged() {
    }
}

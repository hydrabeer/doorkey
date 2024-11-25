package service.search.interface_adapter;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * The search view model.
 */
public class SearchViewModel extends ViewModel<SearchState> {

    public SearchViewModel() {
        super(ViewConstants.SEARCH_VIEW);
        setState(new SearchState());
    }
}

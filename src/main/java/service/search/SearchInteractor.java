package service.search;

import java.util.ArrayList;
import java.util.List;

import entity.AbstractVaultItem;
import entity.PasswordVaultItem;

/**
 * The Search Interactor holds the main logic of the Search use case.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final SearchDataAccessInterface searchDAO;
    private final SearchOutputBoundary searchPresenter;

    public SearchInteractor(SearchOutputBoundary outputBoundary,
                            SearchDataAccessInterface searchDataAccessInterface) {
        this.searchDAO = searchDataAccessInterface;
        this.searchPresenter = outputBoundary;
    }

    @Override
    public void execute(SearchInputData inputData) {
        final String query = inputData.getQuery();
        final List<AbstractVaultItem> results = new ArrayList<>();

        for (AbstractVaultItem item : searchDAO.getItems()) {
            if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                results.add(item);
            }
            else if (item instanceof PasswordVaultItem) {
                final String usernameField = ((PasswordVaultItem) item).getUsername();
                if (usernameField.toLowerCase().contains(query.toLowerCase())) {
                    results.add(item);
                }
            }
        }

        if (results.isEmpty()) {
            searchPresenter.prepareNoResultsView("No results found for \"" + query + "\".");
        }
        else {
            final SearchOutputData outputData = new SearchOutputData(results);
            searchPresenter.prepareResultsView(outputData);
        }
    }
}

package service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.AbstractVaultItem;
import entity.PasswordVaultItem;
import repository.RepositoryProvider;
import repository.UserRepository;

/**
 * The Search Interactor holds the main logic of the Search use case.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final RepositoryProvider repositoryProvider;
    private final SearchOutputBoundary searchPresenter;

    public SearchInteractor(
            RepositoryProvider repositoryProvider,
            SearchOutputBoundary outputBoundary
    ) {
        this.repositoryProvider = repositoryProvider;
        this.searchPresenter = outputBoundary;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        final String query = searchInputData.getQuery().toLowerCase();
        final List<AbstractVaultItem> results = new ArrayList<>();

        final Optional<UserRepository> repository = repositoryProvider.getRepository();
        if (repository.isEmpty()) {
            searchPresenter.prepareResultsView(new SearchOutputData(results));
            return;
        }

        final List<AbstractVaultItem> vaultItems = repository
                .get()
                .getCurrentUser()
                .getVault()
                .getItems();

        for (AbstractVaultItem item : vaultItems) {
            if (item.getTitle().toLowerCase().contains(query)) {
                results.add(item);
            }
            else if (item instanceof PasswordVaultItem) {
                final String username = ((PasswordVaultItem) item).getUsername();
                if (username.toLowerCase().contains(query)) {
                    results.add(item);
                }
            }
        }

        searchPresenter.prepareResultsView(new SearchOutputData(results));
    }
}

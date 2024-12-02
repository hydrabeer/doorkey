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
    public void execute(SearchInputData inputData) {
        final String query = inputData.getQuery();
        final List<AbstractVaultItem> results = new ArrayList<>();

        final Optional<UserRepository> repositoryOptional = repositoryProvider.getRepository();
        if (repositoryOptional.isEmpty()) {
            searchPresenter.prepareResultsView(new SearchOutputData(results));
            return;
        }

        final List<AbstractVaultItem> vaultItems = repositoryOptional.get()
                .getCurrentUser()
                .getVault()
                .getItems();

        for (AbstractVaultItem item : vaultItems) {
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

        final SearchOutputData outputData = new SearchOutputData(results);
        searchPresenter.prepareResultsView(outputData);
    }
}

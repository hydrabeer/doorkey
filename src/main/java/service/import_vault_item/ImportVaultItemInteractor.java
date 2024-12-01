package service.import_vault_item;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import entity.AbstractVaultItem;
import exception.AuthException;
import interface_adapter.vault_imports.ImportFactory;
import interface_adapter.vault_imports.PasswordManager;
import repository.RepositoryProvider;
import repository.UserRepository;

/**
 * The import vault item interactor.
 */
public class ImportVaultItemInteractor implements ImportVaultItemInputBoundary {
    private final RepositoryProvider repositoryProvider;
    private final ImportVaultItemOutputBoundary presenter;

    public ImportVaultItemInteractor(
            RepositoryProvider repositoryProvider,
            ImportVaultItemOutputBoundary presenter
    ) {
        this.repositoryProvider = repositoryProvider;
        this.presenter = presenter;
    }

    @Override
    public void importVaultItems(ImportVaultItemInputData importVaultItemInputData) {
        final String passwordManagerText = importVaultItemInputData.getPasswordManager();
        final PasswordManager enumPasswordManager;

        try {
            enumPasswordManager = switch (passwordManagerText) {
                case "1Password" -> PasswordManager.ONE_PASSWORD;
                default -> throw new IllegalArgumentException("Invalid password manager.");
            };
        }
        catch (IllegalArgumentException illegalArgumentException) {
            presenter.displayError("Choose a password manager.");
            return;
        }

        final String importDataText = importVaultItemInputData.getImportTextJson();
        final JSONObject importDataJson;
        try {
            importDataJson = new JSONObject(importDataText);
        }
        catch (JSONException jsonException) {
            presenter.displayError("Invalid JSON.");
            return;
        }

        // Why unchecked is safe: At this point, the user must've logged in.
        final UserRepository userRepository = repositoryProvider.getRepositoryUnchecked();

        final List<AbstractVaultItem> vaultItems = ImportFactory.createVaultItems(
                enumPasswordManager,
                importDataJson
        );
        try {
            for (AbstractVaultItem vaultItem : vaultItems) {
                userRepository.addVaultItem(vaultItem);
            }
            displayHomeView();
        }
        catch (IOException ioException) {
            presenter.displayError("Failed to import vault items to local vault.");
        }
        catch (AuthException authException) {
            presenter.displayError("Failed to import vault items to remote vault.");
        }
    }

    @Override
    public void displayHomeView() {
        presenter.displayHomeView();
    }
}

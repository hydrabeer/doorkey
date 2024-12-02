package service.create_vault_item;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import entity.PasswordVaultItem;
import exception.AuthException;
import interface_adapter.validate_url.PhishingUrlValidator;
import repository.RepositoryProvider;
import repository.UserRepository;

/**
 * The CreateVaultItem Interactor.
 */
public class CreateVaultItemInteractor implements CreateVaultItemInputBoundary {
    private final RepositoryProvider repositoryProvider;
    private final PhishingUrlValidator phishingUrlValidator;
    private final CreateVaultItemOutputBoundary presenter;

    public CreateVaultItemInteractor(
        RepositoryProvider repositoryProvider,
        PhishingUrlValidator phishingUrlValidator,
        CreateVaultItemOutputBoundary presenter
    ) {
        this.repositoryProvider = repositoryProvider;
        this.phishingUrlValidator = phishingUrlValidator;
        this.presenter = presenter;
    }

    @Override
    public void createVaultItem(CreateVaultItemRequestModel requestModel) {
        final String password = requestModel.getPassword();
        final String repeatedPassword = requestModel.getRepeatedPassword();
        final boolean isPasswordValid = password.equals(repeatedPassword);

        if (
            !isPasswordValid
        ) {
            presenter.displayErrorMessage("Passwords must match");
            return;
        }

        // Why safe: at this point, user must've already logged in.
        final UserRepository userRepository = repositoryProvider.getRepositoryUnchecked();

        String urlString = requestModel.getUrl();
        if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
            urlString = "https://" + urlString;
        }

        if (!urlString.contains(".")) {
            presenter.displayErrorMessage("Invalid URL");
            return;
        }

        final URL url;
        try {
            url = new URL(urlString);
        }
        catch (MalformedURLException malformedUrlException) {
            presenter.displayErrorMessage("Invalid URL");
            return;
        }

        final boolean isPhishing = phishingUrlValidator.isPhishingUrl(url);
        System.out.println(isPhishing);
        if (isPhishing) {
            presenter.displayErrorMessage("Entered a Phishing URL.");
            return;
        }

        try {
            final PasswordVaultItem vaultItem = new PasswordVaultItem(
                    requestModel.getVaultTitle(),
                    requestModel.getUsername(),
                    requestModel.getPassword(),
                    url.toString()
            );

            userRepository.addVaultItem(vaultItem);

            final CreateVaultItemResponseModel responseModel = new CreateVaultItemResponseModel(
                true,
                "Vault item created successfully"
            );

            presenter.presentCreateVaultItemResponse(responseModel);
        }
        catch (AuthException | IOException exception) { 
            presenter.displayErrorMessage("Error occurred saving vault item");
        }
    }

    @Override
    public void cancel() {
        presenter.cancel();
    }

    @Override
    public void displayHomeView() {
        presenter.displayHomeView();
    }
}


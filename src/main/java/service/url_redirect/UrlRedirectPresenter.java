package service.url_redirect;

import service.password_vault_item.interface_adapter.PasswordVaultItemViewModel;

/**
 * Gives information from the interactor to the User.
 */
public class UrlRedirectPresenter implements UrlRedirectOutputBoundary {
    private PasswordVaultItemViewModel viewModel;

    public UrlRedirectPresenter(PasswordVaultItemViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Shows error messages if opening the URL fails.
     * @param message message to be displayed
     */
    public void displayError(String message) {
        viewModel.getState().setError(message);
    }
}

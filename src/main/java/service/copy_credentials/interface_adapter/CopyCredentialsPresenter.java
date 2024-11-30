package service.copy_credentials.interface_adapter;

import service.copy_credentials.CopyCredenentialsOutputBoundary;
import service.password_vault_item.interface_adapter.PasswordVaultItemViewModel;

/**
 * Copy Credentials presenter.
 */
public class CopyCredentialsPresenter implements CopyCredenentialsOutputBoundary {
    private PasswordVaultItemViewModel viewModel;

    public CopyCredentialsPresenter(PasswordVaultItemViewModel viewModel) {
        this.viewModel = viewModel;
    }
    /**
     * Displays message when user clicks copy username button .
     * @param message message to be displayed to the user.
     */

    public void displayCopyMessage(String message) {
        viewModel.getState().setMessage(message);
    }

}

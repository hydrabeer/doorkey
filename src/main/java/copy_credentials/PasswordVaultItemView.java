package copy_credentials;

/**
 * UI for VaultItem.
 */
public class PasswordVaultItemView {
    private CopyCredentialsController copyController;

    /**
    ** Constructor for PasswordVaultItem UI.
     * @param copyController controller to call copyCredential methods upon button push.
    */
    public PasswordVaultItemView(CopyCredentialsController copyController) {
        this.copyController = copyController;
    }

    /**
     * Method that calls copyUsername method using copyController.
     */
    public void onCopyUsernameButtonClicked() {
    }

    /**
     * Method that calls copyPassword method using copyController.
     */
    public static void onCopyPasswordButtonClicked() {
    }
}

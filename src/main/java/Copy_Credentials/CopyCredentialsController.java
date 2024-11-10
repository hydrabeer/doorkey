package Copy_Credentials;

import entity.VaultItem;

/**
 * The Copy Credentials Interactor.
 */
public class CopyCredentialsController {
    private static CopyCredentialsInteractor copyCredentialsInteractor;

    public CopyCredentialsController(CopyCredentialsInteractor copyCredentialsInteractor) {
        this.copyCredentialsInteractor = copyCredentialsInteractor;
    }

    public static void copyUsernameClicked(VaultItem vaultItem){
        copyCredentialsInteractor.copyUsername(vaultItem);
    }

    public static void copyPasswordClicked(VaultItem vaultItem){
        copyCredentialsInteractor.copyPassword(vaultItem);
    }
}

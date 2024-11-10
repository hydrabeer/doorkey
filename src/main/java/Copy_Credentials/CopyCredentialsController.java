package Copy_Credentials;

import entity.VaultItem;

/**
 * The Copy Credentials Interactor.
 */
public class CopyCredentialsController {

    public static void copyUsernameClicked(VaultItem vaultItem){
        CopyCredentialsInteractor copyCredentialsInteractor = new CopyCredentialsInteractor();
        copyCredentialsInteractor.copyUsername(vaultItem);
    }

    public static void copyPasswordClicked(VaultItem vaultItem){
        CopyCredentialsInteractor copyCredentialsInteractor = new CopyCredentialsInteractor();
        copyCredentialsInteractor.copyPassword(vaultItem);
    }
}

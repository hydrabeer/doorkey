package URL_Rediect;

import entity.VaultItem;

public class OpenURLController {
    private static OpenURLInteractor openURLInteractor;

    public OpenURLController(OpenURLInteractor openURLInteractor) {
        this.openURLInteractor = openURLInteractor;
    }
    public static void openURLClicked(VaultItem vaultItem){
        openURLInteractor.openURL(vaultItem);
    }
}

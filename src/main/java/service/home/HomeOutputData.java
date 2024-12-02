package service.home;

import entity.AbstractVaultItem;

/**
 * Output data from home.
 */
public class HomeOutputData {
    private final AbstractVaultItem vaultItem;

    public HomeOutputData(AbstractVaultItem vaultItem) {
        this.vaultItem = vaultItem;
    }

    public AbstractVaultItem getVaultItem() {
        return vaultItem;
    }
}

package service.home;

import entity.AbstractVaultItem;

/**
 * The home input data.
 */
public class HomeInputData {
    private final AbstractVaultItem chosenVaultItem;

    public HomeInputData(AbstractVaultItem chosenVaultItem) {
        this.chosenVaultItem = chosenVaultItem;
    }

    public AbstractVaultItem getChosenVaultItem() {
        return chosenVaultItem;
    }
}

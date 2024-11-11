package entity;

import java.util.List;

/**
 * LocalVault is a vault that stores items in JSON format.
 */

public class LocalVault extends AbstractVault {
    public LocalVault(List<AbstractVaultItem> items) {
        super(items);
    }
}

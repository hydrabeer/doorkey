package entity;

import java.util.List;

/**
 * Local Vault entity extends Vault entity.
 */

public class LocalVault extends AbstractVault {
    public LocalVault(List<AbstractVaultItem> items) {
        super(items);

    }
}

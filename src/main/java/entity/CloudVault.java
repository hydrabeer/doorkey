package entity;

import java.util.List;

/**
 * CloudVault entity extends Vault entity.
 */
public class CloudVault extends AbstractVault {

    public CloudVault(List<AbstractVaultItem> items) {
        super(items);
    }
}

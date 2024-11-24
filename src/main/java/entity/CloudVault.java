package entity;

import java.util.List;

/**
 * CloudVault is a Vault that stores items in an online database.
 */
public class CloudVault extends AbstractVault {
    public CloudVault(List<AbstractVaultItem> items) {
        super(items);
    }
}

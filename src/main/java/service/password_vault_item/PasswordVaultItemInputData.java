package service.password_vault_item;

import entity.PasswordVaultItem;

/**
 * Input data class for Password Vault Item.
 */
public class PasswordVaultItemInputData {
    private PasswordVaultItem passwordVaultItem;

    public PasswordVaultItemInputData(PasswordVaultItem passwordVaultItem) {
        this.passwordVaultItem = passwordVaultItem;
    }

    public PasswordVaultItem getPasswordVaultItem() {
        return passwordVaultItem;
    }
}

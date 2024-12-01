package interface_adapter.vault_imports;

import java.util.List;

import org.json.JSONObject;

import entity.AbstractVaultItem;

/**
 * The import factory for multiple kinds of vault imports.
 */
public class ImportFactory {
    /**
     * Create vault items from the given JSON object.
     *
     * @param passwordManager The password manager to use.
     * @param item            The JSON object to create the vault item from.
     * @return The created vault items.
     */
    public static List<AbstractVaultItem> createVaultItems(PasswordManager passwordManager, JSONObject item) {
        switch (passwordManager) {
            case ONE_PASSWORD:
                return (new OnePasswordAdapter()).convert(item);
            default:
                return null;
        }
    }
}

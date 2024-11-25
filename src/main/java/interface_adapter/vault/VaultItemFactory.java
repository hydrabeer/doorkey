package interface_adapter.vault;

import org.json.JSONObject;

import entity.AbstractVaultItem;

/**
 * Implements the factory design pattern to create vault items based on the type.
 */
public class VaultItemFactory {
    /**
     * Create a vault item based on the type.
     *
     * @param jsonObject The JSON object to create the vault item from.
     *                   The object should have a "type" field.
     * @return The vault item if the type is valid, null otherwise.
     */
    public static AbstractVaultItem createVaultItemFromJson(JSONObject jsonObject) {
        final String type = jsonObject.getString("type");
        return switch (type) {
            case "passwordItem" -> new entity.PasswordVaultItem(jsonObject);
            case "noteItem" -> new entity.NoteVaultItem(jsonObject);
            default -> null;
        };
    }
}

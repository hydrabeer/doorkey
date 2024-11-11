package entity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import exception.InvalidVaultItemException;

/**
 * Abstract vault entity.
 */
public abstract class AbstractVault {
    private List<AbstractVaultItem> items;

    public AbstractVault(List<AbstractVaultItem> items) {
        this.items = items;
    }

    public List<AbstractVaultItem> getItems() {
        return items;
    }

    public void setItems(List<AbstractVaultItem> items) {
        this.items = items;
    }

    /**
     * Add a VaultItem to the Vault.
     * @param item the VaultItem to be added to the Vault
     */
    public void addItem(AbstractVaultItem item) {
        items.add(item);
    }

    /**
     * Export the Vault as a JSON string.
     * @return the JSON string containing the vault's items
     */
    public String toJSON() {
        JSONArray json = new JSONArray();
        for (AbstractVaultItem item : this.items) {
            json.put(item.toJSONObject());
        }
        return json.toString();
    }

    /**
     * Populate the vault from an existing vault stored as JSON.
     * @param json the JSON string containing vault data to load
     */
    public void loadFromJSON(String json) throws InvalidVaultItemException {
        JSONArray items = new JSONArray(json);
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            if (item.has("password")) {
                this.addItem(new PasswordVaultItem(item));
            } else {
                throw new InvalidVaultItemException("Unknown vault item type");
            }
        }
    }

}

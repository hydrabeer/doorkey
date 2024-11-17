package entity;

import org.json.JSONObject;

/**
 * Abstract vault item entity.
 */
public abstract class AbstractVaultItem {
    private String title;

    public AbstractVaultItem(String title) {
        this.title = title;
    }

    public AbstractVaultItem(JSONObject json) {
        this.title = json.getString("title");
    }

    public String getTitle() {
        return title;
    }

    /**
     * Export the AbstractVaultItem as a JSONObject.
     * @return a JSON object representing the vault item
     */
    public abstract JSONObject toJSONObject();
}

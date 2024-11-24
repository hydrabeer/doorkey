package entity;

import org.json.JSONObject;

/**
 * Abstract vault item entity.
 */
public abstract class AbstractVaultItem {
    private final String title;
    private final String type;

    public AbstractVaultItem(String title, String type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    /**
     * Export the AbstractVaultItem as a JSONObject.
     *
     * @return a JSON object representing the vault item
     */
    public abstract JSONObject toJSONObject();
}

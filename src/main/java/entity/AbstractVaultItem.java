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

    public abstract JSONObject toJSONObject();
}

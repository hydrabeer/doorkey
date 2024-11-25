package entity;

import org.json.JSONObject;

/**
 * A note vault item.
 */
public class NoteVaultItem extends AbstractVaultItem {
    private String content;

    public NoteVaultItem(String title, String content) {
        super(title, "noteItem");
        this.content = content;
    }

    public NoteVaultItem(JSONObject json) {
        super(json.getString("title"), "noteItem");
        this.content = json.getString("content");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Export the AbstractVaultItem as a JSONObject.
     * @return a JSON object representing the vault item
     */
    @Override
    public JSONObject toJSONObject() {
        final JSONObject json = new org.json.JSONObject();
        json.put("title", this.getTitle());
        json.put("content", this.getContent());
        json.put("type", this.getType());
        return json;
    }
}

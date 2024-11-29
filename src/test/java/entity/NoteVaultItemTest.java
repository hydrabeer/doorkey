package entity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class NoteVaultItemTest {
    private static final String title = "Meeting Notes";
    private static final String content = "Discuss project milestones";
    
    @Test
    void testNoteVaultItemToJSON() {
        NoteVaultItem item = new NoteVaultItem(title, content);
        JSONObject json = item.toJSONObject();

        assertTrue(json.getString("title").equals(title));
        assertTrue(json.getString("content").equals(content));
    }

    @Test
    void testNoteVaultItemFromJSON() {
        NoteVaultItem item = new NoteVaultItem(title, content);
        String json = item.toJSONObject().toString();
        item = new NoteVaultItem(new JSONObject(json));

        assertTrue(item.getTitle().equals(title));
        assertTrue(item.getContent().equals(content));
    }

    @Test
    void testNoteVaultItemGetAndSetContent() {
        NoteVaultItem item = new NoteVaultItem(title, content);
        String newContent = "Updated project milestones";
        item.setContent(newContent);

        assertTrue(item.getContent().equals(newContent));
    }
}

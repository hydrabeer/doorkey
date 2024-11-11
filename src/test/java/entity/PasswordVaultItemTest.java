package entity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class PasswordVaultItemTest {
    private static final String title = "Google";
    private static final String username = "doorkey@gmail.com";
    private static final String password = "pa55w0rD";
    private static final String url = "https://google.com";
    
    @Test
    void testPasswordVaultItemToJSON() {
        PasswordVaultItem item = new PasswordVaultItem(title, username, password, url);
        String json = item.toJSON();

        JSONObject parsed = new JSONObject(json);
        assertTrue(parsed.getString("title").equals(title));
        assertTrue(parsed.getString("username").equals(username));
        assertTrue(parsed.getString("password").equals(password));
        assertTrue(parsed.getString("url").equals(url));
    }
}

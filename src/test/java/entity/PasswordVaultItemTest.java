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
        JSONObject json = item.toJSONObject();

        assertTrue(json.getString("title").equals(title));
        assertTrue(json.getString("username").equals(username));
        assertTrue(json.getString("password").equals(password));
        assertTrue(json.getString("url").equals(url));
    }

    @Test
    void testPasswordVaultItemFromJSON() {
        PasswordVaultItem item = new PasswordVaultItem(title, username, password, url);
        String json = item.toJSONObject().toString();
        item = new PasswordVaultItem(new JSONObject(json));

        assertTrue(item.getTitle().equals(title));
        assertTrue(item.getUsername().equals(username));
        assertTrue(item.getPassword().equals(password));
        assertTrue(item.getUrl().equals(url));
    }

    @Test
    void testSetUsername() {
        PasswordVaultItem item = new PasswordVaultItem(title, username, password, url);
        String newUsername = "newuser@gmail.com";
        item.setUsername(newUsername);

        assertTrue(item.getUsername().equals(newUsername));
    }

    @Test
    void testSetPassword() {
        PasswordVaultItem item = new PasswordVaultItem(title, username, password, url);
        String newPassword = "newPa55w0rD";
        item.setPassword(newPassword);

        assertTrue(item.getPassword().equals(newPassword));
    }

    @Test
    void testSetUrl() {
        PasswordVaultItem item = new PasswordVaultItem(title, username, password, url);
        String newUrl = "https://newurl.com";
        item.setUrl(newUrl);

        assertTrue(item.getUrl().equals(newUrl));
    }
}

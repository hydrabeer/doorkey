package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import exception.InvalidVaultItemException;

public class LocalVaultTest {
    private static final String title = "Google";
    private static final String username = "doorkey@gmail.com";
    private static final String password = "pa55w0rD";
    private static final String url = "https://google.com";
    private static final PasswordVaultItem item = new PasswordVaultItem(title, username, password, url);

    @Test
    void testLocalVaultItems() {
        List<AbstractVaultItem> items = new ArrayList<>();
        items.add(item);

        AbstractVault vault = new LocalVault(items);
        assertTrue(vault.getItems() == items);
    }
    
    @Test
    void testLocalVaultItemsPathAndPassword() {
        List<AbstractVaultItem> items = new ArrayList<>();
        items.add(item);

        AbstractVault vault = new LocalVault(items, "vault.doorkey", "password");
        assertTrue(vault.getItems() == items);
        assertTrue(((LocalVault) vault).getPath().equals("vault.doorkey"));
        assertTrue(((LocalVault) vault).getPassword().equals("password"));
    }

    @Test
    void testLocalVaultJSON() throws InvalidVaultItemException {
        List<AbstractVaultItem> items = new ArrayList<>();
        AbstractVault vault = new LocalVault(items);
        String export = vault.toJSON();
        assertTrue(export.equals("[]"));

        items.add(item);
        export = vault.toJSON();
        assertFalse(export.equals("[]"));

        AbstractVault parsed = new LocalVault(new ArrayList<>());
        parsed.loadFromJSON(export);

        assertEquals(parsed.getItems().size(), 1);
        assertTrue(parsed.getItems().get(0).getTitle().equals(title));
        assertTrue(((PasswordVaultItem) parsed.getItems().get(0)).getUsername().equals(username));
        assertTrue(((PasswordVaultItem) parsed.getItems().get(0)).getPassword().equals(password));
        assertTrue(((PasswordVaultItem) parsed.getItems().get(0)).getUrl().equals(url));
    }

    @Test
    void testLocalVaultJSONMissingPassword() throws InvalidVaultItemException {
        List<AbstractVaultItem> items = new ArrayList<>();
        items.add(item);

        AbstractVault vault = new LocalVault(items);
        String export = vault.toJSON();

        // Convert to JSONObject and remove the password key
        JSONArray jsonObject = new JSONArray(export);
        jsonObject.getJSONObject(0).remove("password");

        // Convert back to String
        String modifiedExport = jsonObject.toString();

        // Create a new LocalVault and attempt to load the modified JSON
        AbstractVault parsed = new LocalVault(new ArrayList<>());
        assertThrows(InvalidVaultItemException.class, () -> parsed.loadFromJSON(modifiedExport));
    }

    @Test
    void testGetItems() {
        List<AbstractVaultItem> items = new ArrayList<>();
        items.add(item);

        AbstractVault vault = new LocalVault(items);
        assertEquals(vault.getItems(), items);
    }

    @Test
    void testSetItems() {
        List<AbstractVaultItem> items = new ArrayList<>();
        AbstractVault vault = new LocalVault(items);

        List<AbstractVaultItem> newItems = new ArrayList<>();
        newItems.add(item);
        vault.setItems(newItems);

        assertEquals(vault.getItems(), newItems);
    }

    @Test
    void testAddItem() {
        List<AbstractVaultItem> items = new ArrayList<>();
        AbstractVault vault = new LocalVault(items);

        vault.addItem(item);
        assertEquals(vault.getItems().size(), 1);
        assertEquals(vault.getItems().get(0), item);
    }
}

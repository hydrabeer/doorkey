package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import exception.InvalidVaultItemException;

public class CloudVaultTest {
    private static final String title = "Google";
    private static final String username = "doorkey@gmail.com";
    private static final String password = "pa55w0rD";
    private static final String url = "https://google.com";
    private static final PasswordVaultItem item = new PasswordVaultItem(title, username, password, url);

    @Test
    void testCloudVaultItems() {
        List<AbstractVaultItem> items = new ArrayList<>();
        items.add(item);

        AbstractVault vault = new CloudVault(items);
        assertTrue(vault.getItems() == items);
    }
    
    @Test
    void testCloudVaultAddItem() {
        List<AbstractVaultItem> items = new ArrayList<>();
        AbstractVault vault = new CloudVault(items);

        assertEquals(0, vault.getItems().size());

        vault.addItem(item);
        assertEquals(1, vault.getItems().size());
        assertTrue(vault.getItems().contains(item));
    }

    @Test
    void testCloudVaultSetItems() {
        List<AbstractVaultItem> items = new ArrayList<>();
        AbstractVault vault = new CloudVault(items);

        assertEquals(0, vault.getItems().size());

        List<AbstractVaultItem> newItems = new ArrayList<>();
        newItems.add(item);
        vault.setItems(newItems);

        assertEquals(1, vault.getItems().size());
        assertTrue(vault.getItems().contains(item));
    }

    @Test
    void testCloudVaultJSON() throws InvalidVaultItemException {
        List<AbstractVaultItem> items = new ArrayList<>();
        AbstractVault vault = new CloudVault(items);
        String export = vault.toJSON();
        assertTrue(export.equals("[]"));

        items.add(item);
        export = vault.toJSON();
        assertFalse(export.equals("[]"));

        AbstractVault parsed = new CloudVault(new ArrayList<>());
        parsed.loadFromJSON(export);

        assertEquals(parsed.getItems().size(), 1);
        assertTrue(parsed.getItems().get(0).getTitle().equals(title));
        assertTrue(((PasswordVaultItem) parsed.getItems().get(0)).getUsername().equals(username));
        assertTrue(((PasswordVaultItem) parsed.getItems().get(0)).getPassword().equals(password));
        assertTrue(((PasswordVaultItem) parsed.getItems().get(0)).getUrl().equals(url));
    }
}
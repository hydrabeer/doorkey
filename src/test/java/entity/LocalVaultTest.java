package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
}

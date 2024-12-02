package interface_adapter.vault_imports;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import entity.AbstractVaultItem;
import entity.PasswordVaultItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportFactoryTest {

    @Test
    void testCreateVaultItemsWithOnePassword() throws Exception {
        JSONObject input = new JSONObject();
        JSONArray accounts = new JSONArray();
        JSONObject account = new JSONObject();
        account.put("attrs", new JSONObject().put("email", "test@example.com"));
        JSONArray vaults = new JSONArray();
        JSONObject vault = new JSONObject();
        JSONArray items = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("details", new JSONObject().put("loginFields", new JSONArray()
                .put(new JSONObject()
                        .put("designation", "username")
                        .put("value", "test_user"))
                .put(new JSONObject()
                        .put("designation", "password")
                        .put("value", "test_password"))));
        item.put("overview", new JSONObject()
                .put("url", "https://example.com")
                .put("title", "Example"));
        items.put(item);
        vault.put("items", items);
        vaults.put(vault);
        account.put("vaults", vaults);
        accounts.put(account);
        input.put("accounts", accounts);

        List<AbstractVaultItem> result = ImportFactory.createVaultItems(PasswordManager.ONE_PASSWORD, input);
        assertEquals(1, result.size());
        PasswordVaultItem vaultItem = (PasswordVaultItem) result.get(0);
        assertEquals("Example", vaultItem.getTitle());
        assertEquals("test_user", vaultItem.getUsername());
        assertEquals("test_password", vaultItem.getPassword());
        assertEquals("https://example.com", vaultItem.getUrl());
    }

    @Test
    void testCreateVaultItemsWithUnsupportedPasswordManager() {
        JSONObject input = new JSONObject();
        List<AbstractVaultItem> result = ImportFactory.createVaultItems(PasswordManager.UNSUPPORTED, input);
        assertNull(result);
    }
}
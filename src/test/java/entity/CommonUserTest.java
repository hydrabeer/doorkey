package entity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonUserTest {
    private static final String title = "Google";
    private static final String email = "doorkey@gmail.com";
    private static final String userPassword = "mySecureMasterPa@@5d";
    private static final String password = "pa55w0rD";
    private static final String url = "https://google.com";
    private final List<AbstractVaultItem> items = new ArrayList<>();

    @Test
    void testCommonUser() {
        PasswordVaultItem item = new PasswordVaultItem(title, email, password, url);
        items.add(item);

        AbstractVault vault = new LocalVault(items);
        AbstractUser user = new CommonUser(email, userPassword, vault);
        assertTrue(user.getEmail().equals(email));
        assertTrue(user.getVault() == vault);
        assertTrue(user.getPassword().equals(userPassword));
        assertTrue(user.getVault().getItems().get(0) == item);
    }

    @Test
    void testGetEmail() {
        AbstractVault vault = new LocalVault(items);
        AbstractUser user = new CommonUser(email, userPassword, vault);
        assertEquals(email, user.getEmail());
    }

    @Test
    void testSetEmail() {
        AbstractVault vault = new LocalVault(items);
        AbstractUser user = new CommonUser(email, userPassword, vault);
        String newEmail = "newemail@gmail.com";
        user.setEmail(newEmail);
        assertEquals(newEmail, user.getEmail());
    }

    @Test
    void testGetPassword() {
        AbstractVault vault = new LocalVault(items);
        AbstractUser user = new CommonUser(email, userPassword, vault);
        assertEquals(userPassword, user.getPassword());
    }

    @Test
    void testGetVault() {
        AbstractVault vault = new LocalVault(items);
        AbstractUser user = new CommonUser(email, userPassword, vault);
        assertEquals(vault, user.getVault());
    }

    @Test
    void testSetVault() {
        AbstractVault vault = new LocalVault(items);
        AbstractUser user = new CommonUser(email, userPassword, vault);
        AbstractVault newVault = new LocalVault(new ArrayList<>());
        user.setVault(newVault);
        assertEquals(newVault, user.getVault());
    }
}

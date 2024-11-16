package entity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CommonUserTest {
    private static final String title = "Google";
    private static final String username = "doorkey@gmail.com";
    private static final String password = "pa55w0rD";
    private static final String url = "https://google.com";
    private final List<AbstractVaultItem> items = new ArrayList<>();

    @Test
    void testCommonUser() {
        PasswordVaultItem item = new PasswordVaultItem(title, username, password, url);
        items.add(item);

        AbstractVault vault = new LocalVault(items);
        AbstractUser user = new CommonUser(username, vault);
        assertTrue(user.getUsername().equals(username));
        assertTrue(user.getVault() == vault);
        assertTrue(user.getVault().getItems().get(0) == item);
    }
}

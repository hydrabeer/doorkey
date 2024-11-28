package entity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LocalUserTest {
    private static final String title = "Google";
    private static final String email = "doorkey@gmail.com";
    private static final String userPassword = "mySecureMasterPa@@5d";
    private static final String password = "pa55w0rD";
    private static final String url = "https://google.com";
    private final List<AbstractVaultItem> items = new ArrayList<>();

    @Test
    void testLocalUser() {
        PasswordVaultItem item = new PasswordVaultItem(title, email, password, url);
        items.add(item);

        LocalVault vault = new LocalVault(items);
        AbstractUser user = new LocalUser(vault, userPassword);
        assertTrue(user.getEmail().equals("")); // local users don't have an email
        assertTrue(user.getVault() == vault);
        assertTrue(user.getPassword().equals(userPassword));
        assertTrue(user.getVault().getItems().get(0) == item);
    }
}

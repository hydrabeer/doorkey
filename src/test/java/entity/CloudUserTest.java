package entity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import interface_adapter.net.auth.RemoteAuth;
import org.junit.jupiter.api.Test;

public class CloudUserTest {
    private static final String title = "Google";
    private static final String userAndGoogleEmail = "doorkey@gmail.com";
    private static final String userPassword = "mySecureMasterPa@@5d";
    private static final String password = "pa55w0rD";
    private static final String url = "https://google.com";
    private static final RemoteAuth remoteAuth = new RemoteAuth(
            "idToken",
            "refreshToken",
            "localId",
            60
    );
    private final List<AbstractVaultItem> items = new ArrayList<>();

    @Test
    void testCommonUser() {
        PasswordVaultItem item = new PasswordVaultItem(title, userAndGoogleEmail, password, url);
        items.add(item);

        AbstractVault vault = new LocalVault(items);
        CloudUser user = new CloudUser(userAndGoogleEmail, userPassword, vault, remoteAuth);
        assertTrue(user.getEmail().equals(userAndGoogleEmail));
        assertTrue(user.getVault() == vault);
        assertTrue(user.getPassword().equals(userPassword));
        assertTrue(user.getVault().getItems().get(0) == item);
        assertTrue(user.getRemoteAuth() == remoteAuth);
    }
}

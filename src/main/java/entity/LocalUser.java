package entity;

/**
 * Local user's vault will be stored locally in a .doorkey file.
 */
public class LocalUser extends AbstractUser {
    public LocalUser(LocalVault vault, String password) {
        // local users have no email, hence super with empty string
        super("", password, vault);
    }
}

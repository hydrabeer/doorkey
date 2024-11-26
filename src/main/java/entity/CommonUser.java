package entity;

/**
 * CommonUser entity extends user entity.
 */
public class CommonUser extends AbstractUser {

    public CommonUser(String username, String password, AbstractVault vault) {
        super(username, password, vault);
    }
}

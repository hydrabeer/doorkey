package entity;

/**
 * CommonUser entity extends user entity.
 */
public class CommonUser extends AbstractUser {

    public CommonUser(String username, AbstractVault vault) {
        super(username, vault);
    }
}

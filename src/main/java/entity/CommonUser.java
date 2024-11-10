package entity;

/**
 * CommonUser entity extends user entity.
 */
public class CommonUser extends AbstractUser {

    public CommonUser(String name, AbstractVault vault) {
        super(name, vault);
    }
}

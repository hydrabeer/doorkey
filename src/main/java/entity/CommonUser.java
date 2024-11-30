package entity;

/**
 * CommonUser entity extends user entity.
 */
public class CommonUser extends AbstractUser {

    public CommonUser(String email, String password, AbstractVault vault) {
        super(email, password, vault);
    }
}

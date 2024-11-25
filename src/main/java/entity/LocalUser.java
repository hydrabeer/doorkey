package entity;

public class LocalUser extends AbstractUser {
    public LocalUser(LocalVault vault) {
        super("", vault); // local users have no email
    }
}

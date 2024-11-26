package mock;

import entity.AbstractUser;
import entity.AbstractVaultItem;
import entity.CommonUser;
import entity.LocalVault;
import exception.AuthException;
import interface_adapter.net.auth.AuthErrorReason;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of UserRepository for testing.
 */
public class MockUserRepository implements UserRepository {
    private final Map<String, AbstractUser> users = new HashMap<>();
    private final Map<String, String> passwords = new HashMap<>();

    @Override
    public AbstractUser signupUser(String email, String password) throws AuthException {
        if (users.containsKey(email)) {
            throw new AuthException(AuthErrorReason.EMAIL_EXISTS, "User already exists.");
        }
        AbstractUser newUser = new CommonUser(email, password, new LocalVault(new ArrayList<>()));
        users.put(email, newUser);
        passwords.put(email, password);
        return newUser;
    }

    @Override
    public AbstractUser signInUser(String email, String password) throws AuthException {
        if (users.containsKey(email) && passwords.get(email).equals(password)) {
            return users.get(email);
        }
        throw new AuthException(AuthErrorReason.WRONG_CREDENTIALS, "Wrong credentials.");
    }

    @Override
    public void addVaultItem(AbstractUser user, AbstractVaultItem item) {
        user.getVault().addItem(item);
    }

    @Override
    public void removeVaultItem(AbstractUser user, AbstractVaultItem item) {
        List<AbstractVaultItem> items = user.getVault().getItems();
        items.remove(item);
        user.getVault().setItems(items);
    }
}

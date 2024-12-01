package mock;

import entity.AbstractUser;
import entity.AbstractVaultItem;
import entity.CommonUser;
import entity.LocalVault;
import exception.AuthException;
import interface_adapter.net.auth.AuthErrorReason;
import repository.UserRepository;

import java.io.IOException;
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
    private boolean throwIOExceptionAdd = false;
    private boolean throwAuthExceptionAdd = false;
    private boolean throwIOExceptionSignin = false;
    private boolean throwIOExceptionSignup = false;
    private AbstractUser user;

    @Override
    public AbstractUser signupUser(String email, String password) throws AuthException, IOException {
        if (users.containsKey(email)) {
            throw new AuthException(AuthErrorReason.EMAIL_EXISTS, "User already exists.");
        }
        AbstractUser newUser = new CommonUser(email, password, new LocalVault(new ArrayList<>()));
        users.put(email, newUser);
        passwords.put(email, password);
        this.user = newUser;

        if (throwIOExceptionSignup) {
            throw new IOException("IO error");
        }

        return newUser;
    }

    @Override
    public AbstractUser signInUser(String email, String password) throws AuthException, IOException {
        if (throwIOExceptionSignin) {
            throw new IOException("IO error");
        }
        if (users.containsKey(email) && passwords.get(email).equals(password)) {
            AbstractUser user = users.get(email);
            this.user = user;
            return user;
        }
        throw new AuthException(AuthErrorReason.WRONG_CREDENTIALS, "Wrong credentials.");
    }

    @Override
    public void signOutUser() {
        user = null;
    }

    @Override
    public AbstractUser getCurrentUser() {
        return user;
    }

    @Override
    public void addVaultItem(AbstractVaultItem item) throws IOException, AuthException {
        if (throwIOExceptionAdd) {
            throw new IOException("IO error");
        }
        if (throwAuthExceptionAdd) {
            throw new AuthException(AuthErrorReason.UNKNOWN, "Auth error");
        }
        user.getVault().addItem(item);
    }

    @Override
    public void removeVaultItem(AbstractVaultItem item) {
        List<AbstractVaultItem> items = user.getVault().getItems();
        items.remove(item);
        user.getVault().setItems(items);
    }

    public void setThrowIOExceptionAdd(boolean throwIOExceptionAdd) {
        this.throwIOExceptionAdd = throwIOExceptionAdd;
    }

    public void setThrowAuthExceptionAdd(boolean throwAuthExceptionAdd) {
        this.throwAuthExceptionAdd = throwAuthExceptionAdd;
    }

    public void setThrowIOExceptionSignin(boolean throwIOExceptionSignin) {
        this.throwIOExceptionSignin = throwIOExceptionSignin;
    }

    public void setThrowIOExceptionSignup(boolean throwIOExceptionSignup) {
        this.throwIOExceptionSignup = throwIOExceptionSignup;
    }
}

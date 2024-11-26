package data_access;

import java.util.ArrayList;

import entity.AbstractUser;
import entity.AbstractVaultItem;
import entity.LocalUser;
import entity.LocalVault;
import exception.AuthException;
import interface_adapter.net.auth.AuthErrorReason;
import repository.UserRepository;

/**
 * Repository for user data.
 */
public class LocalVaultUserDataAccessObject implements UserRepository {
    private String path;
    private String password;

    public LocalVaultUserDataAccessObject() {
    }

    /**
     * Signs up a user by local vault path and password.
     *
     * @param filepath    The path to the local .doorkey vault.
     * @param vaultPassword The password of the user.
     * @return The user that was signed up.
     * @throws AuthException Never.
     */
    @Override
    public AbstractUser signupUser(String filepath, String vaultPassword) throws AuthException {
        this.path = filepath;
        this.password = vaultPassword;
        return new LocalUser(new LocalVault(new ArrayList<>(), this.path, this.password));
    }

    /**
     * Logs in a user by local vault path and password.
     *
     * @param filepath    The path to the local .doorkey vault.
     * @param vaultPassword The password of the user.
     * @return The user that was logged in.
     * @throws AuthException If vault loading or decryption fails (such as bad password).
     */
    @Override
    public AbstractUser signInUser(String filepath, String vaultPassword) throws AuthException {
        this.path = filepath;
        this.password = vaultPassword;

        // Read the vault as JSON
        try {
            final String content = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)));

            final LocalVault vault = new LocalVault(new ArrayList<>(), this.path, this.password);
            vault.loadFromJSON(content);

            return new LocalUser(vault);
        }
        catch (java.nio.file.NoSuchFileException exception) {
            throw new AuthException(AuthErrorReason.REQUEST_ERROR, "File not found: " + this.path);
        }
        catch (java.io.IOException exception) {
            throw new AuthException(AuthErrorReason.UNKNOWN, "Failed to sign in user: " + exception.getMessage());
        }
        catch (exception.InvalidVaultItemException exception) {
            throw new AuthException(AuthErrorReason.WRONG_CREDENTIALS, "Failed to load vault item; wrong password?");
        }
    }

    /**
     * Add an item to the user's local vault.
     *
     * @param user The user to add the item to.
     * @param item The item to add to the user's vault.
     * @throws AuthException If the item could not be added to the user's vault.
     */
    @Override
    public void addVaultItem(AbstractUser user, AbstractVaultItem item) throws AuthException {
        if (!(user instanceof LocalUser)) {
            throw new IllegalArgumentException("User must be a LocalUser.");
        }

        user.getVault().addItem(item);

        // Need to write to the local path again
        final String exported = user.getVault().toJSON();
        try {
            java.nio.file.Files.write(java.nio.file.Paths.get(this.path), exported.getBytes());
        }
        catch (java.io.IOException exception) {
            throw new AuthException(AuthErrorReason.UNKNOWN, "Failed to add vault item: " + exception.getMessage());
        }
    }
}

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
     * @param path    The path to the local .doorkey vault.
     * @param password The password of the user.
     * @return The user that was signed up.
     * @throws AuthException Never.
     */
    @Override
    public AbstractUser signupUser(String path, String password) throws AuthException {
        this.path = path;
        this.password = password;
        return new LocalUser(new LocalVault(new ArrayList<>(), this.path, this.password));
    }

    /**
     * Logs in a user by local vault path and password.
     *
     * @param path    The path to the local .doorkey vault.
     * @param password The password of the user.
     * @return The user that was logged in.
     * @throws AuthException If vault loading or decryption fails (such as bad password).
     */
    @Override
    public AbstractUser signInUser(String path, String password) throws AuthException {
        this.path = path;
        this.password = password;

        // Read the vault as JSON
        try {
            String content = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)));

            LocalVault vault = new LocalVault(new ArrayList<>(), this.path, this.password);
            vault.loadFromJSON(content);

            return new LocalUser(vault);
        } catch (java.nio.file.NoSuchFileException e) {
            throw new AuthException(AuthErrorReason.REQUEST_ERROR, "File not found: " + this.path);
        } catch (Exception e) {
            throw new AuthException(AuthErrorReason.UNKNOWN, "Failed to sign in user: " + e.getMessage());
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
        String exported = user.getVault().toJSON();
        try {
            java.nio.file.Files.write(java.nio.file.Paths.get(this.path), exported.getBytes());
        } catch (Exception e) {
            throw new AuthException(AuthErrorReason.UNKNOWN, "Failed to add vault item: " + e.getMessage());
        }
    }
}

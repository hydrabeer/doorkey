package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * @throws IOException Never. (Other implementations of the DAO may throw, however).
     */
    @Override
    public AbstractUser signupUser(String filepath, String vaultPassword) throws IOException {
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
     * @throws IOException If there was an error reading/loading a local vault from disk.
     */
    @Override
    public AbstractUser signInUser(String filepath, String vaultPassword) throws IOException {
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
            throw new IOException("File not found: " + this.path);
        }
        catch (exception.InvalidVaultItemException exception) {
            throw new IOException("Failed to load invalid vault item; wrong password?");
        }
    }

    /**
     * Add an item to the user's local vault.
     *
     * @param user The user to add the item to.
     * @param item The item to add to the user's vault.
     * @throws IOException If there was an issue updating and saving the local vault to disk.
     */
    @Override
    public void addVaultItem(AbstractUser user, AbstractVaultItem item) throws IOException {
        if (!(user instanceof LocalUser)) {
            throw new IllegalArgumentException("User must be a LocalUser.");
        }

        user.getVault().addItem(item);

        saveVaultToDisk(user);
    }

    /**
     * Remove an item from the user's local .doorkey vault.
     *
     * @param user The user to remove the item from.
     * @param item The item to remove from the user's vault.
     * @throws IOException If there was an issue updating and saving the local vault to disk.
     */
    @Override
    public void removeVaultItem(AbstractUser user, AbstractVaultItem item) throws IOException {
        if (!(user instanceof LocalUser)) {
            throw new IllegalArgumentException("User must be a LocalUser.");
        }

        final List<AbstractVaultItem> vaultItems = user.getVault().getItems();
        vaultItems.remove(item);
        user.getVault().setItems(vaultItems);

        saveVaultToDisk(user);
    }

    /**
     * Save the current vault state to a local .doorkey file.
     * @param user The user to save the vault from.
     * @throws IOException If there was an issue updating and saving the local vault to disk.
     */
    private void saveVaultToDisk(AbstractUser user) throws IOException {
        final String exported = user.getVault().toJSON();
        java.nio.file.Files.write(java.nio.file.Paths.get(this.path), exported.getBytes());
    }
}

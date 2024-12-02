package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.crypto.InvalidCipherTextException;

import entity.AbstractUser;
import entity.AbstractVaultItem;
import entity.LocalUser;
import entity.LocalVault;
import interface_adapter.crypto.Utils;
import interface_adapter.crypto.cipher.ChaCha20Cipher;
import repository.UserRepository;

/**
 * Repository for user data.
 */
public class LocalVaultUserRepository implements UserRepository {
    private String path;
    private String password;
    private LocalUser currentUser;

    public LocalVaultUserRepository() {
    }

    /**
     * Signs up a user by local vault path and password.
     *
     * @param filepath    The path to the local .doorkey vault.
     * @param vaultPassword The password of the user.
     * @return The user that was signed up.
     */
    @Override
    public AbstractUser signupUser(String filepath, String vaultPassword) {
        this.path = filepath;
        this.password = vaultPassword;
        this.currentUser = new LocalUser(new LocalVault(new ArrayList<>(), this.path, this.password), this.password);
        return this.currentUser;
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

            final String decrypted = new ChaCha20Cipher().decrypt(password, Utils.decodeFromBase64(content));

            final LocalVault vault = new LocalVault(new ArrayList<>(), this.path, this.password);
            vault.loadFromJSON(decrypted);

            this.currentUser = new LocalUser(vault, this.password);
            return this.currentUser;
        }
        catch (java.nio.file.NoSuchFileException exception) {
            throw new IOException("File not found: " + this.path);
        }
        catch (exception.InvalidVaultItemException exception) {
            throw new IOException("Failed to load invalid vault item; wrong password?");
        }
        catch (InvalidCipherTextException | IllegalArgumentException exception) {
            throw new IOException("Unable to decrypt vault; wrong password?");
        }
    }

    @Override
    public void signOutUser() {
        this.currentUser = null;
    }

    @Override
    public AbstractUser getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Add an item to the user's local vault.
     *
     * @param item The item to add to the user's vault.
     * @throws IOException If there was an issue updating and saving the local vault to disk.
     */
    @Override
    public void addVaultItem(AbstractVaultItem item) throws IOException {
        if (currentUser == null) {
            throw new RuntimeException("Attempted to perform operation when the user is not signed in.");
        }

        currentUser.getVault().addItem(item);

        saveVaultToDisk(currentUser);
    }

    /**
     * Remove an item from the user's local .doorkey vault.
     *
     * @param item The item to remove from the user's vault.
     * @throws IOException If there was an issue updating and saving the local vault to disk.
     */
    @Override
    public void removeVaultItem(AbstractVaultItem item) throws IOException {
        if (currentUser == null) {
            throw new RuntimeException("Attempted to perform operation when the user is not signed in.");
        }

        final List<AbstractVaultItem> vaultItems = currentUser.getVault().getItems();
        vaultItems.remove(item);
        currentUser.getVault().setItems(vaultItems);

        saveVaultToDisk(currentUser);
    }

    /**
     * Save the current vault state to a local .doorkey file.
     * @param user The user to save the vault from.
     * @throws IOException If there was an issue updating and saving the local vault to disk.
     */
    private void saveVaultToDisk(LocalUser user) throws IOException {
        final String exported = user.getVault().toJSON();
        final String encrypted = Utils.encodeToBase64(new ChaCha20Cipher().encrypt(password, exported));
        java.nio.file.Files.write(java.nio.file.Paths.get(this.path), encrypted.getBytes());
    }
}

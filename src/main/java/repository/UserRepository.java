package repository;

import java.io.IOException;

import entity.AbstractUser;
import entity.AbstractVaultItem;
import exception.AuthException;

/**
 * Repository for user data.
 */
public interface UserRepository {
    /**
     * Signs up a user by email and password.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return The user that was signed up.
     * @throws AuthException If the user could not be signed up.
     * @throws IOException If there was an issue creating a local vault.
     */
    AbstractUser signupUser(String email, String password) throws AuthException, IOException;

    /**
     * Logs in a user by email (optional) and password.
     *
     * @param email    The email of the user, if using remote authentication. Null otherwise.
     * @param password The password of the user.
     * @return The user that was logged in.
     * @throws AuthException If the user could not be logged in.
     * @throws IOException If there was an issue saving the local vault to disk.
     */
    AbstractUser signInUser(String email, String password) throws AuthException, IOException;

    /**
     * Add an item to the user's vault.
     *
     * @param user The user to add the item to.
     * @param item The item to add to the user's vault.
     * @throws AuthException If the item could not be added to the user's vault.
     * @throws IOException If there was an issue updating and saving the local vault to disk.
     */
    void addVaultItem(AbstractUser user, AbstractVaultItem item) throws AuthException, IOException;

    /**
     * Remove an item from the user's vault.
     *
     * @param user The user to remove the item from.
     * @param item The item to remove from the user's vault.
     * @throws AuthException If the item could not be removed from the user's vault.
     * @throws IOException If there was an issue updating and saving the local vault to disk.
     */
    void removeVaultItem(AbstractUser user, AbstractVaultItem item) throws AuthException, IOException;
}

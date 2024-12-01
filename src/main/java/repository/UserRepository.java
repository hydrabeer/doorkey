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
     * @throws IOException If there was an issue loading the local vault from disk.
     */
    AbstractUser signInUser(String email, String password) throws AuthException, IOException;

    /**
     * Signs out the currently signed-in user.
     * If already signed out, does nothing.
     */
    void signOutUser();

    /**
     * Gets the currently signed-in user.
     * @return The currently signed-in user.
     */
    AbstractUser getCurrentUser();

    /**
     * Add an item to the user's vault.
     *
     * @param item The item to add to the user's vault.
     * @throws AuthException If the item could not be added to the user's vault.
     * @throws IOException If there was an issue updating and saving the local vault to disk.
     */
    void addVaultItem(AbstractVaultItem item) throws AuthException, IOException;

    /**
     * Remove an item from the user's vault.
     *
     * @param item The item to remove from the user's vault.
     * @throws AuthException If the item could not be removed from the user's vault.
     * @throws IOException If there was an issue updating and saving the local vault to disk.
     */
    void removeVaultItem(AbstractVaultItem item) throws AuthException, IOException;
}

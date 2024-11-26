package repository;

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
     */
    AbstractUser signupUser(String email, String password) throws AuthException;

    /**
     * Logs in a user by email (optional) and password.
     *
     * @param email    The email of the user, if using remote authentication. Null otherwise.
     * @param password The password of the user.
     * @return The user that was logged in.
     * @throws AuthException If the user could not be logged in.
     */
    AbstractUser signInUser(String email, String password) throws AuthException;

    /**
     * Add an item to the user's vault.
     *
     * @param user The user to add the item to.
     * @param item The item to add to the user's vault.
     * @throws AuthException If the item could not be added to the user's vault.
     */
    void addVaultItem(AbstractUser user, AbstractVaultItem item) throws AuthException;

    /**
     * Remove an item from the user's vault.
     *
     * @param user The user to remove the item from.
     * @param item The item to remove from the user's vault.
     * @throws AuthException If the item could not be removed from the user's vault.
     */
    void removeVaultItem(AbstractUser user, AbstractVaultItem item) throws AuthException;
}

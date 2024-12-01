package repository;

import java.util.Optional;

/**
 * Repository provider for Doorkey.
 * At the application startup, it is impossible to know which repository to use, as the user
 * may choose to 1) use locally, 2) use via sync. Therefore, the repository has to be dynamically
 * determined as passed around in the application AFTER the login screen was passed.
 */
public interface RepositoryProvider {
    /**
     * Unchecked-get the repository for the application.
     * This operation is useful when we know the repository has to be accessed after login/signup.
     * For example, if a user clicks on 'add item' button, the invariant is that the user has
     * already logged in. Therefore, the repository is guaranteed to be set.
     * @return the repository for the application.
     * @throws RuntimeException if the repository is not set.
     */
    UserRepository getRepositoryUnchecked();

    /**
     * Get the repository for the application.
     * @return the repository for the application.
     */
    Optional<UserRepository> getRepository();

    /**
     * Set the repository for the application.
     * @param repository the repository to set.
     */
    void setRepository(UserRepository repository);

    /**
     * Set the repository to null.
     */
    void clearRepository();
}

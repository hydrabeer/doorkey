package repository;

import exception.AuthException;

/**
 * The AuthRepository interface.
 */
public interface AuthRepository {
    /**
     * Signs up a user.
     *
     * @param email    The email.
     * @param password The password.
     * @return The AuthResponse.
     * @throws AuthException If an error occurs.
     */
    AuthResponse signUp(String email, String password) throws AuthException;

    /**
     * Signs in a user.
     *
     * @param email    The email.
     * @param password The password.
     * @return The AuthResponse.
     * @throws AuthException If an error occurs.
     */
    AuthResponse signIn(String email, String password) throws AuthException;

    /**
     * Refreshes the user's refresh token.
     *
     * @param refreshToken The refresh token.
     * @return The AuthResponse.
     * @throws AuthException If an error occurs.
     */
    AuthResponse refreshToken(String refreshToken) throws AuthException;
}

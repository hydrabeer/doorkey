package interface_adapter.crypto.key_generator;

/**
 * An interface that produces a secure encryption key from a given password.
 */
public interface KeyGenerator {
    /**
     * Derive a secure encryption key from a user-provided password string.
     * @param password the user-provided password
     * @param salt a securely random array of bytes; ideally 16 bytes in length
     * @return a 32-byte array containing a secure encryption key
     */
    byte[] deriveKeyFromPassword(String password, byte[] salt);
}

package interface_adapter.crypto.random_generator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * A concrete implementation of RandomGenerator using Java's built-in SecureRandom.
 */
public class SecureRandomGenerator implements RandomGenerator {
    private static SecureRandom random;

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        }
        catch (NoSuchAlgorithmException exception) {
            random = new SecureRandom();
        }
    }

    /**
     * Generate securely random bytes suitable for cryptography.
     * @param size the number of random bytes to return
     * @return a byte array with length 'size' filled with random bytes
     */
    public byte[] getRandomBytes(int size) {
        final byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }
}

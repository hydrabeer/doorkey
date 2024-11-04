package interface_adapter.crypto;

import java.security.SecureRandom;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

public class CryptoAdapter {
    private static final SecureRandom random = new SecureRandom();
    private static final int ARGON2_VERSION = Argon2Parameters.ARGON2_VERSION_13;
    private static final int ARGON2_TYPE = Argon2Parameters.ARGON2_d;
    private static final int ARGON2_ITERATIONS = 4;
    private static final int ARGON2_MEM_AS_KB = 1048576;
    private static final int ARGON2_PARALLELISM = 4;

    /**
     * Generate securely random bytes suitable for cryptography.
     * @param size the number of random bytes to return
     * @return a byte array with length 'size' filled with random bytes
     */
    public static byte[] getRandomBytes(int size) {
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }

    private static Argon2Parameters getParameters(byte[] salt) {
        return new Argon2Parameters.Builder(ARGON2_TYPE)
            .withVersion(ARGON2_VERSION)
            .withIterations(ARGON2_ITERATIONS)
            .withMemoryAsKB(ARGON2_MEM_AS_KB)
            .withParallelism(ARGON2_PARALLELISM)
            .withSalt(salt)
            .build();
    }

    /**
     * Derive a secure encryption key from a user-provided password string.
     * @param password the user-provided password
     * @param salt a securely random array of bytes; ideally 16 bytes in length
     * @return a 32-byte array containing a secure encryption key
     */
    public static byte[] deriveKey(String password, byte[] salt) {
        Argon2Parameters params = CryptoAdapter.getParameters(salt);
        Argon2BytesGenerator argon = new Argon2BytesGenerator();
        argon.init(params);

        byte[] key = new byte[32];
        argon.generateBytes(password.getBytes(), key);
        return key;
    }
}

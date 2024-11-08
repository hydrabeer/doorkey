package interface_adapter.crypto.random_generator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecureRandomGenerator implements RandomGenerator {
    private static SecureRandom random;

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            random = new SecureRandom();
        }
    }

    public byte[] getRandomBytes(int size) {
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }
}

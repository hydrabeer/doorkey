package interface_adapter.crypto.random_generator;

import java.security.SecureRandom;

public class SecureRandomGenerator implements RandomGenerator {
    private static final SecureRandom random = new SecureRandom();

    public byte[] getRandomBytes(int size) {
        byte[] bytes = new byte[size];
        random.nextBytes(bytes);
        return bytes;
    }
}

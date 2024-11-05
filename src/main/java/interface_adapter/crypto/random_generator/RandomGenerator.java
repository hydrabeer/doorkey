package interface_adapter.crypto.random_generator;

public interface RandomGenerator {
    /**
     * Generate securely random bytes suitable for cryptography.
     * @param size the number of random bytes to return
     * @return a byte array with length 'size' filled with random bytes
     */
    public byte[] getRandomBytes(int size);
}

package interface_adapter.crypto.key_generator;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

/**
 * A KeyGenerator concrete implementation using the memory-hard Argon2 KDF.
 */
public class Argon2KeyGenerator implements KeyGenerator {

    private static final int ARGON2_VERSION = Argon2Parameters.ARGON2_VERSION_13;
    private static final int ARGON2_TYPE = Argon2Parameters.ARGON2_d;
    private static final int ARGON2_ITERATIONS = 4;
    private static final int ARGON2_MEM_AS_KB = 262144;
    private static final int ARGON2_PARALLELISM = 4;
    private static final int ARGON2_OUTPUT_LENGTH = 32;

    private static Argon2Parameters getParameters(byte[] salt) {
        return new Argon2Parameters.Builder(ARGON2_TYPE)
                .withVersion(ARGON2_VERSION)
                .withIterations(ARGON2_ITERATIONS)
                .withMemoryAsKB(ARGON2_MEM_AS_KB)
                .withParallelism(ARGON2_PARALLELISM)
                .withSalt(salt)
                .build();
    }

    @Override
    public byte[] deriveKeyFromPassword(String password, byte[] salt) {
        final Argon2Parameters params = Argon2KeyGenerator.getParameters(salt);
        final Argon2BytesGenerator argon = new Argon2BytesGenerator();
        argon.init(params);

        final byte[] key = new byte[ARGON2_OUTPUT_LENGTH];
        argon.generateBytes(password.getBytes(), key);
        return key;
    }
}

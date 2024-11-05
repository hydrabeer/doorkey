package interface_adapter.crypto.key_generator;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

public class Argon2KeyGenerator implements KeyGenerator {
    private static final int ARGON2_VERSION = Argon2Parameters.ARGON2_VERSION_13;
    private static final int ARGON2_TYPE = Argon2Parameters.ARGON2_d;
    private static final int ARGON2_ITERATIONS = 4;
    private static final int ARGON2_MEM_AS_KB = 1048576;
    private static final int ARGON2_PARALLELISM = 4;

    private static Argon2Parameters getParameters(byte[] salt) {
        return new Argon2Parameters.Builder(ARGON2_TYPE)
            .withVersion(ARGON2_VERSION)
            .withIterations(ARGON2_ITERATIONS)
            .withMemoryAsKB(ARGON2_MEM_AS_KB)
            .withParallelism(ARGON2_PARALLELISM)
            .withSalt(salt)
            .build();
    }

    public byte[] deriveKeyFromPassword(String password, byte[] salt) {
        Argon2Parameters params = Argon2KeyGenerator.getParameters(salt);
        Argon2BytesGenerator argon = new Argon2BytesGenerator();
        argon.init(params);

        byte[] key = new byte[32];
        argon.generateBytes(password.getBytes(), key);
        return key;
    }
}

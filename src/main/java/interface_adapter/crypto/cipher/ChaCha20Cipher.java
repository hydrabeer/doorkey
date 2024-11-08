package interface_adapter.crypto.cipher;

import java.nio.charset.StandardCharsets;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.modes.ChaCha20Poly1305;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.util.Arrays;

import interface_adapter.crypto.key_generator.Argon2KeyGenerator;
import interface_adapter.crypto.key_generator.KeyGenerator;
import interface_adapter.crypto.random_generator.RandomGenerator;
import interface_adapter.crypto.random_generator.SecureRandomGenerator;

public class ChaCha20Cipher implements Cipher {
    private final KeyGenerator keyGenerator = new Argon2KeyGenerator();
    private final RandomGenerator randomGenerator = new SecureRandomGenerator();
    private byte[] key;
    private byte[] salt;
    private byte[] nonce;

    public byte[] encrypt(String password, String input) {
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        salt = randomGenerator.getRandomBytes(16);
        nonce = randomGenerator.getRandomBytes(12);
        key = keyGenerator.deriveKeyFromPassword(password, salt);

        ChaCha20Poly1305 cipher = new ChaCha20Poly1305();
        CipherParameters params = getCipherParams();
        cipher.init(true, params);

        byte[] out = new byte[cipher.getOutputSize(inputBytes.length)];
        int offset = cipher.processBytes(inputBytes, 0, inputBytes.length, out, 0);
        try {
            cipher.doFinal(out, offset);
        } catch (Exception e) {
            // Ignore the exception as it can only be raised during decryption (encryption is always valid)
        }
        return Arrays.concatenate(salt, nonce, out);
    }

    public String decrypt(String password, byte[] input) throws InvalidCipherTextException {
        salt = Arrays.copyOfRange(input, 0, 16);
        nonce = Arrays.copyOfRange(input, 16, 28);
        byte[] inputBytes = Arrays.copyOfRange(input, 28, input.length);
        key = keyGenerator.deriveKeyFromPassword(password, salt);

        ChaCha20Poly1305 cipher = new ChaCha20Poly1305();
        CipherParameters params = getCipherParams();
        cipher.init(false, params);

        byte[] out = new byte[cipher.getOutputSize(inputBytes.length)];
        int offset = cipher.processBytes(inputBytes, 0, inputBytes.length, out, 0);
        cipher.doFinal(out, offset);

        return new String(out, StandardCharsets.UTF_8);
    }

    private CipherParameters getCipherParams() {
        KeyParameter keyParam = getKeyParam();
        return new AEADParameters(keyParam, 128, nonce);
    }

    private KeyParameter getKeyParam() {
        return new KeyParameter(key);
    }
}

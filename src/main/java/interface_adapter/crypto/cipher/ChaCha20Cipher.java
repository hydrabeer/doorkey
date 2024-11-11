package interface_adapter.crypto.cipher;

import java.nio.charset.StandardCharsets;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.modes.ChaCha20Poly1305;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;

import interface_adapter.crypto.key_generator.Argon2KeyGenerator;
import interface_adapter.crypto.key_generator.KeyGenerator;
import interface_adapter.crypto.random_generator.RandomGenerator;
import interface_adapter.crypto.random_generator.SecureRandomGenerator;

/**
 * Implementation of the ChaCha20 cipher algorithm.
 */
public class ChaCha20Cipher implements Cipher {
    private final int saltLength = 16;
    private final int nonceLength = 12;
    private final KeyGenerator keyGenerator = new Argon2KeyGenerator();
    private final RandomGenerator randomGenerator = new SecureRandomGenerator();
    private byte[] key;
    private byte[] salt;
    private byte[] nonce;

    /**
     * Encrypt the given input string with the given password.
     * <hr><strong>Note: you must create a new Cipher() for every encryption/decryption operation!</strong>
     * @param password the password string
     * @param input the input string to encrypt
     * @return a byte array containing the encrypted data
     */
    public byte[] encrypt(String password, String input) {
        final byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        salt = randomGenerator.getRandomBytes(saltLength);
        nonce = randomGenerator.getRandomBytes(nonceLength);
        key = keyGenerator.deriveKeyFromPassword(password, salt);

        final ChaCha20Poly1305 cipher = new ChaCha20Poly1305();
        final CipherParameters params = getCipherParams();
        cipher.init(true, params);

        final byte[] out = new byte[cipher.getOutputSize(inputBytes.length)];
        final int offset = cipher.processBytes(inputBytes, 0, inputBytes.length, out, 0);
        try {
            cipher.doFinal(out, offset);
        }
        catch (IllegalStateException | InvalidCipherTextException exception) {
            // Ignore the exception as it can only be raised during decryption (encryption is always valid)
        }
        return Arrays.concatenate(salt, nonce, out);
    }

    /**
     * Decrypt the given ciphertext with the provided password.
     * <hr><strong>Note: you must create a new Cipher() for every encryption/decryption operation!</strong>
     * @param password the password string
     * @param input the byte array of encrypted bytes to decrypt
     * @return the original plaintext string as provided to encrypt()
     * @throws InvalidCipherTextException if the password is incorrect or input is invalid
     */
    public String decrypt(String password, byte[] input) throws InvalidCipherTextException {
        salt = Arrays.copyOfRange(input, 0, saltLength);
        nonce = Arrays.copyOfRange(input, saltLength, saltLength + nonceLength);
        final byte[] inputBytes = Arrays.copyOfRange(input, saltLength + nonceLength, input.length);
        key = keyGenerator.deriveKeyFromPassword(password, salt);

        final ChaCha20Poly1305 cipher = new ChaCha20Poly1305();
        final CipherParameters params = getCipherParams();
        cipher.init(false, params);

        final byte[] out = new byte[cipher.getOutputSize(inputBytes.length)];
        final int offset = cipher.processBytes(inputBytes, 0, inputBytes.length, out, 0);
        cipher.doFinal(out, offset);

        return new String(out, StandardCharsets.UTF_8);
    }

    private CipherParameters getCipherParams() {
        final KeyParameter keyParam = getKeyParam();
        final int macBits = 128;
        return new AEADParameters(keyParam, macBits, nonce);
    }

    private KeyParameter getKeyParam() {
        return new KeyParameter(key);
    }
}

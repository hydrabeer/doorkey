package interface_adapter.cipher;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import interface_adapter.crypto.cipher.ChaCha20Cipher;
import interface_adapter.crypto.random_generator.SecureRandomGenerator;
import interface_adapter.crypto.random_generator.RandomGenerator;

public class CipherTest {
    private static ChaCha20Cipher cipher = new ChaCha20Cipher();
    private static final String plaintext1 = "hello world";
    private static final String plaintext2 = "this is the way";
    private static final String password1 = "my_Pa55w0rD";
    private static final String password2 = "5trong_pwD";

    @BeforeEach
    public void resetCipher() {
        cipher = new ChaCha20Cipher();
    }

    @Test
    public void testEncryptSuccessAndLength() {
        byte[] ciphertext = cipher.encrypt(password1, plaintext1);
        assertTrue(ciphertext.length == plaintext1.length() + 16 + 12 + 16);

        byte[] zeros = new byte[ciphertext.length];
        assertFalse(Arrays.areEqual(ciphertext, zeros));
    }

    @Test
    public void testDecryptSuccessAndLength() throws InvalidCipherTextException {
        byte[] ciphertext = cipher.encrypt(password1, plaintext1);
        String decrypted = cipher.decrypt(password1, ciphertext);
        assertTrue(plaintext1.equals(decrypted));
    }

    @Test
    public void testDecryptLargeInput() throws InvalidCipherTextException {
        RandomGenerator randomGenerator = new SecureRandomGenerator();
        byte[] data = randomGenerator.getRandomBytes(1048576);
        String plaintext = new String(data, StandardCharsets.UTF_8);

        byte[] ciphertext = cipher.encrypt(password1, plaintext);
        String decrypted = cipher.decrypt(password1, ciphertext);
        assertTrue(plaintext.equals(decrypted));
    }

    @Test
    public void testEncryptNonDeterministic() {
        byte[] ciphertext1 = cipher.encrypt(password1, plaintext1);
        resetCipher();
        byte[] ciphertext2 = cipher.encrypt(password1, plaintext1);
        assertFalse(Arrays.areEqual(ciphertext1, ciphertext2));
    }

    @Test
    public void testEncryptDifferentPasswordsNonDeterministic() {
        byte[] ciphertext1 = cipher.encrypt(password1, plaintext1);
        resetCipher();
        byte[] ciphertext2 = cipher.encrypt(password2, plaintext1);
        assertFalse(Arrays.areEqual(ciphertext1, ciphertext2));
        assertTrue(ciphertext1.length == ciphertext2.length);
    }

    @Test
    public void testEncryptDifferentLengthPlaintexts() {
        byte[] ciphertext1 = cipher.encrypt(password1, plaintext1);
        resetCipher();
        byte[] ciphertext2 = cipher.encrypt(password2, plaintext2);
        assertFalse(ciphertext1.length == ciphertext2.length);
    }

    @Test
    public void testDecryptWithWrongPassword() {
        byte[] ciphertext = cipher.encrypt(password1, plaintext1);
        assertThrows(InvalidCipherTextException.class, () -> {
            cipher.decrypt(password2, ciphertext);
        });
    }
}

package interface_adapter.key_generator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import interface_adapter.crypto.key_generator.Argon2KeyGenerator;
import interface_adapter.crypto.key_generator.KeyGenerator;
import interface_adapter.crypto.random_generator.RandomGenerator;
import interface_adapter.crypto.random_generator.SecureRandomGenerator;

public class KeyGeneratorTest {
    private static KeyGenerator keyGen = new Argon2KeyGenerator();
    private static RandomGenerator randGen = new SecureRandomGenerator();

    @Test
    public void testDeriveKeyFromPasswordLengthAndNonEmpty() {
        byte[] zeros = new byte[32];
        byte[] actual = keyGen.deriveKeyFromPassword("00000000", new byte[16]);
        assertTrue(actual.length == 32);
        assertFalse(Arrays.equals(zeros, actual));
    }

    @Test
    public void testDeriveKeyFromPasswordDifferentPasswords() {
        String password1 = "my5ecur3pa55w0rd";
        String password2 = "my2nd53curepaswd";
        byte[] key1 = keyGen.deriveKeyFromPassword(password1, new byte[16]);
        byte[] key2 = keyGen.deriveKeyFromPassword(password2, new byte[16]);
        assertFalse(Arrays.equals(key1, key2));
    }

    @Test
    public void testDeriveKeyFromPasswordDifferentSalts() {
        byte[] salt1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] salt2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2};
        byte[] key1 = keyGen.deriveKeyFromPassword("00000000", salt1);
        byte[] key2 = keyGen.deriveKeyFromPassword("00000000", salt2);
        assertFalse(Arrays.equals(key1, key2));
    }

    @Test
    public void testDeriveKeyFromPasswordDeterministic() {
        byte[] salt = randGen.getRandomBytes(16);
        byte[] key1 = keyGen.deriveKeyFromPassword("my5ecur3pa55w0rd", salt);
        byte[] key2 = keyGen.deriveKeyFromPassword("my5ecur3pa55w0rd", salt);
        assertTrue(Arrays.equals(key1, key2));
    }
}

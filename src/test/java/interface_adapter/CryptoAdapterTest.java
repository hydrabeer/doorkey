package interface_adapter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import interface_adapter.crypto.CryptoAdapter;

public class CryptoAdapterTest {

    @Test
    public void testGetRandomBytes() {
        byte[] zeros = new byte[16];
        byte[] random = CryptoAdapter.getRandomBytes(16);
        assertTrue(random.length == 16);
        assertFalse(Arrays.equals(zeros, random));
    }

    @Test
    public void testDeriveKeyLengthAndNonEmpty() {
        byte[] zeros = new byte[32];
        byte[] actual = CryptoAdapter.deriveKey("00000000", new byte[16]);
        assertTrue(actual.length == 32);
        assertFalse(Arrays.equals(zeros, actual));
    }

    @Test
    public void testDeriveKeyDifferentPasswords() {
        String password1 = "my5ecur3pa55w0rd";
        String password2 = "my2nd53curepaswd";
        byte[] key1 = CryptoAdapter.deriveKey(password1, new byte[16]);
        byte[] key2 = CryptoAdapter.deriveKey(password2, new byte[16]);
        assertFalse(Arrays.equals(key1, key2));
    }

    @Test
    public void testDeriveKeyDifferentSalts() {
        byte[] salt1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] salt2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2};
        byte[] key1 = CryptoAdapter.deriveKey("00000000", salt1);
        byte[] key2 = CryptoAdapter.deriveKey("00000000", salt2);
        assertFalse(Arrays.equals(key1, key2));
    }

    @Test
    public void testDeriveKeyDeterministic() {
        byte[] salt = CryptoAdapter.getRandomBytes(16);
        byte[] key1 = CryptoAdapter.deriveKey("my5ecur3pa55w0rd", salt);
        byte[] key2 = CryptoAdapter.deriveKey("my5ecur3pa55w0rd", salt);
        assertTrue(Arrays.equals(key1, key2));
    }
}

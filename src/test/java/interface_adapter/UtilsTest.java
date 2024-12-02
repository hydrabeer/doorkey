package interface_adapter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import interface_adapter.crypto.Utils;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilsTest {
    @Test
    public void bytesToBase64Test() {
        byte[] data = "hello".getBytes();
        String base64 = Utils.encodeToBase64(data);
        byte[] decoded = Utils.decodeFromBase64(base64);
        assertTrue(Arrays.equals(decoded, "hello".getBytes()));
    }

    @Test
    public void invalidBase64ToBytesTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Utils.decodeFromBase64("invalid_{base64}");
        });
    }
}

package interface_adapter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import interface_adapter.crypto.Utils;

public class UtilsTest {
    @Test
    public void bytesToBase64Test() {
        byte[] data = "hello".getBytes();
        String base64 = Utils.encodeToBase64(data);
        byte[] decoded = Utils.decodeFromBase64(base64);
        assertTrue(Arrays.equals(decoded, "hello".getBytes()));
    }
}

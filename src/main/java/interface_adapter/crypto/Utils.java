package interface_adapter.crypto;

import java.util.Base64;

/**
 * Generic helpers for cryptography.
 */
public class Utils {
    /**
     * Encodes a byte array to a Base64 string.
     *
     * @param bytes the byte array to encode
     * @return the encoded Base64 string
     */
    public static String encodeToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Decodes a Base64 string to a byte array.
     *
     * @param base64 the Base64 string to decode
     * @return the decoded byte array
     */
    public static byte[] decodeFromBase64(String base64) throws IllegalArgumentException {
        return Base64.getDecoder().decode(base64);
    }
}

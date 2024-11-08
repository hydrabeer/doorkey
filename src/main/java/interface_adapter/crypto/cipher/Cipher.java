package interface_adapter.crypto.cipher;

import org.bouncycastle.crypto.InvalidCipherTextException;

public interface Cipher {
    /**
     * Encrypt the given input string with the given password.
     * <hr><strong>Note: you must create a new Cipher() for every encryption/decryption operation!</strong>
     * @param password the password string
     * @param input the input string to encrypt
     * @return a byte array containing the encrypted data
     */
    public byte[] encrypt(String password, String input);

    /**
     * Decrypt the given ciphertext with the provided password.
     * <hr><strong>Note: you must create a new Cipher() for every encryption/decryption operation!</strong>
     * @param password the password string
     * @param input the byte array of encrypted bytes to decrypt
     * @return the original plaintext string as provided to encrypt()
     * @throws InvalidCipherTextException if the password is incorrect or input is invalid
     */
    public String decrypt(String password, byte[] input) throws InvalidCipherTextException;
}

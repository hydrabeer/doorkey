package exception;

/**
 * Thrown if the JSON is malformed or the vault item's type is unknown.
 */
public class InvalidVaultItemException extends Exception {
    public InvalidVaultItemException(String message) {
        super(message);
    }
}

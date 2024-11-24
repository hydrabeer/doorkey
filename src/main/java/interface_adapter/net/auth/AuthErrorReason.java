package interface_adapter.net.auth;

/**
 * Reasons for an authentication error.
 */
public enum AuthErrorReason {
    WRONG_CREDENTIALS,
    REQUEST_ERROR,
    EMAIL_EXISTS,
    UNKNOWN,
}

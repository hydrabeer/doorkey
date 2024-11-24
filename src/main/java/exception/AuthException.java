package exception;

import interface_adapter.net.auth.AuthErrorReason;

/**
 * Thrown if there is an error signing up or logging in.
 */
public class AuthException extends Exception {
    private final AuthErrorReason reason;

    public AuthException(AuthErrorReason reason, String message) {
        super(reason.name() + ": " + message);
        this.reason = reason;
    }

    public AuthErrorReason getReason() {
        return this.reason;
    }

    /**
     * Returns a view-appropriate error message.
     *
     * @return The error message.
     */
    public String getViewMessage() {
        return switch (this.reason) {
            case REQUEST_ERROR -> "Request error.";
            case WRONG_CREDENTIALS -> "Wrong credentials.";
            case EMAIL_EXISTS -> "Email already exists.";
            case UNKNOWN -> "Unknown error.";
        };
    }
}

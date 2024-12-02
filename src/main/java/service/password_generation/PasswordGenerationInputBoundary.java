package service.password_generation;

/**
 * The input boundary interface for the password generation use case.
 */
public interface PasswordGenerationInputBoundary {
    /**
     * Generates a password.
     */
    void generate();
}

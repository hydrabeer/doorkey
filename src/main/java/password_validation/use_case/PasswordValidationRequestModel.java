package password_validation.use_case;

/**
 * The request model for the password validation use case. Carries data from the
 * controller to the interactor.
 */
public class PasswordValidationRequestModel {

    private final String password;
    private final boolean enforceEntropy;

    /**
     * Constructs a new PasswordValidationRequestModel with the specified
     * password and context.
     *
     * @param password The password input by the user.
     * @param enforceEntropy True if validation is during signup, else false.
     */
    public PasswordValidationRequestModel(String password, boolean enforceEntropy) {
        this.password = password;
        this.enforceEntropy = enforceEntropy;
    }

    /**
     * Retrieves the password input by the user.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Indicates whether the validation requires entropy to be enforced.
     *
     * @return True if entropy is enforced; false otherwise.
     */
    public boolean enforceEntropy() {
        return enforceEntropy;
    }
}

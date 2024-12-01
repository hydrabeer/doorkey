package service.password_validation;

/**
 * The input boundary interface for the password validation use case.
 */
public interface PasswordValidationInputBoundary {

    /**
     * Validates the password based on the provided request model.
     *
     * @param requestModel The request model containing the password and context information.
     */
    void validate(PasswordValidationRequestModel requestModel);
}

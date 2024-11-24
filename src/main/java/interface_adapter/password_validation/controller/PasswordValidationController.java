package interface_adapter.password_validation.controller;

import password_validation.use_case.PasswordValidationInputBoundary;
import password_validation.use_case.PasswordValidationRequestModel;

/**
 * The controller for the password validation use case.
 */
public class PasswordValidationController {
    private final PasswordValidationInputBoundary interactor;

    public PasswordValidationController(PasswordValidationInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Validates the provided password based on specified criteria.
     *
     * @param password        The password to validate.
     * @param enforceEntropy  Indicates whether entropy requirements should be enforced.
     */
    public void validatePassword(String password, boolean enforceEntropy) {

        final PasswordValidationRequestModel requestModel = 
            new PasswordValidationRequestModel(password, enforceEntropy);
        interactor.validate(requestModel);
    }
}

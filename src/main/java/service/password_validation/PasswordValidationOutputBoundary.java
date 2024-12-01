package service.password_validation;

/**
 * Presents the password validation response model to Presenter.
 */
public interface PasswordValidationOutputBoundary {

    /**
     * Performs the results of password validation to the presenter.
     *
     * @param responseModel is the response model with the validation results.
     */
    void present(PasswordValidationResponseModel responseModel);
}

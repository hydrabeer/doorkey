package service.password_generation;

/**
 * The output boundary interface for the password generation use case.
 */
public interface PasswordGenerationOutputBoundary {

    /**
     * Presents the results of the password generation to the presenter.
     *
     * @param responseModel The response model containing generation results.
     */
    void present(PasswordGenerationResponseModel responseModel);
}

package service.password_generation;

/**
 * The response model for the password generation use case.
 */
public class PasswordGenerationResponseModel {

    private final String generatedPassword;
    private final boolean generationSuccess;
    private final String errorMessage;

    public PasswordGenerationResponseModel(String generatedPassword, boolean generationSuccess, String errorMessage) {
        this.generatedPassword = generatedPassword;
        this.generationSuccess = generationSuccess;
        this.errorMessage = errorMessage;
    }

    public String getGeneratedPassword() {
        return generatedPassword;
    }

    public boolean isGenerationSuccess() {
        return generationSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

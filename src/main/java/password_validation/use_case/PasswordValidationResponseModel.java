package password_validation.use_case;

/**
 * The response model for the password validation use case. Carries validation
 * results from the interactor to the presenter.
 */
public class PasswordValidationResponseModel {

    private final boolean meetsRequirements;
    private final int entropy;
    private final boolean isValid;
    private final boolean lengthReq;
    private final boolean upperLowerReq;
    private final boolean numericReq;
    private final boolean specialCharReq;

    public PasswordValidationResponseModel(boolean meetsRequirements, int entropy, boolean isValid,
            boolean lengthReq, boolean upperLowerReq,
            boolean numericReq, boolean specialCharReq) {

        this.meetsRequirements = meetsRequirements;
        this.entropy = entropy;
        this.isValid = isValid;
        this.lengthReq = lengthReq;
        this.upperLowerReq = upperLowerReq;
        this.numericReq = numericReq;
        this.specialCharReq = specialCharReq;
    }

    public boolean isMeetsRequirements() {
        return meetsRequirements;
    }

    public int getEntropy() {
        return entropy;
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean isLengthReq() {
        return lengthReq;
    }

    public boolean isUpperLowerReq() {
        return upperLowerReq;
    }

    public boolean isNumericReq() {
        return numericReq;
    }

    public boolean isSpecialCharReq() {
        return specialCharReq;
    }
}

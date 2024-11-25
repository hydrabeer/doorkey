package service.password_validation;

import java.util.regex.Pattern;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

/**
 * The interactor for the password validation use case. Implements the
 * application-specific business rules for validating passwords.
 */
public class PasswordValidationInteractor implements PasswordValidationInputBoundary {

    private final PasswordValidationOutputBoundary presenter;

    public PasswordValidationInteractor(PasswordValidationOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void validate(PasswordValidationRequestModel requestModel) {
        final String password = requestModel.getPassword();
        final boolean enforceEntropy = requestModel.enforceEntropy();

        final boolean lengthReq = password.length() >= 8;
        final boolean upperLowerReq = Pattern.compile("[A-Z]").matcher(password).find() 
            && Pattern.compile("[a-z]").matcher(password).find();
        final boolean numericReq = Pattern.compile("\\d").matcher(password).find();
        final boolean specialCharReq = Pattern.compile("[!@#$%^&*(),.?\\\":{}|<>]").matcher(password).find();

        final int entropy = calculateEntropy(password);

        final boolean meetsRequirements = lengthReq && upperLowerReq && numericReq && specialCharReq && entropy >= 2;

        final boolean isValid;
        if (enforceEntropy) {
            isValid = meetsRequirements;
        } 
        else {
            // Always valid during vault item creation/editing
            isValid = true;
        }

        final PasswordValidationResponseModel responseModel = new PasswordValidationResponseModel(
                meetsRequirements,
                entropy,
                isValid,
                lengthReq,
                upperLowerReq,
                numericReq,
                specialCharReq
        );

        presenter.present(responseModel);
    }

    /**
     * Calculates the entropy of the password.
     *
     * @param password The password to analyze.
     * @return The calculated entropy score.
     */
    private int calculateEntropy(String password) {
        final Zxcvbn zxcvbn = new Zxcvbn();
        final Strength strength = zxcvbn.measure(password);
        return strength.getScore();
    }
}

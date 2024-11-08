package password_validation.validation;

import password_validation.entropy.EntropyCheck;
import password_validation.requirements.RequirementsCheck;

public class EntropyAndRequirementsValidator implements PasswordValidator {

    private final RequirementsCheck requirementsCheck;
    private final EntropyCheck entropyCheck;

    public EntropyAndRequirementsValidator(RequirementsCheck requirementsCheck, EntropyCheck entropyCheck) {
        this.requirementsCheck = requirementsCheck;
        this.entropyCheck = entropyCheck;
    }

    @Override
    public boolean validate(String password) {
        return requirementsCheck.meetsRequirements(password) && entropyCheck.calculateEntropy(password) > 3.0;
    }
}

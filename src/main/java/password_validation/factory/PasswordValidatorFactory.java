package password_validation.factory;

import password_validation.entropy.EntropyCheck;
import password_validation.entropy.StatisticalEntropyCheck;
import password_validation.requirements.BasicRequirementsCheck;
import password_validation.requirements.RequirementsCheck;
import password_validation.validation.EntropyAndRequirementsValidator;
import password_validation.validation.PasswordValidator;
import password_validation.validation.RequirementsOnlyValidator;

public class PasswordValidatorFactory {

    public static PasswordValidator createRequirementsOnlyValidator(
            boolean requireUpper, boolean requireLower, boolean requireSpecial, boolean requireNumeric,
            int minLength, int maxLength) {

        RequirementsCheck requirementsCheck = new BasicRequirementsCheck(requireUpper, requireLower, requireSpecial, requireNumeric, minLength, maxLength);
        return new RequirementsOnlyValidator(requirementsCheck);
    }

    public static PasswordValidator createEntropyAndRequirementsValidator(
            boolean requireUpper, boolean requireLower, boolean requireSpecial, boolean requireNumeric,
            int minLength, int maxLength, double entropyThreshold) {

        RequirementsCheck requirementsCheck = new BasicRequirementsCheck(requireUpper, requireLower, requireSpecial, requireNumeric, minLength, maxLength);
        EntropyCheck entropyCheck = new StatisticalEntropyCheck(entropyThreshold);
        return new EntropyAndRequirementsValidator(requirementsCheck, entropyCheck);
    }
}

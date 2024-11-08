package password_validation.validation;

import password_validation.requirements.RequirementsCheck;

public class RequirementsOnlyValidator implements PasswordValidator {

    private final RequirementsCheck requirementsCheck;

    public RequirementsOnlyValidator(RequirementsCheck requirementsCheck) {
        this.requirementsCheck = requirementsCheck;
    }

    @Override
    public boolean validate(String password) {
        return requirementsCheck.meetsRequirements(password);
    }
}

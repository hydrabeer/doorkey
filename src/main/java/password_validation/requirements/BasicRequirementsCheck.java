package password_validation.requirements;

public class BasicRequirementsCheck implements RequirementsCheck {

    private boolean requireUpper;
    private boolean requireLower;
    private boolean requireSpecial;
    private boolean requireNumeric;
    private int minLength;
    private int maxLength;

    public BasicRequirementsCheck(boolean requireUpper, boolean requireLower, boolean requireSpecial,
            boolean requireNumeric, int minLength, int maxLength) {
        this.requireUpper = requireUpper;
        this.requireLower = requireLower;
        this.requireSpecial = requireSpecial;
        this.requireNumeric = requireNumeric;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public boolean meetsRequirements(String password) {
        // Incomplete
        return false;
    }
}

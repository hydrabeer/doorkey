package password_validation.entity;

/**
 * A simple implementation of a Password class.
 */
public class Password {

    private final String value;

    public Password(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}

package service.login.interface_adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * The possible state for the Login view.
 */
public class LoginState {
    private String email = "";
    private String password = "";
    private String error = "";
    private Map<String, String> fieldsToErrors = new HashMap<>();

    public LoginState() {
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getError() {
        return this.error;
    }

    public Map<String, String> getFieldsToErrors() {
        return fieldsToErrors;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setFieldsToErrors(Map<String, String> fieldsToErrors) {
        this.fieldsToErrors = fieldsToErrors;
    }

    /**
     * Set given field to given error message.
     * @param field The field to set the error message for.
     * @param errorMessage The error message to set.
     */
    public void setFieldError(String field, String errorMessage) {
        fieldsToErrors.put(field, errorMessage);
    }
}

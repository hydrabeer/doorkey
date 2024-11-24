package service.signup.interface_adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * The possible state for the sign up view.
 */
public class SignupState {
    private String email = "";
    private String password = "";
    private String repeatedPassword = "";
    private String errorMessage = "";
    private Map<String, String> fieldsToErrors = new HashMap<>();
    private boolean isSuccess;

    public SignupState() {
    }

    public SignupState(String email, String password, String repeatedPassword, String errorMessage, boolean isSuccess) {
        this.email = email;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.errorMessage = errorMessage;
        this.isSuccess = isSuccess;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRepeatedPassword() {
        return this.repeatedPassword;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public Map<String, String> getFieldsToErrors() {
        return fieldsToErrors;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setRepeatedPassword(String newRepeatedPassword) {
        this.repeatedPassword = newRepeatedPassword;
    }

    public void setErrorMessage(String newErrorMessage) {
        this.errorMessage = newErrorMessage;
    }

    public void setIsSuccess(boolean success) {
        isSuccess = success;
    }

    public void setFieldsToErrors(Map<String, String> newFieldsToErrors) {
        fieldsToErrors = newFieldsToErrors;
    }

    /**
     * Append a new field to the fieldsToErrors map.
     *
     * @param field The field to append.
     * @param error The error message to append.
     */
    public void setFieldError(String field, String error) {
        fieldsToErrors.put(field, error);
    }
}

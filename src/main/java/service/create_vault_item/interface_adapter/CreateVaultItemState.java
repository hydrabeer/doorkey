package service.create_vault_item.interface_adapter;

/**
 * The CreateVaultItem state.
 */
public class CreateVaultItemState {
    private String errorMessage = "";
    private String successMessage = "";
    private boolean clearFields;

    public CreateVaultItemState() {
    }

    public CreateVaultItemState(String errorMessage, String successMessage) {
        this.errorMessage = errorMessage;
        this.successMessage = successMessage;
        this.clearFields = false;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public boolean getClearFields() {
        return clearFields;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public void setClearFields(boolean clearFields) {
        this.clearFields = clearFields;
    }
}

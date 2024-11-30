package service.create_vault_item;

public class CreateVaultItemResponseModel {

    private final boolean success;
    private final String message;

    public CreateVaultItemResponseModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

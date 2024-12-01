package service.import_vault_item;

/**
 * The input data for importing a vault item.
 */
public class ImportVaultItemInputData {
    private final String passwordManager;
    private final String importTextJson;

    public ImportVaultItemInputData(
            String passwordManager,
            String importTextJson
    ) {
        this.passwordManager = passwordManager;
        this.importTextJson = importTextJson;
    }

    public String getPasswordManager() {
        return passwordManager;
    }

    public String getImportTextJson() {
        return importTextJson;
    }
}

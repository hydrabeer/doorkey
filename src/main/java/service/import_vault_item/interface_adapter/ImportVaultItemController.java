package service.import_vault_item.interface_adapter;

import service.import_vault_item.ImportVaultItemInputBoundary;
import service.import_vault_item.ImportVaultItemInputData;

/**
 * The import vault item state.
 */
public class ImportVaultItemController {
    private final ImportVaultItemInputBoundary interactor;

    public ImportVaultItemController(ImportVaultItemInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Import a vault item.
     *
     * @param passwordManager The chosen password manager, a string. May be null.
     * @param importTextJson  The import JSON value.
     */
    public void importVaultItems(
            String passwordManager,
            String importTextJson
    ) {
        final ImportVaultItemInputData importVaultItemInputData = new ImportVaultItemInputData(
                passwordManager,
                importTextJson
        );
        interactor.importVaultItems(importVaultItemInputData);
    }

    /**
     * Display the home view.
     */
    public void displayHomeView() {
        interactor.displayHomeView();
    }
}

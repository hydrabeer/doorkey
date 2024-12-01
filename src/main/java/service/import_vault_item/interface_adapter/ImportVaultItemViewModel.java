package service.import_vault_item.interface_adapter;

import interface_adapter.ViewModel;
import views.ViewConstants;

/**
 * The import vault item view model.
 */
public class ImportVaultItemViewModel extends ViewModel<String> {
    public ImportVaultItemViewModel() {
        super(ViewConstants.PASSWORD_VAULT_ITEM_VIEW);
        setState("");
    }
}

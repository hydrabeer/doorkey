package mock;

import service.import_vault_item.ImportVaultItemOutputBoundary;

public class MockImportVaultItemPresenter implements ImportVaultItemOutputBoundary {
    public boolean homeViewDisplayed = false;
    public boolean errorDisplayed = false;
    public String errorMessage = "";

    @Override
    public void displayHomeView() {
        homeViewDisplayed = true;
    }

    @Override
    public void displayError(String message) {
        errorDisplayed = true;
        errorMessage = message;
    }
}
package mock;

import exception.InvalidVaultItemException;
import service.home.HomeOutputBoundary;
import service.home.HomeOutputData;

import java.util.ArrayList;
import java.util.List;

public class MockHomePresenter implements HomeOutputBoundary {
    public List<String> generalErrors = new ArrayList<>();
    @Override
    public void prepareShowVaultView(HomeOutputData homeOutputData) throws InvalidVaultItemException{
        generalErrors.add("Show VaultView");
        }

    @Override
    public void displayLoginView() {
        generalErrors.add("Show LoginView");
    }

    @Override
    public void displayCreateVaultItemView(){
        generalErrors.add("Show CreateView");
    }

    @Override
    public void displayImportView(){
        generalErrors.add("Show ImportView");
    }

    public boolean hasGeneralError(String error) {
        return generalErrors.contains(error);
    }
}

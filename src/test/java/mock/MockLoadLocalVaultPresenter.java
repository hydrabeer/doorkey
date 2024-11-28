package mock;

import service.local.load.LoadLocalVaultOutputBoundary;
import service.local.load.LoadLocalVaultOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock load local vault presenter.
 */
public class MockLoadLocalVaultPresenter implements LoadLocalVaultOutputBoundary {
    public List<LoadLocalVaultOutputData> successViews = new ArrayList<>();
    public List<String> generalErrors = new ArrayList<>();

    @Override
    public void prepareSuccessView(LoadLocalVaultOutputData loadLocalVaultOutputData) {
        successViews.add(loadLocalVaultOutputData);
    }

    @Override
    public void prepareErrorView(String error) {
        generalErrors.add(error);
    }
}

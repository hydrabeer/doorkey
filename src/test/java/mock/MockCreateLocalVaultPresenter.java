package mock;

import service.local.create.CreateLocalVaultOutputBoundary;
import service.local.create.CreateLocalVaultOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock create local vault presenter.
 */
public class MockCreateLocalVaultPresenter implements CreateLocalVaultOutputBoundary {
    public List<CreateLocalVaultOutputData> successViews = new ArrayList<>();
    public List<String> generalErrors = new ArrayList<>();

    @Override
    public void prepareSuccessView(CreateLocalVaultOutputData createLocalVaultOutputData) {
        successViews.add(createLocalVaultOutputData);
    }

    @Override
    public void prepareErrorView(String error) {
        generalErrors.add(error);
    }
}

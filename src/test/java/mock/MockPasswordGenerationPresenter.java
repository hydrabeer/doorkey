package mock;

import service.password_generation.PasswordGenerationOutputBoundary;
import service.password_generation.PasswordGenerationResponseModel;

public class MockPasswordGenerationPresenter implements PasswordGenerationOutputBoundary {
    private PasswordGenerationResponseModel presentedModel;

    @Override
    public void present(PasswordGenerationResponseModel responseModel) {
        this.presentedModel = responseModel;
    }

    public PasswordGenerationResponseModel getPresentedModel() {
        return presentedModel;
    }
}
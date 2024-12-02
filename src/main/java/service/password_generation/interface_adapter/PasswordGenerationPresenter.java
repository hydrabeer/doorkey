package service.password_generation.interface_adapter;

import service.password_generation.PasswordGenerationOutputBoundary;
import service.password_generation.PasswordGenerationResponseModel;

/**
 * The presenter for the password generation use case.
 */
public class PasswordGenerationPresenter implements PasswordGenerationOutputBoundary {

    private final PasswordGenerationViewModel viewModel;

    public PasswordGenerationPresenter(PasswordGenerationViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(PasswordGenerationResponseModel responseModel) {
        viewModel.setGeneratedPassword(responseModel.getGeneratedPassword());
        viewModel.setErrorMessage(responseModel.getErrorMessage());
    }
}

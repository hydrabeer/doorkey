package service.password_validation.interface_adapter;

import service.password_validation.PasswordValidationOutputBoundary;
import service.password_validation.PasswordValidationResponseModel;

/**
 * The presenter for the password validation use case.
 */
public class PasswordValidationPresenter implements PasswordValidationOutputBoundary {

    private final PasswordValidationViewModel viewModel;

    public PasswordValidationPresenter(PasswordValidationViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(PasswordValidationResponseModel responseModel) {
        viewModel.setMeetsRequirements(responseModel.isMeetsRequirements());
        viewModel.setEntropy(responseModel.getEntropy());
        viewModel.setIsValid(responseModel.isValid());
        viewModel.setLengthReq(responseModel.isLengthReq());
        viewModel.setUpperLowerReq(responseModel.isUpperLowerReq());
        viewModel.setNumericReq(responseModel.isNumericReq());
        viewModel.setSpecialCharReq(responseModel.isSpecialCharReq());
    }
}

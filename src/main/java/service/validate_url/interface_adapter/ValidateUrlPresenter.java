package service.validate_url.interface_adapter;

import service.ViewManagerModel;
import service.validate_url.ValidateUrlOutputBoundary;
import service.validate_url.ValidateUrlOutputData;

/**
 * Presenter for validating URLs.
 */
public class ValidateUrlPresenter implements ValidateUrlOutputBoundary {

    private final ValidateUrlViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public ValidateUrlPresenter(ValidateUrlViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareView(ValidateUrlOutputData outputData) {
        final ValidateUrlState state = viewModel.getState();
        state.setPhishingUrl(outputData.isPhishingUrl());
        viewModel.setState(state);
        viewModel.onStateChanged();

        viewManagerModel.setState(viewModel.getViewName());
        viewManagerModel.onStateChanged();
    }
}

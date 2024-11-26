package mock;

import service.signup.SignupOutputBoundary;
import service.signup.SignupOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock signup presenter.
 */
public class MockSignupPresenter implements SignupOutputBoundary {
    public List<SignupOutputData> successViews = new ArrayList<>();
    public List<String> generalErrors = new ArrayList<>();
    public List<String> fieldErrors = new ArrayList<>();
    public List<String> clearedFields = new ArrayList<>();

    @Override
    public void prepareSuccessView(SignupOutputData signupOutputData) {
        successViews.add(signupOutputData);
    }

    @Override
    public void prepareErrorView(String error) {
        generalErrors.add(error);
    }

    @Override
    public void prepareErrorView(String field, String error) {
        fieldErrors.add(field + ": " + error);
    }

    @Override
    public void clearError(String field) {
        clearedFields.add(field);
    }

    public boolean hasSuccessView() {
        return !successViews.isEmpty();
    }

    public boolean hasGeneralError(String error) {
        return generalErrors.contains(error);
    }

    public boolean hasFieldError(String field, String error) {
        return fieldErrors.contains(field + ": " + error);
    }

    public boolean hasClearedField(String field) {
        return clearedFields.contains(field);
    }
}

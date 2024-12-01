package mock;

import service.url_redirect.UrlRedirectOutputBoundary;

import java.util.ArrayList;
import java.util.List;

public class MockUrlRedirectPresenter implements UrlRedirectOutputBoundary {
    public List<String> generalErrors = new ArrayList<>();

    @Override
    public void displayError(String message) {
        generalErrors.add(message);
    }

    public boolean hasGeneralError(String error) {
        return generalErrors.stream().anyMatch(s -> s.startsWith(error));
    }
}

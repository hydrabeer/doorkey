package views;

import interface_adapter.ViewModel;

public class TestViewModel extends ViewModel<TestViewState> {
    public TestViewModel() {
        super(ViewConstants.TEST_VIEW);
        setState(new TestViewState());
    }
}

package interface_adapter;

/**
 * The main ViewModel for DoorKey, which all ViewModels implement.
 *
 * @param <T> The ViewModel data.
 */
public abstract class ViewModel<T> {
    private final String viewName;
    protected T state;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return this.viewName;
    }

    public void setState(T state) {
        this.state = state;
        onStateChanged();
    }

    protected abstract void onStateChanged();
}

package interface_adapter;

/**
 * The main ViewModel for Doorkey, which all ViewModels implement.
 *
 * @param <T> The ViewModel data.
 */
public abstract class ViewModel<T> {
    protected T state;
    private final String viewName;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return this.viewName;
    }

    /**
     * Set a new state.
     * @param state The new state to set.
     */
    public void setState(T state) {
        this.state = state;
        onStateChanged();
    }

    protected abstract void onStateChanged();
}

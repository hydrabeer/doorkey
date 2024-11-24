package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The main ViewModel for DoorKey, which all ViewModels extend.
 *
 * @param <T> The ViewModel data.
 */
public class ViewModel<T> {
    private T state;
    private final String viewName;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return this.viewName;
    }

    /**
     * Set a new state.
     *
     * @param state The new state to set.
     */
    public void setState(T state) {
        this.state = state;
        onStateChanged();
    }

    /**
     * Get the current state.
     *
     * @return The current state.
     */
    public T getState() {
        return this.state;
    }

    /**
     * Fires a state changed event.
     * This is equivalent to firePropertyChange("state").
     */
    public void onStateChanged() {
        firePropertyChange("state");
    }

    /**
     * Add a property change listener.
     * @param listener The listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Remove a property change listener.
     * @param listener The listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Fire the property change event with the given property name.
     * @param propertyName The name of the property that changed.
     */
    public void firePropertyChange(String propertyName) {
        support.firePropertyChange(propertyName, null, this.state);
    }
}

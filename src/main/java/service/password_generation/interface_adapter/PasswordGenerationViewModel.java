package service.password_generation.interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ViewModel for password generation, holding the generated password and status.
 */
public class PasswordGenerationViewModel {
    private final PropertyChangeSupport support;

    private String generatedPassword;
    private boolean generationSuccess;
    private String errorMessage;

    /**
     * Constructs a new PasswordGenerationViewModel and initializes property change support.
     */
    public PasswordGenerationViewModel() {
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * Adds a listener to listen for property changes.
     *
     * @param listener the PropertyChangeListener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Sets the generated password.
     *
     * @param generatedPassword the generated password to set
     */
    public void setGeneratedPassword(String generatedPassword) {
        final String previousPassword = this.generatedPassword;
        this.generatedPassword = generatedPassword;
        support.firePropertyChange("generatedPassword", previousPassword, generatedPassword);
    }

    /**
     * Sets the error message if generation failed.
     *
     * @param errorMessage the error message to set
     */
    public void setErrorMessage(String errorMessage) {
        final String previousError = this.errorMessage;
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", previousError, errorMessage);
    }

    public String getGeneratedPassword() {
        return generatedPassword;
    }

    public boolean isGenerationSuccess() {
        return generationSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

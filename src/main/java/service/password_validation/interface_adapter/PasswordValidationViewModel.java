package service.password_validation.interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ViewModel for password validation, holding validation results for the
 * view to display.
 */
public class PasswordValidationViewModel {

    private final PropertyChangeSupport support;

    private boolean meetsRequirements;
    private int entropy;
    private boolean isValid;
    private boolean lengthReq;
    private boolean upperLowerReq;
    private boolean numericReq;
    private boolean specialCharReq;

    /**
     * Constructs a new PasswordValidationViewModel and initializes property change support.
     */
    public PasswordValidationViewModel() {
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
     * Sets whether the password meets all requirements.
     *
     * @param meetsRequirements true if all requirements are met, false otherwise
     */
    public void setMeetsRequirements(boolean meetsRequirements) {
        final boolean previousMeetsRequirements = this.meetsRequirements;
        this.meetsRequirements = meetsRequirements;
        support.firePropertyChange("meetsRequirements", previousMeetsRequirements, meetsRequirements);
    }

    /**
     * Sets the entropy score of the password.
     *
     * @param entropy the entropy score to set
     */
    public void setEntropy(int entropy) {
        final int previousEntropy = this.entropy;
        this.entropy = entropy;
        support.firePropertyChange("entropy", previousEntropy, entropy);
    }

    /**
     * Sets the validity status of the password.
     *
     * @param isValid true if the password is valid, false otherwise
     */
    public void setIsValid(boolean isValid) {
        final boolean previousIsValid = this.isValid;
        this.isValid = isValid;
        support.firePropertyChange("isValid", previousIsValid, isValid);
    }

    /**
     * Sets whether the password meets the length requirement.
     *
     * @param lengthReq true if the length requirement is met, false otherwise
     */
    public void setLengthReq(boolean lengthReq) {
        final boolean previousLengthReq = this.lengthReq;
        this.lengthReq = lengthReq;
        support.firePropertyChange("lengthReq", previousLengthReq, lengthReq);
    }

    /**
     * Sets whether the password meets both uppercase and lowercase requirements.
     *
     * @param upperLowerReq true if both uppercase and lowercase requirements are met, false otherwise
     */
    public void setUpperLowerReq(boolean upperLowerReq) {
        final boolean previousUpperLowerReq = this.upperLowerReq;
        this.upperLowerReq = upperLowerReq;
        support.firePropertyChange("upperLowerReq", previousUpperLowerReq, upperLowerReq);
    }

    /**
     * Sets whether the password meets the numeric requirement.
     *
     * @param numericReq true if the numeric requirement is met, false otherwise
     */
    public void setNumericReq(boolean numericReq) {
        final boolean previousNumericReq = this.numericReq;
        this.numericReq = numericReq;
        support.firePropertyChange("numericReq", previousNumericReq, numericReq);
    }

    /**
     * Sets whether the password meets the special character requirement.
     *
     * @param specialCharReq true if the special character requirement is met, false otherwise
     */
    public void setSpecialCharReq(boolean specialCharReq) {
        final boolean previousSpecialCharReq = this.specialCharReq;
        this.specialCharReq = specialCharReq;
        support.firePropertyChange("specialCharReq", previousSpecialCharReq, specialCharReq);
    }

    public boolean isMeetsRequirements() {
        return meetsRequirements;
    }
    
    public boolean isValid() {
        return isValid;
    }
    
    public boolean isLengthReq() {
        return lengthReq;
    }
    
    public boolean isUpperLowerReq() {
        return upperLowerReq;
    }
    
    public boolean isNumericReq() {
        return numericReq;
    }
    
    public boolean isSpecialCharReq() {
        return specialCharReq;
    }

    public int getEntropy() {
        return entropy;
    }
}

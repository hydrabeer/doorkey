package presenters.views.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presenters.views.ViewConstants;

/**
 * The default form generator component.
 */
public class DoorkeyForm extends JPanel {
    private final Map<String, JTextField> fieldMap = new HashMap<>();
    private final Map<String, JLabel> errorFieldMap = new HashMap<>();
    private final JLabel errorLabel = new JLabel();
    private DoorkeyButton button;

    public DoorkeyForm() {
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        errorLabel.setForeground(ViewConstants.TEXT_ERROR_COLOR);
        errorLabel.setFont(new DoorkeyFont());
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(errorLabel);
        addBasicSeparator(4);
    }

    /**
     * Get the value of a field.
     *
     * @param fieldId The field id.
     * @return The field value.
     * @throws RuntimeException when fieldId is unknown.
     */
    public String getFieldValue(String fieldId) {
        final JTextField field = fieldMap.get(fieldId);
        if (field == null) {
            throw new RuntimeException("Unknown fieldId");
        }
        return field.getText();
    }

    /**
     * Sets an error message at the given form field.
     *
     * @param message The error message to set.
     * @param fieldId The field id, used to set the error label.
     * @throws RuntimeException when fieldId is unknown.
     */
    public void setFieldError(String message, String fieldId) {
        final JLabel targetErrorLabel = errorFieldMap.get(fieldId);
        if (targetErrorLabel == null) {
            throw new RuntimeException("Unknown fieldId");
        }

        targetErrorLabel.setText(message);
    }

    /**
     * Sets the overall error message.
     *
     * @param message The error message to set.
     */
    public void setError(String message) {
        errorLabel.setText(message);
    }

    /**
     * Clears an error message at the given field.
     *
     * @param fieldId The field id, used to get the error label.
     */
    public void clearFieldError(String fieldId) {
        setFieldError("", fieldId);
    }

    /**
     * Clears the overall error message.
     */
    public void clearError() {
        setError("");
    }

    /**
     * Adds a new field with a label.
     *
     * @param field     The field to be added.
     * @param labelName The label name.
     * @param fieldId   The field id, used for retrieving the field value.
     */
    public void addField(JTextField field, String labelName, String fieldId) {
        addFormLabel(labelName, fieldId);
        addBasicSeparator(4);
        addInputField(field, fieldId);
        addBasicSeparator(4);
    }

    /**
     * Adds a DButton to submit the form.
     *
     * @param submitButtonText The submit button text.
     * @param listener         The action to be performed.
     */
    public void addSubmitButton(String submitButtonText, ActionListener listener) {
        final DoorkeyButton targetButton = new DoorkeyButton.DoorkeyButtonBuilder(submitButtonText)
                .addListener(listener)
                .build();

        addBasicSeparator(8);

        this.add(targetButton);
        this.button = targetButton;
    }

    /**
     * Adds a DButton to submit the form, with empty action.
     *
     * @param submitButtonText The submit button text.
     */
    public void addSubmitButton(String submitButtonText) {
        final DoorkeyButton targetButton = new DoorkeyButton(submitButtonText);
        addBasicSeparator(8);

        this.add(targetButton);
        this.button = targetButton;
    }

    /**
     * Add an action listener to the submit button.
     * @param listener The listener to add.
     * @throws RuntimeException when the button field is null.
     */
    public void addActionListener(ActionListener listener) {
        if (button == null) {
            throw new RuntimeException("Button not added");
        }
        button.addActionListener(listener);
    }

    private void addFormLabel(String labelName, String fieldId) {
        final JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        final JLabel formLabel = new JLabel(labelName);
        formLabel.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        formLabel.setFont(new DoorkeyFont());

        final JLabel targetErrorLabel = new JLabel();
        targetErrorLabel.setForeground(ViewConstants.TEXT_ERROR_COLOR);
        targetErrorLabel.setFont(new DoorkeyFont());
        errorFieldMap.put(fieldId, targetErrorLabel);

        labelPanel.add(formLabel, BorderLayout.WEST);
        labelPanel.add(targetErrorLabel, BorderLayout.EAST);

        labelPanel.setMaximumSize(new Dimension(300, formLabel.getPreferredSize().height));

        this.add(labelPanel);
    }

    private void addInputField(JTextField field, String fieldId) {
        fieldMap.put(fieldId, field);

        field.setFont(new DoorkeyFont());
        field.setBackground(ViewConstants.BACKGROUND_COLOR);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ViewConstants.INPUT_BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10))
        );

        field.setMaximumSize(new Dimension(300, 30));
        field.setPreferredSize(new Dimension(250, 30));
        field.setMinimumSize(new Dimension(200, 30));

        this.add(field);
    }

    private void addBasicSeparator(int height) {
        this.add(Box.createRigidArea(new Dimension(0, height)));
    }
}

package presenters.views.components;

import presenters.views.ViewConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * The default form generator component.
 */
public class DForm extends JPanel {
    private final Map<String, JTextField> fieldMap = new HashMap<>();
    private final Map<String, JLabel> errorFieldMap = new HashMap<>();
    private final JLabel errorLabel = new JLabel();

    public DForm() {
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        errorLabel.setForeground(ViewConstants.TEXT_ERROR_COLOR);
        errorLabel.setFont(new DFont());
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(errorLabel);
        addBasicSeparator(4);
    }

    /**
     * Get the value of a field.
     *
     * @param fieldId The field id.
     * @return The field value.
     */
    public String getFieldValue(String fieldId) {
        JTextField field = fieldMap.get(fieldId);
        if (field == null) throw new RuntimeException("Unknown fieldId");
        return field.getText();
    }

    /**
     * Sets an error message at the given form field.
     *
     * @param message The error message to set.
     * @param fieldId The field id, used to set the error label.
     */
    public void setFieldError(String message, String fieldId) {
        JLabel errorLabel = errorFieldMap.get(fieldId);
        if (errorLabel == null) throw new RuntimeException("Unknown fieldId");

        errorLabel.setText(message);
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
        DButton button = new DButton.DButtonBuilder(submitButtonText)
                .addListener(listener)
                .build();

        addBasicSeparator(8);

        this.add(button);
    }

    // private void addFormLabel(String labelName) {
    //     JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    //     labelPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
    //
    //     JLabel formLabel = new JLabel(labelName);
    //     formLabel.setForeground(ViewConstants.TEXT_MUTED_COLOR);
    //     formLabel.setFont(new DFont());
    //
    //     labelPanel.add(formLabel);
    //     labelPanel.setMaximumSize(new Dimension(300, formLabel.getPreferredSize().height));
    //
    //     this.add(labelPanel);
    // }

    private void addFormLabel(String labelName, String fieldId) {
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        JLabel formLabel = new JLabel(labelName);
        formLabel.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        formLabel.setFont(new DFont());

        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(ViewConstants.TEXT_ERROR_COLOR);
        errorLabel.setFont(new DFont());
        errorFieldMap.put(fieldId, errorLabel);

        labelPanel.add(formLabel, BorderLayout.WEST);
        labelPanel.add(errorLabel, BorderLayout.EAST);

        labelPanel.setMaximumSize(new Dimension(300, formLabel.getPreferredSize().height));

        this.add(labelPanel);
    }

    private void addInputField(JTextField field, String fieldId) {
        fieldMap.put(fieldId, field);

        field.setFont(new DFont());
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

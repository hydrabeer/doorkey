package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import service.password_validation.interface_adapter.PasswordValidationController;
import service.password_validation.interface_adapter.PasswordValidationViewModel;

/**
 * The dialog view for creating a new vault item.
 */
public class CreateVaultItemView extends JDialog {

    private final PasswordValidationViewModel viewModel;
    private final PasswordValidationController controller;

    // UI Components
    private JTextField urlField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel titleLabel;
    private JLabel urlLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JPanel requirementsPanel;
    private JProgressBar strengthBar;
    private JButton saveButton;
    private JButton cancelButton;

    // Requirement Labels
    private JLabel lengthRequirementLabel;
    private JLabel upperLowerRequirementLabel;
    private JLabel numericRequirementLabel;
    private JLabel specialCharRequirementLabel;

    private final int componentWidth = 400;

    /**
     * Constructs the CreateVaultItemView dialog.
     *
     * @param owner The parent frame.
     * @param viewModel The view model for password validation.
     * @param controller The controller for password validation.
     */
    public CreateVaultItemView(
            Frame owner, PasswordValidationViewModel viewModel, PasswordValidationController controller) {
        super(owner, "New Vault Item", true);
        this.viewModel = viewModel;
        this.controller = controller;

        initializeComponents();
        layoutComponents();
        registerEventHandlers();

        getContentPane().setBackground(Color.BLACK);

        setPreferredSize(new Dimension(componentWidth + 100, 800));
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);

        // Add property change listener
        viewModel.addPropertyChangeListener(evt -> {
            if (SwingUtilities.isEventDispatchThread()) {
                updateView(evt);
            } 
            else {
                SwingUtilities.invokeLater(() -> updateView(evt));
            }
        });

        controller.validatePassword("", false);
    }

    /**
     * Initializes all UI components.
     */
    private void initializeComponents() {
        final Font titleFont = new Font("Futura", Font.BOLD, 30);
        final Font labelFont = new Font("Futura", Font.PLAIN, 16);
        final Font requirementFont = new Font("Futura", Font.PLAIN, 14);

        titleLabel = new JLabel("New Vault Item");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        urlLabel = createLabel("Enter URL", labelFont);
        usernameLabel = createLabel("Enter your username", labelFont);
        passwordLabel = createLabel("Enter your password", labelFont);
        confirmPasswordLabel = createLabel("Confirm your password", labelFont);

        urlField = createTextField();
        usernameField = createTextField();
        passwordField = createPasswordField();
        confirmPasswordField = createPasswordField();

        requirementsPanel = createRequirementsPanel(requirementFont);

        strengthBar = new JProgressBar(0, 100);
        strengthBar.setPreferredSize(new Dimension(componentWidth, 20));
        strengthBar.setMaximumSize(new Dimension(componentWidth, 20));
        strengthBar.setStringPainted(false);
        strengthBar.setForeground(Color.GRAY);
        strengthBar.setBackground(Color.DARK_GRAY);
        strengthBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        strengthBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveButton = createStyledButton("Save");
        cancelButton = createStyledButton("Cancel");
    }

    /**
     * Creates a JLabel with specified text and font.
     *
     * @param text The text for the label.
     * @param font The font to apply.
     * @return A configured JLabel.
     */
    private JLabel createLabel(String text, Font font) {
        final JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    /**
     * Creates a JTextField with predefined settings.
     *
     * @return A configured JTextField.
     */
    private JTextField createTextField() {
        final JTextField textField = new JTextField(20);
        textField.setMaximumSize(new Dimension(componentWidth, 35));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        textField.setCaretColor(Color.WHITE);
        textField.setSelectionColor(new Color(80, 80, 80));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return textField;
    }

    /**
     * Creates a JPasswordField with predefined settings.
     *
     * @return A configured JPasswordField.
     */
    private JPasswordField createPasswordField() {
        final JPasswordField passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(componentWidth, 35));
        passwordField.setBackground(Color.BLACK);
        passwordField.setForeground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setSelectionColor(new Color(80, 80, 80));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return passwordField;
    }

    /**
     * Creates the password guidelines panel arranged in two columns without
     * hyphens.
     *
     * @param font The font to apply to requirement labels.
     * @return A configured JPanel with password guidelines.
     */
    private JPanel createRequirementsPanel(Font font) {
        final JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.setLayout(new GridLayout(0, 2, 20, 20));
        panel.setMaximumSize(new Dimension(componentWidth, 0));

        // Initialize requirement labels and assign to instance variables
        lengthRequirementLabel = createRequirementLabel("At least 8 characters", font);
        upperLowerRequirementLabel = createRequirementLabel("At least one uppercase and lowercase letter", font);
        numericRequirementLabel = createRequirementLabel("At least one number", font);
        specialCharRequirementLabel = createRequirementLabel("At least one special character", font);

        panel.add(lengthRequirementLabel);
        panel.add(upperLowerRequirementLabel);
        panel.add(numericRequirementLabel);
        panel.add(specialCharRequirementLabel);

        return panel;
    }

    /**
     * Creates a JLabel for password guidelines.
     *
     * @param text The text for the requirement.
     * @param font The font to apply.
     * @return A configured JLabel.
     */
    private JLabel createRequirementLabel(String text, Font font) {
        final JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.RED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    /**
     * Arranges all components within the dialog.
     */
    private void layoutComponents() {

        final JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.BLACK);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        contentPanel.add(createFieldPanel(urlLabel, urlField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        contentPanel.add(createFieldPanel(usernameLabel, usernameField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        contentPanel.add(createFieldPanel(passwordLabel, passwordField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        final JLabel requirementsTitle = 
            createLabel("Password Guidelines:", new Font("Futura", Font.BOLD, 18));
        requirementsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(requirementsTitle);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(requirementsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        final JLabel strengthLabel = createLabel("Password Strength:", new Font("Futura", Font.PLAIN, 16));
        strengthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(strengthLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(strengthBar);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        contentPanel.add(createFieldPanel(confirmPasswordLabel, confirmPasswordField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        contentPanel.add(createButtonsPanel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        final JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(Color.BLACK);
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.add(Box.createVerticalGlue());
        wrapperPanel.add(contentPanel);
        wrapperPanel.add(Box.createVerticalGlue());

        setContentPane(wrapperPanel);
    }

    /**
     * Creates a panel containing the Save and Cancel buttons aligned
     * horizontally.
     *
     * @return A JPanel containing the Save and Cancel buttons.
     */
    private JPanel createButtonsPanel() {
        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));

        // Add Save and Cancel buttons
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        return buttonsPanel;
    }

    /**
     * Creates a panel containing a label and its corresponding field.
     *
     * @param label The JLabel to display.
     * @param field The input component (JTextField or JPasswordField).
     * @return A JPanel containing the label and field.
     */
    private JPanel createFieldPanel(JLabel label, Component field) {
        final JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(field);

        return panel;
    }

    /**
     * Creates a styled JButton with black background, white text, and gray
     * border.
     *
     * @param text The text to display on the button.
     * @return A styled JButton.
     */
    private JButton createStyledButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Futura", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.GRAY, 2));
        button.setPreferredSize(new Dimension(120, 50));
        button.setMaximumSize(new Dimension(120, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    /**
     * Registers event handlers for the UI components.
     */
    private void registerEventHandlers() {

        saveButton.addActionListener(e -> {
            dispose();
        });

        cancelButton.addActionListener(e -> {
            dispose(); 
        });

        passwordField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                final String password = new String(passwordField.getPassword());
                controller.validatePassword(password, false);
            }
        });

        // Optionally, add DocumentListener to confirmPasswordField to validate matching passwords
        confirmPasswordField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                final String password = new String(passwordField.getPassword());
                final String confirmPassword = new String(confirmPasswordField.getPassword());
                // Implement matching logic if necessary
                // For example, update a label or enable/disable the save button
            }
        });
    }

    /**
     * Updates the UI components based on changes in the view model.
     *
     * @param evt The property change event.
     */
    private void updateView(PropertyChangeEvent evt) {
        final String propertyName = evt.getPropertyName();
        switch (propertyName) {
            case "lengthReq" -> updateRequirementLabel(lengthRequirementLabel, viewModel.isLengthReq());
            case "upperLowerReq" -> updateRequirementLabel(upperLowerRequirementLabel, viewModel.isUpperLowerReq());
            case "numericReq" -> updateRequirementLabel(numericRequirementLabel, viewModel.isNumericReq());
            case "specialCharReq" -> updateRequirementLabel(specialCharRequirementLabel, viewModel.isSpecialCharReq());
            case "entropy" -> updateStrengthBar((int) viewModel.getEntropy());
            default -> {
            }
        }
    }

    /**
     * Updates the color of a requirement label based on whether the requirement
     * is met.
     *
     * @param label The JLabel to update.
     * @param requirementMet True if the requirement is met; false otherwise.
     */
    private void updateRequirementLabel(JLabel label, boolean requirementMet) {
        if (requirementMet) {
            label.setForeground(Color.GREEN);
        } 
        else {
            label.setForeground(Color.RED);
        }
    }

    /**
     * Updates the strength bar based on the entropy score.
     *
     * @param entropyScore The entropy score (0-4).
     */
    private void updateStrengthBar(int entropyScore) {
        final int value = entropyScore * 25;
        strengthBar.setValue(value);

        switch (entropyScore) {
            case 0, 1 ->
                strengthBar.setForeground(Color.RED);
            case 2 ->
                strengthBar.setForeground(Color.ORANGE);
            case 3 ->
                strengthBar.setForeground(Color.YELLOW);
            case 4 ->
                strengthBar.setForeground(Color.GREEN);
            default ->
                strengthBar.setForeground(Color.GRAY);
        }
    }

    /**
     * Simplified DocumentListener for convenience.
     */
    public abstract class SimpleDocumentListener implements DocumentListener {

        public abstract void update(DocumentEvent e);

        @Override
        public void insertUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            update(e);
        }
    }
}

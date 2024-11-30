package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component; // <-- Added import
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.text.JTextComponent;
import service.password_validation.interface_adapter.PasswordValidationController;
import service.password_validation.interface_adapter.PasswordValidationViewModel;

/**
 * The dialog view for creating a new vault item.
 */
public class CreateVaultItemView extends JPanel {

    private final PasswordValidationViewModel viewModel;
    private final PasswordValidationController controller;

    private JTextField urlField;
    private JTextField vaultTitleField;
    private JTextField usernameField;
    private JPasswordField passwordInputField;
    private JPasswordField confirmPasswordField;

    private JLabel lengthRequirementLabel;
    private JLabel upperLowerRequirementLabel;
    private JLabel numericRequirementLabel;
    private JLabel specialCharRequirementLabel;

    private JProgressBar strengthBar;

    private JButton saveButton;
    private JButton cancelButton;

    private final int componentWidth = 300;

    /**
     * Constructs the CreateVaultItemView dialog.
     *
     * @param viewModel  The view model for password validation.
     * @param controller The controller for password validation.
     */
    public CreateVaultItemView(
            PasswordValidationViewModel viewModel, PasswordValidationController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
        initializeComponents();
        layoutComponents();
        registerEventHandlers();
        setPreferredSize(new Dimension(400, 550));

        viewModel.addPropertyChangeListener(evt -> {
            if (SwingUtilities.isEventDispatchThread()) {
                updateView(evt);
            } else {
                SwingUtilities.invokeLater(() -> updateView(evt));
            }
        });

        controller.validatePassword("", false);
    }

    /**
     * Initializes all UI components.
     */
    private void initializeComponents() {
        final Font labelFont = new Font(ViewConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12);
        final Font requirementFont = new Font(ViewConstants.DEFAULT_FONT_NAME, Font.PLAIN, 10);

        JLabel urlLabel = new JLabel("Enter URL");
        urlLabel.setFont(labelFont);
        urlLabel.setForeground(Color.WHITE);

        JLabel vaultTitleLabel = new JLabel("Enter Vault Title");
        vaultTitleLabel.setFont(labelFont);
        vaultTitleLabel.setForeground(Color.WHITE);

        JLabel usernameLabel = new JLabel("Enter your username");
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Enter your password");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(Color.WHITE);

        JLabel confirmPasswordLabel = new JLabel("Confirm your password");
        confirmPasswordLabel.setFont(labelFont);
        confirmPasswordLabel.setForeground(Color.WHITE);

        // Initialize text fields
        urlField = new JTextField(15);
        configureTextComponent(urlField);

        vaultTitleField = new JTextField(15);
        configureTextComponent(vaultTitleField);

        usernameField = new JTextField(15);
        configureTextComponent(usernameField);

        passwordInputField = new JPasswordField(15);
        configureTextComponent(passwordInputField);

        confirmPasswordField = new JPasswordField(15);
        configureTextComponent(confirmPasswordField);

        // Initialize password requirement labels
        lengthRequirementLabel = new JLabel("At least 8 characters");
        lengthRequirementLabel.setFont(requirementFont);
        lengthRequirementLabel.setForeground(Color.RED);

        upperLowerRequirementLabel = new JLabel("At least one uppercase and lowercase letter");
        upperLowerRequirementLabel.setFont(requirementFont);
        upperLowerRequirementLabel.setForeground(Color.RED);

        numericRequirementLabel = new JLabel("At least one number");
        numericRequirementLabel.setFont(requirementFont);
        numericRequirementLabel.setForeground(Color.RED);

        specialCharRequirementLabel = new JLabel("At least one special character");
        specialCharRequirementLabel.setFont(requirementFont);
        specialCharRequirementLabel.setForeground(Color.RED);

        // Initialize strength bar
        strengthBar = new JProgressBar(0, 100);
        strengthBar.setPreferredSize(new Dimension(componentWidth, 15));
        strengthBar.setMaximumSize(new Dimension(componentWidth, 15));
        strengthBar.setStringPainted(false);
        strengthBar.setForeground(Color.GRAY);
        strengthBar.setBackground(Color.DARK_GRAY);
        strengthBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Initialize buttons
        saveButton = createStyledButton("Save", 90, 35);
        cancelButton = createStyledButton("Cancel", 90, 35);
    }

    /**
     * Configures a JTextComponent with predefined settings.
     *
     * @param textComponent The text component to configure.
     */
    private void configureTextComponent(JTextComponent textComponent) {
        textComponent.setMaximumSize(new Dimension(componentWidth, 25));
        textComponent.setBackground(Color.BLACK);
        textComponent.setForeground(Color.WHITE);
        textComponent.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        textComponent.setCaretColor(Color.WHITE);
        textComponent.setSelectionColor(new Color(80, 80, 80));
    }

    /**
     * Creates a styled JButton with black background, white text, and gray border.
     *
     * @param text   The text to display on the button.
     * @param width  The preferred width of the button.
     * @param height The preferred height of the button.
     * @return A styled JButton.
     */
    private JButton createStyledButton(String text, int width, int height) {
        final JButton button = new JButton(text);
        button.setFont(new Font(ViewConstants.DEFAULT_FONT_NAME, Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.GRAY, 1));
        button.setPreferredSize(new Dimension(width, height));
        button.setMaximumSize(new Dimension(width, height));
        return button;
    }

    /**
     * Arranges all components within the dialog.
     */
    private void layoutComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.BLACK);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.setMaximumSize(new Dimension(componentWidth, Integer.MAX_VALUE));

        JLabel titleLabel = new JLabel("New Vault Item");
        titleLabel.setFont(new Font(ViewConstants.DEFAULT_FONT_NAME, Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        contentPanel.add(createLabeledField("Enter URL", urlField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createLabeledField("Enter Vault Title", vaultTitleField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createLabeledField("Enter your username", usernameField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createLabeledField("Enter your password", passwordInputField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Password guidelines
        JLabel guidelinesLabel = new JLabel("Password Guidelines:");
        guidelinesLabel.setFont(new Font(ViewConstants.DEFAULT_FONT_NAME, Font.BOLD, 14));
        guidelinesLabel.setForeground(Color.WHITE);
        guidelinesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(guidelinesLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Requirements panel
        JPanel requirementsPanel = new JPanel();
        requirementsPanel.setBackground(Color.BLACK);
        requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.Y_AXIS));
        requirementsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        requirementsPanel.add(lengthRequirementLabel);
        requirementsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        requirementsPanel.add(upperLowerRequirementLabel);
        requirementsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        requirementsPanel.add(numericRequirementLabel);
        requirementsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        requirementsPanel.add(specialCharRequirementLabel);

        contentPanel.add(requirementsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Password strength
        JLabel strengthLabel = new JLabel("Password Strength:");
        strengthLabel.setFont(new Font(ViewConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        strengthLabel.setForeground(Color.WHITE);
        strengthLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(strengthLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        strengthBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(strengthBar);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Confirm password field
        contentPanel.add(createLabeledField("Confirm your password", confirmPasswordField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add buttons with spacing
        buttonsPanel.add(saveButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsPanel.add(cancelButton);

        contentPanel.add(buttonsPanel);

        // Add contentPanel to the main panel
        this.add(contentPanel);
        // Add glue to push content to the top
        this.add(Box.createVerticalGlue());
    }

    /**
     * Creates a panel with a label and associated field.
     *
     * @param labelText The text for the label.
     * @param field     The field component.
     * @return A JPanel containing the label and field.
     */
    private JPanel createLabeledField(String labelText, JTextComponent field) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-aligned

        JLabel label = new JLabel(labelText);
        label.setFont(new Font(ViewConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setAlignmentX(Component.LEFT_ALIGNMENT); // Left-aligned

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);

        return panel;
    }

    /**
     * Registers event handlers for the UI components.
     */
    private void registerEventHandlers() {

        saveButton.addActionListener(event -> {
            System.out.println("Save button clicked. Placeholder action executed.");
        });

        cancelButton.addActionListener(event -> {
            System.out.println("Cancel button clicked. Placeholder action executed.");
        });

        passwordInputField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                final String password = new String(passwordInputField.getPassword());
                controller.validatePassword(password, false);
            }
        });

        confirmPasswordField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                // Placeholder for confirm password logic
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
     * Updates the color of a requirement label based on whether the requirement is met.
     *
     * @param label           The JLabel to update.
     * @param requirementMet  True if the requirement is met; false otherwise.
     */
    private void updateRequirementLabel(JLabel label, boolean requirementMet) {
        label.setForeground(requirementMet ? Color.GREEN : Color.RED);
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
            case 0, 1 -> strengthBar.setForeground(Color.RED);
            case 2 -> strengthBar.setForeground(Color.ORANGE);
            case 3 -> strengthBar.setForeground(Color.YELLOW);
            case 4 -> strengthBar.setForeground(Color.GREEN);
            default -> strengthBar.setForeground(Color.GRAY);
        }
    }

    /**
     * Simplified DocumentListener for convenience.
     */
    public abstract class SimpleDocumentListener implements javax.swing.event.DocumentListener {

        /**
         * Updates the password validation when the password field changes.
         *
         * @param event The document event.
         */
        public abstract void update(DocumentEvent event);

        @Override
        public void insertUpdate(DocumentEvent event) {
            update(event);
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            update(event);
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
            update(event);
        }
    }
}

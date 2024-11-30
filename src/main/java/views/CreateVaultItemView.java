package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.event.DocumentListener;

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
    private JLabel titleLabel;
    private JLabel urlLabel;
    private JLabel vaultTitleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JPanel requirementsPanel;
    private JProgressBar strengthBar;
    private JButton saveButton;
    private JButton cancelButton;

    private JLabel lengthRequirementLabel;
    private JLabel upperLowerRequirementLabel;
    private JLabel numericRequirementLabel;
    private JLabel specialCharRequirementLabel;

    private final int componentWidth = 300;

    /**
     * Constructs the CreateVaultItemView dialog.
     *
     * @param viewModel The view model for password validation.
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
        final Font titleFont = new Font(ViewConstants.DEFAULT_FONT_NAME, Font.BOLD, 18);
        final Font labelFont = new Font(ViewConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12);
        final Font requirementFont = new Font(ViewConstants.DEFAULT_FONT_NAME, Font.PLAIN, 10);

        titleLabel = new JLabel("New Vault Item");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        urlLabel = createLabel("Enter URL", labelFont);
        vaultTitleLabel = createLabel("Enter Vault Title", labelFont);
        usernameLabel = createLabel("Enter your username", labelFont);
        passwordLabel = createLabel("Enter your password", labelFont);
        confirmPasswordLabel = createLabel("Confirm your password", labelFont);

        urlField = createTextField();
        vaultTitleField = createTextField();
        usernameField = createTextField();
        passwordInputField = createPasswordField();
        confirmPasswordField = createPasswordField();

        requirementsPanel = createRequirementsPanel(requirementFont);

        strengthBar = new JProgressBar(0, 100);
        strengthBar.setPreferredSize(new Dimension(componentWidth, 15));
        strengthBar.setMaximumSize(new Dimension(componentWidth, 15));
        strengthBar.setStringPainted(false);
        strengthBar.setForeground(Color.GRAY);
        strengthBar.setBackground(Color.DARK_GRAY);
        strengthBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        strengthBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveButton = createStyledButton("Save", 90, 35);
        cancelButton = createStyledButton("Cancel", 90, 35);
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
        final JTextField textField = new JTextField(15);
        textField.setMaximumSize(new Dimension(componentWidth, 25));
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
        final JPasswordField passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(componentWidth, 25));
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

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMaximumSize(new Dimension(componentWidth, Integer.MAX_VALUE));

        lengthRequirementLabel =
            createRequirementLabel("At least 8 characters", font);
        upperLowerRequirementLabel =
            createRequirementLabel("At least one uppercase and lowercase letter", font);
        numericRequirementLabel =
            createRequirementLabel("At least one number", font);
        specialCharRequirementLabel =
            createRequirementLabel("At least one special character", font);

        panel.add(lengthRequirementLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(upperLowerRequirementLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(numericRequirementLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
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
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        final JPanel topFieldsPanel = new JPanel();
        topFieldsPanel.setBackground(Color.BLACK);
        topFieldsPanel.setLayout(new BoxLayout(topFieldsPanel, BoxLayout.Y_AXIS));
        topFieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel passwordGuidelinesPanel = new JPanel();
        passwordGuidelinesPanel.setBackground(Color.BLACK);
        passwordGuidelinesPanel.setLayout(new BoxLayout(passwordGuidelinesPanel, BoxLayout.Y_AXIS));
        passwordGuidelinesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel passwordStrengthPanel = new JPanel();
        passwordStrengthPanel.setBackground(Color.BLACK);
        passwordStrengthPanel.setLayout(new BoxLayout(passwordStrengthPanel, BoxLayout.Y_AXIS));
        passwordStrengthPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel confirmPasswordPanel = new JPanel();
        confirmPasswordPanel.setBackground(Color.BLACK);
        confirmPasswordPanel.setLayout(new BoxLayout(confirmPasswordPanel, BoxLayout.Y_AXIS));
        confirmPasswordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttonsPanel = createButtonsPanel();

        addWithSpacing(topFieldsPanel, titleLabel, 20);
        addWithSpacing(topFieldsPanel, createFieldPanel(urlLabel, urlField), 10);
        addWithSpacing(topFieldsPanel, createFieldPanel(vaultTitleLabel, vaultTitleField), 10);
        addWithSpacing(topFieldsPanel, createFieldPanel(usernameLabel, usernameField), 10);
        addWithSpacing(topFieldsPanel, createFieldPanel(passwordLabel, passwordInputField), 10);

        final JLabel requirementsTitle =
            createLabel("Password Guidelines:", new Font(ViewConstants.DEFAULT_FONT_NAME, Font.BOLD, 14));
        requirementsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        addWithSpacing(passwordGuidelinesPanel, requirementsTitle, 5);
        addWithSpacing(passwordGuidelinesPanel, requirementsPanel, 15);

        final JLabel strengthLabel =
            createLabel("Password Strength:", new Font(ViewConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        strengthLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordStrengthPanel.add(strengthLabel);
        passwordStrengthPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        passwordStrengthPanel.add(strengthBar);
        passwordStrengthPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        addWithSpacing(confirmPasswordPanel, createFieldPanel(confirmPasswordLabel, confirmPasswordField), 10);

        contentPanel.add(topFieldsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(passwordGuidelinesPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(passwordStrengthPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(confirmPasswordPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(buttonsPanel);

        final JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(Color.BLACK);
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.add(Box.createVerticalGlue());
        wrapperPanel.add(contentPanel);
        wrapperPanel.add(Box.createVerticalGlue());

        this.setLayout(new BorderLayout());
        this.add(wrapperPanel, BorderLayout.CENTER);
    }

    /**
     * Adds a component and a rigid area to the specified panel.
     *
     * @param panel The panel to add components to.
     * @param component The component to add.
     * @param spacingHeight The height of the rigid area.
     */
    private void addWithSpacing(JPanel panel, Component component, int spacingHeight) {
        panel.add(component);
        panel.add(Box.createRigidArea(new Dimension(0, spacingHeight)));
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
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

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
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 4)));
        panel.add(field);

        return panel;
    }

    /**
     * Creates a styled JButton with black background, white text, and gray
     * border.
     *
     * @param text The text to display on the button.
     * @param width The preferred width of the button.
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
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
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
                final String password = new String(passwordInputField.getPassword());
                final String confirmPassword = new String(confirmPasswordField.getPassword());
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
            case "lengthReq" ->
                updateRequirementLabel(lengthRequirementLabel, viewModel.isLengthReq());
            case "upperLowerReq" ->
                updateRequirementLabel(upperLowerRequirementLabel, viewModel.isUpperLowerReq());
            case "numericReq" ->
                updateRequirementLabel(numericRequirementLabel, viewModel.isNumericReq());
            case "specialCharReq" ->
                updateRequirementLabel(specialCharRequirementLabel, viewModel.isSpecialCharReq());
            case "entropy" ->
                updateStrengthBar((int) viewModel.getEntropy());
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
        } else {
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
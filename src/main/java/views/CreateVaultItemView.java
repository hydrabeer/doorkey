package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.text.JTextComponent;

import service.create_vault_item.interface_adapter.CreateVaultItemController;
import service.create_vault_item.interface_adapter.CreateVaultItemState;
import service.create_vault_item.interface_adapter.CreateVaultItemViewModel;
import service.home.interface_adapter.HomeState;
import service.home.interface_adapter.HomeViewModel;
import service.password_validation.interface_adapter.PasswordValidationController;
import service.password_validation.interface_adapter.PasswordValidationViewModel;

/**
 * The dialog view for creating a new vault item.
 */
public class CreateVaultItemView extends JPanel implements PropertyChangeListener {
    private final CreateVaultItemViewModel createVaultItemViewModel;
    private final CreateVaultItemController createVaultItemController;
    private final PasswordValidationViewModel passwordValidationViewModel;
    private final PasswordValidationController passwordValidationController;
    private final HomeViewModel homeViewModel;

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

    private final JButton saveButton = createStyledButton("Save", 90, 35);
    private final JButton cancelButton = createStyledButton("Cancel", 90, 35);

    private final int componentWidth = 300;

    /**
     * Constructs the CreateVaultItemView dialog.
     *
     * @param homeViewModel The view model for the home view.
     * @param createVaultItemViewModel The view model for creating vault items.
     * @param createVaultItemController The controller for creating vault items.
     * @param passwordValidationViewModel The view model for password validation.
     * @param passwordValidationController The controller for password validation.
     */
    public CreateVaultItemView(
            HomeViewModel homeViewModel,
            CreateVaultItemViewModel createVaultItemViewModel,
            CreateVaultItemController createVaultItemController,
            PasswordValidationViewModel passwordValidationViewModel,
            PasswordValidationController passwordValidationController
    ) {
        this.homeViewModel = homeViewModel;
        this.createVaultItemViewModel = createVaultItemViewModel;
        this.createVaultItemController = createVaultItemController;
        this.passwordValidationViewModel = passwordValidationViewModel;
        this.passwordValidationController = passwordValidationController;
        this.createVaultItemViewModel.addPropertyChangeListener(this);

        initializeComponents();
        layoutComponents();
        registerEventHandlers();
        setPreferredSize(new Dimension(400, 550));

        passwordValidationViewModel.addPropertyChangeListener(evt -> {
            updatePasswordValidationView(evt);
        });

        passwordValidationController.validatePassword("", false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final CreateVaultItemState state = (CreateVaultItemState) evt.getNewValue();
        final String errorMessage = state.getErrorMessage();

        if (errorMessage.isEmpty()) {
            JOptionPane.showMessageDialog(this, errorMessage);
        }

        final boolean shouldClearFields = state.getClearFields();
        if (shouldClearFields) {
            resetTextFieldValues();
        }
    }

    private void resetTextFieldValues() {
        urlField.setText("");
        vaultTitleField.setText("");
        usernameField.setText("");
        passwordInputField.setText("");
        confirmPasswordField.setText("");
    }

    /**
     * Initializes all UI components.
     */
    private void initializeComponents() {
        final Font requirementFont = new Font(ViewConstants.DEFAULT_FONT_NAME, Font.PLAIN, 10);

        urlField = createTextField(15);
        vaultTitleField = createTextField(15);
        usernameField = createTextField(15);
        passwordInputField = createPasswordField(15);
        confirmPasswordField = createPasswordField(15);

        lengthRequirementLabel = createRequirementLabel("At least 8 characters", requirementFont);
        upperLowerRequirementLabel = createRequirementLabel(
                "At least one uppercase and lowercase letter",
                requirementFont
        );
        numericRequirementLabel = createRequirementLabel("At least one number", requirementFont);
        specialCharRequirementLabel = createRequirementLabel("At least one special character", requirementFont);

        strengthBar = createStrengthBar();
    }

    /**
     * Arranges all components within the dialog.
     */
    private void layoutComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final JPanel contentPanel = createContentPanel();

        contentPanel.add(createTitleLabel());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        contentPanel.add(createLabeledField("Enter URL", urlField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createLabeledField("Enter Vault Title", vaultTitleField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createLabeledField("Enter your username", usernameField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createLabeledField("Enter your password", passwordInputField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createGuidelinesSection());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createStrengthSection());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createLabeledField("Confirm your password", confirmPasswordField));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        contentPanel.add(createButtonsPanel());

        this.add(contentPanel);
        this.add(Box.createVerticalGlue());
    }

    /**
     * Registers event handlers for the UI components.
     */
    private void registerEventHandlers() {
        saveButton.addActionListener(event -> onSaveButtonClicked());

        cancelButton.addActionListener(event -> {
            createVaultItemController.cancel();
        });

        passwordInputField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                final String password = new String(passwordInputField.getPassword());
                passwordValidationController.validatePassword(password, false);
            }
        });
    }

    /**
     * Event handler for when the save button is clicked.
     */
    private void onSaveButtonClicked() {
        final String url = urlField.getText();
        final String vaultTitle = vaultTitleField.getText();
        final String username = usernameField.getText();
        final String password = new String(passwordInputField.getPassword());
        final String repeatedPassword = new String(confirmPasswordField.getPassword());

        final HomeState homeState = homeViewModel.getState();
        if (homeState.getUser().isPresent() && homeState.getUserRepository().isPresent()) {
            createVaultItemController.createVaultItem(
                homeState.getUser().get(),
                homeState.getUserRepository().get(),
                url,
                vaultTitle,
                username,
                password,
                repeatedPassword
            );
        }
    }

    /**
     * Updates the UI components based on changes in the password validation
     * view model.
     *
     * @param evt The property change event.
     */
    private void updatePasswordValidationView(PropertyChangeEvent evt) {
        final String propertyName = evt.getPropertyName();
        switch (propertyName) {
            case "lengthReq" ->
                updateRequirementLabel(lengthRequirementLabel, passwordValidationViewModel.isLengthReq());
            case "upperLowerReq" ->
                updateRequirementLabel(upperLowerRequirementLabel, passwordValidationViewModel.isUpperLowerReq());
            case "numericReq" ->
                updateRequirementLabel(numericRequirementLabel, passwordValidationViewModel.isNumericReq());
            case "specialCharReq" ->
                updateRequirementLabel(specialCharRequirementLabel, passwordValidationViewModel.isSpecialCharReq());
            case "entropy" ->
                updateStrengthBar((int) passwordValidationViewModel.getEntropy());
            default -> {
                // Nothing more to be done here
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
     * Initializes a requirement JLabel.
     *
     * @param text The text for the label.
     * @param font The font to use.
     * @return The initialized JLabel.
     */
    private JLabel createRequirementLabel(String text, Font font) {
        final JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.RED);
        return label;
    }

    /**
     * Creates a JTextField with specified columns and configures it.
     *
     * @param columns The number of columns for the text field.
     * @return Configured JTextField.
     */
    private JTextField createTextField(int columns) {
        final JTextField field = new JTextField(columns);
        configureTextComponent(field);
        return field;
    }

    /**
     * Creates a JPasswordField with specified columns and configures it.
     *
     * @param columns The number of columns for the password field.
     * @return Configured JPasswordField.
     */
    private JPasswordField createPasswordField(int columns) {
        final JPasswordField field = new JPasswordField(columns);
        configureTextComponent(field);
        return field;
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
     * Creates and configures the strength progress bar.
     *
     * @return Configured JProgressBar.
     */
    private JProgressBar createStrengthBar() {
        final JProgressBar bar = new JProgressBar(0, 100);
        bar.setPreferredSize(new Dimension(componentWidth, 15));
        bar.setMaximumSize(new Dimension(componentWidth, 15));
        bar.setStringPainted(false);
        bar.setForeground(Color.GRAY);
        bar.setBackground(Color.DARK_GRAY);
        bar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return bar;
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
        return button;
    }

    /**
     * Creates the main content panel.
     *
     * @return Configured JPanel.
     */
    private JPanel createContentPanel() {
        final JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(componentWidth, Integer.MAX_VALUE));
        return panel;
    }

    /**
     * Creates the title label.
     *
     * @return Configured JLabel.
     */
    private JLabel createTitleLabel() {
        final JLabel titleLabel = new JLabel("Create Vault Item");
        titleLabel.setFont(new Font(ViewConstants.DEFAULT_FONT_NAME, Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return titleLabel;
    }

    /**
     * Creates a panel with a label and associated field.
     *
     * @param labelText The text for the label.
     * @param field The field component.
     * @return A JPanel containing the label and field.
     */
    private JPanel createLabeledField(String labelText, JTextComponent field) {
        final JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel label = new JLabel(labelText);
        label.setFont(new Font(ViewConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);

        return panel;
    }

    /**
     * Creates the password guidelines section.
     *
     * @return Configured JPanel.
     */
    private JPanel createGuidelinesSection() {
        final JLabel guidelinesLabel = new JLabel("Password Guidelines:");
        guidelinesLabel.setFont(new Font(ViewConstants.DEFAULT_FONT_NAME, Font.BOLD, 14));
        guidelinesLabel.setForeground(Color.WHITE);
        guidelinesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JPanel requirementsPanel = new JPanel();
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

        final JPanel guidelinesSection = new JPanel();
        guidelinesSection.setBackground(Color.BLACK);
        guidelinesSection.setLayout(new BoxLayout(guidelinesSection, BoxLayout.Y_AXIS));
        guidelinesSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        guidelinesSection.add(guidelinesLabel);
        guidelinesSection.add(Box.createRigidArea(new Dimension(0, 5)));
        guidelinesSection.add(requirementsPanel);

        return guidelinesSection;
    }

    /**
     * Creates the password strength section.
     *
     * @return Configured JPanel.
     */
    private JPanel createStrengthSection() {
        final JLabel strengthLabel = new JLabel("Password Strength:");
        strengthLabel.setFont(new Font(ViewConstants.DEFAULT_FONT_NAME, Font.PLAIN, 12));
        strengthLabel.setForeground(Color.WHITE);
        strengthLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        strengthBar.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JPanel strengthSection = new JPanel();
        strengthSection.setBackground(Color.BLACK);
        strengthSection.setLayout(new BoxLayout(strengthSection, BoxLayout.Y_AXIS));
        strengthSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        strengthSection.add(strengthLabel);
        strengthSection.add(Box.createRigidArea(new Dimension(0, 5)));
        strengthSection.add(strengthBar);

        return strengthSection;
    }

    /**
     * Creates the buttons panel.
     *
     * @return Configured JPanel.
     */
    private JPanel createButtonsPanel() {
        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        buttonsPanel.add(saveButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonsPanel.add(cancelButton);

        return buttonsPanel;
    }

    /**
     * Simplified DocumentListener for convenience.
     */
    private abstract class SimpleDocumentListener implements javax.swing.event.DocumentListener {

        /**
         * Updates a Document Event.
         * @param event The event which is being updated.
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

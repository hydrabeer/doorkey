package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import service.home.interface_adapter.HomeState;
import service.home.interface_adapter.HomeViewModel;
import service.import_vault_item.interface_adapter.ImportVaultItemController;
import service.import_vault_item.interface_adapter.ImportVaultItemViewModel;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;

/**
 * The import vault item view.
 */
public class ImportVaultItemView extends JPanel implements ActionListener, PropertyChangeListener {
    private final ImportVaultItemViewModel importVaultItemViewModel;
    private final HomeViewModel homeViewModel;
    private final ImportVaultItemController importVaultItemController;

    private final JTextArea importTextField = new JTextArea();
    private final JComboBox<String> passwordManagerChooser = new JComboBox<>();
    private final JLabel errorText = new JLabel();

    public ImportVaultItemView(
            ImportVaultItemViewModel importVaultItemViewModel,
            HomeViewModel homeViewModel,
            ImportVaultItemController importVaultItemController
    ) {
        this.importVaultItemViewModel = importVaultItemViewModel;
        this.homeViewModel = homeViewModel;
        this.importVaultItemController = importVaultItemController;
        this.importVaultItemViewModel.addPropertyChangeListener(this);

        setUpMainPanel();

        addImportText();

        addDisclaimerText();

        addErrorText();

        addPasswordManagerChooser();

        addImportTextField();

        addSubmitAndBackButton();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final String error = (String) evt.getNewValue();
        errorText.setText(error);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
    }

    private void setUpMainPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    }

    private void addImportText() {
        final JLabel importText = new JLabel("Import Vault");
        importText.setFont(new DoorkeyFont(24));
        importText.setForeground(Color.WHITE);
        importText.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(importText);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addErrorText() {
        errorText.setFont(new DoorkeyFont());
        errorText.setForeground(Color.RED);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(errorText);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addPasswordManagerChooser() {
        passwordManagerChooser.addItem("1Password");
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(passwordManagerChooser);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addDisclaimerText() {
        final JLabel importInfoText = new JLabel("Paste your JSON export below.");
        importInfoText.setAlignmentX(Component.CENTER_ALIGNMENT);
        importInfoText.setFont(new DoorkeyFont());
        importInfoText.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        this.add(importInfoText);
    }

    private void addImportTextField() {
        final JPanel importTextFieldContainer = new JPanel();
        importTextFieldContainer.setLayout(new BoxLayout(importTextFieldContainer, BoxLayout.Y_AXIS));
        importTextFieldContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        importTextFieldContainer.setBackground(ViewConstants.BACKGROUND_COLOR);
        importTextField.setLineWrap(true);
        importTextField.setWrapStyleWord(true);
        importTextField.setFont(new DoorkeyFont());
        importTextField.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JScrollPane scrollPane = new JScrollPane(importTextField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        importTextFieldContainer.add(scrollPane);
        this.add(importTextFieldContainer);
    }

    private void addSubmitAndBackButton() {
        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        final DoorkeyButton submitButton = new DoorkeyButton.DoorkeyButtonBuilder("Import")
                .addListener(event -> {
                    final HomeState state = homeViewModel.getState();
                    if (state.getUser().isPresent() && state.getUserRepository().isPresent()) {
                        final String chosenPasswordManager = (String) passwordManagerChooser.getSelectedItem();
                        final String jsonText = importTextField.getText();
                        importVaultItemController.importVaultItems(chosenPasswordManager, jsonText,
                                state.getUser().get(), state.getUserRepository().get()
                        );
                    }
                })
                .build();

        final DoorkeyButton backButton = new DoorkeyButton.DoorkeyButtonBuilder("< Back")
                .addListener(event -> importVaultItemController.displayHomeView())
                .build();

        buttonsPanel.add(backButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonsPanel.add(submitButton);

        this.add(buttonsPanel);
    }
}

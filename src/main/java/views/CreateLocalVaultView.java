package views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.filechooser.FileNameExtensionFilter;

import service.ViewManagerModel;
import service.local.create.interface_adapter.CreateLocalVaultController;
import service.local.create.interface_adapter.CreateLocalVaultState;
import service.local.create.interface_adapter.CreateLocalVaultViewModel;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;
import views.components.DoorkeyForm;

/**
 * A view that allows the user to create a new local Doorkey vault.
 */
public class CreateLocalVaultView extends JPanel implements ActionListener, PropertyChangeListener {
    private DoorkeyButton back;
    private final DoorkeyForm form = new DoorkeyForm();
    private final JFileChooser saver = new JFileChooser();
    private final CreateLocalVaultController createLocalVaultController;
    private final CreateLocalVaultViewModel createLocalVaultViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateLocalVaultView(
        CreateLocalVaultController createLocalVaultController,
        CreateLocalVaultViewModel createLocalVaultViewModel,
        ViewManagerModel viewManagerModel
    ) {
        this.createLocalVaultController = createLocalVaultController;
        this.createLocalVaultViewModel = createLocalVaultViewModel;
        this.viewManagerModel = viewManagerModel;
        this.createLocalVaultViewModel.addPropertyChangeListener(this);

        setBackground();
        setBackButton();
        addPageTitle();
        addForm();
    }

    private void setBackground() {
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
    }

    private void setBackButton() {
        this.back = new DoorkeyButton.DoorkeyButtonBuilder("< Back")
            .addListener(event -> {
                viewManagerModel.setState(ViewConstants.LOCAL_VAULT_VIEW);
                viewManagerModel.onStateChanged();
            }).build();
        this.add(back);
    }

    private void addPageTitle() {
        final JLabel title = new JLabel("Create a new local vault:");
        title.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new DoorkeyFont(24));
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addForm() {
        form.addField(new JPasswordField(), "Password", "password");
        form.addField(new JPasswordField(), "Confirm", "confirm");

        saver.setDialogTitle("Choose a filename to save your local vault to:");
        saver.setFileFilter(
                new FileNameExtensionFilter("Doorkey vault", "doorkey")
        );
        this.add(saver);

        form.addSubmitButton("Create");
        this.form.addActionListener(event -> {
            final String password = form.getFieldValue("password");
            final String confirm = form.getFieldValue("confirm");
            if (!password.equals(confirm)) {
                form.setError("Passwords don't match!");
            }
            else {
                createLocalVaultController.createLocalVault(saver, password);
            }
        });
        this.add(form);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final CreateLocalVaultState createLocalVaultState = (CreateLocalVaultState) evt.getNewValue();
        // Only the overall error is ever changed for now. TODO: Optional - more error messages.
        if (createLocalVaultState.isSuccess()) {
            form.setError("");
        }
        else {
            form.setError("Invalid vault selection or empty password.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
    }
}

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
import service.local.load.interface_adapter.LoadLocalVaultController;
import service.local.load.interface_adapter.LoadLocalVaultState;
import service.local.load.interface_adapter.LoadLocalVaultViewModel;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;
import views.components.DoorkeyForm;

/**
 * A view to load an existing local .doorkey vault.
 */
public class LoadLocalVaultView extends JPanel implements ActionListener, PropertyChangeListener {
    private DoorkeyButton back;
    private final DoorkeyForm form = new DoorkeyForm();
    private final JFileChooser saver = new JFileChooser();
    private final LoadLocalVaultController loadLocalVaultController;
    private final LoadLocalVaultViewModel loadLocalVaultViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoadLocalVaultView(
        LoadLocalVaultController loadLocalVaultController,
        LoadLocalVaultViewModel loadLocalVaultViewModel,
        ViewManagerModel viewManagerModel
    ) {
        this.loadLocalVaultController = loadLocalVaultController;
        this.loadLocalVaultViewModel = loadLocalVaultViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loadLocalVaultViewModel.addPropertyChangeListener(this);

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
        final JLabel title = new JLabel("Load an existing .doorkey file:");
        title.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        title.setFont(new DoorkeyFont(24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addForm() {
        form.addField(new JPasswordField(), "Password", "password");
        saver.setDialogTitle("Select where your local vault is located:");
        saver.setFileFilter(
                new FileNameExtensionFilter("Doorkey vault", "doorkey")
        );
        this.add(saver);

        form.addSubmitButton("Load");
        this.form.addActionListener(event -> {
            loadLocalVaultController.loadLocalVault(saver, form.getFieldValue("password"));
        });
        this.add(form);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoadLocalVaultState loadLocalVaultState = (LoadLocalVaultState) evt.getNewValue();
        // Only the overall error is ever changed for now. TODO: Optional - more error messages.
        if (loadLocalVaultState.isSuccess()) {
            form.setError("");
        }
        else {
            form.setError("Invalid vault selection or wrong password.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
    }
}

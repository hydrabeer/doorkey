package views;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.filechooser.FileNameExtensionFilter;

import service.ViewManagerModel;
import service.local.load.interface_adapter.LoadLocalVaultController;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;
import views.components.DoorkeyForm;

/**
 * A view to load an existing local .doorkey vault.
 */
public class LoadLocalVaultView extends JPanel {
    private DoorkeyButton back;
    private final DoorkeyForm form = new DoorkeyForm();
    private final JFileChooser saver = new JFileChooser();
    private final LoadLocalVaultController controller = new LoadLocalVaultController(saver, form);
    private final ViewManagerModel viewManagerModel;

    public LoadLocalVaultView(ViewManagerModel viewManagerModel) {
        setBackground();
        setBackButton();
        addPageTitle();
        addForm();
        this.viewManagerModel = viewManagerModel;
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
        this.form.addActionListener(event -> controller.formSubmitted());
        this.add(form);
    }
}

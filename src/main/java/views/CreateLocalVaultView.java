package views;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.filechooser.FileNameExtensionFilter;

import interface_adapter.NavigableUiPanel;
import service.local.create.interface_adapter.CreateLocalVaultController;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;
import views.components.DoorkeyForm;

/**
 * A view that allows the user to create a new local Doorkey vault.
 */
public class CreateLocalVaultView extends NavigableUiPanel {
    private DoorkeyButton back;
    private final DoorkeyForm form = new DoorkeyForm();
    private final JFileChooser saver = new JFileChooser();
    private final CreateLocalVaultController controller = new CreateLocalVaultController(saver, form);
    private final ViewManager viewManager;

    public CreateLocalVaultView(ViewManager viewManager) {
        setBackground();
        setBackButton();
        addPageTitle();
        addForm();
        this.viewManager = viewManager;
    }

    private void setBackground() {
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
    }

    private void setBackButton() {
        this.back = new DoorkeyButton.DoorkeyButtonBuilder("< Back")
            .addListener(event -> {
                viewManager.showView(ViewConstants.LOCAL_VAULT_VIEW);
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
        this.form.addActionListener(event -> controller.formSubmitted());
        this.add(form);
    }

    @Override
    public String getViewName() {
        return ViewConstants.CREATE_LOCAL_VAULT_VIEW;
    }

}

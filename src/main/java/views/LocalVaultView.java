package views;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import views.components.DoorkeyButton;
import views.components.DoorkeyFont;

/**
 * A view that allows users to pick between loading or creating a local .doorkey vault.
 */
public class LocalVaultView extends JPanel {
    private final DoorkeyButton back = new DoorkeyButton("< Back");
    private final DoorkeyButton load = new DoorkeyButton("Load");
    private final DoorkeyButton create = new DoorkeyButton("Create");
    private final LoadLocalVaultView loadView = new LoadLocalVaultView();
    private final CreateLocalVaultView createView = new CreateLocalVaultView();

    public LocalVaultView() {
        this.setBackground();
        this.setBackButton();
        this.addPageTitle();
        this.addLoadButton();
        this.addCreateButton();
    }

    private void setBackground() {
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
    }

    private void setBackButton() {
        this.add(back);
    }

    public DoorkeyButton getBackButton() {
        return back;
    }

    private void addPageTitle() {
        final JLabel title = new JLabel("Load or create a new local vault:");
        title.setFont(new DoorkeyFont(24));
        title.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addLoadButton() {
        this.add(load);
    }

    public DoorkeyButton getLoadButton() {
        return this.load;
    }

    public LoadLocalVaultView getLoadView() {
        return this.loadView;
    }

    private void addCreateButton() {
        this.add(create);
    }

    public DoorkeyButton getCreateButton() {
        return this.create;
    }

    public CreateLocalVaultView getCreateView() {
        return this.createView;
    }
}

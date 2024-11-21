package views;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

import service.ViewManagerModel;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;

/**
 * A view that allows users to pick between loading or creating a local .doorkey vault.
 */
public class LocalVaultView extends JPanel {
    private DoorkeyButton back;
    private DoorkeyButton load;
    private DoorkeyButton create;
    private LoadLocalVaultView loadView;
    private CreateLocalVaultView createView;
    private final ViewManagerModel viewManagerModel;

    public LocalVaultView(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
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
        this.back = new DoorkeyButton.DoorkeyButtonBuilder("< Back")
            .addListener(event -> {
                viewManagerModel.setState(ViewConstants.LOGIN_VIEW);
                viewManagerModel.onStateChanged();
            }).build();
        this.add(back);
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
        load = new DoorkeyButton.DoorkeyButtonBuilder("Load")
            .addListener(event -> {
                viewManagerModel.setState(ViewConstants.LOAD_LOCAL_VAULT_VIEW);
                viewManagerModel.onStateChanged();
            }).build();
        this.add(load);
    }

    private void addCreateButton() {
        create = new DoorkeyButton.DoorkeyButtonBuilder("Create")
            .addListener(event -> {
                viewManagerModel.setState(ViewConstants.CREATE_LOCAL_VAULT_VIEW);
                viewManagerModel.onStateChanged();
            }).build();
        this.add(create);
    }
}

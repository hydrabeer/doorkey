package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.Border;

import data_access.authentication.FireStoreUserDataAccessObject;
import entity.AbstractVaultItem;
import entity.PasswordVaultItem;
import service.ViewManagerModel;
import service.home.interface_adapter.HomeController;
import service.home.interface_adapter.HomeState;
import service.home.interface_adapter.HomeViewModel;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;

/**
 * The main Home view that pops up after a success login.
 */
public class HomeView extends JPanel implements ActionListener, PropertyChangeListener {
    private final HomeController homeController;
    private final HomeViewModel homeViewModel;
    private final ViewManagerModel viewManagerModel;
    private final JLabel userInfo = new JLabel();
    private final JLabel userRepositoryInfo = new JLabel();
    private final JLabel itemsLabel = new JLabel();
    private final JPanel vaultPanel = new JPanel();

    public HomeView(
            HomeViewModel homeViewModel,
            HomeController homeController,
            ViewManagerModel viewManagerModel
    ) {
        this.homeController = homeController;
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;
        this.homeViewModel.addPropertyChangeListener(this);

        setUpMainPanel();

        addUserInfoPlaceholder();

        addBackButton();

        add(vaultPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final HomeState homeState = (HomeState) evt.getNewValue();
        addVaultItemsPanel(homeState);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
    }

    private void dispatchStates(HomeState homeState) {
        if (homeState.getUser().isPresent()) {
            userInfo.setText(homeState.getUser().get().getEmail());
        }

        if (homeState.getUserRepository().isPresent()) {
            if (homeState.getUserRepository().get() instanceof FireStoreUserDataAccessObject) {
                userRepositoryInfo.setText("Firebase");
            }
            else {
                userRepositoryInfo.setText("Local");
            }
        }

        final StringBuilder vaultItems = new StringBuilder();
        if (homeViewModel.getState().getUser().isEmpty()) {
            return;
        }

        for (AbstractVaultItem vaultItem : homeViewModel.getState().getUser().get().getVault().getItems()) {
            // TODO: Decryption Strategy
            vaultItems.append(vaultItem.getTitle()).append("\n");
        }

        itemsLabel.setText(vaultItems.toString());
    }

    private void setUpMainPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    }

    private void addUserInfoPlaceholder() {
        final BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        userInfo.setForeground(Color.WHITE);
        userInfo.setFont(new DoorkeyFont());
        userInfo.setAlignmentX(CENTER_ALIGNMENT);

        userRepositoryInfo.setForeground(Color.WHITE);
        userRepositoryInfo.setFont(new DoorkeyFont());
        userRepositoryInfo.setAlignmentX(CENTER_ALIGNMENT);

        add(userInfo);
        add(userRepositoryInfo);
    }

    private void addBackButton() {
        final DoorkeyButton back = new DoorkeyButton.DoorkeyButtonBuilder("< Back")
                .addListener(event -> {
                    viewManagerModel.setState(ViewConstants.LOGIN_VIEW);
                    viewManagerModel.onStateChanged();
                }).build();
        this.add(back);
    }

    private void addVaultItems() {
        itemsLabel.setForeground(Color.WHITE);
        itemsLabel.setFont(new DoorkeyFont());
        itemsLabel.setAlignmentX(CENTER_ALIGNMENT);

        add(itemsLabel);
    }
    private void addVaultItemsPanel(HomeState homeState) {
        // Create the parent panel with BoxLayout (vertical alignment)
        final JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
        parentPanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        // Wrap the parent panel in a JScrollPane
        final JScrollPane scrollPane = new JScrollPane(parentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBackground(ViewConstants.BACKGROUND_COLOR);

        if (homeState.getUser().isPresent()) {
            userInfo.setText(homeState.getUser().get().getEmail());
        }

        if (homeState.getUserRepository().isPresent()) {
            if (homeState.getUserRepository().get() instanceof FireStoreUserDataAccessObject) {
                userRepositoryInfo.setText("Firebase");
            }
            else {
                userRepositoryInfo.setText("Local");
            }
        }

        if (homeViewModel.getState().getUser().isEmpty()) {
            return;
        }

        for (AbstractVaultItem vaultItem : homeViewModel.getState().getUser().get().getVault().getItems()) {
            // TODO: Decryption Strategy
            parentPanel.add(addVaultItem(vaultItem));
        }
        vaultPanel.add(scrollPane);
    }

    private JPanel addVaultItem(AbstractVaultItem vaultItem) {
        final JPanel vaultItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vaultItemPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel title = addDisplay(vaultItem.getTitle());
        title.setForeground(Color.WHITE);
        title.setFont(new DoorkeyFont());
        final JButton accessButton = addAccessButton(title.getHeight(), vaultItem);
        vaultItemPanel.add(title);
        vaultItemPanel.add(accessButton);
        vaultItemPanel.setMaximumSize(new Dimension(300, vaultItemPanel.getPreferredSize().height));
        return vaultItemPanel;
    }

    private JButton addAccessButton(int height, AbstractVaultItem vaultItem) {
        final JButton accessButton = new DoorkeyButton.DoorkeyButtonBuilder("\uD83D\uDD13")
                .addListener(event -> {
                    homeController.displayVaultItem(vaultItem);
                })
                .build();
        accessButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        accessButton.setForeground(Color.WHITE);
        accessButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(height, 5, height, 5)));
        return accessButton;
    }

    private JLabel addDisplay(String text) {
        final JLabel display = new JLabel(text);
        display.setForeground(Color.WHITE);
        display.setFont(new DoorkeyFont());
        display.setPreferredSize(new Dimension(250, display.getPreferredSize().height));
        display.setMaximumSize(new Dimension(250, display.getPreferredSize().height));
        final Border border = BorderFactory.createLineBorder(ViewConstants.TEXT_MUTED_COLOR, 1);
        display.setBorder(border);
        display.setVisible(true);
        return display;
    }
}

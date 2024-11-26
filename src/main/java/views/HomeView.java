package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import data_access.authentication.FireStoreUserDataAccessObject;
import entity.AbstractVaultItem;
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
    private final JLabel userInfo = new JLabel();
    private final JLabel userRepositoryInfo = new JLabel();
    private final JPanel vaultPanel = new JPanel();

    public HomeView(
            HomeViewModel homeViewModel,
            HomeController homeController
    ) {
        this.homeController = homeController;
        this.homeViewModel = homeViewModel;
        this.homeViewModel.addPropertyChangeListener(this);

        setUpMainPanel();

        addUserInfoPlaceholder();

        addBackButton();

        add(vaultPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final HomeState homeState = (HomeState) evt.getNewValue();
        dispatchStates(homeState);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
    }

    private void dispatchStates(HomeState homeState) {
        setVaultItems(homeState);
        setRepositoryInfo(homeState);
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
                .addListener(event -> homeController.displayLoginView()).build();
        this.add(back);
    }

    private void setVaultItems(HomeState homeState) {
        final JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
        parentPanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        final JScrollPane scrollPane = new JScrollPane(parentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBackground(ViewConstants.BACKGROUND_COLOR);

        if (homeState.getUser().isPresent()) {
            final String email = homeState.getUser().get().getEmail();
            if (email != null) {
                userInfo.setText(email);
            }
            else {
                userInfo.setText("Used locally - no email");
            }
        }

        if (homeViewModel.getState().getUser().isEmpty()) {
            return;
        }

        for (AbstractVaultItem vaultItem : homeViewModel.getState().getUser().get().getVault().getItems()) {
            parentPanel.add(addVaultItem(vaultItem));
        }

        // Baris: Added this line to remove all the components from the vaultPanel
        vaultPanel.removeAll();
        vaultPanel.add(scrollPane);
    }

    // TODO: Remove this user repository info text (the entire if statement and its body)
    private void setRepositoryInfo(HomeState homeState) {
        if (homeState.getUserRepository().isPresent()) {
            if (homeState.getUserRepository().get() instanceof FireStoreUserDataAccessObject) {
                userRepositoryInfo.setText("Firebase");
            }
            else {
                userRepositoryInfo.setText("Local");
            }
        }
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

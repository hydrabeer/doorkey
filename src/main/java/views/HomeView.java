package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import data_access.FireStoreUserDataAccessObject;
import entity.AbstractVaultItem;
import exception.InvalidVaultItemException;
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
    private final JButton addItemButton = createAddButton();

    public HomeView(
            HomeViewModel homeViewModel,
            HomeController homeController
    ) {
        this.homeController = homeController;
        this.homeViewModel = homeViewModel;
        this.homeViewModel.addPropertyChangeListener(this);

        setUpMainPanel();

        add(vaultPanel, BorderLayout.CENTER);

        addSearchPanel();

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
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    }

    private void setVaultItems(HomeState homeState) {
        final JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
        parentPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        parentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

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
            parentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        final JScrollPane scrollPane = new JScrollPane(parentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(ViewConstants.BACKGROUND_COLOR);
        scrollPane.setPreferredSize(new Dimension(150, 470));
        scrollPane.setMaximumSize(new Dimension(150, 470));
        // Baris: Added this line to remove all the components from the vaultPanel
        vaultPanel.removeAll();
        vaultPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        vaultPanel.add(scrollPane);
        vaultPanel.setPreferredSize(new Dimension(150, 500));
        vaultPanel.setMaximumSize(new Dimension(150, 500));
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
        final JPanel vaultItemPanel = new JPanel();
        vaultItemPanel.setLayout(new BorderLayout());
        vaultItemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        vaultItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        vaultItemPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel title = new JLabel(vaultItem.getTitle());
        title.setForeground(Color.WHITE);
        title.setFont(new DoorkeyFont());
        final JLabel subTitle = new JLabel(vaultItem.getType());
        subTitle.setForeground(Color.WHITE);
        subTitle.setFont(new DoorkeyFont());
        final JButton accessButton = addAccessButton(subTitle.getHeight(), vaultItem);
        vaultItemPanel.add(title, BorderLayout.NORTH);
        vaultItemPanel.add(subTitle, BorderLayout.SOUTH);
        vaultItemPanel.add(accessButton, BorderLayout.EAST);
        return vaultItemPanel;
    }

    private JButton addAccessButton(int height, AbstractVaultItem vaultItem) {
        final JButton accessButton = new DoorkeyButton.DoorkeyButtonBuilder("\uD83D\uDD13")
                // button text is unlock character
                .addListener(event -> {
                    try {
                        homeController.displayVaultItem(vaultItem);
                    }
                    catch (InvalidVaultItemException exception) {
                        throw new RuntimeException(exception);
                    }
                })
                .build();
        accessButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        accessButton.setForeground(Color.WHITE);
        accessButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(height, 5, height, 5)));
        return accessButton;
    }

    private void addSearchPanel() {
        final JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        // Center Panel for Welcome Message
        final JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel welcomeLabel = new JLabel(
                "<html><div style='text-align: center;'>Welcome to Your DoorKey "
                        + "Vault!<br>Choose or add an item to get started.</div></html>");
        welcomeLabel.setFont(new DoorkeyFont());
        welcomeLabel.setForeground(Color.WHITE);
        centerPanel.add(welcomeLabel);
        rightPanel.add(centerPanel, BorderLayout.CENTER);

        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        // Search Bar Label
        final JLabel searchLabel = new JLabel("Search Vault:");
        searchLabel.setFont(new DoorkeyFont());
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topPanel.add(searchLabel);

        topPanel.add(createSearchPanel(), BorderLayout.NORTH);
        rightPanel.add(topPanel, BorderLayout.NORTH);

        // Buttons
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JButton signOutButton = creatSignOutButton();

        buttonPanel.add(signOutButton, BorderLayout.CENTER);
        buttonPanel.add(addItemButton, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        rightPanel.setPreferredSize(new Dimension(200, 470));
        rightPanel.setMaximumSize(new Dimension(200, 470));
        add(rightPanel, BorderLayout.CENTER);
    }

    private JTextField createSearchPanel() {
        final JTextField searchField = new JTextField();
        searchField.setFont(new DoorkeyFont());
        searchField.setBackground(ViewConstants.BACKGROUND_COLOR);
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(4, 100, 4, 100))
        );

        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setMaximumSize(new Dimension(200, 30));
        searchField.setAlignmentX(Component.LEFT_ALIGNMENT);
        return searchField;
    }

    private JButton creatSignOutButton() {
        final JButton backButton = new DoorkeyButton.DoorkeyButtonBuilder("\uD83D\uDD12")
                .addListener(event -> {
                    homeController.displayLoginView();
                }
                            ).build();
        backButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return backButton;
    }

    private JButton createAddButton() {
        final JButton addButton = new DoorkeyButton.DoorkeyButtonBuilder("➕")
                .addListener(event -> {
                    homeController.displayCreateVaultItemView();
                }
                ).build();
        addButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        addButton.setForeground(Color.WHITE);
        addButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return addButton;
    }
}

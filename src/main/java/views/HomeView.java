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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import entity.AbstractVaultItem;
import exception.InvalidVaultItemException;
import service.home.interface_adapter.HomeController;
import service.home.interface_adapter.HomeViewModel;
import service.search.interface_adapter.SearchController;
import service.search.interface_adapter.SearchState;
import service.search.interface_adapter.SearchViewModel;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;

/**
 * The main Home view that pops up after a success login.
 */
public class HomeView extends JPanel implements ActionListener, PropertyChangeListener {
    private final HomeController homeController;
    private final HomeViewModel homeViewModel;
    private final SearchController searchController;
    private final SearchViewModel searchViewModel;
    private final JPanel vaultPanel = new JPanel();
    private final JButton addItemButton = createAddButton();
    private final JButton importItemButton = createImportButton();
    private final JTextField searchField = createSearchField();

    public HomeView(
            HomeViewModel homeViewModel,
            HomeController homeController,
            SearchViewModel searchViewModel,
            SearchController searchController
    ) {
        this.homeController = homeController;
        this.homeViewModel = homeViewModel;
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;
        this.homeViewModel.addPropertyChangeListener(this);
        this.searchViewModel.addPropertyChangeListener(this::updateVaultView);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onSearchFieldChanged(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onSearchFieldChanged(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onSearchFieldChanged(e);
            }
        });

        setUpMainPanel();

        add(vaultPanel, BorderLayout.CENTER);

        searchController.execute("");

        addSearchPanel();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        rerenderVaultPanel(searchField.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
    }

    private void setUpMainPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    }

    private void onSearchFieldChanged(DocumentEvent event) {
        rerenderVaultPanel(searchField.getText());
    }

    private void rerenderVaultPanel(String searchQuery) {
        if (homeViewModel.getState().getUser().isEmpty()) {
            return;
        }

        if (homeViewModel.getState().getUserRepository().isEmpty()) {
            return;
        }

        searchController.execute(searchQuery);
    }

    private void updateVaultView(PropertyChangeEvent event) {
        final JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
        parentPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        parentPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 15));

        final SearchState state = (SearchState) event.getNewValue();
        for (AbstractVaultItem vaultItem : state.getItems()) {
            parentPanel.add(addVaultItem(vaultItem));
            parentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        final JScrollPane scrollPane = new JScrollPane(parentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(ViewConstants.BACKGROUND_COLOR);
        scrollPane.setPreferredSize(new Dimension(200, 470));
        scrollPane.setMaximumSize(new Dimension(200, 470));
        // Baris: Added this line to remove all the components from the vaultPanel
        vaultPanel.removeAll();
        vaultPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        vaultPanel.add(scrollPane);
        vaultPanel.setPreferredSize(new Dimension(200, 500));
        vaultPanel.setMaximumSize(new Dimension(200, 500));
        vaultPanel.revalidate();
        vaultPanel.repaint();
    }

    private JPanel addVaultItem(AbstractVaultItem vaultItem) {
        final JPanel vaultItemPanel = new JPanel();
        vaultItemPanel.setLayout(new BorderLayout());
        vaultItemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        vaultItemPanel.setPreferredSize(new Dimension(170, 80));
        vaultItemPanel.setMaximumSize(new Dimension(170, 80));

        vaultItemPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel title = new JLabel(vaultItem.getTitle());
        title.setPreferredSize(new Dimension(170, title.getPreferredSize().height));
        title.setMaximumSize(new Dimension(170, title.getPreferredSize().height));
        title.setForeground(Color.WHITE);
        title.setFont(new DoorkeyFont());
        final JLabel subTitle = new JLabel(vaultItem.getType());
        subTitle.setForeground(Color.WHITE);
        subTitle.setFont(new DoorkeyFont());
        subTitle.setPreferredSize(new Dimension(170, title.getPreferredSize().height));
        subTitle.setMaximumSize(new Dimension(170, title.getPreferredSize().height));
        final JButton accessButton = addAccessButton(5, vaultItem);
        vaultItemPanel.add(title, BorderLayout.NORTH);
        vaultItemPanel.add(subTitle, BorderLayout.SOUTH);
        vaultItemPanel.add(accessButton, BorderLayout.EAST);
        return vaultItemPanel;
    }

    private JButton addAccessButton(
            int height, AbstractVaultItem vaultItem) {
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

        topPanel.add(searchField, BorderLayout.NORTH);
        rightPanel.add(topPanel, BorderLayout.NORTH);

        addToButtonPanel(rightPanel);

        rightPanel.setPreferredSize(new Dimension(260, 470));
        rightPanel.setMaximumSize(new Dimension(260, 470));
        add(rightPanel, BorderLayout.CENTER);
    }

    private void addToButtonPanel(JPanel rightPanel) {
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JButton signOutButton = createSignOutButton();

        buttonPanel.add(signOutButton, BorderLayout.CENTER);
        buttonPanel.add(addItemButton, BorderLayout.CENTER);
        buttonPanel.add(importItemButton, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        rightPanel.setPreferredSize(new Dimension(260, 470));
        rightPanel.setMaximumSize(new Dimension(260, 470));
        add(rightPanel, BorderLayout.CENTER);
    }

    private JTextField createSearchField() {
        final JTextField search = new JTextField();
        search.setFont(new DoorkeyFont());
        search.setBackground(ViewConstants.BACKGROUND_COLOR);
        search.setForeground(Color.WHITE);
        search.setCaretColor(Color.WHITE);
        search.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10))
        );
        search.setPreferredSize(new Dimension(260, 30));
        search.setMaximumSize(new Dimension(260, 30));
        search.setAlignmentX(Component.LEFT_ALIGNMENT);

        return search;
    }

    private JButton createSignOutButton() {
        final JButton backButton = new DoorkeyButton.DoorkeyButtonBuilder("\uD83D\uDD12")
                .addListener(event -> {
                    homeController.signOut();
                })
                .build();
        backButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return backButton;
    }

    private JButton createAddButton() {
        final JButton addButton = new DoorkeyButton.DoorkeyButtonBuilder("➕")
                .addListener(event -> homeController.displayCreateVaultItemView()
                ).build();
        addButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        addButton.setForeground(Color.WHITE);
        addButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return addButton;
    }

    private JButton createImportButton() {
        final JButton importButton = new DoorkeyButton.DoorkeyButtonBuilder("📤")
                .addListener(event -> homeController.displayImportView())
                .build();
        importButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        importButton.setForeground(Color.WHITE);
        importButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return importButton;
    }
}

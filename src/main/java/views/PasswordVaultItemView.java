package presenters.views;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

import org.jetbrains.annotations.NotNull;

import presenters.views.components.DoorkeyButton;
import presenters.views.components.DoorkeyFont;

/**
 * Vault Item.
 */
public class PasswordVaultItemView extends JFrame {
    private final JPanel panel = getMainPanel();

    public PasswordVaultItemView() {
        super("DoorKey");
        setUpMainFrame();

        addVaultItemTitle();
        addActionsPanel();
        addUsernamePanel();
        addPasswordPanel();
        addUrlPanel();
        this.add(panel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void addActionsPanel() {
        final JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionsPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        actionsPanel.setPreferredSize(new Dimension(300, 40));
        actionsPanel.setMaximumSize(new Dimension(300, 40));
        final JButton editButton = addEditButton();
        final JButton deleteButton = addDeleteButton();
        actionsPanel.add(editButton);
        actionsPanel.add(deleteButton);
        panel.add(actionsPanel);
    }

    private void addVaultItemTitle() {
        final JLabel titleLabel = new JLabel("This will be Vault Title");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new DoorkeyFont(18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void addUsernamePanel() {
        final JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel usernameLabel = addLabel("Username:");
        usernamePanel.add(usernameLabel);
        usernamePanel.add(addDisplay("temp username", usernameLabel.getHeight()));
        usernamePanel.add(addCopyButton(usernameLabel.getPreferredSize().height));
        usernamePanel.setMaximumSize(new Dimension(300, usernamePanel.getPreferredSize().height));
        panel.add(usernamePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addPasswordPanel() {
        final JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel passwordLabel = addLabel("Password:");
        passwordPanel.add(passwordLabel);
        passwordPanel.add(addDisplay("temp password", passwordLabel.getHeight()));
        passwordPanel.add(addCopyButton(passwordLabel.getPreferredSize().height));
        passwordPanel.add(addHideButton(passwordLabel.getPreferredSize().height));
        passwordPanel.setMaximumSize(new Dimension(300, passwordPanel.getPreferredSize().height));
        panel.add(passwordPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addUrlPanel() {
        final JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        urlPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel urlLabel = addLabel("URL:     ");
        urlPanel.add(urlLabel);
        urlPanel.add(addDisplay("temp url", urlLabel.getHeight()));
        urlPanel.add(addUrl(urlLabel.getPreferredSize().height));
        urlPanel.setMaximumSize(new Dimension(300, urlPanel.getPreferredSize().height));
        panel.add(urlPanel);
    }

    private JLabel addLabel(String text) {
        final JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new DoorkeyFont());
        return label;
    }

    private JLabel addDisplay(String text, int height) {
        final JLabel display = new JLabel(text);
        display.setForeground(Color.WHITE);
        display.setFont(new DoorkeyFont());
        display.setPreferredSize(new Dimension(150, display.getPreferredSize().height));
        display.setMaximumSize(new Dimension(150, height));
        final Border border = BorderFactory.createLineBorder(ViewConstants.TEXT_MUTED_COLOR, 1);
        display.setBorder(border);
        display.setVisible(true);
        return display;
    }

    private JButton addCopyButton(int height) {
        final JButton copyButton = new DoorkeyButton.DoorkeyButtonBuilder("📋")
                .addListener(event -> {
                    // TODO: Implement navigation controller.
                    showPlaceholderPage();
                })
                .build();
        copyButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        copyButton.setForeground(Color.WHITE);
        copyButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ViewConstants.BACKGROUND_COLOR, 1, true),
                BorderFactory.createEmptyBorder(height, 5, height, 5)));
        return copyButton;
    }

    private JButton addHideButton(int height) {
        final JButton hideButton = new DoorkeyButton.DoorkeyButtonBuilder("\uD83D\uDC41\uFE0F")
                .addListener(event -> {
                    // TODO: Implement navigation controller.
                    showPlaceholderPage();
                })
                .build();
        hideButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        hideButton.setForeground(Color.WHITE);
        hideButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ViewConstants.BACKGROUND_COLOR, 1, true),
                BorderFactory.createEmptyBorder(height, 5, height, 5)));
        return hideButton;
    }

    private JButton addUrl(int height) {
        final JButton urlButton = new DoorkeyButton.DoorkeyButtonBuilder("\uD83D\uDD17")
                .addListener(event -> {
                    // TODO: Implement navigation controller.
                    showPlaceholderPage();
                })
                .build();
        urlButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        urlButton.setForeground(Color.WHITE);
        urlButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ViewConstants.BACKGROUND_COLOR, 1, true),
                BorderFactory.createEmptyBorder(height, 5, height, 5)));
        return urlButton;
    }

    private JButton addEditButton() {
        final JButton editButton = new DoorkeyButton.DoorkeyButtonBuilder("✏")
                .addListener(event -> {
                    // TODO: Implement navigation controller.
                    showPlaceholderPage();
                })
                .build();
        editButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        editButton.setForeground(Color.WHITE);
        editButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(5, 9, 5, 9)));
        return editButton;
    }

    private JButton addDeleteButton() {
        final JButton deleteButton = new DoorkeyButton.DoorkeyButtonBuilder("\uD83D\uDDD1\uFE0F")
                .addListener(event -> {
                    // TODO: Implement navigation controller.
                    showPlaceholderPage();
                })
                .build();
        deleteButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return deleteButton;
    }

    private void showPlaceholderPage() {
        final JPanel newPanel = new JPanel(new BorderLayout());

        final JButton switchButton = new JButton(
                "TODO (will be replaced with a NavigationController): Click to go back"
        );
        switchButton.addActionListener(event -> {
            setContentPane(panel);
            revalidate();
            repaint();
        });

        newPanel.add(switchButton);

        setContentPane(newPanel);
        revalidate();
        repaint();
    }

    @NotNull
    private static JPanel getMainPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ViewConstants.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        return panel;
    }

    private void setUpMainFrame() {
        // The following line is commented out, as it can be activated once
        // the panels are made prettier, and the support to add/close is added.
        //
        // this.setUndecorated(true);
        //
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 500);
        this.setLayout(new BorderLayout());
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
    }
}


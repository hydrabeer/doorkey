package presenters.views;

import org.jetbrains.annotations.NotNull;
import presenters.views.components.DoorkeyButton;
import presenters.views.components.DoorkeyFont;
import presenters.views.components.DoorkeyForm;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PasswordVaultItemView extends JFrame {
    private final JPanel panel = getMainPanel();
    private final DoorkeyForm form = new DoorkeyForm();

    public PasswordVaultItemView() {
        super("DoorKey");
        setUpMainFrame();

        addVaultItemTitle();
        addActionsPanel();
        addUsernamePanel();
        addPasswordPanel();
        addUrlPanel();
        panel.add(form);
        this.add(panel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void addActionsPanel() {
        final JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionsPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        actionsPanel.setPreferredSize(new Dimension(60, 40));
        actionsPanel.setMaximumSize(new Dimension(60, 40));
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
    }

    private void addUsernamePanel() {
        final JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        usernameLabel.setFont(new DoorkeyFont());
        final JLabel usernameDisplay = new JLabel("example_user");
        usernameDisplay.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        usernameDisplay.setFont(new DoorkeyFont());
        usernameDisplay.setPreferredSize(new Dimension(197, usernameLabel.getPreferredSize().height));
        usernameDisplay.setMaximumSize(new Dimension(197, usernameLabel.getPreferredSize().height));
        final Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
        usernameDisplay.setBorder(border);
        final JButton copyButton = addCopyButton();
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameDisplay);
        usernamePanel.add(copyButton);
        usernamePanel.setMaximumSize(new Dimension(350, usernamePanel.getPreferredSize().height));
        form.add(usernamePanel);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addPasswordPanel() {
        final JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new DoorkeyFont());
        final JLabel passwordDisplay = new JLabel("********");
        passwordDisplay.setForeground(Color.WHITE);
        passwordDisplay.setFont(new DoorkeyFont());
        passwordDisplay.setPreferredSize(new Dimension(197, passwordLabel.getPreferredSize().height));
        passwordDisplay.setMaximumSize(new Dimension(197, passwordLabel.getPreferredSize().height));
        final Border border = BorderFactory.createLineBorder(ViewConstants.TEXT_MUTED_COLOR, 1);
        passwordDisplay.setBorder(border);
        final JButton copyButton = addCopyButton();
        final JButton hideButton = addHideButton();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordDisplay);
        passwordPanel.add(copyButton);
        passwordPanel.add(hideButton);
        passwordPanel.setMaximumSize(new Dimension(350, passwordLabel.getPreferredSize().height));
        form.add(passwordPanel);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addUrlPanel() {
        final JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        urlPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel urlLabel = new JLabel("URL:         ");
        urlLabel.setForeground(Color.WHITE);
        urlLabel.setFont(new DoorkeyFont());
        final JButton url = addUrl();
        urlPanel.add(urlLabel);
        urlPanel.add(url);
        urlPanel.setMaximumSize(new Dimension(350, url.getPreferredSize().height + 30));
        form.add(urlPanel);
    }

    private JButton addCopyButton() {
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
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return copyButton;
    }

    private JButton addHideButton() {
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
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return hideButton;
    }

    private JButton addUrl() {
        final JButton hideButton = new DoorkeyButton.DoorkeyButtonBuilder("https://example.com")
                .addListener(event -> {
                    // TODO: Implement navigation controller.
                    showPlaceholderPage();
                })
                .build();
        hideButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        hideButton.setForeground(Color.WHITE);
        hideButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ViewConstants.TEXT_MUTED_COLOR, 1, true),
                BorderFactory.createEmptyBorder(80, 60, 80, 60)));
        return hideButton;
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
                BorderFactory.createLineBorder(ViewConstants.BACKGROUND_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
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
                BorderFactory.createLineBorder(ViewConstants.BACKGROUND_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
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


    /**
     * Get the email input value.
     *
     * @return Current email input value.
     */
    public String getEmail() {
        return form.getFieldValue("email");
    }

    /**
     * Get the email password value.
     *
     * @return Current password input value.
     */
    public String getPassword() {
        return form.getFieldValue("password");
    }

    /**
     * Return the DForm.
     *
     * @return Current login DForm.
     */
    public DoorkeyForm getForm() {
        return form;
    }
}


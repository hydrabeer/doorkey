package views;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.border.Border;

import entity.PasswordVaultItem;
import org.jetbrains.annotations.NotNull;

import service.ViewManagerModel;
import service.copy_credentials.interface_adapter.CopyCredentialsController;
import service.login.interface_adapter.LoginState;
import service.url_redirect.interface_adapter.UrlRedirectController;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;
import views.components.DoorkeyForm;

/**
 * Vault Item.
 */
public class PasswordVaultItemView extends JPanel implements PropertyChangeListener {
    private final JPanel panel = getMainPanel();
    private final String hidePassword = "**********";
    private final CopyCredentialsController copyCredentialsController;
    private final UrlRedirectController urlRedirectController;
    private final DoorkeyForm form = new DoorkeyForm();
    private final PasswordVaultItemViewModel passwordVaultItemViewModel;
    private final ViewManagerModel viewManagerModel;

    public PasswordVaultItemView(ViewManagerModel viewManagerModel,
            CopyCredentialsController copyCredentialsController, UrlRedirectController urlRedirectController,
            PasswordVaultItemViewModel passwordVaultItemViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.copyCredentialsController = copyCredentialsController;
        this.urlRedirectController = urlRedirectController;
        this.passwordVaultItemViewModel = passwordVaultItemViewModel;
        this.passwordVaultItemViewModel.addPropertyChangeListener(this);

        setUpMainFrame();
        this.add(panel, BorderLayout.NORTH);
        this.add(form, BorderLayout.CENTER);
        this.setVisible(true);

    }

    private void addActionsPanel(PasswordVaultItemState passwordVaultItemState) {
        if (passwordVaultItemState.getVaultItem().isEmpty()) {
            return;
        }
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

    private void addVaultItemTitle(PasswordVaultItemState passwordVaultItemState) {
        if (passwordVaultItemState.getVaultItem().isEmpty()) {
            return;
        }
        final PasswordVaultItemState state = passwordVaultItemViewModel.getState();
        final JLabel titleLabel = new JLabel(state.getTitle());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new DoorkeyFont(18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

    }

    private void addUsernamePanel(PasswordVaultItemState passwordVaultItemState) {
        if (passwordVaultItemState.getVaultItem().isEmpty()) {
            return;
        }
        final PasswordVaultItemState state = passwordVaultItemViewModel.getState();
        final JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel usernameLabel = addLabel("Username:");
        usernamePanel.add(usernameLabel);
        usernamePanel.add(addDisplay(state.getUsername(), usernameLabel.getHeight()));
        usernamePanel.add(addCopyButton(usernameLabel.getPreferredSize().height, "username", state.getUsername()));
        usernamePanel.setMaximumSize(new Dimension(300, usernamePanel.getPreferredSize().height));
        form.add(usernamePanel);
        form.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addPasswordPanel(PasswordVaultItemState passwordVaultItemState) {
        if (passwordVaultItemState.getVaultItem().isEmpty()) {
            return;
        }
        final PasswordVaultItemState state = passwordVaultItemViewModel.getState();
        final JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel passwordLabel = addLabel("Password:");
        passwordPanel.add(passwordLabel);
        final JLabel passwordDisplay = addDisplay(hidePassword, passwordLabel.getHeight());
        passwordPanel.add(addCopyButton(passwordLabel.getPreferredSize().height, "password", state.getPassword()));
        passwordPanel.add(addHideButton(passwordLabel.getPreferredSize().height, passwordDisplay, state.getPassword()));
        passwordPanel.setMaximumSize(new Dimension(300, passwordPanel.getPreferredSize().height));
        form.add(passwordPanel);
        form.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addUrlPanel(PasswordVaultItemState passwordVaultItemState) {
        if (passwordVaultItemState.getVaultItem().isEmpty()) {
            return;
        }
        final PasswordVaultItemState state = passwordVaultItemViewModel.getState();
        final JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        urlPanel.setBackground(ViewConstants.BACKGROUND_COLOR);
        final JLabel urlLabel = addLabel("URL:     ");
        urlPanel.add(urlLabel);
        urlPanel.add(addDisplay(state.getUrl(), urlLabel.getHeight()));
        urlPanel.add(addUrl(urlLabel.getPreferredSize().height, state.getUrl()));
        urlPanel.setMaximumSize(new Dimension(300, urlPanel.getPreferredSize().height));
        form.add(urlPanel);
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

    private JButton addCopyButton(int height, String type, String copyText) {
        final JButton copyButton = new DoorkeyButton.DoorkeyButtonBuilder("📋")
                .addListener(event -> {
                    copyMessageSequence(type, copyText);
                })
                .build();
        copyButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        copyButton.setForeground(Color.WHITE);
        copyButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ViewConstants.BACKGROUND_COLOR, 1, true),
                BorderFactory.createEmptyBorder(height, 5, height, 5)));
        return copyButton;
    }

    private JButton addHideButton(int height, JLabel label, String password) {
        final JButton hideButton = new DoorkeyButton.DoorkeyButtonBuilder("\uD83D\uDC41\uFE0F")
                .addListener(event -> {
                    if (hidePassword.equals(label.getText())) {
                        label.setText(password);
                    }
                    else {
                        label.setText(hidePassword);
                    }

                })
                .build();
        hideButton.setBackground(ViewConstants.BACKGROUND_COLOR);
        hideButton.setForeground(Color.WHITE);
        hideButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ViewConstants.BACKGROUND_COLOR, 1, true),
                BorderFactory.createEmptyBorder(height, 5, height, 5)));
        return hideButton;
    }

    private JButton addUrl(int height, String url) {
        final JButton urlButton = new DoorkeyButton.DoorkeyButtonBuilder("\uD83D\uDD17")
                .addListener(event -> {
                    urlRedirectController.openUrl(url);
                    form.setError(passwordVaultItemViewModel.getState().getError());
                    passwordVaultItemViewModel.getState().setError("");
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

    private void copyMessageSequence(String type, String copyText) {
        if ("username".equals(type)) {
            copyCredentialsController.copyUsernameClicked(copyText);
            form.setError(passwordVaultItemViewModel.getState().getMessage());
        }
        else {
            CopyCredentialsController.copyPasswordClicked(copyText);
            form.setError(passwordVaultItemViewModel.getState().getMessage());
        }
        copyCredentialsController.clearClipboard();
        final java.util.Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            public void run() {
                form.setError(passwordVaultItemViewModel.getState().getMessage());
            }
        };
        timer.schedule(timerTask, 10000);
    }

    private void showPlaceholderPage() {
        final JPanel newPanel = new JPanel(new BorderLayout());

        final JButton switchButton = new JButton(
                "Coming Soon 2025"
        );
        switchButton.addActionListener(event -> {
            revalidate();
            repaint();
        });

        newPanel.add(switchButton);

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
        this.setSize(400, 500);
        this.setLayout(new BorderLayout());
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final PasswordVaultItemState passwordVaultItemState = (PasswordVaultItemState) evt.getNewValue();
        addVaultItemTitle(passwordVaultItemState);
        addActionsPanel(passwordVaultItemState);
        addUsernamePanel(passwordVaultItemState);
        addPasswordPanel(passwordVaultItemState);
        addUsernamePanel(passwordVaultItemState);
        this.revalidate();
        this.repaint();
    }

}


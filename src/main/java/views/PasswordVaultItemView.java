package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import service.ViewManagerModel;
import service.copy_credentials.interface_adapter.CopyCredentialsController;
import service.url_redirect.interface_adapter.UrlRedirectController;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;
import views.components.DoorkeyForm;

/**
 * Vault Item.
 */
public class PasswordVaultItemView extends JPanel implements ActionListener, PropertyChangeListener {
    // Constants
    private static final String USERNAME = "Username:";
    private static final String PASSWORD = "Password:";
    private static final String URL = "URL:     ";

    private final String hidePassword = "**********";
    private final CopyCredentialsController copyCredentialsController;
    private final UrlRedirectController urlRedirectController;
    private final DoorkeyForm form = new DoorkeyForm();
    private final PasswordVaultItemViewModel passwordVaultItemViewModel;
    // TODO: Replace with controller - see other TODOs in the file.
    private final ViewManagerModel viewManagerModel;

    // Labels that are meant to update with PropertyChange.
    private final JLabel titleLabel = new JLabel();
    private final JLabel usernameLabel = createJlabel(USERNAME, 10);
    private final JButton usernameCopy = createCopyButton(
            usernameLabel.getPreferredSize().height,
            "username",
            () -> getState().getUsername()
    );

    private final JLabel passwordLabel = createJlabel(PASSWORD, 10);
    private final JButton passwordCopy = createCopyButton(
            passwordLabel.getPreferredSize().height,
            "password",
            () -> getState().getPassword()
    );

    private final JLabel urlLabel = createJlabel(URL, 10);
    private final JButton urlCopy = createCopyButton(
            urlLabel.getPreferredSize().height,
            "url",
            () -> getState().getUrl()
    );

    public PasswordVaultItemView(
            CopyCredentialsController copyCredentialsController,
            UrlRedirectController urlRedirectController,
            PasswordVaultItemViewModel passwordVaultItemViewModel,
            ViewManagerModel viewManagerModel
    ) {
        this.copyCredentialsController = copyCredentialsController;
        this.urlRedirectController = urlRedirectController;
        this.passwordVaultItemViewModel = passwordVaultItemViewModel;
        this.passwordVaultItemViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        addBackButton();

        addVaultItemTitle();

        addActionsPanel();

        addUsernamePanel();

        addPasswordPanel();

        addUrlPanel();

        setUpMainPanel();

        add(form, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final PasswordVaultItemState passwordVaultItemState = (PasswordVaultItemState) evt.getNewValue();
        dispatchStates(passwordVaultItemState);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
    }

    private void dispatchStates(PasswordVaultItemState passwordVaultItemState) {
        form.setError(passwordVaultItemState.getError());

        titleLabel.setText(passwordVaultItemState.getTitle());
        usernameLabel.setText(USERNAME + passwordVaultItemState.getUsername());
        passwordLabel.setText(PASSWORD + hidePassword);
        urlLabel.setText(URL + passwordVaultItemState.getUrl());
    }

    private void addBackButton() {
        final JButton backButton = new DoorkeyButton.DoorkeyButtonBuilder("< Back")
                .addListener(event -> {
                    // TODO: Refactor everything into a package inside service.
                    // TODO: Create the presenter, etc.
                    // TODO: Add a controller, and make controller call interactor, which calls the presenter
                    // TODO: to switch the view.
                    this.viewManagerModel.setState(ViewConstants.HOME_VIEW);
                    this.viewManagerModel.onStateChanged();
                }
        ).build();
        add(backButton);
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
        add(actionsPanel);
    }

    private void addVaultItemTitle() {
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new DoorkeyFont(18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void addUsernamePanel() {
        final JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        usernameLabel.setPreferredSize(new Dimension(150, usernameLabel.getPreferredSize().height));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameCopy);

        usernamePanel.setMaximumSize(new Dimension(300, usernamePanel.getPreferredSize().height));
        form.add(usernamePanel);
        form.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addPasswordPanel() {
        final JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        passwordLabel.setPreferredSize(new Dimension(150, passwordLabel.getPreferredSize().height));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordCopy);

        passwordPanel.setMaximumSize(new Dimension(300, passwordPanel.getPreferredSize().height));
        form.add(passwordPanel);
        form.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addUrlPanel() {
        final JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        urlPanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        urlLabel.setPreferredSize(new Dimension(150, urlLabel.getPreferredSize().height));
        urlPanel.add(urlLabel);
        urlPanel.add(urlCopy);

        urlPanel.setMaximumSize(new Dimension(300, urlPanel.getPreferredSize().height));
        form.add(urlPanel);
    }

    private JLabel createJlabel(String text, int height) {
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

    /**
     * Create a copy button, given a copy function that is executed EACH time the button is clicked.
     * The function may be dependent on the state, so it is natural to expect the return value to be
     * variable. The function must be of type () -> String.
     * @param height The height of the button.
     * @param type The type parameter of the button, a String.
     * @param copyFn The function that is executed when the button is clicked.
     * @return The JButton that is created.
     */
    private JButton createCopyButton(int height, String type, Copier copyFn) {
        final JButton copyButton = new DoorkeyButton.DoorkeyButtonBuilder("📋")
                .addListener(event -> {
                    copyMessageSequence(type, copyFn.copy());
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

    private void setUpMainPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    }

    private PasswordVaultItemState getState() {
        return passwordVaultItemViewModel.getState();
    }

    /**
     * Copy the text.
     */
    private interface Copier {
        /**
         * Copy the text.
         * @return The text to copy.
         */
        String copy();
    }
}


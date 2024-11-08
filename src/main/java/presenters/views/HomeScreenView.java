package presenters.views;

import org.jetbrains.annotations.NotNull;
import presenters.views.components.DButton;
import presenters.views.components.DFont;
import presenters.views.components.DForm;

import javax.swing.*;
import java.awt.*;

/**
 * The main Home screen view that pops up when the application is launched.
 */
public class HomeScreenView extends JFrame {
    private final JPanel panel = getMainPanel();
    private final DForm form = new DForm();

    public HomeScreenView() {
        super("DoorKey");
        setUpMainFrame();

        addWelcomeTitle();

        addExplainerTitle();

        addLoginForm();

        addAlternativeLabel();

        addLocalAndSignup();

        this.add(panel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void addLoginForm() {
        form.addField(new JTextField(), "Email", "email");
        form.addField(new JPasswordField(), "Password", "password");
        form.addSubmitButton("Log In");
        panel.add(form);
    }

    private void addExplainerTitle() {
        JLabel subtitleLabel = new JLabel("Enter your email below to log into your account");
        subtitleLabel.setFont(new DFont());
        subtitleLabel.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(subtitleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addWelcomeTitle() {
        JLabel titleLabel = new JLabel("Welcome to DoorKey!");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new DFont(24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
    }

    private void addAlternativeLabel() {
        JLabel separatorLabel = new JLabel("Or");
        separatorLabel.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        separatorLabel.setFont(new DFont());
        separatorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(separatorLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addLocalAndSignup() {
        JPanel localPanel = new JPanel();
        localPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        localPanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        DButton signUpButton = new DButton.DButtonBuilder("Sign Up")
                .addListener((event) -> {
                    // TODO: Implement navigation controller.
                    showPlaceholderPage();
                })
                .build();

        DButton useLocallyButton = new DButton.DButtonBuilder("Use Locally")
                .addListener((event) -> {
                    showPlaceholderPage();
                })
                .build();

        localPanel.add(signUpButton);
        localPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        localPanel.add(useLocallyButton);

        panel.add(localPanel);
    }

    private void showPlaceholderPage() {
        JPanel newPanel = new JPanel(new BorderLayout());

        JButton switchButton = new JButton("TODO (will be replaced with a NavigationController): Click to go back");
        switchButton.addActionListener((event) -> {
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
        JPanel panel = new JPanel();
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
    public DForm getForm() {
        return form;
    }
}

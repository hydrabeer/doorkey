package views;

import interface_adapter.NavigatableUIPanel;
import service.login.interface_adapter.LoginController;
import service.login.interface_adapter.LoginViewModel;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;
import views.components.DoorkeyForm;

import javax.swing.*;
import java.awt.*;

/**
 * The main Home screen view that pops up when the application is launched.
 */
public class LoginView extends NavigatableUIPanel {
    private final LoginController loginController;
    private final LoginViewModel viewModel;

    public LoginView(LoginController loginController, LoginViewModel viewModel) {
        this.loginController = loginController;
        this.viewModel = viewModel;
        viewModel.setLoginInteractor(loginController);

        setUpMainPanel();

        addWelcomeTitle();

        addExplainerTitle();

        addLoginForm();

        addAlternativeLabel();

        addLocalAndSignup();
    }

    private void addLoginForm() {
        final DoorkeyForm form = new DoorkeyForm();
        form.addField(new JTextField(), "Email", "email");
        form.addField(new JPasswordField(), "Password", "password");
        form.addSubmitButton("Log In", (event) -> {
            // LoginState newState = viewModel.getState();
            String email = form.getFieldValue("email");
            String password = form.getFieldValue("password");
            // newState.setEmail();
            // newState.setPassword();
            // viewModel.setState(newState);
            // TODO: pass in current state, dynamic updates
            loginController.login(email, password);
            // loginController.login(form.getFieldValue("email"), form.getFieldValue("password"));
        });
        this.add(form);
    }

    private void addExplainerTitle() {
        final JLabel subtitleLabel = new JLabel("Enter your email below to log into your account");
        subtitleLabel.setFont(new DoorkeyFont());
        subtitleLabel.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(subtitleLabel);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addWelcomeTitle() {
        final JLabel titleLabel = new JLabel("Welcome to DoorKey!");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new DoorkeyFont(24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(titleLabel);
    }

    private void addAlternativeLabel() {
        final JLabel separatorLabel = new JLabel("Or");
        separatorLabel.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        separatorLabel.setFont(new DoorkeyFont());
        separatorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(separatorLabel);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addLocalAndSignup() {
        final JPanel localPanel = new JPanel();
        localPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        localPanel.setBackground(ViewConstants.BACKGROUND_COLOR);

        final DoorkeyButton signUpButton = new DoorkeyButton.DoorkeyButtonBuilder("Sign Up")
                .addListener(event -> {
                    // TODO
                })
                .build();

        final DoorkeyButton useLocallyButton = new DoorkeyButton.DoorkeyButtonBuilder("Use Locally")
                .addListener(event -> {
                    // TODO
                })
                .build();

        localPanel.add(signUpButton);
        localPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        localPanel.add(useLocallyButton);

        this.add(localPanel);
    }

    private void setUpMainPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    }

    public String getViewName() {
        return viewModel.getViewName();
    }
}

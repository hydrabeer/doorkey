package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import service.ViewManagerModel;
import service.signup.interface_adapter.SignupController;
import service.signup.interface_adapter.SignupState;
import service.signup.interface_adapter.SignupViewModel;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;
import views.components.DoorkeyForm;

/**
 * The sign up view.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final SignupViewModel signupViewModel;
    private final SignupController signupController;
    private final ViewManagerModel viewManagerModel;
    private final DoorkeyForm form = new DoorkeyForm();

    public SignupView(
            SignupViewModel signupViewModel,
            SignupController signupController,
            ViewManagerModel viewManagerModel
    ) {
        this.signupViewModel = signupViewModel;
        this.signupViewModel.addPropertyChangeListener(this);
        this.signupController = signupController;
        this.viewManagerModel = viewManagerModel;

        setUpMainPanel();

        addSignupTitle();

        addExplainerTitle();

        addSignupForm();

        addOr();

        addGoBack();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState signupState = (SignupState) evt.getNewValue();
        dispatchErrorStates(signupState);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
    }

    private void addSignupTitle() {
        final JLabel titleLabel = new JLabel("Sign up for Doorkey Sync");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new DoorkeyFont(24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(titleLabel);
    }

    private void addExplainerTitle() {
        final JLabel subtitleLabel = new JLabel("Your passwords will be automatically synced with cloud.");
        subtitleLabel.setFont(new DoorkeyFont());
        subtitleLabel.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(subtitleLabel);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addSignupForm() {
        form.addField(new JTextField(), "Email", "email");
        form.addField(new JPasswordField(), "Password", "password");
        form.addField(new JPasswordField(), "Repeat Password", "repeatedPassword");
        form.addSubmitButton("Sign Up", event -> {
            final String email = form.getFieldValue("email");
            final String password = form.getFieldValue("password");
            final String repeatedPassword = form.getFieldValue("repeatedPassword");
            signupController.signup(email, password, repeatedPassword);
        });
        this.add(form);
    }

    private void addOr() {
        final JLabel separatorLabel = new JLabel("Or");
        separatorLabel.setForeground(ViewConstants.TEXT_MUTED_COLOR);
        separatorLabel.setFont(new DoorkeyFont());
        separatorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(separatorLabel);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void addGoBack() {
        final DoorkeyButton goBackButton = new DoorkeyButton.DoorkeyButtonBuilder("Go Back")
                .addListener(event -> {
                    viewManagerModel.setState(ViewConstants.LOGIN_VIEW);
                    viewManagerModel.onStateChanged();
                })
                .build();

        this.add(goBackButton);
    }

    private void setUpMainPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    }

    private void dispatchErrorStates(SignupState signupState) {
        form.setError(signupState.getErrorMessage());
        for (String field : signupState.getFieldsToErrors().keySet()) {
            form.setFieldError(signupState.getFieldsToErrors().get(field), field);
        }
    }
}

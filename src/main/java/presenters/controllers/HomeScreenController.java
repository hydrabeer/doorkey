package presenters.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presenters.views.HomeScreenView;
import presenters.views.components.DoorkeyForm;

/**
 * The controller for Main Page.
 */
public class HomeScreenController {
    private final HomeScreenView view;

    public HomeScreenController(HomeScreenView view) {
        this.view = view;

        getForm().addActionListener(new LogInButtonListener());
    }

    private DoorkeyForm getForm() {
        return view.getForm();
    }

    /**
     * LogInButtonListener class.
     * Implements ActionListener.
     */
    private final class LogInButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            final String email = view.getEmail();
            final String password = view.getPassword();

            getForm().setError("Attempting login with email: " + email + " " + "password: " + password);
        }
    }
}

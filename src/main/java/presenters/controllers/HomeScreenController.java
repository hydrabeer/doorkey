package presenters.controllers;

import presenters.views.HomeScreenView;
import presenters.views.components.DForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller for Main Page.
 */
public class HomeScreenController {
    private final HomeScreenView view;

    public HomeScreenController(HomeScreenView view) {
        this.view = view;

        getForm().addActionListener(new LogInButtonListener());
    }

    private DForm getForm() {
        return view.getForm();
    }

    private class LogInButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmail();
            String password = view.getPassword();

            getForm().setError("Attempting login with email: " + email + " " + "password: " + password);
        }
    }
}

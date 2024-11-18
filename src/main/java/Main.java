import service.login.LoginInteractor;
import service.login.interface_adapter.LoginController;
import service.login.interface_adapter.LoginPresenter;
import service.login.interface_adapter.LoginViewModel;
import views.LoginView;
import views.ViewConstants;
import views.ViewManager;

import javax.swing.*;

/**
 * The main class for our program.
 */
public class Main {
    /**
     * The main method that starts the GUI.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewManager viewManager = new ViewManager();

            LoginViewModel loginViewModel = new LoginViewModel();
            LoginPresenter loginPresenter = new LoginPresenter(loginViewModel, viewManager);
            LoginInteractor interactor = new LoginInteractor(loginPresenter);
            LoginController loginController = new LoginController(interactor);
            LoginView loginView = new LoginView(loginController, loginViewModel);

            viewManager.addView(loginView);
            viewManager.showView(ViewConstants.LOGIN_VIEW);
        });
    }
}

import javax.swing.SwingUtilities;

import service.login.LoginInteractor;
import service.login.interface_adapter.LoginController;
import service.login.interface_adapter.LoginPresenter;
import service.login.interface_adapter.LoginViewModel;
import views.LocalVaultView;
import views.LoginView;
import views.ViewConstants;
import views.ViewManager;

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
        SwingUtilities.invokeLater(Main::initializeMainUi);
    }

    /**
     * Initializes the main UI.
     */
    private static void initializeMainUi() {
        final ViewManager viewManager = new ViewManager();

        final LoginViewModel loginViewModel = new LoginViewModel();
        final LoginPresenter loginPresenter = new LoginPresenter(loginViewModel, viewManager);
        final LoginInteractor interactor = new LoginInteractor(loginPresenter);
        final LoginController loginController = new LoginController(interactor);
        final LoginView loginView = new LoginView(loginController, loginViewModel);
        viewManager.addView(loginView);

        final LocalVaultView localVaultView = new LocalVaultView(viewManager);      
        viewManager.addView(localVaultView);

        viewManager.showView(ViewConstants.LOGIN_VIEW);
    }
}

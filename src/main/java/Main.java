import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import data_access.authentication.FireStoreUserDataAccessObject;
import data_access.authentication.FirebaseAuthRepository;
import interface_adapter.net.http.CommonHttpClient;
import service.ViewManagerModel;
import service.home.HomeInteractor;
import service.home.interface_adapter.HomeController;
import service.home.interface_adapter.HomePresenter;
import service.home.interface_adapter.HomeViewModel;
import service.login.LoginInteractor;
import service.login.interface_adapter.LoginController;
import service.login.interface_adapter.LoginPresenter;
import service.login.interface_adapter.LoginViewModel;
import service.signup.SignupInteractor;
import service.signup.interface_adapter.SignupController;
import service.signup.interface_adapter.SignupPresenter;
import service.signup.interface_adapter.SignupViewModel;
import views.*;

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
        final JFrame mainFrame = new JFrame("DoorKey");
        // The following line is commented out, as it can be activated once
        // the panels are made prettier, and the support to add/close is added.
        //
        // mainFrame.setUndecorated(true);
        //
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 500);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setBackground(ViewConstants.BACKGROUND_COLOR);

        final CardLayout cardLayout = new CardLayout();
        final JPanel views = new JPanel(cardLayout);

        mainFrame.add(views, BorderLayout.CENTER);

        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // ===========
        // TODO For evan: After you create view models, initialize them like I did with login
        final LoadLocalVaultView loadView = new LoadLocalVaultView(viewManagerModel);
        views.add(loadView, ViewConstants.LOAD_LOCAL_VAULT_VIEW);

        final CreateLocalVaultView createView = new CreateLocalVaultView(viewManagerModel);
        views.add(createView, ViewConstants.CREATE_LOCAL_VAULT_VIEW);

        final LocalVaultView localVaultView = new LocalVaultView(viewManagerModel);
        views.add(localVaultView, ViewConstants.LOCAL_VAULT_VIEW);
        // ===================

        final CommonHttpClient httpClient = new CommonHttpClient();
        final FirebaseAuthRepository firebaseAuthRepository = new FirebaseAuthRepository(httpClient);
        final FireStoreUserDataAccessObject fireStoreUserDataAccessObject = new FireStoreUserDataAccessObject(firebaseAuthRepository, httpClient);

        final HomeViewModel homeViewModel = new HomeViewModel();
        final HomePresenter homePresenter = new HomePresenter(homeViewModel, viewManagerModel);
        final HomeInteractor homeInteractor = new HomeInteractor(homePresenter);
        final HomeController homeController = new HomeController(homeInteractor);
        final HomeView homeView = new HomeView(homeViewModel, homeController, viewManagerModel);
        views.add(homeView, ViewConstants.HOME_VIEW);

        final SignupViewModel signupViewModel = new SignupViewModel();
        final SignupPresenter signupPresenter = new SignupPresenter(signupViewModel, homeViewModel, viewManagerModel);
        final SignupInteractor signupInteractor = new SignupInteractor(fireStoreUserDataAccessObject, signupPresenter);
        final SignupController signupController = new SignupController(signupInteractor);
        final SignupView signupView = new SignupView(signupViewModel, signupController, viewManagerModel);
        views.add(signupView, ViewConstants.SIGNUP_VIEW);

        final LoginViewModel loginViewModel = new LoginViewModel();
        final LoginPresenter loginPresenter = new LoginPresenter(loginViewModel, homeViewModel, viewManagerModel);
        final LoginInteractor loginInteractor = new LoginInteractor(fireStoreUserDataAccessObject, loginPresenter);
        final LoginController loginController = new LoginController(loginInteractor);
        final LoginView loginView = new LoginView(loginViewModel, loginController, viewManagerModel);
        views.add(loginView, ViewConstants.LOGIN_VIEW);

        viewManagerModel.setState(ViewConstants.LOGIN_VIEW);
        viewManagerModel.onStateChanged();

        mainFrame.setVisible(true);
    }
}

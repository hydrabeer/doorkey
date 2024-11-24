package app;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import data_access.authentication.FireStoreUserDataAccessObject;
import data_access.authentication.FirebaseAuthRepository;
import interface_adapter.net.http.CommonHttpClient;
import interface_adapter.net.http.HttpClient;
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
import views.CreateLocalVaultView;
import views.HomeView;
import views.LoadLocalVaultView;
import views.LocalVaultView;
import views.LoginView;
import views.SignupView;
import views.ViewConstants;
import views.ViewManager;

/**
 * A builder class for the application.
 */
public class AppBuilder {
    private final JFrame mainFrame;
    private final JPanel viewsPanel;
    private final ViewManagerModel viewManagerModel;
    private final FireStoreUserDataAccessObject fireStoreUserDataAccessObject;
    private final HomeViewModel homeViewModel;

    public AppBuilder(String title, int width, int height) {
        mainFrame = new JFrame(title);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(width, height);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setBackground(ViewConstants.BACKGROUND_COLOR);

        // The following line is commented out, as it can be activated once
        // the panels are made prettier, and the support to add/close is added.
        //
        // mainFrame.setUndecorated(true);

        homeViewModel = new HomeViewModel();

        final CardLayout cardLayout = new CardLayout();
        viewsPanel = new JPanel(cardLayout);
        mainFrame.add(viewsPanel, BorderLayout.CENTER);

        viewManagerModel = new ViewManagerModel();
        new ViewManager(viewsPanel, cardLayout, viewManagerModel);

        final HttpClient httpClient = new CommonHttpClient();
        final FirebaseAuthRepository firebaseAuthRepository = new FirebaseAuthRepository(httpClient);
        fireStoreUserDataAccessObject = new FireStoreUserDataAccessObject(firebaseAuthRepository, httpClient);
    }

    /**
     * Adds the LoadLocalVaultView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addLoadLocalVaultView() {
        final LoadLocalVaultView loadView = new LoadLocalVaultView(viewManagerModel);
        viewsPanel.add(loadView, ViewConstants.LOAD_LOCAL_VAULT_VIEW);
        return this;
    }

    /**
     * Adds the CreateLocalVaultView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addCreateLocalVaultView() {
        // TODO For evan: After you create view models, initialize them like I did with login, etc.
        final CreateLocalVaultView createView = new CreateLocalVaultView(viewManagerModel);
        viewsPanel.add(createView, ViewConstants.CREATE_LOCAL_VAULT_VIEW);
        return this;
    }

    /**
     * Adds the LocalVaultView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addLocalVaultView() {
        // TODO For evan: After you create view models, initialize them like I did with login, etc.
        final LocalVaultView localVaultView = new LocalVaultView(viewManagerModel);
        viewsPanel.add(localVaultView, ViewConstants.LOCAL_VAULT_VIEW);
        return this;
    }

    /**
     * Adds the HomeView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addHomeView() {
        final HomePresenter homePresenter = new HomePresenter(homeViewModel, viewManagerModel);
        final HomeInteractor homeInteractor = new HomeInteractor(homePresenter);
        final HomeController homeController = new HomeController(homeInteractor);
        final HomeView homeView = new HomeView(homeViewModel, homeController, viewManagerModel);
        viewsPanel.add(homeView, ViewConstants.HOME_VIEW);
        return this;
    }

    /**
     * Adds the SignupView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addSignupView() {
        final SignupViewModel signupViewModel = new SignupViewModel();
        final SignupPresenter signupPresenter = new SignupPresenter(signupViewModel, homeViewModel, viewManagerModel);
        final SignupInteractor signupInteractor = new SignupInteractor(fireStoreUserDataAccessObject, signupPresenter);
        final SignupController signupController = new SignupController(signupInteractor);
        final SignupView signupView = new SignupView(signupViewModel, signupController, viewManagerModel);
        viewsPanel.add(signupView, ViewConstants.SIGNUP_VIEW);
        return this;
    }

    /**
     * Adds the LoginView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addLoginView() {
        final LoginViewModel loginViewModel = new LoginViewModel();
        final LoginPresenter loginPresenter = new LoginPresenter(loginViewModel, homeViewModel, viewManagerModel);
        final LoginInteractor loginInteractor = new LoginInteractor(fireStoreUserDataAccessObject, loginPresenter);
        final LoginController loginController = new LoginController(loginInteractor);
        final LoginView loginView = new LoginView(loginViewModel, loginController, viewManagerModel);
        viewsPanel.add(loginView, ViewConstants.LOGIN_VIEW);
        return this;
    }

    /**
     * Sets the initial view of the application.
     *
     * @param viewName The name of the view to set as the initial view.
     * @return The AppBuilder instance.
     */
    public AppBuilder setInitialView(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.onStateChanged();
        return this;
    }

    /**
     * Builds the JFrame.
     *
     * @return The JFrame.
     */
    public JFrame build() {
        return mainFrame;
    }
}

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
import service.local.create.CreateLocalVaultInteractor;
import service.local.create.interface_adapter.CreateLocalVaultController;
import service.local.create.interface_adapter.CreateLocalVaultPresenter;
import service.local.create.interface_adapter.CreateLocalVaultViewModel;
import service.local.load.LoadLocalVaultInteractor;
import service.local.load.interface_adapter.LoadLocalVaultController;
import service.local.load.interface_adapter.LoadLocalVaultPresenter;
import service.local.load.interface_adapter.LoadLocalVaultViewModel;
import service.login.LoginInteractor;
import service.login.interface_adapter.LoginController;
import service.login.interface_adapter.LoginPresenter;
import service.login.interface_adapter.LoginViewModel;
import service.password_validation.PasswordValidationInteractor;
import service.password_validation.interface_adapter.PasswordValidationController;
import service.password_validation.interface_adapter.PasswordValidationPresenter;
import service.password_validation.interface_adapter.PasswordValidationViewModel;
import service.signup.SignupInteractor;
import service.signup.interface_adapter.SignupController;
import service.signup.interface_adapter.SignupPresenter;
import service.signup.interface_adapter.SignupViewModel;
import views.CreateLocalVaultView;
import views.CreateVaultItemView;
import views.HomeView;
import views.LoadLocalVaultView;
import views.LoginView;
import views.SignupView;
import views.ViewConstants;
import views.ViewManager;

/**
 * A builder class for the application.
 */
public class AppBuilder {
    private final JFrame mainFrame;
    private final JPanel views;
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
        views = new JPanel(cardLayout);
        mainFrame.add(views, BorderLayout.CENTER);

        viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        final HttpClient httpClient = new CommonHttpClient();
        final FirebaseAuthRepository firebaseAuthRepository = new FirebaseAuthRepository(httpClient);
        fireStoreUserDataAccessObject = new FireStoreUserDataAccessObject(firebaseAuthRepository, httpClient);
    }

    /**
     * Adds the CreateLocalVaultView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addCreateLocalVaultView() {
        final CreateLocalVaultViewModel createLocalVaultViewModel = new CreateLocalVaultViewModel();
        final CreateLocalVaultPresenter createLocalVaultPresenter = new CreateLocalVaultPresenter(
                createLocalVaultViewModel, viewManagerModel);
        final CreateLocalVaultInteractor createLocalVaultInteractor = new CreateLocalVaultInteractor(
                createLocalVaultPresenter);
        final CreateLocalVaultController createLocalVaultController = new CreateLocalVaultController(
                createLocalVaultInteractor);
        final CreateLocalVaultView createLocalVaultView = new CreateLocalVaultView(
                createLocalVaultController, createLocalVaultViewModel, viewManagerModel);
        views.add(createLocalVaultView, ViewConstants.CREATE_LOCAL_VAULT_VIEW);
        return this;
    }

    /**
     * Adds the LocalVaultView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addLocalVaultView() {
        final LoadLocalVaultViewModel loadLocalVaultViewModel = new LoadLocalVaultViewModel();
        final LoadLocalVaultPresenter loadLocalVaultPresenter = new LoadLocalVaultPresenter(
                loadLocalVaultViewModel, viewManagerModel);
        final LoadLocalVaultInteractor loadLocalVaultInteractor = new LoadLocalVaultInteractor(
                loadLocalVaultPresenter);
        final LoadLocalVaultController loadLocalVaultController = new LoadLocalVaultController(
                loadLocalVaultInteractor);
        final LoadLocalVaultView loadLocalVaultView = new LoadLocalVaultView(
                loadLocalVaultController, loadLocalVaultViewModel, viewManagerModel);
        views.add(loadLocalVaultView, ViewConstants.LOAD_LOCAL_VAULT_VIEW);
        return this;
    }
    
    /**
     * Adds the CreateVaultItemView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addCreateVaultItemView() {
        final PasswordValidationViewModel passwordValidationViewModel = new PasswordValidationViewModel();
        final PasswordValidationPresenter passwordValidationPresenter = 
            new PasswordValidationPresenter(passwordValidationViewModel);
        final PasswordValidationInteractor passwordValidationInteractor = 
            new PasswordValidationInteractor(passwordValidationPresenter);
        final PasswordValidationController passwordValidationController = 
            new PasswordValidationController(passwordValidationInteractor);

        final CreateVaultItemView createVaultItemView = new CreateVaultItemView(
                passwordValidationViewModel, passwordValidationController);

        views.add(createVaultItemView, ViewConstants.CREATE_VAULT_ITEM_VIEW);
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
        views.add(homeView, ViewConstants.HOME_VIEW);
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
        views.add(signupView, ViewConstants.SIGNUP_VIEW);
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
        views.add(loginView, ViewConstants.LOGIN_VIEW);
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

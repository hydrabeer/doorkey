package app;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import data_access.FireStoreUserRepository;
import data_access.LocalVaultUserRepository;
import data_access.authentication.FirebaseAuthRepository;
import interface_adapter.net.http.CommonHttpClient;
import interface_adapter.net.http.HttpClient;
import interface_adapter.validate_url.PhishingUrlValidator;
import interface_adapter.validate_url.PhishtankPhishingUrlValidator;
import repository.CommonRepositoryProvider;
import repository.RepositoryProvider;
import service.ViewManagerModel;
import service.copy_credentials.CopyCredentialsInteractor;
import service.copy_credentials.interface_adapter.CopyCredentialsController;
import service.copy_credentials.interface_adapter.CopyCredentialsPresenter;
import service.create_vault_item.CreateVaultItemInteractor;
import service.create_vault_item.interface_adapter.CreateVaultItemController;
import service.create_vault_item.interface_adapter.CreateVaultItemPresenter;
import service.create_vault_item.interface_adapter.CreateVaultItemViewModel;
import service.home.HomeInteractor;
import service.home.interface_adapter.HomeController;
import service.home.interface_adapter.HomePresenter;
import service.home.interface_adapter.HomeViewModel;
import service.import_vault_item.ImportVaultItemInteractor;
import service.import_vault_item.interface_adapter.ImportVaultItemController;
import service.import_vault_item.interface_adapter.ImportVaultItemPresenter;
import service.import_vault_item.interface_adapter.ImportVaultItemViewModel;
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
import service.password_vault_item.PasswordVaultItemInteractor;
import service.password_vault_item.interface_adapter.PasswordVaultItemController;
import service.password_vault_item.interface_adapter.PasswordVaultItemPresenter;
import service.password_vault_item.interface_adapter.PasswordVaultItemViewModel;
import service.signup.SignupInteractor;
import service.signup.interface_adapter.SignupController;
import service.signup.interface_adapter.SignupPresenter;
import service.signup.interface_adapter.SignupViewModel;
import service.url_redirect.DesktopWrapper;
import service.url_redirect.RealDesktopWrapper;
import service.url_redirect.UrlRedirectInteractor;
import service.url_redirect.interface_adapter.UrlRedirectController;
import service.url_redirect.interface_adapter.UrlRedirectPresenter;
import views.CreateLocalVaultView;
import views.CreateVaultItemView;
import views.HomeView;
import views.ImportVaultItemView;
import views.LoadLocalVaultView;
import views.LocalVaultView;
import views.LoginView;
import views.PasswordVaultItemView;
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
    private final FireStoreUserRepository fireStoreUserRepository;
    private final LocalVaultUserRepository localVaultUserRepository;
    private final RepositoryProvider repositoryProvider;
    private final HomeViewModel homeViewModel;
    private final PasswordVaultItemViewModel passwordVaultItemViewModel;
    private final CreateVaultItemViewModel createVaultItemViewModel;
    private final ImportVaultItemViewModel importVaultItemViewModel;

    public AppBuilder(String title, int width, int height) {
        this.mainFrame = new JFrame(title);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(width, height);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setBackground(ViewConstants.BACKGROUND_COLOR);

        // The following line is commented out, as it can be activated once
        // the panels are made prettier, and the support to add/close is added.
        //
        // mainFrame.setUndecorated(true);
        this.repositoryProvider = new CommonRepositoryProvider();
        this.homeViewModel = new HomeViewModel();
        this.passwordVaultItemViewModel = new PasswordVaultItemViewModel();
        this.importVaultItemViewModel = new ImportVaultItemViewModel();
        this.createVaultItemViewModel = new CreateVaultItemViewModel();

        final CardLayout cardLayout = new CardLayout();
        this.views = new JPanel(cardLayout);
        mainFrame.add(views, BorderLayout.CENTER);

        this.viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        final HttpClient httpClient = new CommonHttpClient();
        final FirebaseAuthRepository firebaseAuthRepository = new FirebaseAuthRepository(httpClient);
        this.fireStoreUserRepository = new FireStoreUserRepository(firebaseAuthRepository, httpClient);
        this.localVaultUserRepository = new LocalVaultUserRepository();
    }

    /**
     * Adds the CreateVaultItemView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addCreateVaultItemView() {
        final PasswordValidationViewModel passwordValidationViewModel = new PasswordValidationViewModel();
        final PasswordValidationPresenter passwordValidationPresenter = new PasswordValidationPresenter(
                passwordValidationViewModel
        );
        final PasswordValidationInteractor passwordValidationInteractor = new PasswordValidationInteractor(
                passwordValidationPresenter
        );
        final PasswordValidationController passwordValidationController = new PasswordValidationController(
                passwordValidationInteractor
        );

        final HttpClient client = new CommonHttpClient();
        final PhishingUrlValidator phishtankPhishingUrlValidator =
                new PhishtankPhishingUrlValidator(client);
        final CreateVaultItemPresenter createVaultItemPresenter = new CreateVaultItemPresenter(
                createVaultItemViewModel,
                homeViewModel,
                viewManagerModel
        );
        final CreateVaultItemInteractor createVaultItemInteractor = new CreateVaultItemInteractor(
                repositoryProvider,
                phishtankPhishingUrlValidator,
                createVaultItemPresenter
        );
        final CreateVaultItemController createVaultItemController = new CreateVaultItemController(
                createVaultItemInteractor
        );

        final CreateVaultItemView createVaultItemView = new CreateVaultItemView(
                createVaultItemViewModel,
                createVaultItemController,
                passwordValidationViewModel,
                passwordValidationController
        );

        views.add(createVaultItemView, ViewConstants.CREATE_VAULT_ITEM_VIEW);
        return this;
    }
    
    /**
     * Adds the LocalVaultView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addLocalVaultView() {
        final LocalVaultView localVaultView = new LocalVaultView(viewManagerModel);
        views.add(localVaultView, ViewConstants.LOCAL_VAULT_VIEW);
        return this;
    }

    /**
     * Adds the CreateLocalVaultView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addCreateLocalVaultView() {
        final CreateLocalVaultViewModel createLocalVaultViewModel = new CreateLocalVaultViewModel();
        final CreateLocalVaultPresenter createLocalVaultPresenter = new CreateLocalVaultPresenter(
                createLocalVaultViewModel,
                homeViewModel,
                viewManagerModel
        );
        final CreateLocalVaultInteractor createLocalVaultInteractor = new CreateLocalVaultInteractor(
                repositoryProvider,
                localVaultUserRepository,
                createLocalVaultPresenter
        );
        final CreateLocalVaultController createLocalVaultController = new CreateLocalVaultController(
                createLocalVaultInteractor);
        final CreateLocalVaultView createLocalVaultView = new CreateLocalVaultView(
                createLocalVaultController, createLocalVaultViewModel, viewManagerModel);
        views.add(createLocalVaultView, ViewConstants.CREATE_LOCAL_VAULT_VIEW);
        return this;
    }

    /**
     * Adds the LoadLocalVaultView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addLoadLocalVaultView() {
        final LoadLocalVaultViewModel loadLocalVaultViewModel = new LoadLocalVaultViewModel();
        final LoadLocalVaultPresenter loadLocalVaultPresenter = new LoadLocalVaultPresenter(
                loadLocalVaultViewModel,
                homeViewModel,
                viewManagerModel
        );
        final LoadLocalVaultInteractor loadLocalVaultInteractor = new LoadLocalVaultInteractor(
                repositoryProvider,
                localVaultUserRepository,
                loadLocalVaultPresenter
        );
        final LoadLocalVaultController loadLocalVaultController = new LoadLocalVaultController(
                loadLocalVaultInteractor
        );
        final LoadLocalVaultView loadLocalVaultView = new LoadLocalVaultView(
                loadLocalVaultController, loadLocalVaultViewModel, viewManagerModel
        );
        views.add(loadLocalVaultView, ViewConstants.LOAD_LOCAL_VAULT_VIEW);
        return this;
    }

    /**
     * Adds the ImportVaultItemView to the viewsPanel.
     * @return The AppBuilder instance.
     */
    public AppBuilder addImportVaultItemView() {
        final ImportVaultItemPresenter importVaultItemPresenter = new ImportVaultItemPresenter(
                importVaultItemViewModel,
                homeViewModel,
                viewManagerModel
        );
        final ImportVaultItemInteractor importVaultItemInteractor = new ImportVaultItemInteractor(
                repositoryProvider,
                importVaultItemPresenter
        );
        final ImportVaultItemController importVaultItemController = new ImportVaultItemController(
                importVaultItemInteractor
        );
        final ImportVaultItemView importVaultItemView = new ImportVaultItemView(
                importVaultItemViewModel,
                importVaultItemController
        );

        views.add(importVaultItemView, ViewConstants.IMPORT_VAULT_ITEM_VIEW);
        return this;
    }

    /**
     * Adds the HomeView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addHomeView() {
        final HomePresenter homePresenter = new HomePresenter(
                homeViewModel,
                passwordVaultItemViewModel,
                viewManagerModel
        );
        final HomeInteractor homeInteractor = new HomeInteractor(
                repositoryProvider,
                homePresenter
        );
        final HomeController homeController = new HomeController(homeInteractor);
        final HomeView homeView = new HomeView(homeViewModel, homeController);
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
        final SignupInteractor signupInteractor = new SignupInteractor(fireStoreUserRepository, signupPresenter);
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
        final LoginInteractor loginInteractor = new LoginInteractor(
                repositoryProvider,
                fireStoreUserRepository,
                loginPresenter
        );
        final LoginController loginController = new LoginController(loginInteractor);
        final LoginView loginView = new LoginView(loginViewModel, loginController, viewManagerModel);
        views.add(loginView, ViewConstants.LOGIN_VIEW);
        return this;
    }

    /**
     * Adds the PasswordVaultItemView to the viewsPanel.
     *
     * @return The AppBuilder instance.
     */
    public AppBuilder addPasswordVaultItemView() {
        final CopyCredentialsPresenter copyCredentialsPresenter = new CopyCredentialsPresenter(
                passwordVaultItemViewModel
        );
        final CopyCredentialsInteractor copyCredentialsInteractor = new CopyCredentialsInteractor(
                copyCredentialsPresenter
        );
        final CopyCredentialsController copyCredentialsController = new CopyCredentialsController(
                copyCredentialsInteractor
        );
        final UrlRedirectPresenter urlRedirectPresenter = new UrlRedirectPresenter(
                passwordVaultItemViewModel
        );
        final DesktopWrapper desktopWrapper = new RealDesktopWrapper();
        final UrlRedirectInteractor urlRedirectInteractor = new UrlRedirectInteractor(
                desktopWrapper, urlRedirectPresenter);
        final UrlRedirectController urlRedirectController = new UrlRedirectController(urlRedirectInteractor);
        final PasswordVaultItemPresenter passwordVaultItemPresenter = new PasswordVaultItemPresenter(
                passwordVaultItemViewModel,
                viewManagerModel,
                homeViewModel
        );
        final PasswordVaultItemInteractor passwordVaultItemInteractor = new PasswordVaultItemInteractor(
                repositoryProvider,
                passwordVaultItemPresenter
        );
        final PasswordVaultItemController passwordVaultItemController = new PasswordVaultItemController(
                passwordVaultItemInteractor);
        final PasswordVaultItemView passwordVaultItemView = new PasswordVaultItemView(
                copyCredentialsController,
                urlRedirectController,
                passwordVaultItemController,
                passwordVaultItemViewModel
        );
        views.add(passwordVaultItemView, ViewConstants.PASSWORD_VAULT_ITEM_VIEW);
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

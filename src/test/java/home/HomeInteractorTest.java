package home;

import entity.AbstractVaultItem;
import entity.PasswordVaultItem;
import exception.InvalidVaultItemException;
import mock.MockHomePresenter;
import mock.MockRepositoryProvider;
import mock.MockUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.home.HomeInputData;
import service.home.HomeInteractor;

public class HomeInteractorTest {
    private MockUserRepository mockUserRepository = new MockUserRepository();
    private MockHomePresenter mockHomePresenter = new MockHomePresenter();
    private MockRepositoryProvider repositoryProvider = new MockRepositoryProvider(mockUserRepository);
    private HomeInteractor homeInteractor = new HomeInteractor(
            repositoryProvider,
            mockHomePresenter
    );

    @BeforeEach
    public void setUp() {
        mockUserRepository = new MockUserRepository();
        mockHomePresenter = new MockHomePresenter();
        mockUserRepository = new MockUserRepository();
        homeInteractor = new HomeInteractor(
                repositoryProvider,
                mockHomePresenter
        );
    }

    @Test
    public void displayVaultItemTest() throws InvalidVaultItemException {
        AbstractVaultItem item = new PasswordVaultItem(
                "title", "username", "password", "https://www.example.com/");
        HomeInputData inputData = new HomeInputData(item);
        homeInteractor.displayVaultItem(inputData);
        assert(mockHomePresenter.hasGeneralError("Show VaultView"));
    }

    @Test
    public void signOutTest() {
        homeInteractor.signOut();
        assert(mockHomePresenter.hasGeneralError("Show LoginView"));
    }

    @Test
    public void displayCreateVaultItemViewTest() {
        homeInteractor.displayCreateVaultItemView();
        assert(mockHomePresenter.hasGeneralError("Show CreateView"));
    }

    @Test
    public void displayImportViewTest() {
        homeInteractor.displayImportView();
        assert(mockHomePresenter.hasGeneralError("Show ImportView"));
    }
}

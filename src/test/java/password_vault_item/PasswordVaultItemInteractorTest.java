package password_vault_item;

import entity.AbstractUser;
import entity.PasswordVaultItem;
import exception.AuthException;
import mock.MockPasswordVaultItemPresenter;
import mock.MockRepositoryProvider;
import mock.MockUserRepository;
import org.junit.jupiter.api.Test;
import service.password_vault_item.PasswordVaultItemInputData;
import service.password_vault_item.PasswordVaultItemInteractor;

import java.io.IOException;

public class PasswordVaultItemInteractorTest {
    @Test
    public void testDisplayDeleteMessage() {
        MockUserRepository mockUserRepository = new MockUserRepository();
        MockPasswordVaultItemPresenter passwordVaultItemOutputBoundary = new MockPasswordVaultItemPresenter();
        MockRepositoryProvider repositoryProvider = new MockRepositoryProvider(mockUserRepository);
        PasswordVaultItemInteractor interactor = new PasswordVaultItemInteractor(
                repositoryProvider,
                passwordVaultItemOutputBoundary
        );
        interactor.displayDeleteMessage();
        assert (passwordVaultItemOutputBoundary.hasMessage(
                "Press delete again to confirm. Press copy button to reset"));
    }

    @Test
    public void testDisplayHomeView() {
        MockUserRepository mockUserRepository = new MockUserRepository();
        MockPasswordVaultItemPresenter passwordVaultItemOutputBoundary = new MockPasswordVaultItemPresenter();
        MockRepositoryProvider repositoryProvider = new MockRepositoryProvider(mockUserRepository);
        PasswordVaultItemInteractor interactor = new PasswordVaultItemInteractor(
                repositoryProvider,
                passwordVaultItemOutputBoundary
        );
        interactor.displayHomeView();
        assert (passwordVaultItemOutputBoundary.hasMessage(
                "Display home view"));
    }

    @Test
    public void testDeleteItem() throws AuthException, IOException {
        MockUserRepository mockUserRepository = new MockUserRepository();
        MockRepositoryProvider repositoryProvider = new MockRepositoryProvider(mockUserRepository);
        AbstractUser john = mockUserRepository.signupUser("John@gmail.com", "password");
        final PasswordVaultItem item = new PasswordVaultItem(
                "New item", "John", "password", "https://www.example.com/");
        mockUserRepository.addVaultItem(item);
        PasswordVaultItemInputData data = new PasswordVaultItemInputData(item);
        MockPasswordVaultItemPresenter passwordVaultItemOutputBoundary = new MockPasswordVaultItemPresenter();
        PasswordVaultItemInteractor interactor = new PasswordVaultItemInteractor(
                repositoryProvider, passwordVaultItemOutputBoundary
        );
        interactor.deleteItem(data);
        assert (john.getVault().getItems().isEmpty());
    }
}

package password_vault_item;

import entity.AbstractUser;
import entity.PasswordVaultItem;
import exception.AuthException;
import mock.MockPasswordVaultItemPresenter;
import mock.MockUserRepository;
import org.junit.jupiter.api.Test;
import service.password_vault_item.PasswordVaultItemInputData;
import service.password_vault_item.PasswordVaultItemInteractor;

import java.io.IOException;

public class PasswordVaultItemInteractorTest {

    @Test
    public void testDisplayDeleteMessage() {
        MockPasswordVaultItemPresenter passwordVaultItemOutputBoundary =  new MockPasswordVaultItemPresenter();
        PasswordVaultItemInteractor interactor = new PasswordVaultItemInteractor(passwordVaultItemOutputBoundary);
        interactor.displayDeleteMessage();
        assert(passwordVaultItemOutputBoundary.hasMessage(
                "Press delete again to confirm. Press copy button to reset"));
    }
    @Test
    public void testDisplayHomeView() {
        MockPasswordVaultItemPresenter passwordVaultItemOutputBoundary =  new MockPasswordVaultItemPresenter();
        PasswordVaultItemInteractor interactor = new PasswordVaultItemInteractor(passwordVaultItemOutputBoundary);
        interactor.displayHomeView();
        assert(passwordVaultItemOutputBoundary.hasMessage(
                "Display home view"));
    }

    @Test
    public void testDeleteItem() throws AuthException, IOException {
        MockUserRepository mockUserRepository = new MockUserRepository();
        AbstractUser john = mockUserRepository.signupUser("John@gmail.com", "password");
        final PasswordVaultItem item = new PasswordVaultItem(
                "New item", "John", "password", "https://www.example.com/");
        mockUserRepository.addVaultItem(john, item);
        PasswordVaultItemInputData data = new PasswordVaultItemInputData(john, mockUserRepository, item);
        MockPasswordVaultItemPresenter passwordVaultItemOutputBoundary =  new MockPasswordVaultItemPresenter();
        PasswordVaultItemInteractor interactor = new PasswordVaultItemInteractor(passwordVaultItemOutputBoundary);
        interactor.deleteItem(data);
        assert(john.getVault().getItems().isEmpty());
    }

}

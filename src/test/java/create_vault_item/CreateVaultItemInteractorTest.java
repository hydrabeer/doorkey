package create_vault_item;

import static org.junit.jupiter.api.Assertions.*;

import service.create_vault_item.CreateVaultItemInteractor;
import service.create_vault_item.CreateVaultItemInputBoundary;
import service.create_vault_item.CreateVaultItemOutputBoundary;
import service.create_vault_item.CreateVaultItemRequestModel;
import service.create_vault_item.CreateVaultItemResponseModel;

import entity.AbstractUser;
import entity.PasswordVaultItem;
import exception.AuthException;
import repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Test class for CreateVaultItemInteractor.
 */
public class CreateVaultItemInteractorTest {

    private CreateVaultItemInputBoundary interactor;
    private TestCreateVaultItemPresenter presenter;
    private TestUserRepository userRepository;
    private TestAbstractUser user;

    @BeforeEach
    void setUp() {
        presenter = new TestCreateVaultItemPresenter();
        interactor = new CreateVaultItemInteractor(presenter);
        userRepository = new TestUserRepository();
        user = new TestAbstractUser();
    }

    /**
     * Test case where passwords do not match.
     */
    @Test
    void testCreateVaultItem_PasswordsDoNotMatch() {
        // Arrange
        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
                "My Vault",
                "user123",
                "password1",
                "password2",
                "http://example.com",
                user,
                userRepository
        );

        // Act
        interactor.createVaultItem(requestModel);

        // Assert
        assertNotNull(presenter.getErrorMessage(), "Error message should not be null");
        assertEquals("Passwords must match", presenter.getErrorMessage(), "Error message should indicate password mismatch");
        assertNull(presenter.getResponseModel(), "Response model should be null when passwords do not match");
        assertFalse(userRepository.isAddVaultItemCalled(), "UserRepository.addVaultItem should not be called when passwords do not match");
    }

    /**
     * Test case for successful vault item creation.
     */
    @Test
    void testCreateVaultItem_SuccessfulCreation() {
        // Arrange
        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
                "My Vault",
                "user123",
                "securePass!123",
                "securePass!123",
                "http://example.com",
                user,
                userRepository
        );

        // Act
        interactor.createVaultItem(requestModel);

        // Assert
        assertTrue(userRepository.isAddVaultItemCalled(), "UserRepository.addVaultItem should be called");
        PasswordVaultItem capturedItem = userRepository.getCapturedVaultItem();
        assertNotNull(capturedItem, "Captured vault item should not be null");
        assertEquals("My Vault", capturedItem.getVaultTitle(), "Vault title should match");
        assertEquals("user123", capturedItem.getUsername(), "Username should match");
        assertEquals("securePass!123", capturedItem.getPassword(), "Password should match");
        assertEquals("http://example.com", capturedItem.getUrl(), "URL should match");

        CreateVaultItemResponseModel response = presenter.getResponseModel();
        assertNotNull(response, "Response model should not be null");
        assertTrue(response.isSuccess(), "Response should indicate success");
        assertEquals("Vault item created successfully", response.getMessage(), "Success message should match");
        assertNull(presenter.getErrorMessage(), "Error message should be null on success");
    }

    /**
     * Test case where UserRepository.addVaultItem throws AuthException.
     */
    @Test
    void testCreateVaultItem_AuthException() {
        // Arrange
        userRepository.setThrowAuthException(true);

        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
                "Secure Vault",
                "user456",
                "AnotherPass!456",
                "AnotherPass!456",
                "http://secure.com",
                user,
                userRepository
        );

        // Act
        interactor.createVaultItem(requestModel);

        // Assert
        assertNotNull(presenter.getErrorMessage(), "Error message should not be null");
        assertEquals("Error occured saving vault item", presenter.getErrorMessage(), "Error message should indicate saving failure");
        assertNull(presenter.getResponseModel(), "Response model should be null when an exception occurs");
    }

    /**
     * Test case where UserRepository.addVaultItem throws IOException.
     */
    @Test
    void testCreateVaultItem_IOException() {
        // Arrange
        userRepository.setThrowIOException(true);

        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
                "Data Vault",
                "user789",
                "Pass!789",
                "Pass!789",
                "http://data.com",
                user,
                userRepository
        );

        // Act
        interactor.createVaultItem(requestModel);

        // Assert
        assertNotNull(presenter.getErrorMessage(), "Error message should not be null");
        assertEquals("Error occured saving vault item", presenter.getErrorMessage(), "Error message should indicate saving failure");
        assertNull(presenter.getResponseModel(), "Response model should be null when an exception occurs");
    }

    /**
     * Test case for the cancel operation.
     */
    @Test
    void testCancel() {
        // Act
        interactor.cancel();

        // Assert
        assertTrue(presenter.isCancelCalled(), "Presenter's cancel method should be called");
    }

    /**
     * Test case for displaying the home view.
     */
    @Test
    void testDisplayHomeView() {
        // Act
        interactor.displayHomeView();

        // Assert
        assertTrue(presenter.isDisplayHomeViewCalled(), "Presenter's displayHomeView method should be called");
    }

    /**
     * Test case for null password in request model.
     */
    @Test
    void testCreateVaultItem_NullPassword() {
        // Arrange
        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
                "Null Password Vault",
                "user000",
                null,
                null,
                "http://null.com",
                user,
                userRepository
        );

        // Act
        interactor.createVaultItem(requestModel);

        // Assert
        assertNotNull(presenter.getErrorMessage(), "Error message should not be null");
        assertEquals("Passwords must match", presenter.getErrorMessage(), "Error message should indicate password mismatch due to null passwords");
        assertNull(presenter.getResponseModel(), "Response model should be null when passwords are null");
        assertFalse(userRepository.isAddVaultItemCalled(), "UserRepository.addVaultItem should not be called when passwords are null");
    }

    /**
     * Test case for empty password strings in request model.
     */
    @Test
    void testCreateVaultItem_EmptyPassword() {
        // Arrange
        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
                "Empty Password Vault",
                "userEmpty",
                "",
                "",
                "http://empty.com",
                user,
                userRepository
        );

        // Act
        interactor.createVaultItem(requestModel);

        // Assert
        assertTrue(userRepository.isAddVaultItemCalled(), "UserRepository.addVaultItem should be called even with empty passwords");
        PasswordVaultItem capturedItem = userRepository.getCapturedVaultItem();
        assertNotNull(capturedItem, "Captured vault item should not be null");
        assertEquals("", capturedItem.getPassword(), "Password should be empty string");

        CreateVaultItemResponseModel response = presenter.getResponseModel();
        assertNotNull(response, "Response model should not be null");
        assertTrue(response.isSuccess(), "Response should indicate success");
        assertEquals("Vault item created successfully", response.getMessage(), "Success message should match");
        assertNull(presenter.getErrorMessage(), "Error message should be null on success");
    }

    /**
     * Inner class to test presenter interactions.
     */
    private static class TestCreateVaultItemPresenter implements CreateVaultItemOutputBoundary {

        private CreateVaultItemResponseModel responseModel;
        private String errorMessage;
        private boolean cancelCalled = false;
        private boolean displayHomeViewCalled = false;

        @Override
        public void presentCreateVaultItemResponse(CreateVaultItemResponseModel responseModel) {
            this.responseModel = responseModel;
        }

        @Override
        public void displayErrorMessage(String message) {
            this.errorMessage = message;
        }

        @Override
        public void cancel() {
            this.cancelCalled = true;
        }

        @Override
        public void displayHomeView() {
            this.displayHomeViewCalled = true;
        }

        public CreateVaultItemResponseModel getResponseModel() {
            return responseModel;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public boolean isCancelCalled() {
            return cancelCalled;
        }

        public boolean isDisplayHomeViewCalled() {
            return displayHomeViewCalled;
        }
    }

    /**
     * Test implementation of UserRepository.
     */
    private static class TestUserRepository implements UserRepository {

        private boolean addVaultItemCalled = false;
        private PasswordVaultItem capturedVaultItem = null;
        private boolean throwAuthException = false;
        private boolean throwIOException = false;

        @Override
        public void addVaultItem(AbstractUser user, PasswordVaultItem vaultItem) throws AuthException, IOException {
            if (throwAuthException) {
                throw new AuthException("Authentication failed");
            }
            if (throwIOException) {
                throw new IOException("IO failure");
            }
            this.addVaultItemCalled = true;
            this.capturedVaultItem = vaultItem;
        }

        public boolean isAddVaultItemCalled() {
            return addVaultItemCalled;
        }

        public PasswordVaultItem getCapturedVaultItem() {
            return capturedVaultItem;
        }

        public void setThrowAuthException(boolean throwAuthException) {
            this.throwAuthException = throwAuthException;
        }

        public void setThrowIOException(boolean throwIOException) {
            this.throwIOException = throwIOException;
        }
    }

    /**
     * Test implementation of AbstractUser.
     */
    private static class TestAbstractUser extends AbstractUser {
        // Implement abstract methods if any
    }
}

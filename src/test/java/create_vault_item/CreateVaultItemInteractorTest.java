package create_vault_item;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.AbstractVault;
import entity.CommonUser;
import entity.LocalVault;
import entity.PasswordVaultItem;
import exception.AuthException;
import mock.MockUserRepository;
import service.create_vault_item.CreateVaultItemInputBoundary;
import service.create_vault_item.CreateVaultItemInteractor;
import service.create_vault_item.CreateVaultItemOutputBoundary;
import service.create_vault_item.CreateVaultItemRequestModel;
import service.create_vault_item.CreateVaultItemResponseModel;

/**
 * Test class for CreateVaultItemInteractor.
 */
public class CreateVaultItemInteractorTest {

    private CreateVaultItemInputBoundary interactor;
    private TestCreateVaultItemPresenter presenter;
    private MockUserRepository userRepository;
    private CommonUser user;

    @BeforeEach
    void setUp() throws AuthException {
        presenter = new TestCreateVaultItemPresenter();
        interactor = new CreateVaultItemInteractor(presenter);
        userRepository = new MockUserRepository();
        AbstractVault vault = new LocalVault(new java.util.ArrayList<>());
        user = new CommonUser("test@example.com", "password123", vault);
        userRepository.signupUser("test@example.com", "password123");
    }

    /**
     * Test case where passwords do not match.
     */
    @Test
    void testCreateVaultItemPasswordsDoNotMatch() {
        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            user,
            userRepository,
            "url",           
            "vaultTitle",       
            "username",      
            "password321",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);

        assertEquals("Passwords must match", presenter.getErrorMessage());
        assertNull(presenter.getResponseModel());
    }

    /**
     * Test case for successful vault item creation.
     */
    @Test
    void testCreateVaultItemSuccess() throws AuthException, IOException {
        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            user,
            userRepository,
            "url",           
            "vaultTitle",       
            "username",      
            "password123",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);
        System.out.println(user.getVault().getItems());

        assertFalse(user.getVault().getItems().isEmpty(), "Vault should have at least one item after creation.");

        PasswordVaultItem addedItem = null;
        for (entity.AbstractVaultItem item : user.getVault().getItems()) {
            if (item instanceof PasswordVaultItem) {
                addedItem = (PasswordVaultItem) item;
                break;
            }
        }
        assertNotNull(addedItem, "Last added vault item should not be null.");
        assertEquals("vaultTitle", addedItem.getTitle());
        assertEquals("username", addedItem.getUsername());
        assertEquals("password123", addedItem.getPassword());
        assertEquals("url", addedItem.getUrl());

        assertNotNull(presenter.getResponseModel());
        assertTrue(presenter.getResponseModel().isSuccess());
        assertEquals("Vault item created successfully", presenter.getResponseModel().getMessage());
    }

    /**
     * Test case where an AuthException is thrown during vault item creation.
     */
    @Test
    void testCreateVaultItemAuthException() throws AuthException {
        userRepository.setThrowAuthException(true);

        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            user,
            userRepository,
            "url",           
            "vaultTitle",       
            "username",      
            "password123",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);

        assertEquals("Error occured saving vault item", presenter.getErrorMessage());
        assertNull(presenter.getResponseModel());
    }

    /**
     * Test case where an IOException is thrown during vault item creation.
     */
    @Test
    void testCreateVaultItemIOException() throws AuthException {
        userRepository.setThrowIOException(true);

        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            user,
            userRepository,
            "url",           
            "vaultTitle",       
            "username",      
            "password123",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);

        assertEquals("Error occured saving vault item", presenter.getErrorMessage());
        assertNull(presenter.getResponseModel());
    }

    /**
     * Test the cancel method.
     */
    @Test
    void testCancel() {
        interactor.cancel();
        assertTrue(presenter.isCancelCalled());
    }

    /**
     * Test the displayHomeView method.
     */
    @Test
    void testDisplayHomeView() {
        interactor.displayHomeView();
        assertTrue(presenter.isDisplayHomeViewCalled());
    }

    /**
     * Inner class to capture presenter interactions.
     */
    private static final class TestCreateVaultItemPresenter implements CreateVaultItemOutputBoundary {
        private String errorMessage;
        private CreateVaultItemResponseModel responseModel;
        private boolean cancelCalled = false;
        private boolean displayHomeViewCalled = false;

        @Override
        public void displayErrorMessage(String message) {
            this.errorMessage = message;
        }

        @Override
        public void presentCreateVaultItemResponse(CreateVaultItemResponseModel response) {
            this.responseModel = response;
        }

        @Override
        public void cancel() {
            this.cancelCalled = true;
        }

        @Override
        public void displayHomeView() {
            this.displayHomeViewCalled = true;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public CreateVaultItemResponseModel getResponseModel() {
            return responseModel;
        }

        public boolean isCancelCalled() {
            return cancelCalled;
        }

        public boolean isDisplayHomeViewCalled() {
            return displayHomeViewCalled;
        }
    }
}

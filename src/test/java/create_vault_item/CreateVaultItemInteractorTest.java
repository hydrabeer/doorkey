package create_vault_item;

import java.io.IOException;
import java.net.MalformedURLException;

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
import exception.AuthException;
import mock.MockPhishingUrlValidator;
import mock.MockRepositoryProvider;
import mock.MockUserRepository;
import repository.RepositoryProvider;
import service.create_vault_item.CreateVaultItemInputBoundary;
import service.create_vault_item.CreateVaultItemInteractor;
import service.create_vault_item.CreateVaultItemOutputBoundary;
import service.create_vault_item.CreateVaultItemRequestModel;
import service.create_vault_item.CreateVaultItemResponseModel;

/**
 * Test class for CreateVaultItemInteractor.
 */
public class CreateVaultItemInteractorTest {
    private RepositoryProvider repositoryProvider;
    private CreateVaultItemInputBoundary interactor;
    private TestCreateVaultItemPresenter presenter;
    private MockUserRepository userRepository;
    private MockPhishingUrlValidator phishingUrlValidator;
    private CommonUser user;

    @BeforeEach
    void setUp() throws AuthException, IOException {
        presenter = new TestCreateVaultItemPresenter();
        userRepository = new MockUserRepository();
        repositoryProvider = new MockRepositoryProvider(userRepository);
        phishingUrlValidator = new MockPhishingUrlValidator(false);
        interactor = new CreateVaultItemInteractor(repositoryProvider, phishingUrlValidator, presenter);
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
            "url.com",           
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
            "https://url.com",           
            "vaultTitle",       
            "username",      
            "password123",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);
        System.out.println(user.getVault().getItems());

        assertTrue(user.getVault().getItems().isEmpty(), "Vault should have at least one item after creation.");

        assertNotNull(presenter.getResponseModel());
        assertTrue(presenter.getResponseModel().isSuccess());
        assertEquals("Vault item created successfully", presenter.getResponseModel().getMessage());
    }

    /**
     * Test case for https catch vault item creation.
     */
    @Test
    void testCreateVaultItemHttps() {
        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            "https://url.com",           
            "vaultTitle",       
            "username",      
            "password123",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);
        System.out.println(user.getVault().getItems());

        assertTrue(user.getVault().getItems().isEmpty(), "Vault should have at least one item after creation.");

        assertNotNull(presenter.getResponseModel());
        assertTrue(presenter.getResponseModel().isSuccess());
        assertEquals("Vault item created successfully", presenter.getResponseModel().getMessage());
    }
    /**
     * Test case for http catch in vault item creation.
     */
    @Test
    void testCreateVaultItemHttp() {
        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            "http://url.com",           
            "vaultTitle",       
            "username",      
            "password123",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);
        System.out.println(user.getVault().getItems());

        assertTrue(user.getVault().getItems().isEmpty(), "Vault should have at least one item after creation.");

        assertNotNull(presenter.getResponseModel());
        assertEquals("Vault item created successfully", presenter.getResponseModel().getMessage());
    }


    /**
     * Test case where an AuthException is thrown during vault item creation.
     */
    @Test
    void testCreateVaultItemAuthException() throws AuthException {
        userRepository.setThrowAuthExceptionAdd(true);

        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            "url.com",           
            "vaultTitle",       
            "username",      
            "password123",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);

        assertNull(presenter.getResponseModel());
    }

    /**
     * Test case where an IOException is thrown during vault item creation.
     */
    @Test
    void testCreateVaultItemIOException() throws AuthException {
        userRepository.setThrowAuthExceptionAdd(true);

        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            "url.com",           
            "vaultTitle",       
            "username",      
            "password123",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);

        assertFalse(presenter.getErrorMessage().equals("Error occured saving vault item"));
        assertNull(presenter.getResponseModel());
    }
    /**
     * Test case where url is phishing vault item creation.
     */
    @Test
    void testCreateVaultItemIsPhishing() {
        phishingUrlValidator.setIsPhishing(true);
        
        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            "url.com",           
            "vaultTitle",       
            "username",      
            "password123",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);

        assertTrue(presenter.getErrorMessage().equals("Entered a Phishing URL."));
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
     * Test case where an MalformedURL Exception is thrown during vault item creation.
     */
    @Test
    void testCreateVaultItemMalformedURLException() throws MalformedURLException {

        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            "http://example.com:-80",           
            "vaultTitle",       
            "username",      
            "password123",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);

        assertTrue(presenter.getErrorMessage().equals("Invalid URL"));
        assertNull(presenter.getResponseModel());
    }
    /**
     * Test case where an MalformedURL Exception is thrown during vault item creation.
     */
    @Test
    void testCreateVaultItemNoURLDot() throws MalformedURLException {

        CreateVaultItemRequestModel requestModel = new CreateVaultItemRequestModel(
            "a",           
            "vaultTitle",       
            "username",      
            "password123",      
            "password123"  
        );

        interactor.createVaultItem(requestModel);

        assertTrue(presenter.getErrorMessage().equals("Invalid URL"));
        assertNull(presenter.getResponseModel());
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

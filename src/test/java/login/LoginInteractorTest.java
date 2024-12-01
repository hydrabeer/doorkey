package login;

import entity.AbstractUser;
import exception.AuthException;
import mock.MockLoginPresenter;
import mock.MockUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.login.LoginInteractor;
import service.login.LoginInputData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the LoginInteractor.
 */
public class LoginInteractorTest {
    private MockUserRepository mockUserRepository = new MockUserRepository();
    private MockLoginPresenter mockLoginPresenter = new MockLoginPresenter();
    private LoginInteractor loginInteractor = new LoginInteractor(mockUserRepository, mockLoginPresenter);

    @BeforeEach
    public void setUp() {
        mockUserRepository = new MockUserRepository();
        mockLoginPresenter = new MockLoginPresenter();
        loginInteractor = new LoginInteractor(mockUserRepository, mockLoginPresenter);
    }

    /**
     * Test login with an invalid email format.
     */
    @Test
    public void testLogin_InvalidEmail() {
        String invalidEmail = "invalidemail.com";
        String password = "password123";
        LoginInputData inputData = new LoginInputData(invalidEmail, password);

        loginInteractor.login(inputData);

        assertFalse(mockLoginPresenter.hasSuccessView(), "Success view for invalid email");
        assertTrue(mockLoginPresenter.hasFieldError("email", "Invalid email."),
                "Error view for invalid email");
        assertTrue(mockLoginPresenter.hasClearedField("password"),
                "Password not cleared");
        assertTrue(mockLoginPresenter.generalErrors.isEmpty(),
                "General error view for invalid email");
    }

    /**
     * Test login with an empty password.
     */
    @Test
    public void testLogin_EmptyPassword() {
        String email = "user@example.com";
        String emptyPassword = "";
        LoginInputData inputData = new LoginInputData(email, emptyPassword);

        loginInteractor.login(inputData);

        assertFalse(mockLoginPresenter.hasSuccessView(), "Success view for empty password");
        assertTrue(mockLoginPresenter.hasFieldError("password", "Cannot be empty."),
                "Error view for empty password");
        assertTrue(mockLoginPresenter.hasClearedField("email"),
                "Clear email error if any");
        assertTrue(mockLoginPresenter.generalErrors.isEmpty(),
                "General errors for empty password");
    }

    /**
     * Test login with both invalid email and empty password.
     */
    @Test
    public void testLogin_InvalidEmailAndEmptyPassword() {
        String invalidEmail = "invalidemail.com";
        String emptyPassword = "";
        LoginInputData inputData = new LoginInputData(invalidEmail, emptyPassword);

        loginInteractor.login(inputData);

        assertFalse(mockLoginPresenter.hasSuccessView(), "Success view for invalid email and empty password");
        assertTrue(mockLoginPresenter.hasFieldError("email", "Invalid email."),
                "Error view for invalid email");
        assertTrue(mockLoginPresenter.hasFieldError("password", "Cannot be empty."),
                "Error view for empty password");
        assertFalse(mockLoginPresenter.hasClearedField("email"),
                "Clear email error when it's invalid");
        assertFalse(mockLoginPresenter.hasClearedField("password"),
                "Clear password error when it's empty");
        assertTrue(mockLoginPresenter.generalErrors.isEmpty(),
                "SGeneral errors for invalid email and empty password");
    }

    /**
     * Test login with correct email but wrong password.
     */
    @Test
    public void testLogin_WrongCredentials() throws AuthException {
        String email = "user@example.com";
        String correctPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        mockUserRepository.signupUser(email, correctPassword);
        LoginInputData inputData = new LoginInputData(email, wrongPassword);

        loginInteractor.login(inputData);

        assertFalse(mockLoginPresenter.hasSuccessView(), "Success view for wrong credentials");
        assertTrue(mockLoginPresenter.hasClearedField("email"),
                "Clear email error on authentication failure");
        assertTrue(mockLoginPresenter.hasClearedField("password"),
                "Clear password error on authentication failure");
        assertTrue(mockLoginPresenter.hasGeneralError("Wrong credentials."),
                "No general error for wrong credentials");
        assertTrue(mockLoginPresenter.fieldErrors.isEmpty(),
                "Field errors for wrong credentials");
    }

    /**
     * Test successful login with correct email and password.
     */
    @Test
    public void testLogin_Success() throws AuthException {
        String email = "user@example.com";
        String password = "password123";
        AbstractUser user = mockUserRepository.signupUser(email, password);
        LoginInputData inputData = new LoginInputData(email, password);

        loginInteractor.login(inputData);

        assertTrue(mockLoginPresenter.hasSuccessView(), "No success view for valid credentials");
        assertEquals(user, mockLoginPresenter.successViews.get(0).getUser(),
                "Success view should contain the correct user");
        assertTrue(mockLoginPresenter.hasClearedField("email"),
                "Clear email error on successful login");
        assertTrue(mockLoginPresenter.hasClearedField("password"),
                "Clear password error on successful login");
        assertTrue(mockLoginPresenter.generalErrors.isEmpty(),
                "General errors on successful login");
        assertTrue(mockLoginPresenter.fieldErrors.isEmpty(),
                "Field-specific errors on successful login");
    }
}

package signup;

import entity.AbstractUser;
import exception.AuthException;
import mock.MockRepositoryProvider;
import mock.MockSignupPresenter;
import mock.MockUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.signup.SignupInputData;
import service.signup.SignupInteractor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the SignupInteractor.
 */
public class SignupInteractorTest {
    private MockUserRepository mockUserRepository = new MockUserRepository();
    private MockRepositoryProvider mockRepositoryProvider = new MockRepositoryProvider(mockUserRepository);
    private MockSignupPresenter mockSignupPresenter = new MockSignupPresenter();
    private SignupInteractor signupInteractor = new SignupInteractor(mockRepositoryProvider,
            mockUserRepository,
            mockSignupPresenter
    );

    @BeforeEach
    public void setUp() {
        mockUserRepository = new MockUserRepository();
        mockRepositoryProvider = new MockRepositoryProvider(mockUserRepository);
        mockSignupPresenter = new MockSignupPresenter();
        signupInteractor = new SignupInteractor(mockRepositoryProvider, mockUserRepository, mockSignupPresenter);
    }

    @Test
    public void testSignup_InvalidEmail() {
        String invalidEmail = "invalidemail.com";
        String password = "password123";
        String repeatedPassword = "password123";
        SignupInputData inputData = new SignupInputData(invalidEmail, password, repeatedPassword);

        signupInteractor.signup(inputData);

        assertFalse(mockSignupPresenter.hasSuccessView(), "No success view for invalid email");
        assertTrue(mockSignupPresenter.hasFieldError("email", "Invalid email."), "Error for invalid email");
        assertTrue(mockSignupPresenter.hasClearedField("password"), "Cleared password error");
        assertTrue(mockSignupPresenter.hasClearedField("repeatedPassword"), "Cleared repeated password error");
        assertTrue(mockSignupPresenter.generalErrors.isEmpty(), "No general errors for invalid email");
    }

    @Test
    public void testSignup_EmptyPassword() {
        String email = "user@example.com";
        String emptyPassword = "";
        String repeatedPassword = "";
        SignupInputData inputData = new SignupInputData(email, emptyPassword, repeatedPassword);

        signupInteractor.signup(inputData);

        assertFalse(mockSignupPresenter.hasSuccessView(), "No success view for empty password");
        assertTrue(mockSignupPresenter.hasFieldError("password", "Cannot be empty."), "Error for empty password");
        assertTrue(mockSignupPresenter.hasFieldError("repeatedPassword", "Cannot be empty."), "Error for empty repeated password");
        assertTrue(mockSignupPresenter.hasClearedField("email"), "Cleared email error");
        assertTrue(mockSignupPresenter.generalErrors.isEmpty(), "No general errors for empty password");
    }

    @Test
    public void testSignup_PasswordsDoNotMatch() {
        String email = "user@example.com";
        String password = "password123";
        String repeatedPassword = "password321";
        SignupInputData inputData = new SignupInputData(email, password, repeatedPassword);

        signupInteractor.signup(inputData);

        assertFalse(mockSignupPresenter.hasSuccessView(), "No success view when passwords don't match");
        assertTrue(mockSignupPresenter.hasFieldError("repeatedPassword", "Passwords don't match."), "Error for password mismatch");
        assertTrue(mockSignupPresenter.hasClearedField("email"), "Cleared email error");
        assertTrue(mockSignupPresenter.hasClearedField("password"), "Cleared password error");
        assertTrue(mockSignupPresenter.generalErrors.isEmpty(), "No general errors for password mismatch");
    }

    @Test
    public void testSignup_InvalidEmailAndEmptyPassword() {
        String invalidEmail = "invalidemail.com";
        String emptyPassword = "";
        String emptyRepeatedPassword = "";
        SignupInputData inputData = new SignupInputData(invalidEmail, emptyPassword, emptyRepeatedPassword);

        signupInteractor.signup(inputData);

        assertFalse(mockSignupPresenter.hasSuccessView(), "No success view for invalid email and empty password");
        assertTrue(mockSignupPresenter.hasFieldError("email", "Invalid email."), "Error for invalid email");
        assertTrue(mockSignupPresenter.hasFieldError("password", "Cannot be empty."), "Error for empty password");
        assertTrue(mockSignupPresenter.hasFieldError("repeatedPassword", "Cannot be empty."), "Error for empty repeated password");
        assertFalse(mockSignupPresenter.clearedFields.contains("email"), "Did not clear email error");
        assertFalse(mockSignupPresenter.clearedFields.contains("password"), "Did not clear password error");
        assertTrue(mockSignupPresenter.generalErrors.isEmpty(), "No general errors for multiple input issues");
    }

    @Test
    public void testSignup_UserAlreadyExists() throws AuthException, IOException {
        String email = "user@example.com";
        String password = "password123";
        String repeatedPassword = "password123";
        mockUserRepository.signupUser(email, password);
        SignupInputData inputData = new SignupInputData(email, password, repeatedPassword);

        signupInteractor.signup(inputData);

        assertFalse(mockSignupPresenter.hasSuccessView(), "No success view when user already exists");
        assertTrue(mockSignupPresenter.hasGeneralError("Email already exists."), "General error for existing user");
        assertTrue(mockSignupPresenter.hasClearedField("email"), "Cleared email error");
        assertTrue(mockSignupPresenter.hasClearedField("password"), "Cleared password error");
        assertTrue(mockSignupPresenter.hasClearedField("repeatedPassword"), "Cleared repeated password error");
        assertTrue(mockSignupPresenter.fieldErrors.isEmpty(), "No field-specific errors for existing user");
    }

    @Test
    public void testSignup_Success() throws AuthException, IOException {
        String email = "newuser@example.com";
        String password = "password123";
        String repeatedPassword = "password123";
        SignupInputData inputData = new SignupInputData(email, password, repeatedPassword);

        signupInteractor.signup(inputData);

        assertTrue(mockSignupPresenter.hasSuccessView(), "Success view for valid signup");
        AbstractUser signedUpUser = mockUserRepository.signInUser(email, password);
        assertEquals(
                signedUpUser,
                mockSignupPresenter.successViews.get(0).getUserRepository().getCurrentUser(),
                "Signed up user matches"
        );
        assertTrue(mockSignupPresenter.hasClearedField("email"), "Cleared email error on success");
        assertTrue(mockSignupPresenter.hasClearedField("password"), "Cleared password error on success");
        assertTrue(mockSignupPresenter.hasClearedField("repeatedPassword"), "Cleared repeated password error on success");
        assertTrue(mockSignupPresenter.generalErrors.isEmpty(), "No general errors on successful signup");
        assertTrue(mockSignupPresenter.fieldErrors.isEmpty(), "No field-specific errors on successful signup");
    }

    @Test
    public void testSignup_IoException() {
        String email = "newuser@example.com";
        String password = "password123";
        String repeatedPassword = "password123";
        SignupInputData inputData = new SignupInputData(email, password, repeatedPassword);
        mockUserRepository.setThrowIOExceptionSignup(true);

        signupInteractor.signup(inputData);

        assertFalse(mockSignupPresenter.hasSuccessView(), "No success view for IO exception");
    }
}

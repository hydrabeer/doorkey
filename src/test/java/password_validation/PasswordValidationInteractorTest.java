package password_validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import service.password_validation.PasswordValidationInputBoundary;
import service.password_validation.PasswordValidationInteractor;
import service.password_validation.PasswordValidationOutputBoundary;
import service.password_validation.PasswordValidationRequestModel;
import service.password_validation.PasswordValidationResponseModel;

/**
 * Test class for PasswordValidationInteractor.
 */
public class PasswordValidationInteractorTest {
    
    private PasswordValidationInputBoundary interactor;
    private TestPasswordValidationPresenter presenter;
    
    @BeforeEach
    void setUp() {
        presenter = new TestPasswordValidationPresenter();
        interactor = new PasswordValidationInteractor(presenter);
    }

    // Valid Data Testing
    @Test
    void testValidPasswordAllRequirementsMet() {
        final String password = "ValidPass123!";
        final boolean enforceEntropy = true;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        interactor.validate(requestModel);
        final PasswordValidationResponseModel response = presenter.getResponseModel();

        assertNotNull(response);
        assertTrue(response.isValid());
        assertTrue(response.isLengthReq());
        assertTrue(response.isUpperLowerReq());
        assertTrue(response.isNumericReq());
        assertTrue(response.isSpecialCharReq());
        assertTrue(response.isMeetsRequirements());
        assertTrue(response.getEntropy() > 2);
    }

    // Boundary Data Testing
    @Test
    void testBoundaryPasswordMinimumLength() {
        final String password = "A1z!23?#";
        final boolean enforceEntropy = true;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        interactor.validate(requestModel);
        final PasswordValidationResponseModel response = presenter.getResponseModel();

        assertNotNull(response);
        assertTrue(response.isValid());
        assertTrue(response.isLengthReq());
        assertTrue(response.isUpperLowerReq());
        assertTrue(response.isNumericReq());
        assertTrue(response.isSpecialCharReq());
    }

    @Test
    void testBoundaryLessThanMinimumLength() {
        final String password = "A1a!234";
        final boolean enforceEntropy = true;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        interactor.validate(requestModel);
        final PasswordValidationResponseModel response = presenter.getResponseModel();

        assertNotNull(response);
        assertFalse(response.isValid());
        assertFalse(response.isLengthReq());
        assertTrue(response.isUpperLowerReq());
        assertTrue(response.isNumericReq());
        assertTrue(response.isSpecialCharReq());
    }

    // Erroneous Data Testing
    @Test
    void testInvalidPasswordMissingUppercase() {
        final String password = "validpass123!";
        final boolean enforceEntropy = true;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        interactor.validate(requestModel);
        final PasswordValidationResponseModel response = presenter.getResponseModel();

        assertNotNull(response);
        assertFalse(response.isValid());
        assertTrue(response.isLengthReq());
        assertFalse(response.isUpperLowerReq());
        assertTrue(response.isNumericReq());
        assertTrue(response.isSpecialCharReq());
    }

    @Test
    void testInvalidPasswordMissingLowercase() {
        final String password = "VALIDPASS123!";
        final boolean enforceEntropy = true;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        interactor.validate(requestModel);
        final PasswordValidationResponseModel response = presenter.getResponseModel();

        assertNotNull(response);
        assertFalse(response.isValid());
        assertTrue(response.isLengthReq());
        assertFalse(response.isUpperLowerReq());
        assertTrue(response.isNumericReq());
        assertTrue(response.isSpecialCharReq());
    }

    @Test
    void testInvalidPasswordMissingNumber() {
        final String password = "ValidPass!";
        final boolean enforceEntropy = true;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        interactor.validate(requestModel);
        final PasswordValidationResponseModel response = presenter.getResponseModel();

        assertNotNull(response);
        assertFalse(response.isValid());
        assertTrue(response.isLengthReq());
        assertTrue(response.isUpperLowerReq());
        assertFalse(response.isNumericReq());
        assertTrue(response.isSpecialCharReq());
    }

    @Test
    void testInvalidPasswordMissingSpecialCharacter() {
        final String password = "ValidPass123";
        final boolean enforceEntropy = true;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        interactor.validate(requestModel);
        final PasswordValidationResponseModel response = presenter.getResponseModel();

        assertNotNull(response);
        assertFalse(response.isValid());
        assertTrue(response.isLengthReq());
        assertTrue(response.isUpperLowerReq());
        assertTrue(response.isNumericReq());
        assertFalse(response.isSpecialCharReq());
    }

    // Entropy Testing
    @Test
    void testPasswordLowEntropy() {
        final String password = "aaaaaaa";
        final boolean enforceEntropy = true;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        interactor.validate(requestModel);
        final PasswordValidationResponseModel response = presenter.getResponseModel();

        assertNotNull(response);
        assertFalse(response.isValid());
        assertFalse(response.isLengthReq());
        assertFalse(response.isUpperLowerReq());
        assertFalse(response.isNumericReq());
        assertFalse(response.isSpecialCharReq());
        assertTrue(response.getEntropy() <= 2);
    }

    @Test
    void testPasswordHighEntropy() {
        final String password = "C0mpl3x!Passw0rd#2021";
        final boolean enforceEntropy = true;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        interactor.validate(requestModel);
        final PasswordValidationResponseModel response = presenter.getResponseModel();

        assertNotNull(response);
        assertTrue(response.isValid());
        assertTrue(response.isLengthReq());
        assertTrue(response.isUpperLowerReq());
        assertTrue(response.isNumericReq());
        assertTrue(response.isSpecialCharReq());
        assertTrue(response.getEntropy() > 2);
    }

    // No Enforcement Testing (Vault Item Creation)
    @Test
    void testPasswordNoEnforcement() {
        final String password = "weak";
        final boolean enforceEntropy = false;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        interactor.validate(requestModel);
        final PasswordValidationResponseModel response = presenter.getResponseModel();

        assertNotNull(response);
        assertTrue(response.isValid());
        assertFalse(response.isLengthReq());
        assertFalse(response.isUpperLowerReq());
        assertFalse(response.isNumericReq());
        assertFalse(response.isSpecialCharReq());
    }

    // Null and Empty String Testing
    @Test
    void testPasswordNull() {
        final String password = null;
        final boolean enforceEntropy = true;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        final NullPointerException exception =
            assertThrows(NullPointerException.class, () -> interactor.validate(requestModel));
        assertNotNull(exception);
    }

    @Test
    void testPasswordEmptyString() {
        final String password = "";
        final boolean enforceEntropy = true;
        final PasswordValidationRequestModel requestModel =
            new PasswordValidationRequestModel(password, enforceEntropy);

        interactor.validate(requestModel);
        final PasswordValidationResponseModel response = presenter.getResponseModel();

        assertNotNull(response);
        assertFalse(response.isValid());
        assertFalse(response.isLengthReq());
        assertFalse(response.isUpperLowerReq());
        assertFalse(response.isNumericReq());
        assertFalse(response.isSpecialCharReq());
    }

    private static final class TestPasswordValidationPresenter implements PasswordValidationOutputBoundary {

        private PasswordValidationResponseModel responseModel;
    
        @Override
        public void present(PasswordValidationResponseModel response) {
            this.responseModel = response;
        }
    
        public PasswordValidationResponseModel getResponseModel() {
            return responseModel;
        }
    }
    
}

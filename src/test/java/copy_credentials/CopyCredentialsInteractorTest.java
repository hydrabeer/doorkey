package copy_credentials;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import service.copy_credentials.CopyCredenentialsOutputBoundary;
import service.copy_credentials.CopyCredentialsInputBoundary;
import service.copy_credentials.CopyCredentialsInteractor;
import service.copy_credentials.UsernameInputData;
import service.copy_credentials.PasswordInputData;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CopyCredentialsInteractorTest {
    private static final String testUsername = "Chad";
    private static final String testPassword = "password";

    @Test
    public void testCopyUsername() throws IOException, UnsupportedFlavorException {
        UsernameInputData usernameInputData = new UsernameInputData(testUsername);
        CopyCredenentialsOutputBoundary copyCredenentialsOutputBoundary = new CopyCredenentialsOutputBoundary() {
            @Override
            public void displayCopyMessage(String message) {

            }

        };
        CopyCredentialsInputBoundary copyCredentialsInputBoundary = new CopyCredentialsInteractor(copyCredenentialsOutputBoundary);
        copyCredentialsInputBoundary.copyUsername(usernameInputData);
        assertEquals(Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor), testUsername);
    }

    @Test
    public void testCopyPassword() throws IOException, UnsupportedFlavorException {
        PasswordInputData passwordInputData = new PasswordInputData(testPassword);
        CopyCredenentialsOutputBoundary copyCredenentialsOutputBoundary = new CopyCredenentialsOutputBoundary() {
            @Override
            public void displayCopyMessage(String message) {

            }

        };
        CopyCredentialsInputBoundary copyCredentialsInputBoundary = new CopyCredentialsInteractor(copyCredenentialsOutputBoundary);
        copyCredentialsInputBoundary.copyPassword(passwordInputData);
        assertEquals((Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)), testPassword);
    }
}

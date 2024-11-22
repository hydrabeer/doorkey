package copy_credentials;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import service.copy_credentials.*;

import static org.junit.jupiter.api.Assertions.*;

public class CopyCredentialsInteractorTest {

    @Test
    public void testCopyUsername() throws IOException, UnsupportedFlavorException {
        UsernameInputData usernameInputData = new UsernameInputData("Amaan");
        CopyCredenentialsOutputBoundary copyCredenentialsOutputBoundary = new CopyCredenentialsOutputBoundary() {
            @Override
            public void displayUsernameCopyMessage(String message) {

            }

            @Override
            public void displayPasswordCopyMessage(String message) {

            }
        };
        CopyCredentialsInputBoundary copyCredentialsInputBoundary = new CopyCredentialsInteractor(copyCredenentialsOutputBoundary);
        copyCredentialsInputBoundary.copyUsername(usernameInputData);
        assertEquals((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor), "Amaan");
    }
    @Test
    public void testCopyPassword() throws IOException, UnsupportedFlavorException {
        PasswordInputData passwordInputData = new PasswordInputData("password");
        CopyCredenentialsOutputBoundary copyCredenentialsOutputBoundary = new CopyCredenentialsOutputBoundary() {
            @Override
            public void displayUsernameCopyMessage(String message) {

            }

            @Override
            public void displayPasswordCopyMessage(String message) {

            }
        };
        CopyCredentialsInputBoundary copyCredentialsInputBoundary = new CopyCredentialsInteractor(copyCredenentialsOutputBoundary);
        copyCredentialsInputBoundary.copyPassword(passwordInputData);
        assertEquals((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor), "password");
    }
}

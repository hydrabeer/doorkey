package copy_credentials;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import copy_credentials.CopyCredentialsInteractor;
import copy_credentials.CopyCredentialsInputBoundary;
import copy_credentials.PasswordInputData;
import copy_credentials.UsernameInputData;

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

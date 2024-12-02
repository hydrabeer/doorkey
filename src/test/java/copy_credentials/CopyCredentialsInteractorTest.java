package copy_credentials;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import service.copy_credentials.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CopyCredentialsInteractorTest {
    private static final String testUsername = "Chad";
    private static final String testPassword = "password";

    @Test
    public void testCopyUsername() throws IOException, UnsupportedFlavorException {
        UsernameInputData usernameInputData = new UsernameInputData(testUsername, 10000);
        CopyCredenentialsOutputBoundary copyCredenentialsOutputBoundary = new CopyCredenentialsOutputBoundary() {
            @Override
            public void displayCopyMessage(String message) {

            }

        };
        CopyCredentialsInputBoundary copyCredentialsInputBoundary = new CopyCredentialsInteractor(
                copyCredenentialsOutputBoundary);
        copyCredentialsInputBoundary.copyUsername(usernameInputData);
        assertEquals(Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor), testUsername);
    }

    @Test
    public void testCopyPassword() throws IOException, UnsupportedFlavorException {
        PasswordInputData passwordInputData = new PasswordInputData(testPassword, 10000);
        CopyCredenentialsOutputBoundary copyCredenentialsOutputBoundary = new CopyCredenentialsOutputBoundary() {
            @Override
            public void displayCopyMessage(String message) {

            }

        };
        CopyCredentialsInputBoundary copyCredentialsInputBoundary = new CopyCredentialsInteractor(
                copyCredenentialsOutputBoundary);
        copyCredentialsInputBoundary.copyPassword(passwordInputData);
        assertEquals((Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)), testPassword);
    }


    @Test
    public void testClearClipBoard() throws IOException, UnsupportedFlavorException, InterruptedException {
        CopyCredenentialsOutputBoundary copyCredenentialsOutputBoundary = new CopyCredenentialsOutputBoundary() {
            @Override
            public void displayCopyMessage(String message) {

            }

        };
        CopyCredentialsInputBoundary copyCredentialsInputBoundary = new CopyCredentialsInteractor(
                copyCredenentialsOutputBoundary);
        TimeInputData timeInputData = new TimeInputData(5);
        // Set the clipboard to the test password
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(testPassword), null);

        // Verify the clipboard was set correctly
        assertEquals(testPassword, Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
        copyCredentialsInputBoundary.clearClipboard(timeInputData.getTime());
        final CountDownLatch latch = new CountDownLatch(1);

        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    // Perform the assertion inside the timer task
                    assertEquals("",
                            Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)
                    );
                } catch (UnsupportedFlavorException | IOException exception) {
                    throw new RuntimeException(exception);
                } finally {
                    // Signal that the task has finished
                    latch.countDown();
                }
            }
        };

        timer.schedule(timerTask, 100);

        // Wait for the timer to complete or time out
        boolean completed = latch.await(150, TimeUnit.MILLISECONDS);
        assertTrue(completed);

        // Optionally clean up the timer
        timer.cancel();
    }
}

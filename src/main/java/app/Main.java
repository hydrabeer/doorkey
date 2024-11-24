package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import views.ViewConstants;

/**
 * The main class for our program.
 */
public class Main {
    /**
     * The main method that starts the GUI.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::initializeMainUi);
    }

    /**
     * Initializes the main UI.
     */
    private static void initializeMainUi() {
        final JFrame mainFrame = new AppBuilder("DoorKey", 400, 550)
                .addHomeView()
                .addLoginView()
                .addSignupView()
                .addLocalVaultView()
                .addCreateLocalVaultView()
                .addLocalVaultView()
                .setInitialView(ViewConstants.LOGIN_VIEW)
                .build();

        mainFrame.setVisible(true);
    }
}

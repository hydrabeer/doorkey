import javax.swing.SwingUtilities;

import presenters.controllers.HomeScreenController;
import presenters.views.HomeScreenView;

/**
 * The main class for our program.
 */
public class Main {
    /**
     * The main method that starts the GUI.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final HomeScreenView view = new HomeScreenView();
            new HomeScreenController(view);
        });
    }
}

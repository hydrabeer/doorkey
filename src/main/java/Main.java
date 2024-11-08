import presenters.controllers.HomeScreenController;
import presenters.views.HomeScreenView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeScreenView view = new HomeScreenView();
            new HomeScreenController(view);
        });
    }
}

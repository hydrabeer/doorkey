package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import interface_adapter.NavigableUiPanel;

/**
 * The main ViewManager for the program.
 */
public class ViewManager {
    private final JFrame mainFrame = new JFrame("DoorKey");
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    // From view name to active JPanel
    private final Map<String, NavigableUiPanel> views;

    public ViewManager() {
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.views = new HashMap<>();

        setUpMainFrame();
    }

    /**
     * Add a view to the ViewManager.
     * @param view The view to add.
     */
    public void addView(NavigableUiPanel view) {
        views.put(view.getViewName(), view);
        mainPanel.add(view, view.getViewName());
    }

    /**
     * Show a view.
     * @param name The view ID to show.
     */
    public void showView(String name) {
        cardLayout.show(mainPanel, name);
    }

    /**
     * Return a JPanel from a view.
     * @param name The view ID.
     * @return The view.
     */
    public NavigableUiPanel getView(String name) {
        return views.get(name);
    }

    private void setUpMainFrame() {
        // The following line is commented out, as it can be activated once
        // the panels are made prettier, and the support to add/close is added.
        //
        // this.setUndecorated(true);
        //
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 500);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setBackground(ViewConstants.BACKGROUND_COLOR);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }
}

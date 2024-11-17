package views;

import interface_adapter.NavigatableUIPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The main ViewManager for the program.
 */
public class ViewManager {
    private final JFrame mainFrame = new JFrame("DoorKey");
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    // From view name to active JPanel
    private final Map<String, JPanel> views;

    public ViewManager() {
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.views = new HashMap<>();

        setUpMainFrame();
    }

    public void addView(NavigatableUIPanel view) {
        views.put(view.getViewName(), view);
        mainPanel.add(view, view.getViewName());
    }

    public void showView(String name) {
        cardLayout.show(mainPanel, name);
    }

    public JPanel getView(String name) {
        return views.get(name);
    }

    public JPanel getMainPanel() {
        return mainPanel;
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
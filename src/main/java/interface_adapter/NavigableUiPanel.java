package interface_adapter;

import javax.swing.JPanel;

/**
 * Every JPanel that can separately be navigated in DoorKey must extend this.
 * This is required as we are not extending any other Java framework - such as beans.
 */
public abstract class NavigableUiPanel extends JPanel {
    /**
     * Get the view ID.
     * To implement this, add a constant under ViewConstants file and
     * return the ID.
     * @return The panel ID.
     */
    public abstract String getViewName();
}

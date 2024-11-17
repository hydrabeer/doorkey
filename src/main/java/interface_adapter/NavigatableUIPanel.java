package interface_adapter;

import javax.swing.*;

/**
 * Every JPanel that can separately be navigated in DoorKey must be NavigatableUIPanel.
 * <p>
 * This is required as we are not extending any other Java framework - such as beans.
 */
public abstract class NavigatableUIPanel extends JPanel {
    public abstract String getViewName();
}

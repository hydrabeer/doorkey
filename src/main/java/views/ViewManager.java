package views;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import service.ViewManagerModel;

/**
 * The main ViewManager for the program.
 */
public class ViewManager implements PropertyChangeListener {
    private final JPanel views;
    private final CardLayout cardLayout;

    public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        this.views = views;
        this.cardLayout = cardLayout;
        viewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("state")) {
            final String switchTo = (String) event.getNewValue();
            cardLayout.show(views, switchTo);
        }
    }
}

package views;

import service.ViewManagerModel;
import views.components.DoorkeyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Test - temporary
 * <p>
 * This is NOT Clean!!
 */
public class TestView extends JPanel implements ActionListener, PropertyChangeListener {
    private final JLabel label = new JLabel("Test", SwingConstants.CENTER);

    public TestView(TestViewModel viewModel, ViewManagerModel viewManagerModel) {
        viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);

        // JPanel centerPanel = new JPanel();
        // centerPanel.setLayout(new GridLayout(2, 1));
        // centerPanel.add(new JLabel("email: " + email, SwingConstants.CENTER));
        // centerPanel.add(new JLabel("password: " + password, SwingConstants.CENTER));
        // add(centerPanel, BorderLayout.CENTER);

        final DoorkeyButton button = new DoorkeyButton.DoorkeyButtonBuilder("Go back")
                .addListener((event) -> {
                    viewManagerModel.setState(ViewConstants.LOGIN_VIEW);
                    viewManagerModel.onStateChanged();
                })
                .build();
        add(button, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Performed " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final TestViewState state = (TestViewState) evt.getNewValue();
            this.label.setText("Email: " + state.getEmail() + " Password: " + state.getPassword());
        }
    }
}

package views;

import interface_adapter.NavigatableUIPanel;
import views.components.DoorkeyButton;

import javax.swing.*;
import java.awt.*;

/**
 * Test - temporary
 * <p>
 * This is NOT Clean!!
 */
public class TestView extends NavigatableUIPanel {
    public TestView(String email, String password, ViewManager viewManager) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Test", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        centerPanel.add(new JLabel("email: " + email, SwingConstants.CENTER));
        centerPanel.add(new JLabel("password: " + password, SwingConstants.CENTER));
        add(centerPanel, BorderLayout.CENTER);

        final DoorkeyButton button = new DoorkeyButton.DoorkeyButtonBuilder("Go back")
                .addListener((event) -> {
                    viewManager.showView(ViewConstants.LOGIN_VIEW);
                })
                .build();
        add(button, BorderLayout.SOUTH);
    }


    @Override
    public String getViewName() {
        return ViewConstants.TEST_VIEW;
    }
}

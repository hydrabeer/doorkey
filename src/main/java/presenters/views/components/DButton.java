package presenters.views.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The factory for the default custom button used in DoorKey.
 * It comes bundled with a custom implementation of a rounded border, white or black background,
 * and the given text.
 * Implements the builder pattern.
 */
public class DButton extends JButton {
    /**
     * Initialize a new DButton.
     *
     * @param text The string contained in the text.
     */
    public DButton(String text) {
        super(text);
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        this.setOpaque(true);
        this.setFocusPainted(false);
        this.setFont(new DFont(FontStyle.BOLD));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private DButton(DButtonBuilder builder) {
        this(builder.text);
        this.addActionListener(builder.listener);
    }

    /**
     * Builder for DButton.
     */
    public static class DButtonBuilder {
        private final String text;

        private ActionListener listener = (event) -> {
        };

        public DButtonBuilder(String text) {
            this.text = text;
        }

        /**
         * Add an action listener.
         * @param listener The listener to add.
         * @return DButtonBuilder.
         */
        public DButtonBuilder addListener(ActionListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * Build the DButton.
         * @return DButton.
         */
        public DButton build() {
            return new DButton(this);
        }
    }
}

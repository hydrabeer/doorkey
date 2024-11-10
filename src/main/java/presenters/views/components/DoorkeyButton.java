package presenters.views.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * The factory for the default custom button used in Doorkey.
 * It comes bundled with a custom implementation of a rounded border, white or black background,
 * and the given text.
 * Implements the builder pattern.
 */
public class DoorkeyButton extends JButton {
    /**
     * Initialize a new DButton.
     *
     * @param text The string contained in the text.
     */
    public DoorkeyButton(String text) {
        super(text);
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        this.setOpaque(true);
        this.setFocusPainted(false);
        this.setFont(new DoorkeyFont(FontStyle.BOLD));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private DoorkeyButton(DoorkeyButtonBuilder builder) {
        this(builder.text);
        this.addActionListener(builder.listener);
    }

    /**
     * Builder for DButton.
     */
    public static class DoorkeyButtonBuilder {
        private final String text;

        private ActionListener listener = event -> {
        };

        public DoorkeyButtonBuilder(String text) {
            this.text = text;
        }

        /**
         * Add an action actionListener.
         * @param actionListener The actionListener to add.
         * @return DButtonBuilder.
         */
        public DoorkeyButtonBuilder addListener(ActionListener actionListener) {
            this.listener = actionListener;
            return this;
        }

        /**
         * Build the DButton.
         * @return DButton.
         */
        public DoorkeyButton build() {
            return new DoorkeyButton(this);
        }
    }
}

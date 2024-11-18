package views.components;

import java.awt.Font;

import views.ViewConstants;

/**
 * Extends the Font class, initializing the standard font used in Doorkey.
 */
public class DoorkeyFont extends Font {
    private static final int FONT_SIZE = 14;

    /**
     * Initialize a default font given the style and the font size.
     *
     * @param style    the font style, either regular, bold, or italic.
     * @param fontSize the font size in points.
     */
    public DoorkeyFont(FontStyle style, int fontSize) {
        super(ViewConstants.DEFAULT_FONT_NAME, style.awtFontConstant, fontSize);
    }

    /**
     * Initialize a font with regular style 14 pt fontSize.
     * - Regular
     * - 14 pt
     */
    public DoorkeyFont() {
        this(FontStyle.REGULAR, FONT_SIZE);
    }

    /**
     * Initialize a default plain font given the fontSize.
     * - Input points size
     *
     * @param fontSize the input font size.
     */
    public DoorkeyFont(int fontSize) {
        this(FontStyle.REGULAR, fontSize);
    }

    /**
     * Initialize a default plain font given the FontStyle with 14 pts.
     *
     * @param style the font style.
     */
    public DoorkeyFont(FontStyle style) {
        this(style, FONT_SIZE);
    }
}

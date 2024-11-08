package presenters.views.components;

import presenters.views.ViewConstants;

import java.awt.*;

/**
 * Extends the Font class, initializing the standard font used in DoorKey.
 */
public class DFont extends Font {
    /**
     * Initialize a default font given the style and the font size.
     *
     * @param style    the font style, either regular, bold, or italic.
     * @param fontSize the font size in points.
     */
    public DFont(FontStyle style, int fontSize) {
        super(ViewConstants.DEFAULT_FONT_NAME, style.awtFontConstant, fontSize);
    }

    /**
     * Initialize a default plain font with the following parameters:
     * - 14 pt
     * - Regular
     */
    public DFont() {
        this(FontStyle.REGULAR, 14);
    }

    /**
     * Initialize a default plain font given:
     * - Input points size
     *
     * @param fontSize the input font size.
     */
    public DFont(int fontSize) {
        this(FontStyle.REGULAR, fontSize);
    }

    /**
     * Initialize a default plain font given the FontStyle with 14 pts.
     *
     * @param style the font style.
     */
    public DFont(FontStyle style) {
        this(style, 14);
    }
}

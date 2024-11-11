package presenters.views.components;

/**
 * Wrapper around Java AWT Font enums, used internally to simplify
 * common fonts and allow constructor overloading with integers.
 */
public enum FontStyle {
    REGULAR(0),
    BOLD(1),
    ITALIC(2);

    @SuppressWarnings("checkstyle:VisibilityModifier")
    final int awtFontConstant;

    FontStyle(int awtFontConstant) {
        this.awtFontConstant = awtFontConstant;
    }
}

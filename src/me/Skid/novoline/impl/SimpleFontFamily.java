package me.Skid.novoline.impl;

import java.awt.Font;
import me.Skid.novoline.api.FontFamily;
import me.Skid.novoline.api.FontRenderer;
import me.Skid.novoline.api.FontType;

final class SimpleFontFamily implements FontFamily {

    private final FontType fontType;
    private final Font awtFont;

    private SimpleFontFamily(FontType fontType, Font awtFont) {
        this.fontType = fontType;
        this.awtFont = awtFont;
    }

    static FontFamily create(FontType fontType, Font awtFont) {
        return new SimpleFontFamily(fontType, awtFont);
    }

    public FontRenderer ofSize(int size) {
        return SimpleFontRenderer.create(this.awtFont.deriveFont(0, (float) size));
    }

    public FontType font() {
        return this.fontType;
    }
}

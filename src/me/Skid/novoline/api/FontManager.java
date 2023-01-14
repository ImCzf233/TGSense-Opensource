package me.Skid.novoline.api;

@FunctionalInterface
public interface FontManager {

    FontFamily fontFamily(FontType fonttype);

    default FontRenderer font(FontType fontType, int size) {
        return this.fontFamily(fontType).ofSize(size);
    }
}

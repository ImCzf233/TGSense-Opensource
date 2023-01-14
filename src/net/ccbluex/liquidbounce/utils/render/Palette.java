package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum Palette {

    GREEN(() -> {
        return new Color(0, 255, 138);
    }), WHITE(() -> {
    return Color.WHITE;
}), PURPLE(() -> {
    return new Color(198, 139, 255);
}), DARK_PURPLE(() -> {
    return new Color(133, 46, 215);
}), BLUE(() -> {
    return new Color(116, 202, 255);
});

    private final Supplier colorSupplier;

    private Palette(Supplier colorSupplier) {
        this.colorSupplier = colorSupplier;
    }

    public static Color fade(Color color) {
        return fade(color, 2, 100, 2.0F);
    }

    public static Color fade(Color color, int index, int count, float customValue) {
        float[] hsb = new float[3];

        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float) (System.currentTimeMillis() % 2000L) / 1000.0F + (float) index / (float) count * 2.0F) % customValue - 1.0F);

        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color fade1(Color color) {
        return fade1(color, 2, 100);
    }

    public static Color fade1(Color color, int index, int count) {
        float[] hsb = new float[3];

        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float) (System.currentTimeMillis() % 2000L) / 1000.0F + (float) index / (float) count * 2.0F) % 2.0F - 1.0F);

        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color fade2(Color color, int index, int count) {
        float[] hsb = new float[3];

        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float) (System.currentTimeMillis() % 10000L) / 1000.0F + (float) index / (float) count * 2.0F) % 2.0F - 1.0F);

        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public Color getColor() {
        return (Color) this.colorSupplier.get();
    }

    @Nullable
    public static Color fade2(int color, int indexOf, int fontHeight) {
        return null;
    }

    @NotNull
    public static Object fade2(int color, @NotNull String displayText, int fontHeight) {
        return null;
    }
}

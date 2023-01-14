package me.Skid.utils.Color;

import java.awt.Color;

public enum ColorUtils {

    BLACK(-16711423), BLUE(-12028161), DARKBLUE(-12621684), GREEN(-9830551), DARKGREEN(-9320847), WHITE(-65794), AQUA(-7820064), DARKAQUA(-12621684), GREY(-9868951), DARKGREY(-14342875), RED(-65536), DARKRED(-8388608), ORANGE(-29696), DARKORANGE(-2263808), YELLOW(-256), DARKYELLOW(-2702025), MAGENTA(-18751), DARKMAGENTA(-2252579);

    public int c;

    private ColorUtils(int co) {
        this.c = co;
    }

    public static int getColor(Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getColor(Color leftColor, int brightness) {
        return getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int brightness, int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }

    public static int getColor(int red, int green, int blue) {
        return getColor(red, green, blue, 255);
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        byte color = 0;
        int color1 = color | alpha << 24;

        color1 |= red << 16;
        color1 |= green << 8;
        color1 |= blue;
        return color1;
    }

    public static int blendColours(int[] colours, double progress) {
        int size = colours.length;

        if (progress == 1.0D) {
            return colours[0];
        } else if (progress == 0.0D) {
            return colours[size - 1];
        } else {
            double mulProgress = Math.max(0.0D, (1.0D - progress) * (double) (size - 1));
            int index = (int) mulProgress;

            return fadeBetween(colours[index], colours[index + 1], mulProgress - (double) index);
        }
    }

    public static int fadeBetween(int startColour, int endColour, double progress) {
        if (progress > 1.0D) {
            progress = 1.0D - progress % 1.0D;
        }

        return fadeTo(startColour, endColour, progress);
    }

    public static int fadeBetween(int startColour, int endColour) {
        return fadeBetween(startColour, endColour, 0.0D);
    }

    public static int fadeTo(int startColour, int endColour, double progress) {
        double invert = 1.0D - progress;
        int r = (int) ((double) (startColour >> 16 & 255) * invert + (double) (endColour >> 16 & 255) * progress);
        int g = (int) ((double) (startColour >> 8 & 255) * invert + (double) (endColour >> 8 & 255) * progress);
        int b = (int) ((double) (startColour & 255) * invert + (double) (endColour & 255) * progress);
        int a = (int) ((double) (startColour >> 24 & 255) * invert + (double) (endColour >> 24 & 255) * progress);

        return (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | b & 255;
    }
}

package me.manager;

import java.awt.Color;

public class ColorManager {

    public static int getRainbow(int speed, int offset) {
        float hue = (float) ((System.currentTimeMillis() + (long) offset) % (long) speed);

        return Color.getHSBColor(hue / (float) speed, 0.8F, 1.0F).getRGB();
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((double) (System.currentTimeMillis() + (long) delay) / 20.0D);

        rainbowState %= 360.0D;
        return Color.getHSBColor((float) (rainbowState / 360.0D), 0.8F, 0.7F).brighter().getRGB();
    }

    public static Color getChromaRainbow(double x, double y) {
        float v = 2000.0F;

        return new Color(Color.HSBtoRGB((float) (((double) System.currentTimeMillis() - x * 10.0D * 1.0D - y * 10.0D * 1.0D) % (double) v) / v, 0.8F, 0.8F));
    }

    public static int getRainbow2(int speed, int offset) {
        float hue = (float) ((System.currentTimeMillis() + (long) offset) % (long) speed);

        return Color.getHSBColor(hue / (float) speed, 0.8F, 0.8F).getRGB();
    }

    public static int fluxRainbow(int delay, long timeOffset, float sa) {
        double rainbowState = Math.ceil((double) ((System.currentTimeMillis() + timeOffset) / 8L) + (double) delay / 20.0D);

        rainbowState %= 360.0D;
        return Color.getHSBColor((float) (rainbowState / 360.0D), sa, 1.0F).getRGB();
    }

    public static int as() {
        int[] counter;
        int[] arrn = counter = new int[] { 0};

        ++arrn[0];
        return getRainbow3(counter[0] * 20);
    }

    public static int getRainbow3(int tick) {
        double d = 0.0D;
        double delay = Math.ceil((double) ((System.currentTimeMillis() + (long) (tick * 2)) / 5L));
        float rainbow = (double) ((float) (d / 360.0D)) < 0.5D ? -((float) (delay / 360.0D)) : (float) ((delay %= 360.0D) / 360.0D);

        return Color.getHSBColor(rainbow, 0.5F, 1.0F).getRGB();
    }

    public static int astolfoRainbow(int delay, int offset, int index) {
        double rainbowDelay = Math.ceil((double) (System.currentTimeMillis() + (long) (delay * index))) / (double) offset;

        return Color.getHSBColor((double) ((float) ((rainbowDelay %= 360.0D) / 360.0D)) < 0.5D ? -((float) (rainbowDelay / 360.0D)) : (float) (rainbowDelay / 360.0D), 0.5F, 1.0F).getRGB();
    }

    public static Color rainbow(long time, float count, float fade) {
        float hue = ((float) time + (1.0F + count) * 2.0E8F) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0F, 1.0F)), 16);
        Color c = new Color((int) color);

        return new Color((float) c.getRed() / 255.0F * fade, (float) c.getGreen() / 255.0F * fade, (float) c.getBlue() / 255.0F * fade, (float) c.getAlpha() / 255.0F);
    }
}

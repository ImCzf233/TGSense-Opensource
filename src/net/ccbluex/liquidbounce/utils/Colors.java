package net.ccbluex.liquidbounce.utils;

import java.awt.Color;
import java.text.NumberFormat;
import net.minecraft.entity.EntityLivingBase;

public class Colors {

    public static int getColor(Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getColor(int brightness) {
        return getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int brightness, int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }

    public static int getColor(int red, int green, int blue) {
        return getColor(red, green, blue, 255);
    }

    public static Color getHealthColor(EntityLivingBase entityLivingBase) {
        float health = entityLivingBase.getHealth();
        float[] fractions = new float[] { 0.0F, 0.15F, 0.55F, 0.7F, 0.9F};
        Color[] colors = new Color[] { new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
        float progress = health / entityLivingBase.getMaxHealth();

        return health >= 0.0F ? blendColors(fractions, colors, progress).brighter() : colors[0];
    }

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        if (fractions == null) {
            throw new IllegalArgumentException("Fractions can\'t be null");
        } else if (colors == null) {
            throw new IllegalArgumentException("Colours can\'t be null");
        } else if (fractions.length != colors.length) {
            throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        } else {
            int[] indicies = getFractionIndicies(fractions, progress);
            float[] range = new float[] { fractions[indicies[0]], fractions[indicies[1]]};
            Color[] colorRange = new Color[] { colors[indicies[0]], colors[indicies[1]]};
            float max = range[1] - range[0];
            float value = progress - range[0];
            float weight = value / max;

            return blend(colorRange[0], colorRange[1], (double) (1.0F - weight));
        }
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
        float ir = 1.0F - r;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;

        if (red < 0.0F) {
            red = 0.0F;
        } else if (red > 255.0F) {
            red = 255.0F;
        }

        if (green < 0.0F) {
            green = 0.0F;
        } else if (green > 255.0F) {
            green = 255.0F;
        }

        if (blue < 0.0F) {
            blue = 0.0F;
        } else if (blue > 255.0F) {
            blue = 255.0F;
        }

        Color color = null;

        try {
            color = new Color(red, green, blue);
        } catch (IllegalArgumentException illegalargumentexception) {
            NumberFormat nf = NumberFormat.getNumberInstance();

            System.out.println(nf.format((double) red) + "; " + nf.format((double) green) + "; " + nf.format((double) blue));
            illegalargumentexception.printStackTrace();
        }

        return color;
    }

    public static int[] getFractionIndicies(float[] fractions, float progress) {
        int[] range = new int[2];

        int startPoint;

        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
            ;
        }

        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }

        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        byte color = 0;
        int color1 = color | alpha << 24;

        color1 |= red << 16;
        color1 |= green << 8;
        color1 |= blue;
        return color1;
    }

    public static int getRainbow(int speed, int offset) {
        float hue = (float) ((System.currentTimeMillis() + (long) offset) % (long) speed);

        return Color.getHSBColor(hue / (float) speed, 0.4F, 1.0F).getRGB();
    }

    public static int getRainbow(int speed, int offset, float s) {
        float hue = (float) ((System.currentTimeMillis() + (long) offset) % (long) speed);

        return Color.getHSBColor(hue / (float) speed, s, 1.0F).getRGB();
    }

    public static Color getColorFromInt(int color) {
        int red = (color & 16711680) >> 16;
        int green = (color & '\uff00') >> 8;
        int blue = color & 255;

        return new Color(red, green, blue);
    }
}

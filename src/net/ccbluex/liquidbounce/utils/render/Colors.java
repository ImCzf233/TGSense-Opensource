package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import net.minecraft.entity.EntityLivingBase;

public enum Colors {

    BLACK(-16711423), BLUE(-12028161), DARKBLUE(-12621684), GREEN(-9830551), DARKGREEN(-9320847), WHITE(-65794), AQUA(-7820064), DARKAQUA(-12621684), GREY(-9868951), DARKGREY(-14342875), RED(-65536), DARKRED(-8388608), ORANGE(-29696), DARKORANGE(-2263808), YELLOW(-256), DARKYELLOW(-2702025), MAGENTA(-18751), DARKMAGENTA(-2252579);

    public int c;

    private Colors(int co) {
        this.c = co;
    }

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

    public static int getColor(int red, int green, int blue, int alpha) {
        byte color = 0;
        int color1 = color | alpha << 24;

        color1 |= red << 16;
        color1 |= green << 8;
        return color1 | blue;
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

            return net.ccbluex.liquidbounce.utils.Colors.blend(colorRange[0], colorRange[1], (double) (1.0F - weight));
        }
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

    public static int getHealthColor(float health, float maxHealth) {
        float percentage = health / maxHealth;

        return percentage >= 0.75F ? (new Color(125, 250, 125)).getRGB() : ((double) percentage < 0.75D && (double) percentage >= 0.25D ? (new Color(250, 250, 125)).getRGB() : (new Color(250, 125, 125)).getRGB());
    }
}

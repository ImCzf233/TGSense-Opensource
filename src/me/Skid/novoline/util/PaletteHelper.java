package me.Skid.novoline.util;

import java.awt.Color;
import java.util.regex.Pattern;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;

public class PaletteHelper extends MinecraftInstance {

    public static Pattern COLOR_PATTERN = Pattern.compile("(?i)&[0-9A-FK-OR]");

    public static Color getHealthColor(float health, float maxHealth) {
        GlStateManager.pushMatrix();
        float[] fractions = new float[] { 0.0F, 0.5F, 1.0F};
        Color[] colors = new Color[] { new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN};
        float progress = health / maxHealth;

        GlStateManager.popMatrix();
        return blendColors(fractions, colors, progress).brighter();
    }

    public static Color astolfo(float yDist, float yTotal, float saturation, float speedt) {
        float speed = 1800.0F;

        float hue;

        for (hue = (float) (System.currentTimeMillis() % (long) ((int) speed)) + (yTotal - yDist) * speedt; hue > speed; hue -= speed) {
            ;
        }

        if ((double) (hue /= speed) > 0.5D) {
            hue = 0.5F - (hue - 0.5F);
        }

        return Color.getHSBColor(hue += 0.5F, saturation, 1.0F);
    }

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = 0.003921569F * (float) c.getRed();
        float g = 0.003921569F * (float) c.getGreen();
        float b = 0.003921569F * (float) c.getBlue();

        return (new Color(r, g, b, alpha)).getRGB();
    }

    public static String removeColorCode(String text) {
        String finalText = text;

        if (text.contains("??")) {
            for (int i = 0; i < finalText.length(); ++i) {
                if (Character.toString(finalText.charAt(i)).equals("??")) {
                    try {
                        String part1 = finalText.substring(0, i);
                        String part2 = finalText.substring(Math.min(i + 2, finalText.length()));

                        finalText = part1 + part2;
                    } catch (Exception exception) {
                        ;
                    }
                }
            }
        }

        return finalText;
    }

    public static int astolfoColors(int yOffset, int yTotal) {
        float speed = 2900.0F;

        float hue;

        for (hue = (float) (System.currentTimeMillis() % (long) ((int) speed)) + (float) ((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
            ;
        }

        hue /= speed;
        if ((double) (hue /= speed) > 0.5D) {
            hue = 0.5F - (hue - 0.5F);
        }

        return Color.HSBtoRGB(hue += 0.5F, 0.5F, 1.0F);
    }

    private int getHealthColor(EntityLivingBase player) {
        float f = player.getHealth();
        float f1 = player.getMaxHealth();
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);

        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 1.0F) | -16777216;
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

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        Color color = null;

        if (fractions != null && colors != null && fractions.length == colors.length) {
            int[] indicies = getFractionIndicies(fractions, progress);

            if (indicies[0] < 0 || indicies[0] >= fractions.length || indicies[1] < 0 || indicies[1] >= fractions.length) {
                return colors[0];
            }

            float[] range = new float[] { fractions[indicies[0]], fractions[indicies[1]]};
            Color[] colorRange = new Color[] { colors[indicies[0]], colors[indicies[1]]};
            float max = range[1] - range[0];
            float value = progress - range[0];
            float weight = value / max;

            color = blend(colorRange[0], colorRange[1], (double) (1.0F - weight));
        }

        return color;
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

        Color color = new Color(red, green, blue);

        return color;
    }

    public static int getColor(Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getColor(int bright) {
        return getColor(bright, bright, bright, 255);
    }

    public static Color getColorWithOpacity(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
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

    public static int getColor(int brightness, int alpha) {
        return getColor(brightness, brightness, brightness, alpha);
    }

    public static Color rainbow(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((double) ((System.currentTimeMillis() + (long) delay) / 16L));

        return Color.getHSBColor((float) ((rainbow %= 360.0D) / 360.0D), saturation, brightness);
    }

    public static Color rainbow2(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((double) (System.currentTimeMillis() / (long) delay));

        return Color.getHSBColor((float) ((rainbow %= 360.0D) / 360.0D), saturation, brightness);
    }

    public static Color TwoColorEffect(Color color, Color color2, double speed) {
        double thing = speed / 4.0D % 1.0D;
        float clamp = MathematicHelper.clamp((float) (Math.sin(18.84955592153876D * thing) / 2.0D + 0.5D), 0.0F, 1.0F);

        return new Color(MathematicHelper.lerp((float) color.getRed() / 255.0F, (float) color2.getRed() / 255.0F, clamp), MathematicHelper.lerp((float) color.getGreen() / 255.0F, (float) color2.getGreen() / 255.0F, clamp), MathematicHelper.lerp((float) color.getBlue() / 255.0F, (float) color2.getBlue() / 255.0F, clamp));
    }

    public static int astolfo(int delay, float offset) {
        float speed = 3000.0F;

        float hue;

        for (hue = Math.abs((float) (System.currentTimeMillis() % (long) delay) + -offset / 21.0F * 2.0F); hue > speed; hue -= speed) {
            ;
        }

        if ((double) (hue /= speed) > 0.5D) {
            hue = 0.5F - (hue - 0.5F);
        }

        return Color.HSBtoRGB(hue += 0.5F, 0.5F, 1.0F);
    }

    public static Color astolfo(boolean clickgui, int yOffset) {
        float f = 0.0F;
        float speed = clickgui ? 2000.0F : 1000.0F;

        float hue;

        for (hue = (float) (System.currentTimeMillis() % (long) ((int) speed) + (long) yOffset); hue > speed; hue -= speed) {
            ;
        }

        hue /= speed;
        if ((double) f > 0.5D) {
            hue = 0.5F - (hue - 0.5F);
        }

        return Color.getHSBColor(hue += 0.5F, 0.4F, 1.0F);
    }

    public static String stripColor(String name) {
        return PaletteHelper.COLOR_PATTERN.matcher(name).replaceAll("");
    }
}

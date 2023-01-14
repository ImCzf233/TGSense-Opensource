package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class VisualUtils extends MinecraftInstance {

    public static int SkyRainbow(int i, float st, float bright) {
        double v1 = Math.ceil((double) (System.currentTimeMillis() + (long) (i * 109))) / 5.0D;

        return Color.getHSBColor((double) ((float) ((v1 %= 360.0D) / 360.0D)) < 0.5D ? -((float) (v1 / 360.0D)) : (float) (v1 / 360.0D), st, bright).getRGB();
    }

    public static int getRainbow(int index, int offset, float bright, float st) {
        float hue = (float) ((System.currentTimeMillis() + (long) offset * (long) index) % 2000L);

        return Color.getHSBColor(hue /= 2000.0F, st, bright).getRGB();
    }

    public static Color skyRainbow(int i, float st, float bright) {
        double v1 = Math.ceil((double) (System.currentTimeMillis() + (long) (i * 109))) / 5.0D;

        return Color.getHSBColor((double) ((float) ((v1 %= 360.0D) / 360.0D)) < 0.5D ? -((float) (v1 / 360.0D)) : (float) (v1 / 360.0D), st, bright);
    }

    public static int Astolfo(int i, float bright, float st, int index, int offset, float client) {
        double rainbowDelay = Math.ceil((double) (System.currentTimeMillis() + (long) (i * index))) / (double) offset;

        return Color.getHSBColor((double) ((float) ((rainbowDelay %= (double) client) / (double) client)) < 0.5D ? -((float) (rainbowDelay / (double) client)) : (float) (rainbowDelay / (double) client), st, bright).getRGB();
    }

    public static int getRainbowOpaque(int seconds, float saturation, float brightness, int index) {
        float hue = (float) ((System.currentTimeMillis() + (long) index) % (long) (seconds * 1000)) / (float) (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);

        return color;
    }

    public static int getNormalRainbow(int delay, float sat, float brg) {
        double rainbowState = Math.ceil((double) (System.currentTimeMillis() + (long) delay) / 20.0D);

        rainbowState %= 360.0D;
        return Color.getHSBColor((float) (rainbowState / 360.0D), sat, brg).getRGB();
    }

    public static void glColor(int cl) {
        float alpha = (float) (cl >> 24 & 255) / 255.0F;
        float red = (float) (cl >> 16 & 255) / 255.0F;
        float green = (float) (cl >> 8 & 255) / 255.0F;
        float blue = (float) (cl & 255) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void glColor(int red, int green, int blue, int alpha) {
        GlStateManager.color((float) red / 255.0F, (float) green / 255.0F, (float) blue / 255.0F, (float) alpha / 255.0F);
    }

    public static void glColor(Color color) {
        float red = (float) color.getRed() / 255.0F;
        float green = (float) color.getGreen() / 255.0F;
        float blue = (float) color.getBlue() / 255.0F;
        float alpha = (float) color.getAlpha() / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void glColor(int hex, int alpha) {
        float red = (float) (hex >> 16 & 255) / 255.0F;
        float green = (float) (hex >> 8 & 255) / 255.0F;
        float blue = (float) (hex & 255) / 255.0F;

        GlStateManager.color(red, green, blue, (float) alpha / 255.0F);
    }

    public static void glColor(int hex, float alpha) {
        float red = (float) (hex >> 16 & 255) / 255.0F;
        float green = (float) (hex >> 8 & 255) / 255.0F;
        float blue = (float) (hex & 255) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void glColor(Color color, float alpha) {
        float red = (float) color.getRed() / 255.0F;
        float green = (float) color.getGreen() / 255.0F;
        float blue = (float) color.getBlue() / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static Color reAlpha(Color cIn, float alpha) {
        return new Color((float) cIn.getRed() / 255.0F, (float) cIn.getGreen() / 255.0F, (float) cIn.getBlue() / 255.0F, (float) cIn.getAlpha() / 255.0F * alpha);
    }

    public static Color reAlpha(Color cIn, int alpha) {
        return new Color((float) cIn.getRed() / 255.0F, (float) cIn.getGreen() / 255.0F, (float) cIn.getBlue() / 255.0F, (float) cIn.getAlpha() / 255.0F * (float) alpha);
    }

    public static int reAlpha(int n, float n2) {
        Color color = new Color(n);

        return (new Color(0.003921569F * (float) color.getRed(), 0.003921569F * (float) color.getGreen(), 0.003921569F * (float) color.getBlue(), n2)).getRGB();
    }

    private static void quickPolygonCircle(float x, float y, float xRadius, float yRadius, int start, int end, int split) {
        for (int i = end; i >= start; i -= split) {
            GL11.glVertex2d((double) x + Math.sin((double) i * 3.141592653589793D / 180.0D) * (double) xRadius, (double) y + Math.cos((double) i * 3.141592653589793D / 180.0D) * (double) yRadius);
        }

    }

    public static void drawCircleRect(float x, float y, float x1, float y1, float radius, int color) {
        glColor(color);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2884);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glLineWidth(1.0F);
        GL11.glBegin(9);
        float xRadius = (float) Math.min((double) (x1 - x) * 0.5D, (double) radius);
        float yRadius = (float) Math.min((double) (y1 - y) * 0.5D, (double) radius);

        quickPolygonCircle(x + xRadius, y + yRadius, xRadius, yRadius, 180, 270, 4);
        quickPolygonCircle(x1 - xRadius, y + yRadius, xRadius, yRadius, 90, 180, 4);
        quickPolygonCircle(x1 - xRadius, y1 - yRadius, xRadius, yRadius, 0, 90, 4);
        quickPolygonCircle(x + xRadius, y1 - yRadius, xRadius, yRadius, 270, 360, 4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawShadow(int x, int y, int width, int height) {
        ScaledResolution sr = new ScaledResolution((Minecraft) VisualUtils.mc);

        drawTexturedRect(x - 9, y - 9, 9, 9, "paneltopleft", sr);
        drawTexturedRect(x - 9, y + height, 9, 9, "panelbottomleft", sr);
        drawTexturedRect(x + width, y + height, 9, 9, "panelbottomright", sr);
        drawTexturedRect(x + width, y - 9, 9, 9, "paneltopright", sr);
        drawTexturedRect(x - 9, y, 9, height, "panelleft", sr);
        drawTexturedRect(x + width, y, 9, height, "panelright", sr);
        drawTexturedRect(x, y - 9, width, 9, "paneltop", sr);
        drawTexturedRect(x, y + height, width, 9, "panelbottom", sr);
    }

    public static void drawTexturedRect(int x, int y, int width, int height, String image, ScaledResolution sr) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        VisualUtils.mc.getTextureManager().bindTexture(VisualUtils.classProvider.createResourceLocation("quick/shadow/" + image + ".png"));
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GL11.glPopMatrix();
    }

    public static Color getGradientOffset2(Color color1, Color color2, double gident) {
        double f3;
        int f4;

        if (gident > 1.0D) {
            f3 = gident % 1.0D;
            f4 = (int) gident;
            gident = f4 % 2 == 0 ? f3 : 1.0D - f3;
        }

        f3 = 1.0D - gident;
        f4 = (int) ((double) color1.getRed() * f3 + (double) color2.getRed() * gident);
        int f5 = (int) ((double) color1.getGreen() * f3 + (double) color2.getGreen() * gident);
        int f6 = (int) ((double) color1.getBlue() * f3 + (double) color2.getBlue() * gident);

        return new Color(f4, f5, f6);
    }

    public static Color getGradientOffset(Color color1, Color color2, double index) {
        double offs = Math.abs((double) System.currentTimeMillis() / 16.0D) / 60.0D + index;
        double inverse_percent;
        int redPart;

        if (offs > 1.0D) {
            inverse_percent = offs % 1.0D;
            redPart = (int) offs;
            offs = redPart % 2 == 0 ? inverse_percent : 1.0D - inverse_percent;
        }

        inverse_percent = 1.0D - offs;
        redPart = (int) ((double) color1.getRed() * inverse_percent + (double) color2.getRed() * offs);
        int greenPart = (int) ((double) color1.getGreen() * inverse_percent + (double) color2.getGreen() * offs);
        int bluePart = (int) ((double) color1.getBlue() * inverse_percent + (double) color2.getBlue() * offs);
        int alphaPart = (int) ((double) color1.getAlpha() * inverse_percent + (double) color2.getAlpha() * offs);

        return new Color(redPart, greenPart, bluePart, alphaPart);
    }
}

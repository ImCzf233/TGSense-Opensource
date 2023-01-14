package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import java.text.NumberFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class ScaleUtils {

    static Color startColor = new Color(-393028);
    static Color endColor = new Color(-16718593);

    public static void drawOutline(float x, float y, float width, float height, float radius, float line, float offset) {
        enableRender2D();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        float edgeRadius = radius;
        float f = x + radius;
        float centerY = y + radius;
        int vertices = (int) Math.min(Math.max(radius, 10.0F), 90.0F);
        int colorI = 0;
        float centerX = width;

        centerY = height + radius;
        vertices = (int) Math.min(Math.max(radius, 10.0F), 90.0F);

        int i;
        double angleRadians;

        for (i = 0; i <= vertices; ++i) {
            setColor(fadeBetween(ScaleUtils.startColor.getRGB(), ScaleUtils.endColor.getRGB(), 20L * (long) colorI));
            angleRadians = 6.283185307179586D * (double) i / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
            ++colorI;
        }

        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = width + edgeRadius;
        centerY = height + edgeRadius;

        for (i = 0; (float) i <= height - y; ++i) {
            setColor(fadeBetween(ScaleUtils.startColor.getRGB(), ScaleUtils.endColor.getRGB(), 20L * (long) colorI));
            GL11.glVertex2d((double) centerX, (double) (centerY - (float) i));
            ++colorI;
        }

        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = width;
        centerY = y + edgeRadius;

        for (i = 0; i <= vertices; ++i) {
            setColor(fadeBetween(ScaleUtils.startColor.getRGB(), ScaleUtils.endColor.getRGB(), 20L * (long) colorI));
            angleRadians = 6.283185307179586D * (double) (i + 90) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
            ++colorI;
        }

        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = width;
        centerY = y;

        for (i = 0; (float) i <= width - x; ++i) {
            setColor(fadeBetween(ScaleUtils.startColor.getRGB(), ScaleUtils.endColor.getRGB(), 20L * (long) colorI));
            GL11.glVertex2d((double) (centerX - (float) i), (double) centerY);
            ++colorI;
        }

        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x;
        centerY = y + edgeRadius;

        for (i = 0; i <= vertices; ++i) {
            setColor(fadeBetween(ScaleUtils.startColor.getRGB(), ScaleUtils.endColor.getRGB(), 20L * (long) colorI));
            angleRadians = 6.283185307179586D * (double) (i + 180) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
            ++colorI;
        }

        colorI = 0;
        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = width;
        centerY = height + (float) vertices + offset;

        for (i = 0; (float) i <= width - x; ++i) {
            setColor(fadeBetween(ScaleUtils.startColor.getRGB(), ScaleUtils.endColor.getRGB(), 20L * (long) colorI));
            GL11.glVertex2d((double) (centerX - (float) i), (double) centerY);
            ++colorI;
        }

        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x;
        centerY = height + edgeRadius;

        for (i = 0; i <= vertices; ++i) {
            setColor(fadeBetween(ScaleUtils.startColor.getRGB(), ScaleUtils.endColor.getRGB(), 20L * (long) colorI));
            angleRadians = 6.283185307179586D * (double) (i + 180) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY - Math.cos(angleRadians) * (double) edgeRadius);
            ++colorI;
        }

        GL11.glEnd();
        GL11.glLineWidth(line);
        GL11.glBegin(3);
        centerX = x - edgeRadius;
        centerY = height + edgeRadius;

        for (i = 0; (float) i <= height - y; ++i) {
            setColor(fadeBetween(ScaleUtils.startColor.getRGB(), ScaleUtils.endColor.getRGB(), 20L * (long) colorI));
            GL11.glVertex2d((double) centerX, (double) (centerY - (float) i));
            ++colorI;
        }

        GL11.glEnd();
        disableRender2D();
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

        Color color3 = null;

        try {
            color3 = new Color(red, green, blue);
        } catch (IllegalArgumentException illegalargumentexception) {
            NumberFormat nf = NumberFormat.getNumberInstance();

            illegalargumentexception.printStackTrace();
        }

        return color3;
    }

    public static void setColor(int colorHex) {
        float alpha = (float) (colorHex >> 24 & 255) / 255.0F;
        float red = (float) (colorHex >> 16 & 255) / 255.0F;
        float green = (float) (colorHex >> 8 & 255) / 255.0F;
        float blue = (float) (colorHex & 255) / 255.0F;

        GL11.glColor4f(red, green, blue, alpha);
    }

    public static int fadeBetween(int startColour, int endColour, double progress) {
        if (progress > 1.0D) {
            progress = 1.0D - progress % 1.0D;
        }

        return fadeTo(startColour, endColour, progress);
    }

    public static int fadeBetween(int startColour, int endColour, long offset) {
        return fadeBetween(startColour, endColour, (double) ((System.currentTimeMillis() + offset) % 2000L) / 1000.0D);
    }

    public static int fadeBetween(int startColour, int endColour) {
        return fadeBetween(startColour, endColour, 0L);
    }

    public static int fadeTo(int startColour, int endColour, double progress) {
        double invert = 1.0D - progress;
        int r = (int) ((double) (startColour >> 16 & 255) * invert + (double) (endColour >> 16 & 255) * progress);
        int g = (int) ((double) (startColour >> 8 & 255) * invert + (double) (endColour >> 8 & 255) * progress);
        int b = (int) ((double) (startColour & 255) * invert + (double) (endColour & 255) * progress);
        int a = (int) ((double) (startColour >> 24 & 255) * invert + (double) (endColour >> 24 & 255) * progress);

        return (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | b & 255;
    }

    public static void enableRender2D() {
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
    }

    public static void disableRender2D() {
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    public static int[] getScaledMouseCoordinates(Minecraft mc, int mouseX, int mouseY) {
        int x = mouseX;
        int y = mouseY;

        switch (mc.gameSettings.guiScale) {
        case 0:
            x = mouseX << 1;
            y = mouseY << 1;
            break;

        case 1:
            x = (int) ((double) mouseX * 0.5D);
            y = (int) ((double) mouseY * 0.5D);

        case 2:
        default:
            break;

        case 3:
            x = (int) ((double) mouseX * 1.5D);
            y = (int) ((double) mouseY * 1.5D);
        }

        return new int[] { x, y};
    }

    public static double[] getScaledMouseCoordinates(Minecraft mc, double mouseX, double mouseY) {
        double x = mouseX;
        double y = mouseY;

        switch (mc.gameSettings.guiScale) {
        case 0:
            x = mouseX * 2.0D;
            y = mouseY * 2.0D;
            break;

        case 1:
            x = mouseX * 0.5D;
            y = mouseY * 0.5D;

        case 2:
        default:
            break;

        case 3:
            x = mouseX * 1.5D;
            y = mouseY * 1.5D;
        }

        return new double[] { x, y};
    }

    public static void scale(Minecraft mc) {
        switch (mc.gameSettings.guiScale) {
        case 0:
            GlStateManager.scale(0.5D, 0.5D, 0.5D);
            break;

        case 1:
            GlStateManager.scale(2.0F, 2.0F, 2.0F);

        case 2:
        default:
            break;

        case 3:
            GlStateManager.scale(0.6666666666666667D, 0.6666666666666667D, 0.6666666666666667D);
        }

    }
}

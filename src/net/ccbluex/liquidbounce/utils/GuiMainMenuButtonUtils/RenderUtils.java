package net.ccbluex.liquidbounce.utils.GuiMainMenuButtonUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

    public static void drawRoundedRect(double x, double y, double width, double height, double radius, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        float f = (float) (color >> 24 & 255) / 255.0F;
        float f1 = (float) (color >> 16 & 255) / 255.0F;
        float f2 = (float) (color >> 8 & 255) / 255.0F;
        float f3 = (float) (color & 255) / 255.0F;

        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2.0D;
        y *= 2.0D;
        double x1 = width * 2.0D;
        double y1 = height * 2.0D;

        GL11.glDisable(3553);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glEnable(2848);
        GL11.glBegin(9);

        int i;

        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin((double) i * 3.141592653589793D / 180.0D) * radius * -1.0D, y + radius + Math.cos((double) i * 3.141592653589793D / 180.0D) * radius * -1.0D);
        }

        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin((double) i * 3.141592653589793D / 180.0D) * radius * -1.0D, y1 - radius + Math.cos((double) i * 3.141592653589793D / 180.0D) * radius * -1.0D);
        }

        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin((double) i * 3.141592653589793D / 180.0D) * radius, y1 - radius + Math.cos((double) i * 3.141592653589793D / 180.0D) * radius);
        }

        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin((double) i * 3.141592653589793D / 180.0D) * radius, y + radius + Math.cos((double) i * 3.141592653589793D / 180.0D) * radius);
        }

        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawmage(ResourceLocation image, int x, int y, int width, int height, float alpha) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawRect(float x1, float y1, float x2, float y2, int color) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        color(color);
        GL11.glBegin(7);
        GL11.glVertex2d((double) x2, (double) y1);
        GL11.glVertex2d((double) x1, (double) y1);
        GL11.glVertex2d((double) x1, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public static void color(int color) {
        float f = (float) (color >> 24 & 255) / 255.0F;
        float f1 = (float) (color >> 16 & 255) / 255.0F;
        float f2 = (float) (color >> 8 & 255) / 255.0F;
        float f3 = (float) (color & 255) / 255.0F;

        GL11.glColor4f(f1, f2, f3, f);
    }

    public static void drawImage(int x, int y, int width, int height, ResourceLocation image) {
        new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawVLine(float x, float y, float x1, int y1) {
        if (x1 < y) {
            float f = y;

            y = x1;
            x1 = f;
        }

        drawRect2(x, y + 1.0F, x + 1.0F, x1, y1);
    }

    public static void drawHLine(float x, float y, float x1, int y1) {
        if (y < x) {
            float f = x;

            x = y;
            y = f;
        }

        drawRect2(x, x1, y + 1.0F, x1 + 1.0F, y1);
    }

    public static void drawRoundedRect(float n, float n2, float n3, float n4, int n5, int n6) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawVLine(n *= 2.0F, (n2 *= 2.0F) + 1.0F, (n4 *= 2.0F) - 2.0F, n5);
        drawVLine((n3 *= 2.0F) - 1.0F, n2 + 1.0F, n4 - 2.0F, n5);
        drawHLine(n + 2.0F, n3 - 3.0F, n2, n5);
        drawHLine(n + 2.0F, n3 - 3.0F, n4 - 1.0F, n5);
        drawHLine(n + 1.0F, n + 1.0F, n2 + 1.0F, n5);
        drawHLine(n3 - 2.0F, n3 - 2.0F, n2 + 1.0F, n5);
        drawHLine(n3 - 2.0F, n3 - 2.0F, n4 - 2.0F, n5);
        drawHLine(n + 1.0F, n + 1.0F, n4 - 2.0F, n5);
        drawRect(n + 1.0F, n2 + 1.0F, n3 - 1.0F, n4 - 1.0F, n6);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawRoundRect(float x, float y, float x1, float y1, int color) {
        drawRoundedRect(x, y, x1, y1, color, color);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
    }

    public static void drawRect2(float x1, float y1, float x2, float y2, int color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        color2(color);
        GL11.glBegin(7);
        GL11.glVertex2d((double) x2, (double) y1);
        GL11.glVertex2d((double) x1, (double) y1);
        GL11.glVertex2d((double) x1, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void color2(int color) {
        float f = (float) (color >> 24 & 255) / 255.0F;
        float f1 = (float) (color >> 16 & 255) / 255.0F;
        float f2 = (float) (color >> 8 & 255) / 255.0F;
        float f3 = (float) (color & 255) / 255.0F;

        GL11.glColor4f(f1, f2, f3, f);
    }
}

package me.cn;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import net.ccbluex.liquidbounce.api.enums.WDefaultVertexFormats;
import net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

public final class RenderUtils extends MinecraftInstance {

    private static final Map glCapMap = new HashMap();
    private static final int[] DISPLAY_LISTS_2D = new int[4];
    public static int deltaTime;
    public static float delta;

    public static void bindTexture(int texture) {
        GL11.glBindTexture(3553, texture);
    }

    public static Color getGradientOffset(Color color1, Color color2, double offset) {
        double inverse_percent;
        int redPart;

        if (offset > 1.0D) {
            inverse_percent = offset % 1.0D;
            redPart = (int) offset;
            offset = redPart % 2 == 0 ? inverse_percent : 1.0D - inverse_percent;
        }

        inverse_percent = 1.0D - offset;
        redPart = (int) ((double) color1.getRed() * inverse_percent + (double) color2.getRed() * offset);
        int greenPart = (int) ((double) color1.getGreen() * inverse_percent + (double) color2.getGreen() * offset);
        int bluePart = (int) ((double) color1.getBlue() * inverse_percent + (double) color2.getBlue() * offset);

        return new Color(redPart, greenPart, bluePart);
    }

    public static void resetColor() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static void quickPolygonCircle(float x, float y, float xRadius, float yRadius, int start, int end, int split) {
        for (int i = end; i >= start; i -= split) {
            GL11.glVertex2d((double) x + Math.sin((double) i * 3.141592653589793D / 180.0D) * (double) xRadius, (double) y + Math.cos((double) i * 3.141592653589793D / 180.0D) * (double) yRadius);
        }

    }

    public static void drawRoundedCornerRect(float x, float y, float x1, float y1, float radius) {
        GL11.glBegin(9);
        float xRadius = (float) Math.min((double) (x1 - x) * 0.5D, (double) radius);
        float yRadius = (float) Math.min((double) (y1 - y) * 0.5D, (double) radius);

        quickPolygonCircle(x + xRadius, y + yRadius, xRadius, yRadius, 180, 270, 4);
        quickPolygonCircle(x1 - xRadius, y + yRadius, xRadius, yRadius, 90, 180, 4);
        quickPolygonCircle(x1 - xRadius, y1 - yRadius, xRadius, yRadius, 0, 90, 4);
        quickPolygonCircle(x + xRadius, y1 - yRadius, xRadius, yRadius, 270, 360, 4);
        GL11.glEnd();
    }

    public static void glRestoreBlend(boolean wasEnabled) {
        if (!wasEnabled) {
            GL11.glDisable(3042);
        }

    }

    public static boolean glEnableBlend() {
        boolean wasEnabled = GL11.glIsEnabled(3042);

        if (!wasEnabled) {
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(770, 771, 1, 0);
        }

        return wasEnabled;
    }

    public static void quickRenderCircle(double x, double y, double start, double end, double w, double h) {
        double i;

        if (start > end) {
            i = end;
            end = start;
            start = i;
        }

        GL11.glBegin(6);
        GL11.glVertex2d(x, y);

        for (i = end; i >= start; i -= 4.0D) {
            double ldx = Math.cos(i * 3.141592653589793D / 180.0D) * w;
            double ldy = Math.sin(i * 3.141592653589793D / 180.0D) * h;

            GL11.glVertex2d(x + ldx, y + ldy);
        }

        GL11.glVertex2d(x, y);
        GL11.glEnd();
    }

    public static void drawHead(IResourceLocation skin, int x, int y, int width, int height) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtils.mc.getTextureManager().bindTexture(skin);
        drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
        drawScaledCustomSizeModalRect(x, y, 40.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
    }

    public static void drawOutlinedString(String str, int x, int y, int color, int color2) {
        RenderUtils.mc.getFontRendererObj().drawString(str, (int) ((float) x - 1.0F), y, color2);
        RenderUtils.mc.getFontRendererObj().drawString(str, (int) ((float) x + 1.0F), y, color2);
        RenderUtils.mc.getFontRendererObj().drawString(str, x, (int) ((float) y + 1.0F), color2);
        RenderUtils.mc.getFontRendererObj().drawString(str, x, (int) ((float) y - 1.0F), color2);
        RenderUtils.mc.getFontRendererObj().drawString(str, x, y, color);
    }

    public static void quickDrawGradientSidewaysH(double left, double top, double right, double bottom, int col1, int col2) {
        GL11.glBegin(7);
        glColor(col1);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        glColor(col2);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
    }

    public static void drawArc(float n, float n2, double n3, int n4, int n5, double n6, int n7) {
        n3 *= 2.0D;
        n *= 2.0F;
        n2 *= 2.0F;
        float n8 = (float) (n4 >> 24 & 255) / 255.0F;
        float n9 = (float) (n4 >> 16 & 255) / 255.0F;
        float n10 = (float) (n4 >> 8 & 255) / 255.0F;
        float n11 = (float) (n4 & 255) / 255.0F;

        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glLineWidth((float) n7);
        GL11.glEnable(2848);
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glBegin(3);

        for (int n12 = n5; (double) n12 <= n6; ++n12) {
            GL11.glVertex2d((double) n + Math.sin((double) n12 * 3.141592653589793D / 180.0D) * n3, (double) n2 + Math.cos((double) n12 * 3.141592653589793D / 180.0D) * n3);
        }

        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (float) (col1 >> 24 & 255) / 255.0F;
        float f1 = (float) (col1 >> 16 & 255) / 255.0F;
        float f2 = (float) (col1 >> 8 & 255) / 255.0F;
        float f3 = (float) (col1 & 255) / 255.0F;
        float f4 = (float) (col2 >> 24 & 255) / 255.0F;
        float f5 = (float) (col2 >> 16 & 255) / 255.0F;
        float f6 = (float) (col2 >> 8 & 255) / 255.0F;
        float f7 = (float) (col2 & 255) / 255.0F;

        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawRoundedRect(float nameXStart, float nameYStart, float nameXEnd, float nameYEnd, float radius, int color, boolean popPush) {
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        float z = 0.0F;

        if (nameXStart > nameXEnd) {
            z = nameXStart;
            nameXStart = nameXEnd;
            nameXEnd = z;
        }

        if (nameYStart > nameYEnd) {
            z = nameYStart;
            nameYStart = nameYEnd;
            nameYEnd = z;
        }

        double x1 = (double) (nameXStart + radius);
        double y1 = (double) (nameYStart + radius);
        double x2 = (double) (nameXEnd - radius);
        double y2 = (double) (nameYEnd - radius);

        if (popPush) {
            GL11.glPushMatrix();
        }

        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0F);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(9);
        double degree = 0.017453292519943295D;

        double i;

        for (i = 0.0D; i <= 90.0D; ++i) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * (double) radius, y2 + Math.cos(i * degree) * (double) radius);
        }

        for (i = 90.0D; i <= 180.0D; ++i) {
            GL11.glVertex2d(x2 + Math.sin(i * degree) * (double) radius, y1 + Math.cos(i * degree) * (double) radius);
        }

        for (i = 180.0D; i <= 270.0D; ++i) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * (double) radius, y1 + Math.cos(i * degree) * (double) radius);
        }

        for (i = 270.0D; i <= 360.0D; ++i) {
            GL11.glVertex2d(x1 + Math.sin(i * degree) * (double) radius, y2 + Math.cos(i * degree) * (double) radius);
        }

        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        if (popPush) {
            GL11.glPopMatrix();
        }

    }

    public static void drawTexturedRectWithCustomAlpha(float x, float y, float width, float height, String image, float alpha) {
        GL11.glPushMatrix();
        boolean enableBlend = GL11.glIsEnabled(3042);
        boolean disableAlpha = !GL11.glIsEnabled(3008);

        if (!enableBlend) {
            GL11.glEnable(3042);
        }

        if (!disableAlpha) {
            GL11.glDisable(3008);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        RenderUtils.mc.getTextureManager().bindTexture2(new ResourceLocation("loserline/shadow/" + image + ".png"));
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
        if (!enableBlend) {
            GL11.glDisable(3042);
        }

        if (!disableAlpha) {
            GL11.glEnable(3008);
        }

        GlStateManager.resetColor();
        GL11.glPopMatrix();
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        ITessellator tessellator = RenderUtils.classProvider.getTessellatorInstance();
        IWorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(7, RenderUtils.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION_TEX));
        worldrenderer.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + width) * f), (double) ((v + height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + width) * f), (double) (v * f1)).endVertex();
        worldrenderer.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator.draw();
    }

    public static void drawRoundedRect(float nameXStart, float nameYStart, float nameXEnd, float nameYEnd, float radius, int color) {
        drawRoundedRect(nameXStart, nameYStart, nameXEnd, nameYEnd, radius, color, true);
    }

    public static void drawSelectionBoundingBox(IAxisAlignedBB boundingBox) {
        ITessellator tessellator = RenderUtils.classProvider.getTessellatorInstance();
        IWorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(3, RenderUtils.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldrenderer.pos(boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMinZ()).endVertex();
        worldrenderer.pos(boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMaxZ()).endVertex();
        worldrenderer.pos(boundingBox.getMaxX(), boundingBox.getMinY(), boundingBox.getMaxZ()).endVertex();
        worldrenderer.pos(boundingBox.getMaxX(), boundingBox.getMinY(), boundingBox.getMinZ()).endVertex();
        worldrenderer.pos(boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMinZ()).endVertex();
        worldrenderer.pos(boundingBox.getMinX(), boundingBox.getMaxY(), boundingBox.getMinZ()).endVertex();
        worldrenderer.pos(boundingBox.getMinX(), boundingBox.getMaxY(), boundingBox.getMaxZ()).endVertex();
        worldrenderer.pos(boundingBox.getMaxX(), boundingBox.getMaxY(), boundingBox.getMaxZ()).endVertex();
        worldrenderer.pos(boundingBox.getMaxX(), boundingBox.getMaxY(), boundingBox.getMinZ()).endVertex();
        worldrenderer.pos(boundingBox.getMinX(), boundingBox.getMaxY(), boundingBox.getMinZ()).endVertex();
        worldrenderer.pos(boundingBox.getMinX(), boundingBox.getMaxY(), boundingBox.getMaxZ()).endVertex();
        worldrenderer.pos(boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMaxZ()).endVertex();
        worldrenderer.pos(boundingBox.getMaxX(), boundingBox.getMinY(), boundingBox.getMaxZ()).endVertex();
        worldrenderer.pos(boundingBox.getMaxX(), boundingBox.getMaxY(), boundingBox.getMaxZ()).endVertex();
        worldrenderer.pos(boundingBox.getMaxX(), boundingBox.getMaxY(), boundingBox.getMinZ()).endVertex();
        worldrenderer.pos(boundingBox.getMaxX(), boundingBox.getMinY(), boundingBox.getMinZ()).endVertex();
        tessellator.draw();
    }

    public static void glColor(Color color, float alpha) {
        float red = (float) color.getRed() / 255.0F;
        float green = (float) color.getGreen() / 255.0F;
        float blue = (float) color.getBlue() / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void drawAxisAlignedBB(IAxisAlignedBB axisAlignedBB, Color color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        glColor(color);
        drawFilledBox(axisAlignedBB);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawFilledBox(IAxisAlignedBB axisAlignedBB) {
        ITessellator tessellator = RenderUtils.classProvider.getTessellatorInstance();
        IWorldRenderer worldRenderer = tessellator.getWorldRenderer();

        worldRenderer.begin(7, RenderUtils.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMinX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMinZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY(), axisAlignedBB.getMaxZ()).endVertex();
        worldRenderer.pos(axisAlignedBB.getMaxX(), axisAlignedBB.getMinY(), axisAlignedBB.getMaxZ()).endVertex();
        tessellator.draw();
    }

    public static void quickDrawRect(float x, float y, float x2, float y2) {
        GL11.glBegin(7);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
    }

    public static void drawRect(float x, float y, float x2, float y2, int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2f(x2, y);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void drawRect(int x, int y, int x2, int y2, int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2i(x2, y);
        GL11.glVertex2i(x, y);
        GL11.glVertex2i(x, y2);
        GL11.glVertex2i(x2, y2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void quickDrawRect(float x, float y, float x2, float y2, int color) {
        glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
    }

    public static void drawRect(float x, float y, float x2, float y2, Color color) {
        drawRect(x, y, x2, y2, color.getRGB());
    }

    public static void drawBorderedRect(float x, float y, float x2, float y2, float width, int color1, int color2) {
        drawRect(x, y, x2, y2, color2);
        drawBorder(x, y, x2, y2, width, color1);
    }

    public static void drawBorder(float x, float y, float x2, float y2, float width, int color1) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        glColor(color1);
        GL11.glLineWidth(width);
        GL11.glBegin(2);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static float getAnimationState(float animation, float finalState, float speed) {
        float add = RenderUtils.delta * speed;

        if (animation < finalState) {
            if (animation + add < finalState) {
                animation += add;
            } else {
                animation = finalState;
            }
        } else if (animation - add > finalState) {
            animation -= add;
        } else {
            animation = finalState;
        }

        return animation;
    }

    public static void drawTexturedRect(int x, int y, int width, int height, String image, IScaledResolution sr) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        RenderUtils.mc.getTextureManager().bindTexture(RenderUtils.classProvider.createResourceLocation("loserline/potionrender/" + image + ".png"));
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GL11.glPopMatrix();
    }

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = 0.003921569F * (float) c.getRed();
        float g = 0.003921569F * (float) c.getGreen();
        float b = 0.003921569F * (float) c.getBlue();

        return (new Color(r, g, b, alpha)).getRGB();
    }

    public static double getAnimationStateSmooth(double target, double current, double speed) {
        boolean larger = target > current;

        if (speed < 0.0D) {
            speed = 0.0D;
        } else if (speed > 1.0D) {
            speed = 1.0D;
        }

        if (target == current) {
            return target;
        } else {
            double dif = Math.max(target, current) - Math.min(target, current);
            double factor = dif * speed;

            if (factor < 0.1D) {
                factor = 0.1D;
            }

            if (larger) {
                if (current + factor > target) {
                    current = target;
                } else {
                    current += factor;
                }
            } else if (current - factor < target) {
                current = target;
            } else {
                current -= factor;
            }

            return current;
        }
    }

    public static boolean isHovering(int mouseX, int mouseY, float xLeft, float yUp, float xRight, float yBottom) {
        return (float) mouseX > xLeft && (float) mouseX < xRight && (float) mouseY > yUp && (float) mouseY < yBottom;
    }

    public static void drawCircle2(float x, float y, float radius, int color) {
        glColor(color);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glLineWidth(1.0F);
        GL11.glBegin(9);

        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d((double) x + Math.sin((double) i * 3.141592653589793D / 180.0D) * (double) radius, (double) y + Math.cos((double) i * 3.141592653589793D / 180.0D) * (double) radius);
        }

        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawImage(IResourceLocation image, int x, int y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        GL14.glBlendFuncSeparate(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtils.mc.getTextureManager().bindTexture(image);
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        ITessellator tessellator = RenderUtils.classProvider.getTessellatorInstance();
        IWorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(7, RenderUtils.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION_TEX));
        worldrenderer.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) width) * f), (double) ((v + (float) height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) width) * f), (double) (v * f1)).endVertex();
        worldrenderer.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator.draw();
    }

    public static void glColor(int red, int green, int blue, int alpha) {
        GL11.glColor4f((float) red / 255.0F, (float) green / 255.0F, (float) blue / 255.0F, (float) alpha / 255.0F);
    }

    public static void glColor(Color color) {
        glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    private static void glColor(int hex) {
        glColor(hex >> 16 & 255, hex >> 8 & 255, hex & 255, hex >> 24 & 255);
    }

    public static void resetCaps() {
        RenderUtils.glCapMap.forEach(RenderUtils::setGlState);
    }

    public static void setGlCap(int cap, boolean state) {
        RenderUtils.glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
        setGlState(cap, state);
    }

    public static void setGlState(int cap, boolean state) {
        if (state) {
            GL11.glEnable(cap);
        } else {
            GL11.glDisable(cap);
        }

    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        ITessellator tessellator = RenderUtils.classProvider.getTessellatorInstance();
        IWorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(7, RenderUtils.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION_TEX));
        worldrenderer.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) vHeight) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) uWidth) * f), (double) ((v + (float) vHeight) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) uWidth) * f), (double) (v * f1)).endVertex();
        worldrenderer.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator.draw();
    }

    static {
        for (int i = 0; i < RenderUtils.DISPLAY_LISTS_2D.length; ++i) {
            RenderUtils.DISPLAY_LISTS_2D[i] = GL11.glGenLists(1);
        }

        GL11.glNewList(RenderUtils.DISPLAY_LISTS_2D[0], 4864);
        quickDrawRect(-7.0F, 2.0F, -4.0F, 3.0F);
        quickDrawRect(4.0F, 2.0F, 7.0F, 3.0F);
        quickDrawRect(-7.0F, 0.5F, -6.0F, 3.0F);
        quickDrawRect(6.0F, 0.5F, 7.0F, 3.0F);
        GL11.glEndList();
        GL11.glNewList(RenderUtils.DISPLAY_LISTS_2D[1], 4864);
        quickDrawRect(-7.0F, 3.0F, -4.0F, 3.3F);
        quickDrawRect(4.0F, 3.0F, 7.0F, 3.3F);
        quickDrawRect(-7.3F, 0.5F, -7.0F, 3.3F);
        quickDrawRect(7.0F, 0.5F, 7.3F, 3.3F);
        GL11.glEndList();
        GL11.glNewList(RenderUtils.DISPLAY_LISTS_2D[2], 4864);
        quickDrawRect(4.0F, -20.0F, 7.0F, -19.0F);
        quickDrawRect(-7.0F, -20.0F, -4.0F, -19.0F);
        quickDrawRect(6.0F, -20.0F, 7.0F, -17.5F);
        quickDrawRect(-7.0F, -20.0F, -6.0F, -17.5F);
        GL11.glEndList();
        GL11.glNewList(RenderUtils.DISPLAY_LISTS_2D[3], 4864);
        quickDrawRect(7.0F, -20.0F, 7.3F, -17.5F);
        quickDrawRect(-7.3F, -20.0F, -7.0F, -17.5F);
        quickDrawRect(4.0F, -20.3F, 7.3F, -20.0F);
        quickDrawRect(-7.3F, -20.3F, -4.0F, -20.0F);
        GL11.glEndList();
    }
}

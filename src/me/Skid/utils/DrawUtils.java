package me.Skid.utils;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DrawUtils extends GuiScreen {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static void setColor(int color) {
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;

        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawModalRectWithCustomSizedTexture2(float g, float h, float u, float v, double d, double e, double i, double j) {
        float f = (float) (1.0D / i);
        float f1 = (float) (1.0D / j);
        Tessellator tessellator1 = Tessellator.getInstance();
        BufferBuilder BufferBuilder = tessellator1.getBuffer();

        BufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        BufferBuilder.pos((double) g, (double) h + e, 0.0D).tex((double) (u * f), (double) ((v + (float) e) * f1)).endVertex();
        BufferBuilder.pos((double) g + d, (double) h + e, 0.0D).tex((double) ((u + (float) d) * f), (double) ((v + (float) e) * f1)).endVertex();
        BufferBuilder.pos((double) g + d, (double) h, 0.0D).tex((double) ((u + (float) d) * f), (double) (v * f1)).endVertex();
        BufferBuilder.pos((double) g, (double) h, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator1.draw();
    }

    public static void drawModalRectWithCustomSizedTexture3(double g, double h, double u, double v, double d, double e, double i, double j) {
        float f = (float) (1.0D / i);
        float f1 = (float) (1.0D / j);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(g, h + e, 0.0D).tex(u * (double) f, (v + (double) ((float) e)) * (double) f1).endVertex();
        worldrenderer.pos(g + d, h + e, 0.0D).tex((u + (double) ((float) d)) * (double) f, (v + (double) ((float) e)) * (double) f1).endVertex();
        worldrenderer.pos(g + d, h, 0.0D).tex((u + (double) ((float) d)) * (double) f, v * (double) f1).endVertex();
        worldrenderer.pos(g, h, 0.0D).tex(u * (double) f, v * (double) f1).endVertex();
        tessellator.draw();
    }

    public static boolean isHovered(double x, double y, double x1, double y1, int mouseX, int mouseY) {
        return (double) mouseX >= x && (double) mouseY >= y && (double) mouseX <= x1 && (double) mouseY <= y1;
    }

    public static boolean isHovered(int x, int y, int x1, int y1, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x1 && mouseY <= y1;
    }

    public static void drawImage(int x, int y, int width, int height, ResourceLocation image, Color color) {
        new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        setColor(color.getRGB());
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawImage(int x, int y, int width, int height, ResourceLocation image) {
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public static void drawShadowImage(int x, int y, int width, int height, ResourceLocation image) {
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        DrawUtils.mc.getTextureManager().bindTexture(image);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.7F);
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public static void drawShadowImage1(int x, int y, int width, int height, ResourceLocation image) {
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        DrawUtils.mc.getTextureManager().bindTexture(image);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.7F);
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, x - width, y - height, (float) x - (float) width, (float) y - (float) height);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public static void drawImage(double x, double y, double width, double height, ResourceLocation image) {
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        drawModalRectWithCustomSizedTexture(x, y, 0.0D, 0.0D, width, height, (double) ((float) width), (double) ((float) height));
        GL11.glDepthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public static void drawImageDiv2(float x, float y, float width, float height, ResourceLocation image) {
        width /= 2.0F;
        height /= 2.0F;
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, (double) width, (double) height, (double) width, (double) height);
        GL11.glDepthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public static void drawModalRectWithCustomSizedTexture(float g, float h, float u, float v, double d, double e, double i, double j) {
        float f = (float) (1.0D / i);
        float f1 = (float) (1.0D / j);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double) g, (double) h + e, 0.0D).tex((double) (u * f), (double) ((v + (float) e) * f1)).endVertex();
        worldrenderer.pos((double) g + d, (double) h + e, 0.0D).tex((double) ((u + (float) d) * f), (double) ((v + (float) e) * f1)).endVertex();
        worldrenderer.pos((double) g + d, (double) h, 0.0D).tex((double) ((u + (float) d) * f), (double) (v * f1)).endVertex();
        worldrenderer.pos((double) g, (double) h, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator.draw();
    }

    public static void drawModalRectWithCustomSizedTexture(double g, double h, double u, double v, double d, double e, double i, double j) {
        float f = (float) (1.0D / i);
        float f1 = (float) (1.0D / j);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(g, h + e, 0.0D).tex(u * (double) f, (v + (double) ((float) e)) * (double) f1).endVertex();
        worldrenderer.pos(g + d, h + e, 0.0D).tex((u + (double) ((float) d)) * (double) f, (v + (double) ((float) e)) * (double) f1).endVertex();
        worldrenderer.pos(g + d, h, 0.0D).tex((u + (double) ((float) d)) * (double) f, v * (double) f1).endVertex();
        worldrenderer.pos(g, h, 0.0D).tex(u * (double) f, v * (double) f1).endVertex();
        tessellator.draw();
    }

    public static void drawRect(int left, int top, int right, int bottom, int color) {
        int f3;

        if (left < right) {
            f3 = left;
            left = right;
            right = f3;
        }

        if (top < bottom) {
            f3 = top;
            top = bottom;
            bottom = f3;
        }

        float f31 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f31);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double) left, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) top, 0.0D).endVertex();
        worldrenderer.pos((double) left, (double) top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        double f3;

        if (left < right) {
            f3 = left;
            left = right;
            right = f3;
        }

        if (top < bottom) {
            f3 = top;
            top = bottom;
            bottom = f3;
        }

        float f31 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f31);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}

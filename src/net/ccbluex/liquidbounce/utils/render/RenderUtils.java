package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import me.Skid.novoline.util.PaletteHelper;
import me.Skid.utils.GaussianFilter;
import me.Skid.utils.particles.Particle;
import me.Skid.utils.particles.Vec3;
import net.ccbluex.liquidbounce.api.enums.WDefaultVertexFormats;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.api.minecraft.util.ITimer;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ESPUtils;
import net.ccbluex.liquidbounce.utils.ImageUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.util.glu.Cylinder;

public final class RenderUtils extends MinecraftInstance {

    private static final Map glCapMap = new HashMap();
    private static final HashMap shadowCache = new HashMap();
    private static final int[] DISPLAY_LISTS_2D = new int[4];
    public static int deltaTime;
    private static final Frustum frustrum;

    public static int width() {
        return (new ScaledResolution(Minecraft.getMinecraft())).getScaledWidth();
    }

    public static void drawNewRect(float left, float top, float right, float bottom, int color) {
        float f3;

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

        f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double) left, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) top, 0.0D).endVertex();
        worldrenderer.pos((double) left, (double) top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    public static void drawShadow(int x, int y, int width, int height) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        drawTexturedRect(x - 9, y - 9, 9, 9, "paneltopleft", sr);
        drawTexturedRect(x - 9, y + height, 9, 9, "panelbottomleft", sr);
        drawTexturedRect(x + width, y + height, 9, 9, "panelbottomright", sr);
        drawTexturedRect(x + width, y - 9, 9, 9, "paneltopright", sr);
        drawTexturedRect(x - 9, y, 9, height, "panelleft", sr);
        drawTexturedRect(x + width, y, 9, height, "panelright", sr);
        drawTexturedRect(x, y - 9, width, 9, "paneltop", sr);
        drawTexturedRect(x, y + height, width, 9, "panelbottom", sr);
    }

    public static void drawShadow(float x, float y, float width, float height) {
        drawTexturedRect(x - 9.0F, y - 9.0F, 9.0F, 9.0F, "paneltopleft");
        drawTexturedRect(x - 9.0F, y + height, 9.0F, 9.0F, "panelbottomleft");
        drawTexturedRect(x + width, y + height, 9.0F, 9.0F, "panelbottomright");
        drawTexturedRect(x + width, y - 9.0F, 9.0F, 9.0F, "paneltopright");
        drawTexturedRect(x - 9.0F, y, 9.0F, height, "panelleft");
        drawTexturedRect(x + width, y, 9.0F, height, "panelright");
        drawTexturedRect(x, y - 9.0F, width, 9.0F, "paneltop");
        drawTexturedRect(x, y + height, width, 9.0F, "panelbottom");
    }

    public static void drawShadowWithCustomAlpha(float x, float y, float width, float height, float alpha) {
        drawTexturedRectWithCustomAlpha(x - 9.0F, y - 9.0F, 9.0F, 9.0F, "paneltopleft", alpha);
        drawTexturedRectWithCustomAlpha(x - 9.0F, y + height, 9.0F, 9.0F, "panelbottomleft", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y + height, 9.0F, 9.0F, "panelbottomright", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y - 9.0F, 9.0F, 9.0F, "paneltopright", alpha);
        drawTexturedRectWithCustomAlpha(x - 9.0F, y, 9.0F, height, "panelleft", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y, 9.0F, height, "panelright", alpha);
        drawTexturedRectWithCustomAlpha(x, y - 9.0F, width, 9.0F, "paneltop", alpha);
        drawTexturedRectWithCustomAlpha(x, y + height, width, 9.0F, "panelbottom", alpha);
    }

    public static double getAnimationState2(double animation, double finalState, double speed) {
        float add = (float) (0.01D * speed);

        if (animation < finalState) {
            if (animation + (double) add < finalState) {
                animation += (double) add;
            } else {
                animation = finalState;
            }
        } else if (animation - (double) add > finalState) {
            animation -= (double) add;
        } else {
            animation = finalState;
        }

        return animation;
    }

    public static void rectangle(double left, double top, double right, double bottom, int color) {
        double d0;

        if (left < right) {
            d0 = left;
            left = right;
            right = d0;
        }

        if (top < bottom) {
            d0 = top;
            top = bottom;
            bottom = d0;
        }

        float f = (float) (color >> 24 & 255) / 255.0F;
        float f1 = (float) (color >> 16 & 255) / 255.0F;
        float f2 = (float) (color >> 8 & 255) / 255.0F;
        float f3 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f1, f2, f3, f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(left, bottom, 0.0D).endVertex();
        worldRenderer.pos(right, bottom, 0.0D).endVertex();
        worldRenderer.pos(right, top, 0.0D).endVertex();
        worldRenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        rectangle(x + width, y, x1 - width, y + width, borderColor);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        rectangle(x, y, x + width, y1, borderColor);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        rectangle(x1 - width, y, x1, y1, borderColor);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void autoExhibition(double x, double y, double x1, double y1, double size) {
        rectangleBordered(x, y, x1 + size, y1 + size, 0.5D, Colors.getColor(90), Colors.getColor(0));
        rectangleBordered(x + 1.0D, y + 1.0D, x1 + size - 1.0D, y1 + size - 1.0D, 1.0D, Colors.getColor(90), Colors.getColor(61));
        rectangleBordered(x + 2.5D, y + 2.5D, x1 + size - 2.5D, y1 + size - 2.5D, 0.5D, Colors.getColor(61), Colors.getColor(0));
    }

    public static void originalRoundedRect(float nameXStart, float nameYStart, float nameXEnd, float nameYEnd, float radius, int color) {
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
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        worldrenderer.begin(9, DefaultVertexFormats.POSITION);
        double degree = 0.017453292519943295D;

        double i;

        for (i = 0.0D; i <= 90.0D; ++i) {
            worldrenderer.pos(x2 + Math.sin(i * degree) * (double) radius, y2 + Math.cos(i * degree) * (double) radius, 0.0D).endVertex();
        }

        for (i = 90.0D; i <= 180.0D; ++i) {
            worldrenderer.pos(x2 + Math.sin(i * degree) * (double) radius, y1 + Math.cos(i * degree) * (double) radius, 0.0D).endVertex();
        }

        for (i = 180.0D; i <= 270.0D; ++i) {
            worldrenderer.pos(x1 + Math.sin(i * degree) * (double) radius, y1 + Math.cos(i * degree) * (double) radius, 0.0D).endVertex();
        }

        for (i = 270.0D; i <= 360.0D; ++i) {
            worldrenderer.pos(x1 + Math.sin(i * degree) * (double) radius, y2 + Math.cos(i * degree) * (double) radius, 0.0D).endVertex();
        }

        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRectPotion(float x, float y, float x2, float y2, int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void fastRoundedRect(float nameXStart, float nameYStart, float nameXEnd, float nameYEnd, float radius) {
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

        GL11.glEnable(2848);
        GL11.glLineWidth(1.0F);
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
        GL11.glDisable(2848);
    }

    public static void drawRoundRect(float x0, float y0, float x1, float y1, float radius, int color) {
        if (x0 != x1 && y0 != y1) {
            boolean Semicircle = true;
            float f = 5.0F;
            float f2 = (float) (color >> 24 & 255) / 255.0F;
            float f3 = (float) (color >> 16 & 255) / 255.0F;
            float f4 = (float) (color >> 8 & 255) / 255.0F;
            float f5 = (float) (color & 255) / 255.0F;

            GL11.glDisable(2884);
            GL11.glDisable(3553);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glColor4f(f3, f4, f5, f2);
            GL11.glBegin(5);
            GL11.glVertex2f(x0 + radius, y0);
            GL11.glVertex2f(x0 + radius, y1);
            GL11.glVertex2f(x1 - radius, y0);
            GL11.glVertex2f(x1 - radius, y1);
            GL11.glEnd();
            GL11.glBegin(5);
            GL11.glVertex2f(x0, y0 + radius);
            GL11.glVertex2f(x0 + radius, y0 + radius);
            GL11.glVertex2f(x0, y1 - radius);
            GL11.glVertex2f(x0 + radius, y1 - radius);
            GL11.glEnd();
            GL11.glBegin(5);
            GL11.glVertex2f(x1, y0 + radius);
            GL11.glVertex2f(x1 - radius, y0 + radius);
            GL11.glVertex2f(x1, y1 - radius);
            GL11.glVertex2f(x1 - radius, y1 - radius);
            GL11.glEnd();
            GL11.glBegin(6);
            float f6 = x1 - radius;
            float f7 = y0 + radius;

            GL11.glVertex2f(f6, f7);
            boolean j = false;

            float f11;
            int i;

            for (i = 0; i <= 18; ++i) {
                f11 = (float) i * 5.0F;
                GL11.glVertex2f((float) ((double) f6 + (double) radius * Math.cos(Math.toRadians((double) f11))), (float) ((double) f7 - (double) radius * Math.sin(Math.toRadians((double) f11))));
            }

            GL11.glEnd();
            GL11.glBegin(6);
            f6 = x0 + radius;
            f7 = y0 + radius;
            GL11.glVertex2f(f6, f7);

            for (i = 0; i <= 18; ++i) {
                f11 = (float) i * 5.0F;
                GL11.glVertex2f((float) ((double) f6 - (double) radius * Math.cos(Math.toRadians((double) f11))), (float) ((double) f7 - (double) radius * Math.sin(Math.toRadians((double) f11))));
            }

            GL11.glEnd();
            GL11.glBegin(6);
            f6 = x0 + radius;
            f7 = y1 - radius;
            GL11.glVertex2f(f6, f7);

            for (i = 0; i <= 18; ++i) {
                f11 = (float) i * 5.0F;
                GL11.glVertex2f((float) ((double) f6 - (double) radius * Math.cos(Math.toRadians((double) f11))), (float) ((double) f7 + (double) radius * Math.sin(Math.toRadians((double) f11))));
            }

            GL11.glEnd();
            GL11.glBegin(6);
            f6 = x1 - radius;
            f7 = y1 - radius;
            GL11.glVertex2f(f6, f7);

            for (i = 0; i <= 18; ++i) {
                f11 = (float) i * 5.0F;
                GL11.glVertex2f((float) ((double) f6 + (double) radius * Math.cos(Math.toRadians((double) f11))), (float) ((double) f7 + (double) radius * Math.sin(Math.toRadians((double) f11))));
            }

            GL11.glEnd();
            GL11.glEnable(3553);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }

    public static void drawRoundRect(float x, float y, float x1, float y1, int color) {
        drawRoundedRect(x, y, x1, y1, color, color);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
    }

    public static void drawRoundedRect1(float nameXStart, float nameYStart, float nameXEnd, float nameYEnd, float radius, int color) {
        drawRoundedRect(nameXStart, nameYStart, nameXEnd, nameYEnd, radius, color, true);
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

    public static void drawRoundRect3(float x, float y, float x1, float y1, int color) {
        drawRoundedRect2(x, y, x1, y1, color, color);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
    }

    public static void drawRoundedRect(float x, float y, float width, float height, float edgeRadius, int color, float borderWidth, int borderColor) {
        if (color == 16777215) {
            color = Color.WHITE.getRGB();
        }

        if (borderColor == 16777215) {
            borderColor = Color.WHITE.getRGB();
        }

        if (edgeRadius < 0.0F) {
            edgeRadius = 0.0F;
        }

        if (edgeRadius > width / 2.0F) {
            edgeRadius = width / 2.0F;
        }

        if (edgeRadius > height / 2.0F) {
            edgeRadius = height / 2.0F;
        }

        drawRect1((double) (x + edgeRadius), (double) (y + edgeRadius), (double) (width - edgeRadius * 2.0F), (double) (height - edgeRadius * 2.0F), color);
        drawRect1((double) (x + edgeRadius), (double) y, (double) (width - edgeRadius * 2.0F), (double) edgeRadius, color);
        drawRect1((double) (x + edgeRadius), (double) (y + height - edgeRadius), (double) (width - edgeRadius * 2.0F), (double) edgeRadius, color);
        drawRect1((double) x, (double) (y + edgeRadius), (double) edgeRadius, (double) (height - edgeRadius * 2.0F), color);
        drawRect1((double) (x + width - edgeRadius), (double) (y + edgeRadius), (double) edgeRadius, (double) (height - edgeRadius * 2.0F), color);
        enableRender2D();
        color(color);
        GL11.glBegin(6);
        float centerX = x + edgeRadius;
        float centerY = y + edgeRadius;

        GL11.glVertex2d((double) centerX, (double) centerY);
        int vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        int i;
        double angleRadians;

        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) (i + 180) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        GL11.glBegin(6);
        centerX = x + width - edgeRadius;
        centerY = y + edgeRadius;
        GL11.glVertex2d((double) centerX, (double) centerY);
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) (i + 90) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        GL11.glBegin(6);
        centerX = x + edgeRadius;
        centerY = y + height - edgeRadius;
        GL11.glVertex2d((double) centerX, (double) centerY);
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) (i + 270) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        GL11.glBegin(6);
        centerX = x + width - edgeRadius;
        centerY = y + height - edgeRadius;
        GL11.glVertex2d((double) centerX, (double) centerY);
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) i / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        color(borderColor);
        GL11.glLineWidth(borderWidth);
        GL11.glBegin(3);
        centerX = x + edgeRadius;
        centerY = y + edgeRadius;
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) (i + 180) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glVertex2d((double) (x + edgeRadius), (double) y);
        GL11.glVertex2d((double) (x + width - edgeRadius), (double) y);
        centerX = x + width - edgeRadius;
        centerY = y + edgeRadius;

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) (i + 90) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glVertex2d((double) (x + width), (double) (y + edgeRadius));
        GL11.glVertex2d((double) (x + width), (double) (y + height - edgeRadius));
        centerX = x + width - edgeRadius;
        centerY = y + height - edgeRadius;

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) i / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glVertex2d((double) (x + width - edgeRadius), (double) (y + height));
        GL11.glVertex2d((double) (x + edgeRadius), (double) (y + height));
        centerX = x + edgeRadius;
        centerY = y + height - edgeRadius;

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) (i + 270) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glVertex2d((double) x, (double) (y + height - edgeRadius));
        GL11.glVertex2d((double) x, (double) (y + edgeRadius));
        GL11.glEnd();
        disableRender2D();
    }

    public static void enableRender2D() {
        GL11.glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0F);
    }

    public static void disableRender2D() {
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    public static void drawRoundedRect(float nameXStart, float nameYStart, float nameXEnd, float nameYEnd, float radius, int color) {
        drawRoundedRect(nameXStart, nameYStart, nameXEnd, nameYEnd, radius, color, true);
    }

    public static void drawRoundedRect2(float n, float n2, float n3, float n4, int n5, int n6) {
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

    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        enableGL2D();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawVLine(x *= 2.0F, (y *= 2.0F) + 1.0F, (y1 *= 2.0F) - 2.0F, borderC);
        drawVLine((x1 *= 2.0F) - 1.0F, y + 1.0F, y1 - 2.0F, borderC);
        drawHLine(x + 2.0F, x1 - 3.0F, y, borderC);
        drawHLine(x + 2.0F, x1 - 3.0F, y1 - 1.0F, borderC);
        drawHLine(x + 1.0F, x + 1.0F, y + 1.0F, borderC);
        drawHLine(x1 - 2.0F, x1 - 2.0F, y + 1.0F, borderC);
        drawHLine(x1 - 2.0F, x1 - 2.0F, y1 - 2.0F, borderC);
        drawHLine(x + 1.0F, x + 1.0F, y1 - 2.0F, borderC);
        drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
        Gui.drawRect(0, 0, 0, 0, 0);
    }

    public static void color(int color) {
        float f = (float) (color >> 24 & 255) / 255.0F;
        float f1 = (float) (color >> 16 & 255) / 255.0F;
        float f2 = (float) (color >> 8 & 255) / 255.0F;
        float f3 = (float) (color & 255) / 255.0F;

        GL11.glColor4f(f1, f2, f3, f);
    }

    public static double getAnimationState(double n, double n2, double n3) {
        float n4 = (float) ((double) RenderUtils.deltaTime * n3);

        if (n < n2) {
            if (n + (double) n4 < n2) {
                n += (double) n4;
            } else {
                n = n2;
            }
        } else if (n - (double) n4 > n2) {
            n -= (double) n4;
        } else {
            n = n2;
        }

        return n;
    }

    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawHLine(float par1, float par2, float par3, int par4) {
        if (par2 < par1) {
            float f = par1;

            par1 = par2;
            par2 = f;
        }

        drawRect(par1, par3, par2 + 1.0F, par3 + 1.0F, par4);
    }

    public static void drawVLine(float x, float y, float x1, int y1) {
        if (x1 < y) {
            float f = y;

            y = x1;
            x1 = f;
        }

        drawRect(x, y + 1.0F, x + 1.0F, x1, y1);
    }

    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static int height() {
        return (new ScaledResolution(Minecraft.getMinecraft())).getScaledHeight();
    }

    public static void drawFilledCircle1(int xx, int yy, float radius, int col) {
        float f = (float) (col >> 24 & 255) / 255.0F;
        float f1 = (float) (col >> 16 & 255) / 255.0F;
        float f2 = (float) (col >> 8 & 255) / 255.0F;
        float f3 = (float) (col & 255) / 255.0F;
        byte sections = 50;
        double dAngle = 6.283185307179586D / (double) sections;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(6);

        for (int i = 0; i < sections; ++i) {
            float x = (float) ((double) radius * Math.sin((double) i * dAngle));
            float y = (float) ((double) radius * Math.cos((double) i * dAngle));

            GL11.glColor4f(f1, f2, f3, f);
            GL11.glVertex2f((float) xx + x, (float) yy + y);
        }

        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawFilledCircle1(float xx, float yy, float radius, int col) {
        float f = (float) (col >> 24 & 255) / 255.0F;
        float f1 = (float) (col >> 16 & 255) / 255.0F;
        float f2 = (float) (col >> 8 & 255) / 255.0F;
        float f3 = (float) (col & 255) / 255.0F;
        byte sections = 50;
        double dAngle = 6.283185307179586D / (double) sections;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);

        for (int i = 0; i < sections; ++i) {
            float x = (float) ((double) radius * Math.sin((double) i * dAngle));
            float y = (float) ((double) radius * Math.cos((double) i * dAngle));

            GL11.glColor4f(f1, f2, f3, f);
            GL11.glVertex2f(xx + x, yy + y);
        }

        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawFilledCircle1(int xx, int yy, float radius, Color color) {
        byte sections = 50;
        double dAngle = 6.283185307179586D / (double) sections;

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);

        for (int i = 0; i < sections; ++i) {
            float x = (float) ((double) radius * Math.sin((double) i * dAngle));
            float y = (float) ((double) radius * Math.cos((double) i * dAngle));

            GL11.glColor4f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F);
            GL11.glVertex2f((float) xx + x, (float) yy + y);
        }

        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void drawCustomImage(int x, int y, int width, int height, ResourceLocation image) {
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
        RenderUtils.mc.getTextureManager().bindTexture2(new ResourceLocation("langya/shadow/" + image + ".png"));
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

    public static void stopDrawing() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawTexturedRect(float x, float y, float width, float height, String image) {
        GL11.glPushMatrix();
        boolean enableBlend = GL11.glIsEnabled(3042);
        boolean disableAlpha = !GL11.glIsEnabled(3008);

        if (!enableBlend) {
            GL11.glEnable(3042);
        }

        if (!disableAlpha) {
            GL11.glDisable(3008);
        }

        RenderUtils.mc.getTextureManager().bindTexture2(new ResourceLocation("langya/shadow/" + image + ".png"));
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
        if (!enableBlend) {
            GL11.glDisable(3042);
        }

        if (!disableAlpha) {
            GL11.glEnable(3008);
        }

        GL11.glPopMatrix();
    }

    public static void drawEntityKillAuraESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static int getRainbowOpaque(int seconds, float saturation, float brightness, int index) {
        float hue = (float) ((System.currentTimeMillis() + (long) index) % (long) (seconds * 1000)) / (float) (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);

        return color;
    }

    public static int SkyRainbow(int i, float st, float bright) {
        double v1 = Math.ceil((double) (System.currentTimeMillis() + (long) (i * 109))) / 5.0D;

        return Color.getHSBColor((double) ((float) ((v1 %= 360.0D) / 360.0D)) < 0.5D ? -((float) (v1 / 360.0D)) : (float) (v1 / 360.0D), st, bright).getRGB();
    }

    public static void drawTexturedRect(int x, int y, int width, int height, String image, ScaledResolution sr) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        RenderUtils.mc.getTextureManager().bindTexture2(new ResourceLocation("langya/shadow/" + image + ".png"));
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GL11.glPopMatrix();
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

    public static int Astolfo(int i, float bright, float st, int index, int offset, float client) {
        double rainbowDelay = Math.ceil((double) (System.currentTimeMillis() + (long) (i * index))) / (double) offset;

        return Color.getHSBColor((double) ((float) ((rainbowDelay %= (double) client) / (double) client)) < 0.5D ? -((float) (rainbowDelay / (double) client)) : (float) (rainbowDelay / (double) client), st, bright).getRGB();
    }

    public static int getRainbow(int index, int offset, float bright, float st) {
        float hue = (float) ((System.currentTimeMillis() + (long) offset * (long) index) % 2000L);

        return Color.getHSBColor(hue /= 2000.0F, st, bright).getRGB();
    }

    public static void drawGradientSidewaysH(double left, double top, double right, double bottom, int col1, int col2) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        quickDrawGradientSidewaysH(left, top, right, bottom, col1, col2);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
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

    public static Color reAlpha(Color cIn, float alpha) {
        return new Color((float) cIn.getRed() / 255.0F, (float) cIn.getGreen() / 255.0F, (float) cIn.getBlue() / 255.0F, (float) cIn.getAlpha() / 255.0F * alpha);
    }

    public static int reAlpha(int n, float n2) {
        Color color = new Color(n);

        return (new Color(0.003921569F * (float) color.getRed(), 0.003921569F * (float) color.getGreen(), 0.003921569F * (float) color.getBlue(), n2)).getRGB();
    }

    public static void drawGradientSidewaysV(double left, double top, double right, double bottom, int col1, int col2) {
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
        GL11.glVertex2d(left, bottom);
        GL11.glVertex2d(right, bottom);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(right, top);
        GL11.glVertex2d(left, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        Gui.drawRect(0, 0, 0, 0, 0);
    }

    public static void quickDrawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (float) (col1 >> 24 & 255) / 255.0F;
        float f1 = (float) (col1 >> 16 & 255) / 255.0F;
        float f2 = (float) (col1 >> 8 & 255) / 255.0F;
        float f3 = (float) (col1 & 255) / 255.0F;
        float f4 = (float) (col2 >> 24 & 255) / 255.0F;
        float f5 = (float) (col2 >> 16 & 255) / 255.0F;
        float f6 = (float) (col2 >> 8 & 255) / 255.0F;
        float f7 = (float) (col2 & 255) / 255.0F;

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
    }

    public static void drawGradientSideways2(double left, double top, double right, double bottom, int col1, int col2) {
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

    public static void drawBlockBox(WBlockPos blockPos, Color color, boolean outline) {
        IRenderManager renderManager = RenderUtils.mc.getRenderManager();
        ITimer timer = RenderUtils.mc.getTimer();
        double x = (double) blockPos.getX() - renderManager.getRenderPosX();
        double y = (double) blockPos.getY() - renderManager.getRenderPosY();
        double z = (double) blockPos.getZ() - renderManager.getRenderPosZ();
        IAxisAlignedBB axisAlignedBB = RenderUtils.classProvider.createAxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);
        IBlock block = BlockUtils.getBlock(blockPos);

        if (block != null) {
            IEntityPlayerSP player = RenderUtils.mc.getThePlayer();
            double posX = player.getLastTickPosX() + (player.getPosX() - player.getLastTickPosX()) * (double) timer.getRenderPartialTicks();
            double posY = player.getLastTickPosY() + (player.getPosY() - player.getLastTickPosY()) * (double) timer.getRenderPartialTicks();
            double posZ = player.getLastTickPosZ() + (player.getPosZ() - player.getLastTickPosZ()) * (double) timer.getRenderPartialTicks();

            axisAlignedBB = block.getSelectedBoundingBox(RenderUtils.mc.getTheWorld(), RenderUtils.mc.getTheWorld().getBlockState(blockPos), blockPos).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-posX, -posY, -posZ);
        }

        GL11.glBlendFunc(770, 771);
        enableGlCap(3042);
        disableGlCap(new int[] { 3553, 2929});
        GL11.glDepthMask(false);
        glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : (outline ? 26 : 35));
        drawFilledBox(axisAlignedBB);
        if (outline) {
            GL11.glLineWidth(1.0F);
            enableGlCap(2848);
            glColor(color);
            drawSelectionBoundingBox(axisAlignedBB);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthMask(true);
        resetCaps();
    }

    public static void drawImage2(ResourceLocation image, int x, int y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtils.mc.getTextureManager().bindTexture2(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
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

    public static void drawImage3(ResourceLocation image, int x, int y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtils.mc.getTextureManager().bindTexture2(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void glColor1(int color) {}

    public static void enableSmoothLine(float width) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(width);
    }

    public static void disableSmoothLine() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawRoundedCornerRect(float x, float y, float x1, float y1, float radius, int color) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        boolean hasCull = GL11.glIsEnabled(2884);

        GL11.glDisable(2884);
        glColor(color);
        drawRoundedCornerRect(x, y, x1, y1, radius);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        setGlState(2884, hasCull);
    }

    private static void quickPolygonCircle(float x, float y, float xRadius, float yRadius, int start, int end) {
        for (int i = end; i >= start; i -= 4) {
            GL11.glVertex2d((double) x + Math.sin((double) i * 3.141592653589793D / 180.0D) * (double) xRadius, (double) y + Math.cos((double) i * 3.141592653589793D / 180.0D) * (double) yRadius);
        }

    }

    public static void drawRoundedCornerRect(float x, float y, float x1, float y1, float radius) {
        GL11.glBegin(9);
        float xRadius = (float) Math.min((double) (x1 - x) * 0.5D, (double) radius);
        float yRadius = (float) Math.min((double) (y1 - y) * 0.5D, (double) radius);

        quickPolygonCircle(x + xRadius, y + yRadius, xRadius, yRadius, 180, 270);
        quickPolygonCircle(x1 - xRadius, y + yRadius, xRadius, yRadius, 90, 180);
        quickPolygonCircle(x1 - xRadius, y1 - yRadius, xRadius, yRadius, 0, 90);
        quickPolygonCircle(x + xRadius, y1 - yRadius, xRadius, yRadius, 270, 360);
        GL11.glEnd();
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtils.mc.getTextureManager().bindTexture2(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawEntityOnScreen(int posX, int posY, int scale, IEntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, 50.0F);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.getRenderYawOffset();
        float f1 = ent.getRotationYaw();
        float f2 = ent.getRotationPitch();
        float f3 = ent.getPrevRotationYawHead();
        float f4 = ent.getRotationYawHead();

        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
        f = (float) (10 * ent.getTicksExisted() % 360);
        f1 = (float) (10 * ent.getTicksExisted() % 360);
        f2 = 0.0F;
        f4 = ent.getRotationYaw();
        f3 = ent.getRotationYaw();
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();

        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity((Entity) ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, true);
        rendermanager.setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static void doGlScissor(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.getMinecraft();
        int scaleFactor = 1;
        int k = mc.gameSettings.guiScale;

        if (k == 0) {
            k = 1000;
        }

        while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }

        GL11.glScissor(x * scaleFactor, mc.displayHeight - (y + height) * scaleFactor, width * scaleFactor, height * scaleFactor);
    }

    public static void drawBlurredShadow(float x, float y, float width, float height, int blurRadius, Color color) {
        GL11.glPushMatrix();
        GlStateManager.alphaFunc(516, 0.01F);
        width += (float) (blurRadius * 2);
        height += (float) (blurRadius * 2);
        x -= (float) blurRadius;
        y -= (float) blurRadius;
        float _X = x - 0.25F;
        float _Y = y + 0.25F;
        int identifier = (int) (width * height + width + (float) (color.hashCode() * blurRadius) + (float) blurRadius);

        GL11.glEnable(3553);
        GL11.glDisable(2884);
        GL11.glEnable(3008);
        GlStateManager.enableBlend();
        boolean texId = true;
        int texId1;

        if (RenderUtils.shadowCache.containsKey(Integer.valueOf(identifier))) {
            texId1 = ((Integer) RenderUtils.shadowCache.get(Integer.valueOf(identifier))).intValue();
            GlStateManager.bindTexture(texId1);
        } else {
            if (width <= 0.0F) {
                width = 1.0F;
            }

            if (height <= 0.0F) {
                height = 1.0F;
            }

            BufferedImage original = new BufferedImage((int) width, (int) height, 3);
            Graphics g = original.getGraphics();

            g.setColor(color);
            g.fillRect(blurRadius, blurRadius, (int) (width - (float) (blurRadius * 2)), (int) (height - (float) (blurRadius * 2)));
            g.dispose();
            GaussianFilter op = new GaussianFilter((float) blurRadius);
            BufferedImage blurred = op.filter(original, (BufferedImage) null);

            texId1 = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);
            RenderUtils.shadowCache.put(Integer.valueOf(identifier), Integer.valueOf(texId1));
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex2f(_X, _Y);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex2f(_X, _Y + height);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex2f(_X + width, _Y + height);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex2f(_X + width, _Y);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        GL11.glEnable(2884);
        GL11.glPopMatrix();
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

    public static void drawFastRoundedRect(float x0, float y0, float x1, float y1, float radius, int color) {
        boolean Semicircle = true;
        float f = 5.0F;
        float f2 = (float) (color >> 24 & 255) / 255.0F;
        float f3 = (float) (color >> 16 & 255) / 255.0F;
        float f4 = (float) (color >> 8 & 255) / 255.0F;
        float f5 = (float) (color & 255) / 255.0F;

        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f3, f4, f5, f2);
        GL11.glBegin(5);
        GL11.glVertex2f(x0 + radius, y0);
        GL11.glVertex2f(x0 + radius, y1);
        GL11.glVertex2f(x1 - radius, y0);
        GL11.glVertex2f(x1 - radius, y1);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(x0, y0 + radius);
        GL11.glVertex2f(x0 + radius, y0 + radius);
        GL11.glVertex2f(x0, y1 - radius);
        GL11.glVertex2f(x0 + radius, y1 - radius);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(x1, y0 + radius);
        GL11.glVertex2f(x1 - radius, y0 + radius);
        GL11.glVertex2f(x1, y1 - radius);
        GL11.glVertex2f(x1 - radius, y1 - radius);
        GL11.glEnd();
        GL11.glBegin(6);
        float f6 = x1 - radius;
        float f7 = y0 + radius;

        GL11.glVertex2f(f6, f7);
        boolean j = false;

        float f11;
        int i;

        for (i = 0; i <= 18; ++i) {
            f11 = (float) i * 5.0F;
            GL11.glVertex2f((float) ((double) f6 + (double) radius * Math.cos(Math.toRadians((double) f11))), (float) ((double) f7 - (double) radius * Math.sin(Math.toRadians((double) f11))));
        }

        GL11.glEnd();
        GL11.glBegin(6);
        f6 = x0 + radius;
        f7 = y0 + radius;
        GL11.glVertex2f(f6, f7);

        for (i = 0; i <= 18; ++i) {
            f11 = (float) i * 5.0F;
            GL11.glVertex2f((float) ((double) f6 - (double) radius * Math.cos(Math.toRadians((double) f11))), (float) ((double) f7 - (double) radius * Math.sin(Math.toRadians((double) f11))));
        }

        GL11.glEnd();
        GL11.glBegin(6);
        f6 = x0 + radius;
        f7 = y1 - radius;
        GL11.glVertex2f(f6, f7);

        for (i = 0; i <= 18; ++i) {
            f11 = (float) i * 5.0F;
            GL11.glVertex2f((float) ((double) f6 - (double) radius * Math.cos(Math.toRadians((double) f11))), (float) ((double) f7 + (double) radius * Math.sin(Math.toRadians((double) f11))));
        }

        GL11.glEnd();
        GL11.glBegin(6);
        f6 = x1 - radius;
        f7 = y1 - radius;
        GL11.glVertex2f(f6, f7);

        for (i = 0; i <= 18; ++i) {
            f11 = (float) i * 5.0F;
            GL11.glVertex2f((float) ((double) f6 + (double) radius * Math.cos(Math.toRadians((double) f11))), (float) ((double) f7 + (double) radius * Math.sin(Math.toRadians((double) f11))));
        }

        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawEntityBox(IEntity entity, Color color, boolean outline) {
        IRenderManager renderManager = RenderUtils.mc.getRenderManager();
        ITimer timer = RenderUtils.mc.getTimer();

        GL11.glBlendFunc(770, 771);
        enableGlCap(3042);
        disableGlCap(new int[] { 3553, 2929});
        GL11.glDepthMask(false);
        double x = entity.getLastTickPosX() + (entity.getPosX() - entity.getLastTickPosX()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosX();
        double y = entity.getLastTickPosY() + (entity.getPosY() - entity.getLastTickPosY()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosY();
        double z = entity.getLastTickPosZ() + (entity.getPosZ() - entity.getLastTickPosZ()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosZ();
        IAxisAlignedBB entityBox = entity.getEntityBoundingBox();
        IAxisAlignedBB axisAlignedBB = RenderUtils.classProvider.createAxisAlignedBB(entityBox.getMinX() - entity.getPosX() + x - 0.05D, entityBox.getMinY() - entity.getPosY() + y, entityBox.getMinZ() - entity.getPosZ() + z - 0.05D, entityBox.getMaxX() - entity.getPosX() + x + 0.05D, entityBox.getMaxY() - entity.getPosY() + y + 0.15D, entityBox.getMaxZ() - entity.getPosZ() + z + 0.05D);

        if (outline) {
            GL11.glLineWidth(1.0F);
            enableGlCap(2848);
            glColor(color.getRed(), color.getGreen(), color.getBlue(), 95);
            drawSelectionBoundingBox(axisAlignedBB);
        }

        glColor(color.getRed(), color.getGreen(), color.getBlue(), outline ? 26 : 35);
        drawFilledBox(axisAlignedBB);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthMask(true);
        resetCaps();
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

    public static void drawPlatform(double y, Color color, double size) {
        IRenderManager renderManager = RenderUtils.mc.getRenderManager();
        double renderY = y - renderManager.getRenderPosY();

        drawAxisAlignedBB(RenderUtils.classProvider.createAxisAlignedBB(size, renderY + 0.02D, size, -size, renderY, -size), color);
    }

    public static void drawPlatform(IEntity entity, Color color) {
        IRenderManager renderManager = RenderUtils.mc.getRenderManager();
        ITimer timer = RenderUtils.mc.getTimer();
        double x = entity.getLastTickPosX() + (entity.getPosX() - entity.getLastTickPosX()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosX();
        double y = entity.getLastTickPosY() + (entity.getPosY() - entity.getLastTickPosY()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosY();
        double z = entity.getLastTickPosZ() + (entity.getPosZ() - entity.getLastTickPosZ()) * (double) timer.getRenderPartialTicks() - renderManager.getRenderPosZ();
        IAxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().offset(-entity.getPosX(), -entity.getPosY(), -entity.getPosZ()).offset(x, y, z);

        drawAxisAlignedBB(RenderUtils.classProvider.createAxisAlignedBB(axisAlignedBB.getMinX(), axisAlignedBB.getMaxY() + 0.2D, axisAlignedBB.getMinZ(), axisAlignedBB.getMaxX(), axisAlignedBB.getMaxY() + 0.26D, axisAlignedBB.getMaxZ()), color);
    }

    public static void drawTriAngle(float cx, float cy, float r, float n, Color color, boolean polygon) {
        cx = (float) ((double) cx * 2.0D);
        cy = (float) ((double) cy * 2.0D);
        double b = 6.2831852D / (double) n;
        double p = Math.cos(b);
        double s = Math.sin(b);

        r = (float) ((double) r * 2.0D);
        double x = (double) r;
        double y = 0.0D;
        Tessellator tessellator1 = Tessellator.getInstance();
        BufferBuilder BufferBuilder = tessellator1.getBuffer();

        GL11.glLineWidth(1.0F);
        enableGlCap(2848);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.resetColor();
        glColor(color);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        BufferBuilder.begin(polygon ? 9 : 2, DefaultVertexFormats.POSITION);

        for (int ii = 0; (float) ii < n; ++ii) {
            BufferBuilder.pos(x + (double) cx, y + (double) cy, 0.0D).endVertex();
            double t = x;

            x = p * x - s * y;
            y = s * t + p * y;
        }

        tessellator1.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void customRounded(float nameXStart, float nameYStart, float nameXEnd, float nameYEnd, float rTL, float rTR, float rBR, float rBL, int color) {
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

        double xTL = (double) (nameXStart + rTL);
        double yTL = (double) (nameYStart + rTL);
        double xTR = (double) (nameXEnd - rTR);
        double yTR = (double) (nameYStart + rTR);
        double xBR = (double) (nameXEnd - rBR);
        double yBR = (double) (nameYEnd - rBR);
        double xBL = (double) (nameXStart + rBL);
        double yBL = (double) (nameYEnd - rBL);

        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0F);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(9);
        double degree = 0.017453292519943295D;
        double i;

        if (rBR <= 0.0F) {
            GL11.glVertex2d(xBR, yBR);
        } else {
            for (i = 0.0D; i <= 90.0D; ++i) {
                GL11.glVertex2d(xBR + Math.sin(i * degree) * (double) rBR, yBR + Math.cos(i * degree) * (double) rBR);
            }
        }

        if (rTR <= 0.0F) {
            GL11.glVertex2d(xTR, yTR);
        } else {
            for (i = 90.0D; i <= 180.0D; ++i) {
                GL11.glVertex2d(xTR + Math.sin(i * degree) * (double) rTR, yTR + Math.cos(i * degree) * (double) rTR);
            }
        }

        if (rTL <= 0.0F) {
            GL11.glVertex2d(xTL, yTL);
        } else {
            for (i = 180.0D; i <= 270.0D; ++i) {
                GL11.glVertex2d(xTL + Math.sin(i * degree) * (double) rTL, yTL + Math.cos(i * degree) * (double) rTL);
            }
        }

        if (rBL <= 0.0F) {
            GL11.glVertex2d(xBL, yBL);
        } else {
            for (i = 270.0D; i <= 360.0D; ++i) {
                GL11.glVertex2d(xBL + Math.sin(i * degree) * (double) rBL, yBL + Math.cos(i * degree) * (double) rBL);
            }
        }

        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void newDrawRect(float left, float top, float right, float bottom, int color) {
        float f3;

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

        f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator1 = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator1.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double) left, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) top, 0.0D).endVertex();
        worldrenderer.pos((double) left, (double) top, 0.0D).endVertex();
        tessellator1.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRectBasedBorder(float x, float y, float x2, float y2, float width, int color1) {
        drawRect(x - width / 2.0F, y - width / 2.0F, x2 + width / 2.0F, y + width / 2.0F, color1);
        drawRect(x - width / 2.0F, y + width / 2.0F, x + width / 2.0F, y2 + width / 2.0F, color1);
        drawRect(x2 - width / 2.0F, y + width / 2.0F, x2 + width / 2.0F, y2 + width / 2.0F, color1);
        drawRect(x + width / 2.0F, y2 - width / 2.0F, x2 - width / 2.0F, y2 + width / 2.0F, color1);
    }

    private static float drawExhiOutlined(String text, float x, float y, float borderWidth, int borderColor, int mainColor, boolean drawText) {
        Fonts.fontTahomaSmall.drawString(text, x, y - borderWidth, borderColor);
        Fonts.fontTahomaSmall.drawString(text, x, y + borderWidth, borderColor);
        Fonts.fontTahomaSmall.drawString(text, x - borderWidth, y, borderColor);
        Fonts.fontTahomaSmall.drawString(text, x + borderWidth, y, borderColor);
        if (drawText) {
            Fonts.fontTahomaSmall.drawString(text, x, y, mainColor);
        }

        return x + (float) Fonts.fontTahomaSmall.getStringWidth(text) - 2.0F;
    }

    private static int getBorderColor(int level) {
        return level == 2 ? 1884684117 : (level == 3 ? 1879091882 : (level == 4 ? 1890189312 : (level >= 5 ? 1895803392 : 1895825407)));
    }

    private static int getMainColor(int level) {
        return level == 4 ? -5636096 : -1;
    }

    public static void drawRectBasedBorder(double x, double y, double x2, double y2, double width, int color1) {
        newDrawRect(x - width / 2.0D, y - width / 2.0D, x2 + width / 2.0D, y + width / 2.0D, color1);
        newDrawRect(x - width / 2.0D, y + width / 2.0D, x + width / 2.0D, y2 + width / 2.0D, color1);
        newDrawRect(x2 - width / 2.0D, y + width / 2.0D, x2 + width / 2.0D, y2 + width / 2.0D, color1);
        newDrawRect(x + width / 2.0D, y2 - width / 2.0D, x2 - width / 2.0D, y2 + width / 2.0D, color1);
    }

    public static void glColor2(int cl) {
        float alpha = (float) (cl >> 24 & 255) / 255.0F;
        float red = (float) (cl >> 16 & 255) / 255.0F;
        float green = (float) (cl >> 8 & 255) / 255.0F;
        float blue = (float) (cl & 255) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void newDrawRect(double left, double top, double right, double bottom, int color) {
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
        Tessellator tessellator1 = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator1.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f31);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator1.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawExhiRect(float x, float y, float x2, float y2) {
        drawRect(x - 3.5F, y - 3.5F, x2 + 3.5F, y2 + 3.5F, Color.black.getRGB());
        drawRect(x - 3.0F, y - 3.0F, x2 + 3.0F, y2 + 3.0F, (new Color(50, 50, 50)).getRGB());
        drawRect(x - 2.5F, y - 2.5F, x2 + 2.5F, y2 + 2.5F, (new Color(26, 26, 26)).getRGB());
        drawRect(x - 0.5F, y - 0.5F, x2 + 0.5F, y2 + 0.5F, (new Color(50, 50, 50)).getRGB());
        drawRect(x, y, x2, y2, (new Color(18, 18, 18)).getRGB());
    }

    public static void drawExhiRect(float x, float y, float x2, float y2, float alpha) {
        drawRect(x - 3.5F, y - 3.5F, x2 + 3.5F, y2 + 3.5F, (new Color(0.0F, 0.0F, 0.0F, alpha)).getRGB());
        drawRect(x - 3.0F, y - 3.0F, x2 + 3.0F, y2 + 3.0F, (new Color(0.19607843F, 0.19607843F, 0.19607843F, alpha)).getRGB());
        drawRect(x - 2.5F, y - 2.5F, x2 + 2.5F, y2 + 2.5F, (new Color(0.101960786F, 0.101960786F, 0.101960786F, alpha)).getRGB());
        drawRect(x - 0.5F, y - 0.5F, x2 + 0.5F, y2 + 0.5F, (new Color(0.19607843F, 0.19607843F, 0.19607843F, alpha)).getRGB());
        drawRect(x, y, x2, y2, (new Color(0.07058824F, 0.07058824F, 0.07058824F, alpha)).getRGB());
    }

    public static void renderParticles(List particles) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        int i = 0;

        try {
            Iterator iterator = particles.iterator();

            while (iterator.hasNext()) {
                Particle particle = (Particle) iterator.next();

                ++i;
                Vec3 v = particle.position;
                boolean draw = true;
                double x = v.xCoord - RenderUtils.mc.getRenderManager().getRenderPosX();
                double y = v.yCoord - RenderUtils.mc.getRenderManager().getRenderPosY();
                double z = v.zCoord - RenderUtils.mc.getRenderManager().getRenderPosZ();
                double distanceFromPlayer = RenderUtils.mc.getThePlayer().getDistance(v.xCoord, v.yCoord - 1.0D, v.zCoord);
                int quality = (int) (distanceFromPlayer * 4.0D + 10.0D);

                if (quality > 350) {
                    quality = 350;
                }

                if (!isInViewFrustrum((Entity) (new EntityEgg(RenderUtils.mc2.world, v.xCoord, v.yCoord, v.zCoord)))) {
                    draw = false;
                }

                if (i % 10 != 0 && distanceFromPlayer > 25.0D) {
                    draw = false;
                }

                if (i % 3 == 0 && distanceFromPlayer > 15.0D) {
                    draw = false;
                }

                if (draw) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(x, y, z);
                    float scale = 0.04F;

                    GL11.glScalef(-0.04F, -0.04F, -0.04F);
                    GL11.glRotated((double) (-RenderUtils.mc.getRenderManager().getPlayerViewY()), 0.0D, 1.0D, 0.0D);
                    GL11.glRotated((double) RenderUtils.mc.getRenderManager().getPlayerViewX(), 1.0D, 0.0D, 0.0D);
                    Color c = new Color(ColorUtils.INSTANCE.getColor(-9.5F, 0.7F, 1.0F));

                    drawFilledCircleNoGL(0, 0, 0.7D, c.hashCode(), quality);
                    if (distanceFromPlayer < 4.0D) {
                        drawFilledCircleNoGL(0, 0, 1.4D, (new Color(c.getRed(), c.getGreen(), c.getBlue(), 50)).hashCode(), quality);
                    }

                    if (distanceFromPlayer < 20.0D) {
                        drawFilledCircleNoGL(0, 0, 2.3D, (new Color(c.getRed(), c.getGreen(), c.getBlue(), 30)).hashCode(), quality);
                    }

                    GL11.glScalef(0.8F, 0.8F, 0.8F);
                    GL11.glPopMatrix();
                }
            }
        } catch (ConcurrentModificationException concurrentmodificationexception) {
            ;
        }

        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glColor3d(255.0D, 255.0D, 255.0D);
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return isInViewFrustrum(entity.getEntityBoundingBox());
    }

    private static boolean isInViewFrustrum(AxisAlignedBB bb) {
        IEntity current = RenderUtils.mc.getRenderViewEntity();

        RenderUtils.frustrum.setPosition(current.getPosX(), current.getPosY(), current.getPosZ());
        return RenderUtils.frustrum.isBoundingBoxInFrustum(bb);
    }

    public static void drawShadowImage(int x, int y, int width, int height, ResourceLocation image) {
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtils.mc.getTextureManager().bindTexture2(image);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.7F);
        drawModalRectWithCustomSizedTexture((float) x, (float) y, 0.0F, 0.0F, (float) width, (float) height, (float) width, (float) height);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public static void drawFilledCircleNoGL(int x, int y, double r, int c, int quality) {
        float f = (float) (c >> 24 & 255) / 255.0F;
        float f1 = (float) (c >> 16 & 255) / 255.0F;
        float f2 = (float) (c >> 8 & 255) / 255.0F;
        float f3 = (float) (c & 255) / 255.0F;

        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(6);

        for (int i = 0; i <= 360 / quality; ++i) {
            double x2 = Math.sin((double) (i * quality) * 3.141592653589793D / 180.0D) * r;
            double y2 = Math.cos((double) (i * quality) * 3.141592653589793D / 180.0D) * r;

            GL11.glVertex2d((double) x + x2, (double) y + y2);
        }

        GL11.glEnd();
    }

    public static void drawImage3(ResourceLocation image, float x, float y, int width, int height, float r, float g, float b, float al) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(r, g, b, al);
        GL11.glTranslatef(x, y, x);
        RenderUtils.mc.getTextureManager().bindTexture2(image);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glTranslatef(-x, -y, -x);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawImage3(ResourceLocation image, int x, int y, int width, int height, float r, float g, float b, float al) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(r, g, b, al);
        GL11.glTranslatef((float) x, (float) y, (float) x);
        RenderUtils.mc.getTextureManager().bindTexture2(image);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glTranslatef((float) (-x), (float) (-y), (float) (-x));
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawImage2(ResourceLocation image, float x, float y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef(x, y, x);
        RenderUtils.mc.getTextureManager().bindTexture2(image);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glTranslatef(-x, -y, -x);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawFilledCircle2(IEntity entity, Color color) {
        byte sections = 50;
        double dAngle = 6.283185307179586D / (double) sections;

        GL11.glPushAttrib(8192);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);

        for (int i = 0; i < sections; ++i) {
            ;
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static void drawLimitedCircle(float lx, float ly, float x2, float y2, int xx, int yy, float radius, Color color) {
        byte sections = 50;
        double dAngle = 6.283185307179586D / (double) sections;

        GL11.glPushAttrib(8192);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);

        for (int i = 0; i < sections; ++i) {
            float x = (float) ((double) radius * Math.sin((double) i * dAngle));
            float y = (float) ((double) radius * Math.cos((double) i * dAngle));

            GL11.glColor4f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F);
            GL11.glVertex2f(Math.min(x2, Math.max((float) xx + x, lx)), Math.min(y2, Math.max((float) yy + y, ly)));
        }

        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static void drawCircle(double x, double y, double radius, float startAngle, float endAngle, int color, float lineWidth) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);

        for (int i = (int) ((double) startAngle / 360.0D * 100.0D); i <= (int) ((double) endAngle / 360.0D * 100.0D); ++i) {
            double angle = 6.283185307179586D * (double) i / 100.0D + Math.toRadians(180.0D);

            if (color == 1337) {
                color(PaletteHelper.astolfoColors(i * 5, 1));
            } else {
                color(color);
            }

            GL11.glVertex2d(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius);
        }

        GL11.glEnd();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GL11.glDisable(2848);
        GlStateManager.popMatrix();
        GlStateManager.resetColor();
    }

    public static void drawCircle(float x, float y, float radius, int color) {
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;

        GL11.glColor4f(red, green, blue, alpha);
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
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawRect(double x, double y, double x2, double y2, int color) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawRect1(x, y, x2, y2, color);
    }

    public static void drawRect1(double left, double top, double right, double bottom, int color) {
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
        Tessellator tessellator1 = Tessellator.getInstance();
        BufferBuilder BufferBuilder = tessellator1.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f31);
        BufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        BufferBuilder.pos(left, bottom, 0.0D).endVertex();
        BufferBuilder.pos(right, bottom, 0.0D).endVertex();
        BufferBuilder.pos(right, top, 0.0D).endVertex();
        BufferBuilder.pos(left, top, 0.0D).endVertex();
        tessellator1.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawSmoothRect(double left, double top, double right, double bottom, int color) {
        GlStateManager.resetColor();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        drawRect(left, top, right, bottom, color);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawRect(left * 2.0D - 1.0D, top * 2.0D, left * 2.0D, bottom * 2.0D - 1.0D, color);
        drawRect(left * 2.0D, top * 2.0D - 1.0D, right * 2.0D, top * 2.0D, color);
        drawRect(right * 2.0D, top * 2.0D, right * 2.0D + 1.0D, bottom * 2.0D - 1.0D, color);
        GL11.glDisable(3042);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
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

    public static Framebuffer createFramebuffer(Framebuffer framebuffer) {
        return createFramebuffer(framebuffer, false);
    }

    public static Framebuffer createFramebuffer(Framebuffer framebuffer, boolean depth) {
        if (framebuffer != null && framebuffer.framebufferWidth == RenderUtils.mc.getDisplayWidth() && framebuffer.framebufferHeight == RenderUtils.mc.getDisplayHeight()) {
            return framebuffer;
        } else {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }

            return new Framebuffer(RenderUtils.mc.getDisplayWidth(), RenderUtils.mc.getDisplayHeight(), depth);
        }
    }

    public static void resetColor() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer != null && framebuffer.framebufferWidth == RenderUtils.mc2.displayWidth && framebuffer.framebufferHeight == RenderUtils.mc2.displayHeight) {
            return framebuffer;
        } else {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }

            return new Framebuffer(RenderUtils.mc2.displayWidth, RenderUtils.mc2.displayHeight, true);
        }
    }

    public static boolean isHovered(float x, float y, float w, float h, int mouseX, int mouseY) {
        return (float) mouseX >= x && (float) mouseX <= x + w && (float) mouseY >= y && (float) mouseY <= y + h;
    }

    public static double animate(double endPoint, double current, double speed) {
        boolean shouldContinueAnimation = endPoint > current;

        if (speed < 0.0D) {
            speed = 0.0D;
        } else if (speed > 1.0D) {
            speed = 1.0D;
        }

        double dif = Math.max(endPoint, current) - Math.min(endPoint, current);
        double factor = dif * speed;

        return current + (shouldContinueAnimation ? factor : -factor);
    }

    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, (float) ((double) limit * 0.01D));
    }

    public static void bindTexture(int texture) {
        GL11.glBindTexture(3553, texture);
    }

    public static void quickDrawBorderedRect(float x, float y, float x2, float y2, float width, int color1, int color2) {
        quickDrawRect(x, y, x2, y2, color2);
        glColor(color1);
        GL11.glLineWidth(width);
        GL11.glBegin(2);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
    }

    public static void drawCircleRect(float x, float y, float x1, float y1, float radius, int color) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        glColor(color);
        quickRenderCircle((double) (x1 - radius), (double) (y1 - radius), 0.0D, 90.0D, (double) radius, (double) radius);
        quickRenderCircle((double) (x + radius), (double) (y1 - radius), 90.0D, 180.0D, (double) radius, (double) radius);
        quickRenderCircle((double) (x + radius), (double) (y + radius), 180.0D, 270.0D, (double) radius, (double) radius);
        quickRenderCircle((double) (x1 - radius), (double) (y + radius), 270.0D, 360.0D, (double) radius, (double) radius);
        quickDrawRect(x + radius, y + radius, x1 - radius, y1 - radius);
        quickDrawRect(x, y + radius, x + radius, y1 - radius);
        quickDrawRect(x1 - radius, y + radius, x1, y1 - radius);
        quickDrawRect(x + radius, y, x1 - radius, y + radius);
        quickDrawRect(x + radius, y1 - radius, x1 - radius, y1);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawLoadingCircle(float x, float y) {
        for (int i = 0; i < 4; ++i) {
            int rot = (int) (System.nanoTime() / 5000000L * (long) i % 360L);

            drawCircle(x, y, (float) (i * 10), rot - 180, rot);
        }

    }

    public static void drawCircle(float x, float y, float radius, int start, int end) {
        RenderUtils.classProvider.getGlStateManager().enableBlend();
        RenderUtils.classProvider.getGlStateManager().disableTexture2D();
        RenderUtils.classProvider.getGlStateManager().tryBlendFuncSeparate(770, 771, 1, 0);
        glColor(Color.WHITE);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0F);
        GL11.glBegin(3);

        for (float i = (float) end; i >= (float) start; i -= 4.0F) {
            GL11.glVertex2f((float) ((double) x + Math.cos((double) i * 3.141592653589793D / 180.0D) * (double) (radius * 1.001F)), (float) ((double) y + Math.sin((double) i * 3.141592653589793D / 180.0D) * (double) (radius * 1.001F)));
        }

        GL11.glEnd();
        GL11.glDisable(2848);
        RenderUtils.classProvider.getGlStateManager().enableTexture2D();
        RenderUtils.classProvider.getGlStateManager().disableBlend();
    }

    public static void drawFilledCircle(int xx, int yy, float radius, Color color) {
        byte sections = 50;
        double dAngle = 6.283185307179586D / (double) sections;

        GL11.glPushAttrib(8192);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);

        for (int i = 0; i < sections; ++i) {
            float x = (float) ((double) radius * Math.sin((double) i * dAngle));
            float y = (float) ((double) radius * Math.cos((double) i * dAngle));

            GL11.glColor4f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F);
            GL11.glVertex2f((float) xx + x, (float) yy + y);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static void drawImage(IResourceLocation image, int x, int y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        GL14.glBlendFuncSeparate(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtils.mc.getTextureManager().bindTexture(image);
        drawModalRectWithCustomSizedTexture((float) x, (float) y, 0.0F, 0.0F, (float) width, (float) height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawImage4(ResourceLocation image, int x, int y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        GL14.glBlendFuncSeparate(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtils.mc.getTextureManager().bindTexture2(image);
        drawModalRectWithCustomSizedTexture((float) x, (float) y, 0.0F, 0.0F, (float) width, (float) height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
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

    public static void glColor(int red, int green, int blue, int alpha) {
        GL11.glColor4f((float) red / 255.0F, (float) green / 255.0F, (float) blue / 255.0F, (float) alpha / 255.0F);
    }

    public static void glColor(Color color) {
        glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    private static void glColor(int hex) {
        glColor(hex >> 16 & 255, hex >> 8 & 255, hex & 255, hex >> 24 & 255);
    }

    public static void glColor(Color color, int alpha) {
        glColor(color, (int) ((float) alpha / 255.0F));
    }

    public static void glColo2(Color color, float alpha) {
        float red = (float) color.getRed() / 255.0F;
        float green = (float) color.getGreen() / 255.0F;
        float blue = (float) color.getBlue() / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void glColor2(int hex, int alpha) {
        float red = (float) (hex >> 16 & 255) / 255.0F;
        float green = (float) (hex >> 8 & 255) / 255.0F;
        float blue = (float) (hex & 255) / 255.0F;

        GlStateManager.color(red, green, blue, (float) alpha / 255.0F);
    }

    public static void glColor2(int hex, float alpha) {
        float red = (float) (hex >> 16 & 255) / 255.0F;
        float green = (float) (hex >> 8 & 255) / 255.0F;
        float blue = (float) (hex & 255) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void draw2D(IEntityLivingBase entity, double posX, double posY, double posZ, int color, int backgroundColor) {
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotated((double) (-RenderUtils.mc.getRenderManager().getPlayerViewY()), 0.0D, 1.0D, 0.0D);
        GL11.glScaled(-0.1D, -0.1D, 0.1D);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        glColor(color);
        GL11.glCallList(RenderUtils.DISPLAY_LISTS_2D[0]);
        glColor(backgroundColor);
        GL11.glCallList(RenderUtils.DISPLAY_LISTS_2D[1]);
        GL11.glTranslated(0.0D, 21.0D + -(entity.getEntityBoundingBox().getMaxY() - entity.getEntityBoundingBox().getMinY()) * 12.0D, 0.0D);
        glColor(color);
        GL11.glCallList(RenderUtils.DISPLAY_LISTS_2D[2]);
        glColor(backgroundColor);
        GL11.glCallList(RenderUtils.DISPLAY_LISTS_2D[3]);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void glColor3(int red, int green, int blue, int alpha) {
        GlStateManager.color((float) red / 255.0F, (float) green / 255.0F, (float) blue / 255.0F, (float) alpha / 255.0F);
    }

    public static void glColo3(Color color) {
        float red = (float) color.getRed() / 255.0F;
        float green = (float) color.getGreen() / 255.0F;
        float blue = (float) color.getBlue() / 255.0F;
        float alpha = (float) color.getAlpha() / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void glColor3(int hex) {
        float alpha = (float) (hex >> 24 & 255) / 255.0F;
        float red = (float) (hex >> 16 & 255) / 255.0F;
        float green = (float) (hex >> 8 & 255) / 255.0F;
        float blue = (float) (hex & 255) / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void startDrawing() {
        GL11.glEnable(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0);
    }

    public static void draw2D(WBlockPos blockPos, int color, int backgroundColor) {
        IRenderManager renderManager = RenderUtils.mc.getRenderManager();
        double posX = (double) blockPos.getX() + 0.5D - renderManager.getRenderPosX();
        double posY = (double) blockPos.getY() - renderManager.getRenderPosY();
        double posZ = (double) blockPos.getZ() + 0.5D - renderManager.getRenderPosZ();

        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotated((double) (-RenderUtils.mc.getRenderManager().getPlayerViewY()), 0.0D, 1.0D, 0.0D);
        GL11.glScaled(-0.1D, -0.1D, 0.1D);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        glColor(color);
        GL11.glCallList(RenderUtils.DISPLAY_LISTS_2D[0]);
        glColor(backgroundColor);
        GL11.glCallList(RenderUtils.DISPLAY_LISTS_2D[1]);
        GL11.glTranslated(0.0D, 9.0D, 0.0D);
        glColor(color);
        GL11.glCallList(RenderUtils.DISPLAY_LISTS_2D[2]);
        glColor(backgroundColor);
        GL11.glCallList(RenderUtils.DISPLAY_LISTS_2D[3]);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void renderNameTag(String string, double x, double y, double z) {
        IRenderManager renderManager = RenderUtils.mc.getRenderManager();

        GL11.glPushMatrix();
        GL11.glTranslated(x - renderManager.getRenderPosX(), y - renderManager.getRenderPosY(), z - renderManager.getRenderPosZ());
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-RenderUtils.mc.getRenderManager().getPlayerViewY(), 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderUtils.mc.getRenderManager().getPlayerViewX(), 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-0.05F, -0.05F, 0.05F);
        setGlCap(2896, false);
        setGlCap(2929, false);
        setGlCap(3042, true);
        GL11.glBlendFunc(770, 771);
        int width = Fonts.font35.getStringWidth(string) / 2;

        drawRect(-width - 1, -1, width + 1, Fonts.font35.getFontHeight(), Integer.MIN_VALUE);
        Fonts.font35.drawString(string, (float) (-width), 1.5F, Color.WHITE.getRGB(), true);
        resetCaps();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    public static void drawLine(double x, double y, double x1, double y1, float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    public static void makeScissorBox(float x, float y, float x2, float y2) {
        IScaledResolution scaledResolution = RenderUtils.classProvider.createScaledResolution(RenderUtils.mc);
        int factor = scaledResolution.getScaleFactor();

        GL11.glScissor((int) (x * (float) factor), (int) (((float) scaledResolution.getScaledHeight() - y2) * (float) factor), (int) ((x2 - x) * (float) factor), (int) ((y2 - y) * (float) factor));
    }

    public static void resetCaps() {
        RenderUtils.glCapMap.forEach(accept<invokedynamic>());
    }

    public static void enableGlCap(int cap) {
        setGlCap(cap, true);
    }

    public static void enableGlCap(int... caps) {
        int[] aint = caps;
        int i = caps.length;

        for (int j = 0; j < i; ++j) {
            int cap = aint[j];

            setGlCap(cap, true);
        }

    }

    public static void disableGlCap(int cap) {
        setGlCap(cap, true);
    }

    public static void disableGlCap(int... caps) {
        int[] aint = caps;
        int i = caps.length;

        for (int j = 0; j < i; ++j) {
            int cap = aint[j];

            setGlCap(cap, false);
        }

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

    public static int Astolfo(int i, float st, float bright) {
        double currentColor = Math.ceil((double) (System.currentTimeMillis() + (long) (i * 130))) / 6.0D;

        return Color.getHSBColor((double) ((float) ((currentColor %= 360.0D) / 360.0D)) < 0.5D ? -((float) (currentColor / 360.0D)) : (float) (currentColor / 360.0D), st, bright).getRGB();
    }

    public static void drawTexturedRectWithCustomAlpha2(float x, float y, float width, float height, String image, float alpha) {
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
        RenderUtils.mc.getTextureManager().bindTexture2(new ResourceLocation("langya/" + image + ".png"));
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

    public static void drawShadowWithCustomAlpha2(float x, float y, float width, float height, float alpha) {
        drawTexturedRectWithCustomAlpha(x - 9.0F, y - 9.0F, 9.0F, 9.0F, "paneltopleft", alpha);
        drawTexturedRectWithCustomAlpha(x - 9.0F, y + height, 9.0F, 9.0F, "panelbottomleft", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y + height, 9.0F, 9.0F, "panelbottomright", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y - 9.0F, 9.0F, 9.0F, "paneltopright", alpha);
        drawTexturedRectWithCustomAlpha(x - 9.0F, y, 9.0F, height, "panelleft", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y, 9.0F, height, "panelright", alpha);
        drawTexturedRectWithCustomAlpha(x, y - 9.0F, width, 9.0F, "paneltop", alpha);
        drawTexturedRectWithCustomAlpha(x, y + height, width, 9.0F, "panelbottom", alpha);
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

    public static void draw2DBox(IEntity e, Color color, Render3DEvent event) {
        ITimer timer = RenderUtils.mc.getTimer();
        IRenderManager renderManager = RenderUtils.mc.getRenderManager();
        double posX = e.getLastTickPosX() + (e.getPosX() - e.getLastTickPosX()) * (double) event.getPartialTicks() - renderManager.getRenderPosX();
        double posY = e.getLastTickPosY() + (e.getPosY() - e.getLastTickPosY()) * (double) event.getPartialTicks() - renderManager.getRenderPosY();
        double posZ = e.getLastTickPosZ() + (e.getPosZ() - e.getLastTickPosZ()) * (double) event.getPartialTicks() - renderManager.getRenderPosZ();

        RenderUtils.classProvider.createAxisAlignedBB(posX - (double) e.getWidth() / 2.0D, posY, posZ - (double) e.getWidth() / 2.0D, posX + (double) e.getWidth() / 2.0D, posY + (double) e.getHeight() + 0.20000000298023224D, posZ + (double) e.getWidth() / 2.0D);
        GL11.glPushMatrix();
        GL11.glColor4d(0.75D, 0.0D, 0.0D, 0.0D);
        double size = RenderUtils.classProvider.isEntityItem(e) ? 0.15D : 0.375D;
        double boundindY = e.getEntityBoundingBox().getMaxY() - e.getEntityBoundingBox().getMinY() + 0.1D;

        ESPUtils.drawBoundingBox(RenderUtils.classProvider.createAxisAlignedBB(posX - size, posY, posZ - size, posX + size, posY + boundindY, posZ + size));
        ESPUtils.renderOne();
        GL11.glColor4f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, 0.5F);
        ESPUtils.drawBoundingBox(RenderUtils.classProvider.createAxisAlignedBB(posX - size, posY, posZ - size, posX + size, posY + boundindY, posZ + size));
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
        GL11.glColor4f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, 0.5F);
        ESPUtils.drawBoundingBox(RenderUtils.classProvider.createAxisAlignedBB(posX - size, posY, posZ - size, posX + size, posY + boundindY, posZ + size));
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
        ESPUtils.setColor(e);
        GL11.glColor4f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, 0.5F);
        ESPUtils.drawBoundingBox(RenderUtils.classProvider.createAxisAlignedBB(posX - size, posY, posZ - size, posX + size, posY + boundindY, posZ + size));
        GL11.glPolygonOffset(1.0F, 2000000.0F);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
        GL11.glColor4d(0.6D, 0.0D, 0.0D, 1.0D);
        GL11.glPopMatrix();
    }

    public static void enableGL3D(float lineWidth) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(lineWidth);
    }

    public static void disableGL3D() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawWolframEntityESP(IEntity entity, int rgb, double posX, double posY, double posZ) {
        GL11.glPushMatrix();
        GL11.glTranslated(posX, posY, posZ);
        GL11.glRotatef(-entity.getRotationYaw(), 0.0F, 1.0F, 0.0F);
        glColor(rgb);
        enableGL3D(1.0F);
        Cylinder c = new Cylinder();

        GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
        c.setDrawStyle(100011);
        c.draw(0.5F, 0.5F, entity.getHeight() + 0.1F, 18, 1);
        disableGL3D();
        GL11.glPopMatrix();
    }

    public static void glColorHex(int hex) {
        float alpha = (float) (hex >> 24 & 255) / 255.0F;
        float red = (float) (hex >> 16 & 255) / 255.0F;
        float green = (float) (hex >> 8 & 255) / 255.0F;
        float blue = (float) (hex & 255) / 255.0F;

        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawRoundedRect2(float left, float top, float right, float bottom, float radius, int points, int color) {
        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        float f;

        if (left < right) {
            f = left + right;
            right = left;
            left = f - left;
        }

        if (top < bottom) {
            f = top + bottom;
            bottom = top;
            top = f - top;
        }

        float[][] corners = new float[][] { { right + radius, top - radius, 270.0F}, { left - radius, top - radius, 360.0F}, { left - radius, bottom + radius, 90.0F}, { right + radius, bottom + radius, 180.0F}};

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.alphaFunc(516, 0.003921569F);
        GlStateManager.color(f, f1, f2, f3);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder renderer = tessellator.getBuffer();

        renderer.begin(9, DefaultVertexFormats.POSITION);
        float[][] afloat = corners;
        int i = corners.length;

        for (int j = 0; j < i; ++j) {
            float[] c = afloat[j];

            for (int i = 0; i <= points; ++i) {
                double anglerad = 3.141592653589793D * (double) (c[2] + (float) i * 90.0F / (float) points) / 180.0D;

                renderer.pos((double) c[0] + Math.sin(anglerad) * (double) radius, (double) c[1] + Math.cos(anglerad) * (double) radius, 0.0D).endVertex();
            }
        }

        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
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
        frustrum = new Frustum();
    }

    public static class R2DUtils {

        public static void enableGL2D() {
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glDepthMask(true);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glHint(3155, 4354);
        }

        public static void drawOutlinedString(String str, int x, int y, int color, int color2) {
            MinecraftInstance.mc.getFontRendererObj().drawString(str, (int) ((float) x - 1.0F), y, color2);
            MinecraftInstance.mc.getFontRendererObj().drawString(str, (int) ((float) x + 1.0F), y, color2);
            MinecraftInstance.mc.getFontRendererObj().drawString(str, x, (int) ((float) y + 1.0F), color2);
            MinecraftInstance.mc.getFontRendererObj().drawString(str, x, (int) ((float) y - 1.0F), color2);
            MinecraftInstance.mc.getFontRendererObj().drawString(str, x, y, color);
        }

        public static void setColor(Color color) {
            float alpha = (float) (color.getRGB() >> 24 & 255) / 255.0F;
            float red = (float) (color.getRGB() >> 16 & 255) / 255.0F;
            float green = (float) (color.getRGB() >> 8 & 255) / 255.0F;
            float blue = (float) (color.getRGB() & 255) / 255.0F;

            GL11.glColor4f(red, green, blue, alpha);
        }

        public static void disableGL2D() {
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glHint(3154, 4352);
            GL11.glHint(3155, 4352);
        }

        public static int loadGlTexture(BufferedImage bufferedImage) {
            int textureId = GL11.glGenTextures();

            GL11.glBindTexture(3553, textureId);
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexImage2D(3553, 0, 6408, bufferedImage.getWidth(), bufferedImage.getHeight(), 0, 6408, 5121, ImageUtils.readImageToBuffer(bufferedImage));
            GL11.glBindTexture(3553, 0);
            return textureId;
        }

        public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
            enableGL2D();
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            drawVLine(x *= 2.0F, (y *= 2.0F) + 1.0F, (y1 *= 2.0F) - 2.0F, borderC);
            drawVLine((x1 *= 2.0F) - 1.0F, y + 1.0F, y1 - 2.0F, borderC);
            drawHLine(x + 2.0F, x1 - 3.0F, y, borderC);
            drawHLine(x + 2.0F, x1 - 3.0F, y1 - 1.0F, borderC);
            drawHLine(x + 1.0F, x + 1.0F, y + 1.0F, borderC);
            drawHLine(x1 - 2.0F, x1 - 2.0F, y + 1.0F, borderC);
            drawHLine(x1 - 2.0F, x1 - 2.0F, y1 - 2.0F, borderC);
            drawHLine(x + 1.0F, x + 1.0F, y1 - 2.0F, borderC);
            drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
            GL11.glScalef(2.0F, 2.0F, 2.0F);
            disableGL2D();
            Gui.drawRect(0, 0, 0, 0, 0);
        }

        public static void drawRect(double x2, double y2, double x1, double y1, int color) {
            enableGL2D();
            glColor(color);
            drawRect(x2, y2, x1, y1);
            disableGL2D();
        }

        public static void drawOutlinedRect(int x, int y, int width, int height, int lineSize, Color lineColor, Color backgroundColor) {
            drawRect((float) x, (float) y, (float) width, (float) height, backgroundColor.getRGB());
            drawRect((float) x, (float) y, (float) width, (float) (y + lineSize), lineColor.getRGB());
            drawRect((float) x, (float) (height - lineSize), (float) width, (float) height, lineColor.getRGB());
            drawRect((float) x, (float) (y + lineSize), (float) (x + lineSize), (float) (height - lineSize), lineColor.getRGB());
            drawRect((float) (width - lineSize), (float) (y + lineSize), (float) width, (float) (height - lineSize), lineColor.getRGB());
        }

        private static void drawRect(double x2, double y2, double x1, double y1) {
            GL11.glBegin(7);
            GL11.glVertex2d(x2, y1);
            GL11.glVertex2d(x1, y1);
            GL11.glVertex2d(x1, y2);
            GL11.glVertex2d(x2, y2);
            GL11.glEnd();
        }

        public static void glColor(int hex) {
            float alpha = (float) (hex >> 24 & 255) / 255.0F;
            float red = (float) (hex >> 16 & 255) / 255.0F;
            float green = (float) (hex >> 8 & 255) / 255.0F;
            float blue = (float) (hex & 255) / 255.0F;

            GL11.glColor4f(red, green, blue, alpha);
        }

        public static void drawRect(float x, float y, float x1, float y1, int color) {
            enableGL2D();
            glColor(color);
            drawRect(x, y, x1, y1);
            disableGL2D();
        }

        public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int borderColor) {
            enableGL2D();
            glColor(borderColor);
            drawRect(x + width, y, x1 - width, y + width);
            drawRect(x, y, x + width, y1);
            drawRect(x1 - width, y, x1, y1);
            drawRect(x + width, y1 - width, x1 - width, y1);
            disableGL2D();
        }

        public static void drawBorderedRect(float x, float y, float x1, float y1, int insideC, int borderC) {
            enableGL2D();
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            drawVLine(x *= 2.0F, y *= 2.0F, y1 *= 2.0F, borderC);
            drawVLine((x1 *= 2.0F) - 1.0F, y, y1, borderC);
            drawHLine(x, x1 - 1.0F, y, borderC);
            drawHLine(x, x1 - 2.0F, y1 - 1.0F, borderC);
            drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
            GL11.glScalef(2.0F, 2.0F, 2.0F);
            disableGL2D();
        }

        public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
            enableGL2D();
            GL11.glShadeModel(7425);
            GL11.glBegin(7);
            glColor(topColor);
            GL11.glVertex2f(x, y1);
            GL11.glVertex2f(x1, y1);
            glColor(bottomColor);
            GL11.glVertex2f(x1, y);
            GL11.glVertex2f(x, y);
            GL11.glEnd();
            GL11.glShadeModel(7424);
            disableGL2D();
        }

        public static void drawHLine(float x, float y, float x1, int y1) {
            if (y < x) {
                float f = x;

                x = y;
                y = f;
            }

            drawRect(x, x1, y + 1.0F, x1 + 1.0F, y1);
        }

        public static void drawVLine(float x, float y, float x1, int y1) {
            if (x1 < y) {
                float f = y;

                y = x1;
                x1 = f;
            }

            drawRect(x, y + 1.0F, x + 1.0F, x1, y1);
        }

        public static void drawHLine(float x, float y, float x1, int y1, int y2) {
            if (y < x) {
                float f = x;

                x = y;
                y = f;
            }

            drawGradientRect(x, x1, y + 1.0F, x1 + 1.0F, y1, y2);
        }

        public static void drawRect(float x, float y, float x1, float y1) {
            GL11.glBegin(7);
            GL11.glVertex2f(x, y1);
            GL11.glVertex2f(x1, y1);
            GL11.glVertex2f(x1, y);
            GL11.glVertex2f(x, y);
            GL11.glEnd();
        }
    }
}

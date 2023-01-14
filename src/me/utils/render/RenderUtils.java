package me.utils.render;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
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
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.util.glu.Cylinder;

public final class RenderUtils extends MinecraftInstance {

    private static final Map glCapMap = new HashMap();
    private static final int[] DISPLAY_LISTS_2D = new int[4];
    public static int deltaTime;

    public static int SkyRainbow(int i, float st, float bright) {
        double v1 = Math.ceil((double) (System.currentTimeMillis() + (long) (i * 109))) / 5.0D;

        return Color.getHSBColor((double) ((float) ((v1 %= 360.0D) / 360.0D)) < 0.5D ? -((float) (v1 / 360.0D)) : (float) (v1 / 360.0D), st, bright).getRGB();
    }

    public static int getRainbowOpaque(int seconds, float saturation, float brightness, int index) {
        float hue = (float) ((System.currentTimeMillis() + (long) index) % (long) (seconds * 1000)) / (float) (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);

        return color;
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

    public static void prepareScissorBox(float x, float y, float x2, float y2) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scale.getScaleFactor();

        GL11.glScissor((int) (x * (float) factor), (int) (((float) scale.getScaledHeight() - y2) * (float) factor), (int) ((x2 - x) * (float) factor), (int) ((y2 - y) * (float) factor));
    }

    public static void drawGradientSidewaysV(double left, double top, double right, double bottom, int col1, int col2) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        quickDrawGradientSidewaysV(left, top, right, bottom, col1, col2);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
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

    public static void drawRect(double x, double y, double x2, double y2, int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        glColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void quickDrawGradientSidewaysV(double left, double top, double right, double bottom, int col1, int col2) {
        GL11.glBegin(7);
        glColor(col1);
        GL11.glVertex2d(right, top);
        GL11.glVertex2d(left, top);
        glColor(col2);
        GL11.glVertex2d(left, bottom);
        GL11.glVertex2d(right, bottom);
        GL11.glEnd();
    }

    public static void quickDrawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        GL11.glBegin(7);
        glColor(col1);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        glColor(col2);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
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

    public static void drawScaledCustomSizeModalCircle(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        ITessellator tessellator = RenderUtils.classProvider.getTessellatorInstance();
        IWorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(9, RenderUtils.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION_TEX));
        float xRadius = (float) width / 2.0F;
        float yRadius = (float) height / 2.0F;
        float uRadius = ((u + (float) uWidth) * f - u * f) / 2.0F;
        float vRadius = ((v + (float) vHeight) * f1 - v * f1) / 2.0F;

        for (int i = 0; i <= 360; i += 10) {
            double xPosOffset = Math.sin((double) i * 3.141592653589793D / 180.0D);
            double yPosOffset = Math.cos((double) i * 3.141592653589793D / 180.0D);

            worldrenderer.pos((double) ((float) x + xRadius) + xPosOffset * (double) xRadius, (double) ((float) y + yRadius) + yPosOffset * (double) yRadius, 0.0D).tex((double) (u * f + uRadius) + xPosOffset * (double) uRadius, (double) (v * f1 + vRadius) + yPosOffset * (double) vRadius).endVertex();
        }

        tessellator.draw();
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

    public static float fastRoundedRect(float x, float y, float x2, float y2, float rad) {
        return 0.65F;
    }

    private static void quickPolygonCircle(float x, float y, float xRadius, float yRadius, int start, int end, int split) {
        for (int i = end; i >= start; i -= split) {
            GL11.glVertex2d((double) x + Math.sin((double) i * 3.141592653589793D / 180.0D) * (double) xRadius, (double) y + Math.cos((double) i * 3.141592653589793D / 180.0D) * (double) yRadius);
        }

    }

    public static Color skyRainbow(int i, float st, float bright) {
        double v1 = Math.ceil((double) (System.currentTimeMillis() + (long) (i * 109))) / 5.0D;

        return Color.getHSBColor((double) ((float) ((v1 %= 360.0D) / 360.0D)) < 0.5D ? -((float) (v1 / 360.0D)) : (float) (v1 / 360.0D), st, bright);
    }

    public static void quickDrawHead(IResourceLocation skin, int x, int y, int width, int height) {
        RenderUtils.mc.getTextureManager().bindTexture(skin);
        drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
        drawScaledCustomSizeModalRect(x, y, 40.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
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

    public static void drawShadow(int x, int y, int width, int height) {
        IScaledResolution sr = RenderUtils.classProvider.createScaledResolution(RenderUtils.mc);

        drawTexturedRect(x - 9, y - 9, 9, 9, "paneltopleft", sr);
        drawTexturedRect(x - 9, y + height, 9, 9, "panelbottomleft", sr);
        drawTexturedRect(x + width, y + height, 9, 9, "panelbottomright", sr);
        drawTexturedRect(x + width, y - 9, 9, 9, "paneltopright", sr);
        drawTexturedRect(x - 9, y, 9, height, "panelleft", sr);
        drawTexturedRect(x + width, y, 9, height, "panelright", sr);
        drawTexturedRect(x, y - 9, width, 9, "paneltop", sr);
        drawTexturedRect(x, y + height, width, 9, "panelbottom", sr);
    }

    public static int Astolfo(int i, float st, float bright) {
        double currentColor = Math.ceil((double) (System.currentTimeMillis() + (long) (i * 130))) / 6.0D;

        return Color.getHSBColor((double) ((float) ((currentColor %= 360.0D) / 360.0D)) < 0.5D ? -((float) (currentColor / 360.0D)) : (float) (currentColor / 360.0D), st, bright).getRGB();
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

    public static void drawTexturedRect(int x, int y, int width, int height, String image, IScaledResolution sr) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        RenderUtils.mc.getTextureManager().bindTexture(RenderUtils.classProvider.createResourceLocation("langya/cool/potionrender/" + image + ".png"));
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawModalRectWithCustomSizedTexture((float) x, (float) y, 0.0F, 0.0F, (float) width, (float) height, (float) width, (float) height);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GL11.glPopMatrix();
    }

    public static void drawHead(IResourceLocation skin, int x, int y, int width, int height) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtils.mc.getTextureManager().bindTexture(skin);
        drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
        drawScaledCustomSizeModalRect(x, y, 40.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
    }

    public static void drawEntityOnScreen(int posX, int posY, int scale, IEntityLivingBase entity) {
        GlStateManager.pushMatrix();
        GlStateManager.enableColorMaterial();
        GlStateManager.translate((double) posX, (double) posY, 50.0D);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0D, 0.0D, 0.0D);
        float renderYawOffset = entity.getRenderYawOffset();
        float rotationYaw = entity.getRotationYaw();
        float rotationPitch = entity.getRotationPitch();
        float prevRotationYawHead = entity.getPrevRotationYawHead();
        float rotationYawHead = entity.getRotationYawHead();

        entity.setRenderYawOffset(0.0F);
        entity.setRotationYaw(0.0F);
        entity.setRotationPitch(90.0F);
        entity.setRotationYawHead(entity.getRotationYaw());
        entity.setPrevRotationYawHead(entity.getRotationYaw());
        IRenderManager rendermanager = RenderUtils.mc.getRenderManager();

        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);
        entity.setRenderYawOffset(renderYawOffset);
        entity.setRotationYaw(rotationYaw);
        entity.setRotationPitch(rotationPitch);
        entity.setPrevRotationYawHead(prevRotationYawHead);
        entity.setRotationYawHead(rotationYawHead);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glHint(3154, 4354);
    }

    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void bindTexture(int texture) {
        GL11.glBindTexture(3553, texture);
    }

    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, (float) ((double) limit * 0.01D));
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer != null && framebuffer.framebufferWidth == RenderUtils.mc.getDisplayWidth() && framebuffer.framebufferHeight == RenderUtils.mc.getDisplayHeight()) {
            return framebuffer;
        } else {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }

            return new Framebuffer(RenderUtils.mc.getDisplayWidth(), RenderUtils.mc.getDisplayHeight(), true);
        }
    }

    public static void glColor(Color color, int alpha) {
        glColor(color, (float) alpha / 255.0F);
    }

    public static void glColor(Color color, float alpha) {
        float red = (float) color.getRed() / 255.0F;
        float green = (float) color.getGreen() / 255.0F;
        float blue = (float) color.getBlue() / 255.0F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void glColorHex(int hex) {
        float alpha = (float) (hex >> 24 & 255) / 255.0F;
        float red = (float) (hex >> 16 & 255) / 255.0F;
        float green = (float) (hex >> 8 & 255) / 255.0F;
        float blue = (float) (hex & 255) / 255.0F;

        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawGradient(double x, double y, double x2, double y2, int col1, int col2) {
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
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        double f;

        if (left < right) {
            f = left;
            left = right;
            right = f;
        }

        if (top < bottom) {
            f = top;
            top = bottom;
            bottom = f;
        }

        float f1 = (float) (col1 >> 24 & 255) / 255.0F;
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
        GL11.glColor4f(f1, f2, f3, f1);
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
        ITessellator tessellator = RenderUtils.classProvider.getTessellatorInstance();
        IWorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f1, f2, f3, f);
        worldRenderer.begin(7, RenderUtils.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(left, bottom, 0.0D).endVertex();
        worldRenderer.pos(right, bottom, 0.0D).endVertex();
        worldRenderer.pos(right, top, 0.0D).endVertex();
        worldRenderer.pos(left, top, 0.0D).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
        rectangle(x + width, y, x1 - width, y + width, borderColor);
        rectangle(x, y, x + width, y1, borderColor);
        rectangle(x1 - width, y, x1, y1, borderColor);
        rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
    }

    public static void drawBlockOutlineBox(WBlockPos blockPos, Color color, float lineWidth) {
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
        glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        GL11.glLineWidth(lineWidth);
        enableGlCap(2848);
        glColor(color);
        drawSelectionBoundingBox(axisAlignedBB);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthMask(true);
        resetCaps();
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

    public static void drawItemBox(IEntity entity, Color color, boolean outline) {
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
        IAxisAlignedBB axisAlignedBB = RenderUtils.classProvider.createAxisAlignedBB(entityBox.getMinX() - entity.getPosX() + x, entityBox.getMinY() - entity.getPosY() + y, entityBox.getMinZ() - entity.getPosZ() + z, entityBox.getMaxX() - entity.getPosX() + x, entityBox.getMaxY() - entity.getPosY() + y, entityBox.getMaxZ() - entity.getPosZ() + z);

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotated((double) (-entity.getRotationYaw()), 0.0D, 1.0D, 0.0D);
        GL11.glTranslated(-x, -y, -z);
        if (outline) {
            GL11.glLineWidth(1.0F);
            enableGlCap(2848);
            glColor(color.getRed(), color.getGreen(), color.getBlue(), 255);
            drawSelectionBoundingBox(axisAlignedBB);
        } else {
            glColor(color.getRed(), color.getGreen(), color.getBlue(), 35);
            drawFilledBox(axisAlignedBB);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthMask(true);
        resetCaps();
        GL11.glPopMatrix();
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
        IAxisAlignedBB axisAlignedBB = RenderUtils.classProvider.createAxisAlignedBB(entityBox.getMinX() - entity.getPosX() + x - 0.15D, entityBox.getMinY() - entity.getPosY() + y, entityBox.getMinZ() - entity.getPosZ() + z - 0.15D, entityBox.getMaxX() - entity.getPosX() + x + 0.15D, entityBox.getMaxY() - entity.getPosY() + y + 0.15D, entityBox.getMaxZ() - entity.getPosZ() + z + 0.15D);

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotated((double) (-entity.getRotationYaw()), 0.0D, 1.0D, 0.0D);
        GL11.glTranslated(-x, -y, -z);
        if (outline) {
            GL11.glLineWidth(3.0F);
            enableGlCap(2848);
            glColor(0, 0, 0, 255);
            drawSelectionBoundingBox(axisAlignedBB);
            GL11.glLineWidth(1.0F);
            enableGlCap(2848);
            glColor(color.getRed(), color.getGreen(), color.getBlue(), 255);
            drawSelectionBoundingBox(axisAlignedBB);
        } else {
            glColor(color.getRed(), color.getGreen(), color.getBlue(), 35);
            drawFilledBox(axisAlignedBB);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthMask(true);
        resetCaps();
        GL11.glPopMatrix();
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

        drawAxisAlignedBB(RenderUtils.classProvider.createAxisAlignedBB(axisAlignedBB.getMinX() - 0.15D, axisAlignedBB.getMinY(), axisAlignedBB.getMinZ() - 0.15D, axisAlignedBB.getMaxX() + 0.15D, axisAlignedBB.getMaxY() + 0.1D, axisAlignedBB.getMaxZ() + 0.15D), color);
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

    public static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }

    public static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
    }

    public static void drawCircle(float cx, float cy, double r, int segments, float lineWidth, int part, boolean isFull, int c) {
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        r *= 2.0D;
        cx *= 2.0F;
        cy *= 2.0F;
        float f2 = (float) (c >> 24 & 255) / 255.0F;
        float f3 = (float) (c >> 16 & 255) / 255.0F;
        float f4 = (float) (c >> 8 & 255) / 255.0F;
        float f5 = (float) (c & 255) / 255.0F;

        GL11.glEnable(3042);
        startSmooth();
        GL11.glLineWidth(lineWidth);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f3, f4, f5, f2);
        GL11.glBegin(3);

        for (int i = segments - part; i <= segments; ++i) {
            double x = Math.sin((double) i * 3.141592653589793D / 180.0D) * r;
            double y = Math.cos((double) i * 3.141592653589793D / 180.0D) * r;

            GL11.glVertex2d((double) cx + x, (double) cy + y);
            if (isFull) {
                GL11.glVertex2d((double) cx, (double) cy);
            }
        }

        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        endSmooth();
        GL11.glDisable(3042);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
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

    public static void drawFilledCircle(float xx, float yy, float radius, Color color) {
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
            GL11.glVertex2f(xx + x, yy + y);
        }

        GlStateManager.color(0.0F, 0.0F, 0.0F);
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

    public static void drawTexturedModalRect(int p_drawTexturedModalRect_1_, int p_drawTexturedModalRect_2_, int p_drawTexturedModalRect_3_, int p_drawTexturedModalRect_4_, int p_drawTexturedModalRect_5_, int p_drawTexturedModalRect_6_) {
        float lvt_7_1_ = 0.00390625F;
        float lvt_8_1_ = 0.00390625F;
        ITessellator lvt_9_1_ = RenderUtils.classProvider.getTessellatorInstance();
        IWorldRenderer lvt_10_1_ = lvt_9_1_.getWorldRenderer();
        double zLevel = 0.0D;

        lvt_10_1_.begin(7, RenderUtils.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION_TEX));
        lvt_10_1_.pos((double) (p_drawTexturedModalRect_1_ + 0), (double) (p_drawTexturedModalRect_2_ + p_drawTexturedModalRect_6_), zLevel).tex((double) ((float) (p_drawTexturedModalRect_3_ + 0) * 0.00390625F), (double) ((float) (p_drawTexturedModalRect_4_ + p_drawTexturedModalRect_6_) * 0.00390625F)).endVertex();
        lvt_10_1_.pos((double) (p_drawTexturedModalRect_1_ + p_drawTexturedModalRect_5_), (double) (p_drawTexturedModalRect_2_ + p_drawTexturedModalRect_6_), zLevel).tex((double) ((float) (p_drawTexturedModalRect_3_ + p_drawTexturedModalRect_5_) * 0.00390625F), (double) ((float) (p_drawTexturedModalRect_4_ + p_drawTexturedModalRect_6_) * 0.00390625F)).endVertex();
        lvt_10_1_.pos((double) (p_drawTexturedModalRect_1_ + p_drawTexturedModalRect_5_), (double) (p_drawTexturedModalRect_2_ + 0), zLevel).tex((double) ((float) (p_drawTexturedModalRect_3_ + p_drawTexturedModalRect_5_) * 0.00390625F), (double) ((float) (p_drawTexturedModalRect_4_ + 0) * 0.00390625F)).endVertex();
        lvt_10_1_.pos((double) (p_drawTexturedModalRect_1_ + 0), (double) (p_drawTexturedModalRect_2_ + 0), zLevel).tex((double) ((float) (p_drawTexturedModalRect_3_ + 0) * 0.00390625F), (double) ((float) (p_drawTexturedModalRect_4_ + 0) * 0.00390625F)).endVertex();
        lvt_9_1_.draw();
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

    public static float drawJelloShadow() {
        return 0.45F;
    }

    public static float drawImage4() {
        return 0.65F;
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
        int width = Fonts.Posterama35.getStringWidth(string) / 2;

        drawRect(-width - 1, -1, width + 1, Fonts.Posterama35.getFontHeight(), Integer.MIN_VALUE);
        Fonts.Posterama35.drawString(string, (float) (-width), 1.5F, Color.WHITE.getRGB(), true);
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

    public static void drawLine(float x, float y, float x1, float y1, float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    public static void makeScissorBox(float x, float y, float x2, float y2) {
        IScaledResolution scaledResolution = RenderUtils.classProvider.createScaledResolution(RenderUtils.mc);
        int factor = scaledResolution.getScaleFactor();

        GL11.glScissor((int) (x * (float) factor), (int) (((float) scaledResolution.getScaledHeight() - y2) * (float) factor), (int) ((x2 - x) * (float) factor), (int) ((y2 - y) * (float) factor));
    }

    public static void resetCaps() {
        RenderUtils.glCapMap.forEach(RenderUtils::setGlState);
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

    public static void drawRoundedRect(float nameXStart, float nameYStart, float nameXEnd, float nameYEnd, float radius, int color) {
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

    public static void resetColor() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
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
        ITessellator tessellator = RenderUtils.classProvider.getTessellatorInstance();
        IWorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);
        worldrenderer.begin(9, RenderUtils.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
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

    public static void drawImage2(IResourceLocation image, float x, float y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef(x, y, x);
        RenderUtils.mc.getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glTranslatef(-x, -y, -x);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawImage3(ResourceLocation image, float x, float y, int width, int height) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef(x, y, x);
        RenderUtils.mc.getTextureManager().bindTexture((IResourceLocation) image);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, width, height, (float) width, (float) height);
        GL11.glTranslatef(-x, -y, -x);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
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
        ITessellator tessellator = RenderUtils.classProvider.getTessellatorInstance();
        IWorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, RenderUtils.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldrenderer.pos((double) left, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) bottom, 0.0D).endVertex();
        worldrenderer.pos((double) right, (double) top, 0.0D).endVertex();
        worldrenderer.pos((double) left, (double) top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
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
        glColor(color);

        for (int i = 0; i < sections; ++i) {
            float x = (float) ((double) radius * Math.sin((double) i * dAngle));
            float y = (float) ((double) radius * Math.cos((double) i * dAngle));

            GL11.glVertex2f(Math.min(x2, Math.max((float) xx + x, lx)), Math.min(y2, Math.max((float) yy + y, ly)));
        }

        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glEnd();
        GL11.glPopAttrib();
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

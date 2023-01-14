package me.Skid.Tenacity.utils.render;

import java.awt.Color;
import me.Skid.Tenacity.utils.animations.Animation;
import me.Skid.Tenacity.utils.normal.Utils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.WDefaultVertexFormats;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class DrRenderUtils implements Utils {

    public static float zLevel;

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator1 = Tessellator.getInstance();
        BufferBuilder BufferBuilder = tessellator1.getBuffer();

        BufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        BufferBuilder.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + height) * f1)).endVertex();
        BufferBuilder.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + width) * f), (double) ((v + height) * f1)).endVertex();
        BufferBuilder.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + width) * f), (double) (v * f1)).endVertex();
        BufferBuilder.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator1.draw();
    }

    public static void drawGradientRect2(double x, double y, double width, double height, int startColor, int endColor) {
        drawGradientRect(x, y, x + width, y + height, startColor, endColor);
    }

    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        IWorldRenderer worldrenderer = LiquidBounce.INSTANCE.getWrapper().getClassProvider().getTessellatorInstance().getWorldRenderer();

        worldrenderer.begin(7, MinecraftInstance.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION_COLOR));
        worldrenderer.pos(right, top, (double) DrRenderUtils.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, top, (double) DrRenderUtils.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, bottom, (double) DrRenderUtils.zLevel).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos(right, bottom, (double) DrRenderUtils.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradientRectSideways2(double x, double y, double width, double height, int startColor, int endColor) {
        drawGradientRectSideways(x, y, x + width, y + height, startColor, endColor);
    }

    public static void drawGradientRectSideways(double left, double top, double right, double bottom, int startColor, int endColor) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator1 = Tessellator.getInstance();
        BufferBuilder BufferBuilder = tessellator1.getBuffer();

        BufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        BufferBuilder.pos(right, top, (double) DrRenderUtils.zLevel).color(f5, f6, f7, f4).endVertex();
        BufferBuilder.pos(left, top, (double) DrRenderUtils.zLevel).color(f1, f2, f3, f).endVertex();
        BufferBuilder.pos(left, bottom, (double) DrRenderUtils.zLevel).color(f1, f2, f3, f).endVertex();
        BufferBuilder.pos(right, bottom, (double) DrRenderUtils.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator1.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void scissor(double x, double y, double width, double height) {
        IScaledResolution sr = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createScaledResolution(DrRenderUtils.mc);
        double scale = (double) sr.getScaleFactor();
        double finalHeight = height * scale;
        double finalY = ((double) sr.getScaledHeight() - y) * scale;
        double finalX = x * scale;
        double finalWidth = width * scale;

        GL11.glScissor((int) finalX, (int) (finalY - finalHeight), (int) finalWidth, (int) finalHeight);
    }

    public static int interpolateColor(int color1, int color2, float amount) {
        amount = Math.min(1.0F, Math.max(0.0F, amount));
        Color cColor1 = new Color(color1);
        Color cColor2 = new Color(color2);

        return interpolateColorC(cColor1, cColor2, amount).getRGB();
    }

    public static void renderRoundedRect(float x, float y, float width, float height, float radius, int color) {
        drawGoodCircle((double) (x + radius), (double) (y + radius), radius, color);
        drawGoodCircle((double) (x + width - radius), (double) (y + radius), radius, color);
        drawGoodCircle((double) (x + radius), (double) (y + height - radius), radius, color);
        drawGoodCircle((double) (x + width - radius), (double) (y + height - radius), radius, color);
        drawRect2((double) (x + radius), (double) y, (double) (width - radius * 2.0F), (double) height, color);
        drawRect2((double) x, (double) (y + radius), (double) width, (double) (height - radius * 2.0F), color);
    }

    public static Color darker(Color color, float FACTOR) {
        return new Color(Math.max((int) ((float) color.getRed() * FACTOR), 0), Math.max((int) ((float) color.getGreen() * FACTOR), 0), Math.max((int) ((float) color.getBlue() * FACTOR), 0), color.getAlpha());
    }

    public static void drawClickGuiArrow(float x, float y, float size, Animation animation, int color) {
        GL11.glTranslatef(x, y, 0.0F);
        GLUtil.setup2DRendering(() -> {
            GLUtil.render(5, () -> {
                color(color);
                double interpolation = interpolate(0.0D, (double) size / 2.0D, animation.getOutput()).doubleValue();

                if (animation.getOutput() >= 0.48D) {
                    GL11.glVertex2d((double) (size / 2.0F), interpolate((double) size / 2.0D, 0.0D, animation.getOutput()).doubleValue());
                }

                GL11.glVertex2d(0.0D, interpolation);
                if (animation.getOutput() < 0.48D) {
                    GL11.glVertex2d((double) (size / 2.0F), interpolate((double) size / 2.0D, 0.0D, animation.getOutput()).doubleValue());
                }

                GL11.glVertex2d((double) size, interpolation);
            });
        });
        GL11.glTranslatef(-x, -y, 0.0F);
    }

    public static Color interpolateColorC(Color color1, Color color2, float amount) {
        amount = Math.min(1.0F, Math.max(0.0F, amount));
        return new Color(interpolateInt(color1.getRed(), color2.getRed(), (double) amount), interpolateInt(color1.getGreen(), color2.getGreen(), (double) amount), interpolateInt(color1.getBlue(), color2.getBlue(), (double) amount), interpolateInt(color1.getAlpha(), color2.getAlpha(), (double) amount));
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue) {
        return interpolate((double) oldValue, (double) newValue, (double) ((float) interpolationValue)).intValue();
    }

    public static Double interpolate(double oldValue, double newValue, double interpolationValue) {
        return Double.valueOf(oldValue + (newValue - oldValue) * interpolationValue);
    }

    public static boolean isHovering(float x, float y, float width, float height, int mouseX, int mouseY) {
        return (float) mouseX >= x && (float) mouseY >= y && (float) mouseX < x + width && (float) mouseY < y + height;
    }

    public static Color applyOpacity(Color color, float opacity) {
        opacity = Math.min(1.0F, Math.max(0.0F, opacity));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) ((float) color.getAlpha() * opacity));
    }

    public static int applyOpacity(int color, float opacity) {
        Color old = new Color(color);

        return applyOpacity(old, opacity).getRGB();
    }

    public static void drawGoodCircle(double x, double y, float radius, int color) {
        color(color);
        GLUtil.setup2DRendering(() -> {
            GL11.glEnable(2832);
            GL11.glHint(3153, 4354);
            GL11.glPointSize(radius * (float) (2 * MinecraftInstance.mc2.gameSettings.guiScale));
            GLUtil.render(0, () -> {
                GL11.glVertex2d(x, y);
            });
        });
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

    public static void fakeCircleGlow(float posX, float posY, float radius, Color color, float maxAlpha) {
        setAlphaLimit(0.0F);
        GL11.glShadeModel(7425);
        GLUtil.setup2DRendering(() -> {
            GLUtil.render(6, () -> {
                color(color.getRGB(), maxAlpha);
                GL11.glVertex2d((double) posX, (double) posY);
                color(color.getRGB(), 0.0F);

                for (int i = 0; i <= 100; ++i) {
                    double angle = (double) i * 0.06283D + 3.1415D;
                    double x2 = Math.sin(angle) * (double) radius;
                    double y2 = Math.cos(angle) * (double) radius;

                    GL11.glVertex2d((double) posX + x2, (double) posY + y2);
                }

            });
        });
        GL11.glShadeModel(7424);
        setAlphaLimit(1.0F);
    }

    public static Color brighter(Color color, float FACTOR) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();
        int i = (int) (1.0D / (1.0D - (double) FACTOR));

        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        } else {
            if (r > 0 && r < i) {
                r = i;
            }

            if (g > 0 && g < i) {
                g = i;
            }

            if (b > 0 && b < i) {
                b = i;
            }

            return new Color(Math.min((int) ((float) r / FACTOR), 255), Math.min((int) ((float) g / FACTOR), 255), Math.min((int) ((float) b / FACTOR), 255), alpha);
        }
    }

    public static void scale(float x, float y, float scale, Runnable data) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        GL11.glScalef(scale, scale, 1.0F);
        GL11.glTranslatef(-x, -y, 0.0F);
        data.run();
        GL11.glPopMatrix();
    }

    public static void resetColor() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawRect2(double x, double y, double width, double height, int color) {
        resetColor();
        GLUtil.setup2DRendering(() -> {
            GLUtil.render(7, () -> {
                color(color);
                GL11.glVertex2d(x, y);
                GL11.glVertex2d(x, y + height);
                GL11.glVertex2d(x + width, y + height);
                GL11.glVertex2d(x + width, y);
            });
        });
    }

    public static void color(int color, float alpha) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        GlStateManager.color(r, g, b, alpha);
    }

    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, (float) ((double) limit * 0.01D));
    }

    public static void color(int color) {
        color(color, (float) (color >> 24 & 255) / 255.0F);
    }
}

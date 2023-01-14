package me.utils;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;

public class BlurUtils {

    private static Minecraft mc = Minecraft.getMinecraft();
    private static ShaderGroup shaderGroup;
    private static Framebuffer frbuffer;
    private static Framebuffer framebuffer;
    private static Framebuffer frameBuffer;
    private static int lastFactor;
    private static int lastWidth;
    private static int lastHeight;
    private static float lastX;
    private static float lastY;
    private static float lastW;
    private static float lastH;
    private static float lastStrength = 5.0F;
    private static ResourceLocation blurShader = new ResourceLocation("langya/blurarea.json");

    public static void init() {
        try {
            BlurUtils.shaderGroup = new ShaderGroup(BlurUtils.mc.getTextureManager(), BlurUtils.mc.getResourceManager(), BlurUtils.mc.getFramebuffer(), BlurUtils.blurShader);
            BlurUtils.shaderGroup.createBindFramebuffers(BlurUtils.mc.displayWidth, BlurUtils.mc.displayHeight);
            BlurUtils.framebuffer = BlurUtils.shaderGroup.mainFramebuffer;
            BlurUtils.frbuffer = BlurUtils.shaderGroup.getFramebufferRaw("result");
        } catch (IOException | JsonSyntaxException jsonsyntaxexception) {
            jsonsyntaxexception.printStackTrace();
        }

    }

    private static void setValues(float strength, float x, float y, float w, float h, float width, float height) {
        if (strength != BlurUtils.lastStrength || BlurUtils.lastX != x || BlurUtils.lastY != y || BlurUtils.lastW != w || BlurUtils.lastH != h) {
            BlurUtils.lastStrength = strength;
            BlurUtils.lastX = x;
            BlurUtils.lastY = y;
            BlurUtils.lastW = w;
            BlurUtils.lastH = h;

            for (int i = 0; i < 2; ++i) {
                ((Shader) BlurUtils.shaderGroup.listShaders.get(i)).getShaderManager().getShaderUniform("Radius").set(strength);
                ((Shader) BlurUtils.shaderGroup.listShaders.get(i)).getShaderManager().getShaderUniform("BlurXY").set(x, height - y - h);
                ((Shader) BlurUtils.shaderGroup.listShaders.get(i)).getShaderManager().getShaderUniform("BlurCoord").set(w, h);
            }

        }
    }

    public static void blurArea(float x, float y, float x2, float y2, float blurStrength) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            if (x > x2) {
                float scaledResolution = x;

                x = x2;
                x2 = scaledResolution;
            }

            if (y > y2) {
                y = y2;
                y2 = y2;
            }

            ScaledResolution scaledResolution1 = new ScaledResolution(BlurUtils.mc);
            int scaleFactor = scaledResolution1.getScaleFactor();
            int width = scaledResolution1.getScaledWidth();
            int height = scaledResolution1.getScaledHeight();

            if (sizeHasChanged(scaleFactor, width, height) || BlurUtils.framebuffer == null || BlurUtils.frbuffer == null || BlurUtils.shaderGroup == null) {
                init();
            }

            BlurUtils.lastFactor = scaleFactor;
            BlurUtils.lastWidth = width;
            BlurUtils.lastHeight = height;
            float _w = x2 - x;
            float _h = y2 - y;

            setValues(blurStrength, x, y, _w, _h, (float) width, (float) height);
            BlurUtils.framebuffer.bindFramebuffer(true);
            BlurUtils.shaderGroup.render(BlurUtils.mc.timer.renderPartialTicks);
            BlurUtils.mc.getFramebuffer().bindFramebuffer(true);
            Stencil.write(false);
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderUtils.quickDrawRect(x, y, x2, y2);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            Stencil.erase(true);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.pushMatrix();
            GlStateManager.colorMask(true, true, true, false);
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableAlpha();
            BlurUtils.frbuffer.bindFramebufferTexture();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            float f = (float) width;
            float f1 = (float) height;
            float f2 = (float) BlurUtils.frbuffer.framebufferWidth / (float) BlurUtils.frbuffer.framebufferTextureWidth;
            float f3 = (float) BlurUtils.frbuffer.framebufferHeight / (float) BlurUtils.frbuffer.framebufferTextureHeight;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferrender = tessellator.getBuffer();

            bufferrender.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferrender.pos(0.0D, (double) f1, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
            bufferrender.pos((double) f, (double) f1, 0.0D).tex((double) f2, 0.0D).color(255, 255, 255, 255).endVertex();
            bufferrender.pos((double) f, 0.0D, 0.0D).tex((double) f2, (double) f3).color(255, 255, 255, 255).endVertex();
            bufferrender.pos(0.0D, 0.0D, 0.0D).tex(0.0D, (double) f3).color(255, 255, 255, 255).endVertex();
            tessellator.draw();
            BlurUtils.frbuffer.unbindFramebufferTexture();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.popMatrix();
            GlStateManager.disableBlend();
            Stencil.dispose();
            GlStateManager.enableAlpha();
        }
    }

    public static void preCustomBlur(float blurStrength, float x, float y, float x2, float y2, boolean renderClipLayer) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            if (x > x2) {
                float scaledResolution = x;

                x = x2;
                x2 = scaledResolution;
            }

            if (y > y2) {
                y = y2;
                y2 = y2;
            }

            ScaledResolution scaledResolution1 = new ScaledResolution(BlurUtils.mc);
            int scaleFactor = scaledResolution1.getScaleFactor();
            int width = scaledResolution1.getScaledWidth();
            int height = scaledResolution1.getScaledHeight();

            if (sizeHasChanged(scaleFactor, width, height) || BlurUtils.framebuffer == null || BlurUtils.frbuffer == null || BlurUtils.shaderGroup == null) {
                init();
            }

            BlurUtils.lastFactor = scaleFactor;
            BlurUtils.lastWidth = width;
            BlurUtils.lastHeight = height;
            float _w = x2 - x;
            float _h = y2 - y;

            setValues(blurStrength, x, y, _w, _h, (float) width, (float) height);
            BlurUtils.framebuffer.bindFramebuffer(true);
            BlurUtils.shaderGroup.render(BlurUtils.mc.timer.renderPartialTicks);
            BlurUtils.mc.getFramebuffer().bindFramebuffer(true);
            Stencil.write(renderClipLayer);
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }
    }

    public static void postCustomBlur() {
        ScaledResolution scaledResolution = new ScaledResolution(BlurUtils.mc);
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        Stencil.erase(true);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.pushMatrix();
        GlStateManager.colorMask(true, true, true, false);
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableAlpha();
        BlurUtils.frbuffer.bindFramebufferTexture();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f = (float) width;
        float f1 = (float) height;
        float f2 = (float) BlurUtils.frbuffer.framebufferWidth / (float) BlurUtils.frbuffer.framebufferTextureWidth;
        float f3 = (float) BlurUtils.frbuffer.framebufferHeight / (float) BlurUtils.frbuffer.framebufferTextureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0D, (double) f1, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
        worldrenderer.pos((double) f, (double) f1, 0.0D).tex((double) f2, 0.0D).color(255, 255, 255, 255).endVertex();
        worldrenderer.pos((double) f, 0.0D, 0.0D).tex((double) f2, (double) f3).color(255, 255, 255, 255).endVertex();
        worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, (double) f3).color(255, 255, 255, 255).endVertex();
        tessellator.draw();
        BlurUtils.frbuffer.unbindFramebufferTexture();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
        Stencil.dispose();
        GlStateManager.enableAlpha();
    }

    public static void blurAreaRounded(float x, float y, float x2, float y2, float rad, float blurStrength) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            if (x > x2) {
                float scaledResolution = x;

                x = x2;
                x2 = scaledResolution;
            }

            if (y > y2) {
                y = y2;
                y2 = y2;
            }

            ScaledResolution scaledResolution1 = new ScaledResolution(BlurUtils.mc);
            int scaleFactor = scaledResolution1.getScaleFactor();
            int width = scaledResolution1.getScaledWidth();
            int height = scaledResolution1.getScaledHeight();

            if (sizeHasChanged(scaleFactor, width, height) || BlurUtils.framebuffer == null || BlurUtils.frbuffer == null || BlurUtils.shaderGroup == null) {
                init();
            }

            BlurUtils.lastFactor = scaleFactor;
            BlurUtils.lastWidth = width;
            BlurUtils.lastHeight = height;
            float _w = x2 - x;
            float _h = y2 - y;

            setValues(blurStrength, x, y, _w, _h, (float) width, (float) height);
            BlurUtils.framebuffer.bindFramebuffer(true);
            BlurUtils.shaderGroup.render(BlurUtils.mc.timer.renderPartialTicks);
            BlurUtils.mc.getFramebuffer().bindFramebuffer(true);
            Stencil.write(false);
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderUtils.fastRoundedRect(x, y, x2, y2, rad);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            Stencil.erase(true);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.pushMatrix();
            GlStateManager.colorMask(true, true, true, false);
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableAlpha();
            BlurUtils.frbuffer.bindFramebufferTexture();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            float f = (float) width;
            float f1 = (float) height;
            float f2 = (float) BlurUtils.frbuffer.framebufferWidth / (float) BlurUtils.frbuffer.framebufferTextureWidth;
            float f3 = (float) BlurUtils.frbuffer.framebufferHeight / (float) BlurUtils.frbuffer.framebufferTextureHeight;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder worldrenderer = tessellator.getBuffer();

            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos(0.0D, (double) f1, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
            worldrenderer.pos((double) f, (double) f1, 0.0D).tex((double) f2, 0.0D).color(255, 255, 255, 255).endVertex();
            worldrenderer.pos((double) f, 0.0D, 0.0D).tex((double) f2, (double) f3).color(255, 255, 255, 255).endVertex();
            worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, (double) f3).color(255, 255, 255, 255).endVertex();
            tessellator.draw();
            BlurUtils.frbuffer.unbindFramebufferTexture();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.popMatrix();
            GlStateManager.disableBlend();
            Stencil.dispose();
            GlStateManager.enableAlpha();
        }
    }

    private static boolean sizeHasChanged(int scaleFactor, int width, int height) {
        return BlurUtils.lastFactor != scaleFactor || BlurUtils.lastWidth != width || BlurUtils.lastHeight != height;
    }
}

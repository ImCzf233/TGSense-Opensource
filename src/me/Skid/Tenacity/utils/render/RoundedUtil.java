package me.Skid.Tenacity.utils.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class RoundedUtil {

    public static ShaderUtil roundedShader = new ShaderUtil("roundedRect");
    public static ShaderUtil roundedOutlineShader = new ShaderUtil("liquidbounce+/Shaderss/roundRectOutline.frag");
    private static final ShaderUtil roundedTexturedShader = new ShaderUtil("liquidbounce+/Shaderss/roundRectTextured.frag");
    private static final ShaderUtil roundedGradientShader = new ShaderUtil("roundedRectGradient");

    public static void drawRound(float x, float y, float width, float height, float radius, Color color) {
        drawRound(x, y, width, height, radius, false, color);
    }

    public static void drawRoundScale(float x, float y, float width, float height, float radius, Color color, float scale) {
        drawRound(x + width - width * scale, y + height / 2.0F - height / 2.0F * scale, width * scale, height * scale, radius, false, color);
    }

    public static void drawGradientHorizontal(float x, float y, float width, float height, float radius, Color left, Color right) {
        drawGradientRound(x, y, width, height, radius, left, left, right, right);
    }

    public static void drawGradientVertical(float x, float y, float width, float height, float radius, Color top, Color bottom) {
        drawGradientRound(x, y, width, height, radius, bottom, top, bottom, top);
    }

    public static void drawGradientRound(float x, float y, float width, float height, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        DrRenderUtils.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RoundedUtil.roundedGradientShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtil.roundedGradientShader);
        RoundedUtil.roundedGradientShader.setUniformf("color1", new float[] { (float) bottomLeft.getRed() / 255.0F, (float) bottomLeft.getGreen() / 255.0F, (float) bottomLeft.getBlue() / 255.0F, (float) bottomLeft.getAlpha() / 255.0F});
        RoundedUtil.roundedGradientShader.setUniformf("color2", new float[] { (float) topLeft.getRed() / 255.0F, (float) topLeft.getGreen() / 255.0F, (float) topLeft.getBlue() / 255.0F, (float) topLeft.getAlpha() / 255.0F});
        RoundedUtil.roundedGradientShader.setUniformf("color3", new float[] { (float) bottomRight.getRed() / 255.0F, (float) bottomRight.getGreen() / 255.0F, (float) bottomRight.getBlue() / 255.0F, (float) bottomRight.getAlpha() / 255.0F});
        RoundedUtil.roundedGradientShader.setUniformf("color4", new float[] { (float) topRight.getRed() / 255.0F, (float) topRight.getGreen() / 255.0F, (float) topRight.getBlue() / 255.0F, (float) topRight.getAlpha() / 255.0F});
        ShaderUtil.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
        RoundedUtil.roundedGradientShader.unload();
        GlStateManager.disableBlend();
    }

    public static void drawRound(float x, float y, float width, float height, float radius, boolean blur, Color color) {
        DrRenderUtils.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RoundedUtil.roundedShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtil.roundedShader);
        RoundedUtil.roundedShader.setUniformi("blur", new int[] { blur ? 1 : 0});
        RoundedUtil.roundedShader.setUniformf("color", new float[] { (float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F});
        ShaderUtil.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
        RoundedUtil.roundedShader.unload();
        GlStateManager.disableBlend();
    }

    public static void drawRoundOutline(float x, float y, float width, float height, float radius, float outlineThickness, Color color, Color outlineColor) {
        DrRenderUtils.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        RoundedUtil.roundedOutlineShader.init();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtil.roundedOutlineShader);
        RoundedUtil.roundedOutlineShader.setUniformf("outlineThickness", new float[] { outlineThickness * (float) sr.getScaleFactor()});
        RoundedUtil.roundedOutlineShader.setUniformf("color", new float[] { (float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F});
        RoundedUtil.roundedOutlineShader.setUniformf("outlineColor", new float[] { (float) outlineColor.getRed() / 255.0F, (float) outlineColor.getGreen() / 255.0F, (float) outlineColor.getBlue() / 255.0F, (float) outlineColor.getAlpha() / 255.0F});
        ShaderUtil.drawQuads(x - (2.0F + outlineThickness), y - (2.0F + outlineThickness), width + 4.0F + outlineThickness * 2.0F, height + 4.0F + outlineThickness * 2.0F);
        RoundedUtil.roundedOutlineShader.unload();
        GlStateManager.disableBlend();
    }

    public static void drawRoundTextured(float x, float y, float width, float height, float radius, float alpha) {
        DrRenderUtils.resetColor();
        RoundedUtil.roundedTexturedShader.init();
        RoundedUtil.roundedTexturedShader.setUniformi("textureIn", new int[] { 0});
        setupRoundedRectUniforms(x, y, width, height, radius, RoundedUtil.roundedTexturedShader);
        RoundedUtil.roundedTexturedShader.setUniformf("alpha", new float[] { alpha});
        ShaderUtil.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
        RoundedUtil.roundedTexturedShader.unload();
        GlStateManager.disableBlend();
    }

    private static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius, ShaderUtil roundedTexturedShader) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        roundedTexturedShader.setUniformf("location", new float[] { x * (float) sr.getScaleFactor(), (float) Minecraft.getMinecraft().displayHeight - height * (float) sr.getScaleFactor() - y * (float) sr.getScaleFactor()});
        roundedTexturedShader.setUniformf("rectSize", new float[] { width * (float) sr.getScaleFactor(), height * (float) sr.getScaleFactor()});
        roundedTexturedShader.setUniformf("radius", new float[] { radius * (float) sr.getScaleFactor()});
    }
}

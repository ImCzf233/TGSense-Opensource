package me.Skid.Tenacity.utils.render;

import java.awt.Color;
import me.Skid.Tenacity.utils.normal.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class GradientUtil implements Utils {

    private static final ShaderUtil gradientMaskShader = new ShaderUtil("liquidbounce+/Shaderss/gradientmask.frag");
    private static final ShaderUtil gradientShader = new ShaderUtil("liquidbounce+/Shaderss/gradient.frag");

    public static void drawGradient(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        DrRenderUtils.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GradientUtil.gradientShader.init();
        GradientUtil.gradientShader.setUniformf("location", new float[] { x * (float) sr.getScaleFactor(), (float) Minecraft.getMinecraft().displayHeight - height * (float) sr.getScaleFactor() - y * (float) sr.getScaleFactor()});
        GradientUtil.gradientShader.setUniformf("rectSize", new float[] { width * (float) sr.getScaleFactor(), height * (float) sr.getScaleFactor()});
        GradientUtil.gradientShader.setUniformf("alpha", new float[] { alpha});
        GradientUtil.gradientShader.setUniformf("color1", new float[] { (float) bottomLeft.getRed() / 255.0F, (float) bottomLeft.getGreen() / 255.0F, (float) bottomLeft.getBlue() / 255.0F});
        GradientUtil.gradientShader.setUniformf("color2", new float[] { (float) topLeft.getRed() / 255.0F, (float) topLeft.getGreen() / 255.0F, (float) topLeft.getBlue() / 255.0F});
        GradientUtil.gradientShader.setUniformf("color3", new float[] { (float) bottomRight.getRed() / 255.0F, (float) bottomRight.getGreen() / 255.0F, (float) bottomRight.getBlue() / 255.0F});
        GradientUtil.gradientShader.setUniformf("color4", new float[] { (float) topRight.getRed() / 255.0F, (float) topRight.getGreen() / 255.0F, (float) topRight.getBlue() / 255.0F});
        ShaderUtil.drawQuads(x, y, width, height);
        GradientUtil.gradientShader.unload();
        GlStateManager.disableBlend();
    }

    public static void drawGradientLR(float x, float y, float width, float height, float alpha, Color left, Color right) {
        drawGradient(x, y, width, height, alpha, left, left, right, right);
    }

    public static void drawGradientTB(float x, float y, float width, float height, float alpha, Color top, Color bottom) {
        drawGradient(x, y, width, height, alpha, bottom, top, bottom, top);
    }

    public static void applyGradientHorizontal(float x, float y, float width, float height, float alpha, Color left, Color right, Runnable content) {
        applyGradient(x, y, width, height, alpha, left, left, right, right, content);
    }

    public static void applyGradientVertical(float x, float y, float width, float height, float alpha, Color top, Color bottom, Runnable content) {
        applyGradient(x, y, width, height, alpha, bottom, top, bottom, top, content);
    }

    public static void applyGradientCornerRL(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topRight, Runnable content) {
        Color mixedColor = DrRenderUtils.interpolateColorC(topRight, bottomLeft, 0.5F);

        applyGradient(x, y, width, height, alpha, bottomLeft, mixedColor, mixedColor, topRight, content);
    }

    public static void applyGradient(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight, Runnable content) {
        DrRenderUtils.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GradientUtil.gradientMaskShader.init();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        GradientUtil.gradientMaskShader.setUniformf("location", new float[] { x * (float) sr.getScaleFactor(), (float) Minecraft.getMinecraft().displayHeight - height * (float) sr.getScaleFactor() - y * (float) sr.getScaleFactor()});
        GradientUtil.gradientMaskShader.setUniformf("rectSize", new float[] { width * (float) sr.getScaleFactor(), height * (float) sr.getScaleFactor()});
        GradientUtil.gradientMaskShader.setUniformf("alpha", new float[] { alpha});
        GradientUtil.gradientMaskShader.setUniformi("tex", new int[] { 0});
        GradientUtil.gradientMaskShader.setUniformf("color1", new float[] { (float) bottomLeft.getRed() / 255.0F, (float) bottomLeft.getGreen() / 255.0F, (float) bottomLeft.getBlue() / 255.0F});
        GradientUtil.gradientMaskShader.setUniformf("color2", new float[] { (float) topLeft.getRed() / 255.0F, (float) topLeft.getGreen() / 255.0F, (float) topLeft.getBlue() / 255.0F});
        GradientUtil.gradientMaskShader.setUniformf("color3", new float[] { (float) bottomRight.getRed() / 255.0F, (float) bottomRight.getGreen() / 255.0F, (float) bottomRight.getBlue() / 255.0F});
        GradientUtil.gradientMaskShader.setUniformf("color4", new float[] { (float) topRight.getRed() / 255.0F, (float) topRight.getGreen() / 255.0F, (float) topRight.getBlue() / 255.0F});
        content.run();
        GradientUtil.gradientMaskShader.unload();
        GlStateManager.disableBlend();
    }
}

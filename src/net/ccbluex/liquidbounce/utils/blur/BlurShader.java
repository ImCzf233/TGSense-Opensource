package net.ccbluex.liquidbounce.utils.blur;

import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public class BlurShader {

    private final ShaderProgram blurShader = new ShaderProgram("fragment/blur.frag");
    private static Framebuffer blurBuffer = new Framebuffer(1, 1, false);
    private float radius;

    public BlurShader() {
        this.radius = 25.0F;
    }

    public BlurShader(float radius) {
        this.radius = radius;
    }

    public void blur() {
        this.blur(0.0F, 0.0F, (float) Minecraft.getMinecraft().displayWidth, (float) Minecraft.getMinecraft().displayHeight);
    }

    public void blur(float x, float y, float width, float height) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        BlurShader.blurBuffer = RenderUtils.createFramebuffer(BlurShader.blurBuffer, false);
        this.blurShader.init();
        this.setupUniforms(1, 0, width, height);
        BlurShader.blurBuffer.framebufferClear();
        BlurShader.blurBuffer.bindFramebuffer(true);
        GL11.glBindTexture(3553, Minecraft.getMinecraft().getFramebuffer().framebufferTexture);
        this.blurShader.renderCanvas(scaledResolution);
        BlurShader.blurBuffer.unbindFramebuffer();
        this.blurShader.init();
        this.setupUniforms(0, 1, width, height);
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
        GL11.glBindTexture(3553, BlurShader.blurBuffer.framebufferTexture);
        this.blurShader.renderCanvas(scaledResolution);
        this.blurShader.uninit();
    }

    public void setupUniforms(int x, int y, float width, float height) {
        this.blurShader.setUniformi("originalTexture", new int[] { 0});
        this.blurShader.setUniformf("texelSize", new float[] { 1.0F / width, 1.0F / height});
        this.blurShader.setUniformf("direction", new float[] { (float) x, (float) y});
        this.blurShader.setUniformf("radius", new float[] { this.radius});
    }
}

package net.ccbluex.liquidbounce.utils.blur;

import java.nio.FloatBuffer;
import net.ccbluex.liquidbounce.utils.MathUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class GaussianBlur {

    public static ShaderUtil blurShader = new ShaderUtil("shaders/gaussian.frag");
    public static Framebuffer framebuffer = new Framebuffer(1, 1, false);

    public static void setupUniforms(float dir1, float dir2, float radius) {
        GaussianBlur.blurShader.setUniformi("textureIn", new int[] { 0});
        GaussianBlur.blurShader.setUniformf("texelSize", new float[] { 1.0F / (float) Minecraft.getMinecraft().displayWidth, 1.0F / (float) Minecraft.getMinecraft().displayHeight});
        GaussianBlur.blurShader.setUniformf("direction", new float[] { dir1, dir2});
        GaussianBlur.blurShader.setUniformf("radius", new float[] { radius});
        FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);

        for (int i = 0; (float) i <= radius; ++i) {
            weightBuffer.put(MathUtils.calculateGaussianValue((float) i, radius / 2.0F));
        }

        weightBuffer.rewind();
        GL20.glUniform1(GaussianBlur.blurShader.getUniform("weights"), weightBuffer);
    }

    public static void renderBlur(float radius) {
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GaussianBlur.framebuffer = RenderUtils.createFrameBuffer(GaussianBlur.framebuffer);
        GaussianBlur.framebuffer.framebufferClear();
        GaussianBlur.framebuffer.bindFramebuffer(true);
        GaussianBlur.blurShader.init();
        setupUniforms(1.0F, 0.0F, radius);
        RenderUtils.bindTexture(Minecraft.getMinecraft().getFramebuffer().framebufferTexture);
        ShaderUtil.drawQuads();
        GaussianBlur.framebuffer.unbindFramebuffer();
        GaussianBlur.blurShader.unload();
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
        GaussianBlur.blurShader.init();
        setupUniforms(0.0F, 1.0F, radius);
        RenderUtils.bindTexture(GaussianBlur.framebuffer.framebufferTexture);
        ShaderUtil.drawQuads();
        GaussianBlur.blurShader.unload();
        RenderUtils.resetColor();
        GlStateManager.bindTexture(0);
    }
}

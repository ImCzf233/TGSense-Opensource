package net.ccbluex.liquidbounce.utils.blur;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class BloomShader {

    private ShaderProgram bloomShader = new ShaderProgram("fragment/bloom.frag");
    public Framebuffer framebuffer = new Framebuffer(1, 1, false);
    private Framebuffer bloomFramebuffer = new Framebuffer(1, 1, false);
    private static List renders = new ArrayList();
    public int sourceTexture;
    public int radius;
    public int offset;

    public static void drawAndBloom(RenderCallback render) {
        BloomShader.renders.add(render);
    }

    public void renderBlur() {
        if (!BloomShader.renders.isEmpty()) {
            Iterator weightBuffer = BloomShader.renders.iterator();

            while (weightBuffer.hasNext()) {
                RenderCallback i = (RenderCallback) weightBuffer.next();

                i.render();
            }

            BloomShader.renders.clear();
            this.bloomFramebuffer.unbindFramebuffer();
            this.framebuffer = RenderUtils.createFramebuffer(this.framebuffer);
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.0F);
            GlStateManager.enableBlend();
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            FloatBuffer floatbuffer = BufferUtils.createFloatBuffer(256);

            for (int i = 0; i <= this.radius; ++i) {
                floatbuffer.put(MathUtil.calculateGaussianValue((float) i, (float) this.radius));
            }

            floatbuffer.rewind();
            RenderUtils.setAlphaLimit(0.0F);
            this.framebuffer.framebufferClear();
            this.framebuffer.bindFramebuffer(true);
            this.bloomShader.init();
            this.setupUniforms(this.radius, this.offset, 0, floatbuffer);
            RenderUtils.bindTexture(this.sourceTexture);
            this.bloomShader.renderCanvas(new ScaledResolution(Minecraft.getMinecraft()));
            this.bloomShader.uninit();
            this.framebuffer.unbindFramebuffer();
            Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
            this.bloomShader.init();
            this.setupUniforms(this.radius, 0, this.offset, floatbuffer);
            GL13.glActiveTexture('è“?');
            RenderUtils.bindTexture(this.sourceTexture);
            GL13.glActiveTexture('è“?');
            RenderUtils.bindTexture(this.framebuffer.framebufferTexture);
            this.bloomShader.renderCanvas(new ScaledResolution(Minecraft.getMinecraft()));
            this.bloomShader.uninit();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.enableAlpha();
            GlStateManager.bindTexture(0);
        }
    }

    public void setupUniforms(int radius, int directionX, int directionY, FloatBuffer weights) {
        this.bloomShader.setUniformi("inTexture", new int[] { 0});
        this.bloomShader.setUniformi("textureToCheck", new int[] { 16});
        this.bloomShader.setUniformf("radius", new float[] { (float) radius});
        this.bloomShader.setUniformf("texelSize", new float[] { 1.0F / (float) Minecraft.getMinecraft().displayWidth, 1.0F / (float) Minecraft.getMinecraft().displayHeight});
        this.bloomShader.setUniformf("direction", new float[] { (float) directionX, (float) directionY});
        GL20.glUniform1(this.bloomShader.getUniform("weights"), weights);
    }
}

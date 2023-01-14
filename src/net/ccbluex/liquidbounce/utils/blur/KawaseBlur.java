package net.ccbluex.liquidbounce.utils.blur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public class KawaseBlur {

    public static ShaderUtil kawaseDown = new ShaderUtil("fragment/kawaseDown.frag");
    public static ShaderUtil kawaseUp = new ShaderUtil("fragment/kawaseUp.frag");
    public static Framebuffer framebuffer = new Framebuffer(1, 1, false);
    private static int currentIterations;
    private static final List framebufferList = new ArrayList();

    public static void setupUniforms(float offset) {
        KawaseBlur.kawaseDown.setUniformf("offset", new float[] { offset, offset});
        KawaseBlur.kawaseUp.setUniformf("offset", new float[] { offset, offset});
    }

    private static void initFramebuffers(float iterations) {
        Iterator i = KawaseBlur.framebufferList.iterator();

        Framebuffer framebuffer;

        while (i.hasNext()) {
            framebuffer = (Framebuffer) i.next();
            framebuffer.deleteFramebuffer();
        }

        KawaseBlur.framebufferList.clear();
        KawaseBlur.framebufferList.add(RenderUtils.createFramebuffer(KawaseBlur.framebuffer, true));

        for (int i = 1; (float) i <= iterations; ++i) {
            framebuffer = new Framebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, false);
            KawaseBlur.framebufferList.add(RenderUtils.createFramebuffer(framebuffer, true));
        }

    }

    public static void renderBlur(int iterations, int offset) {
        if (KawaseBlur.currentIterations != iterations) {
            initFramebuffers((float) iterations);
            KawaseBlur.currentIterations = iterations;
        }

        renderFBO((Framebuffer) KawaseBlur.framebufferList.get(1), Minecraft.getMinecraft().getFramebuffer().framebufferTexture, KawaseBlur.kawaseDown, (float) offset);

        int i;

        for (i = 1; i < iterations; ++i) {
            renderFBO((Framebuffer) KawaseBlur.framebufferList.get(i + 1), ((Framebuffer) KawaseBlur.framebufferList.get(i)).framebufferTexture, KawaseBlur.kawaseDown, (float) offset);
        }

        for (i = iterations; i > 1; --i) {
            renderFBO((Framebuffer) KawaseBlur.framebufferList.get(i - 1), ((Framebuffer) KawaseBlur.framebufferList.get(i)).framebufferTexture, KawaseBlur.kawaseUp, (float) offset);
        }

        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
        RenderUtils.bindTexture(((Framebuffer) KawaseBlur.framebufferList.get(1)).framebufferTexture);
        KawaseBlur.kawaseUp.init();
        KawaseBlur.kawaseUp.setUniformf("offset", new float[] { (float) offset, (float) offset});
        KawaseBlur.kawaseUp.setUniformf("halfpixel", new float[] { 0.5F / (float) Minecraft.getMinecraft().displayWidth, 0.5F / (float) Minecraft.getMinecraft().displayHeight});
        KawaseBlur.kawaseUp.setUniformi("inTexture", new int[] { 0});
        ShaderUtil.drawQuads();
        KawaseBlur.kawaseUp.unload();
    }

    private static void renderFBO(Framebuffer framebuffer, int framebufferTexture, ShaderUtil shader, float offset) {
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        shader.init();
        RenderUtils.bindTexture(framebufferTexture);
        shader.setUniformf("offset", new float[] { offset, offset});
        shader.setUniformi("inTexture", new int[] { 0});
        shader.setUniformf("halfpixel", new float[] { 0.5F / (float) Minecraft.getMinecraft().displayWidth, 0.5F / (float) Minecraft.getMinecraft().displayHeight});
        ShaderUtil.drawQuads();
        shader.unload();
        framebuffer.unbindFramebuffer();
    }
}

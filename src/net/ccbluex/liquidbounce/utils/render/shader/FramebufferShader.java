package net.ccbluex.liquidbounce.utils.render.shader;

import java.awt.Color;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class FramebufferShader extends Shader {

    private static Framebuffer framebuffer;
    protected float red;
    protected float green;
    protected float blue;
    protected float alpha = 1.0F;
    protected float radius = 2.0F;
    protected float quality = 1.0F;
    private boolean entityShadows;

    public FramebufferShader(String fragmentShader) {
        super(fragmentShader);
    }

    public void startDraw(float partialTicks) {
        FramebufferShader.classProvider.getGlStateManager().enableAlpha();
        FramebufferShader.classProvider.getGlStateManager().pushMatrix();
        FramebufferShader.classProvider.getGlStateManager().pushAttrib();
        FramebufferShader.framebuffer = this.setupFrameBuffer(FramebufferShader.framebuffer);
        FramebufferShader.framebuffer.framebufferClear();
        FramebufferShader.framebuffer.bindFramebuffer(true);
        this.entityShadows = FramebufferShader.mc.getGameSettings().getEntityShadows();
        FramebufferShader.mc.getGameSettings().setEntityShadows(false);
        FramebufferShader.mc.getEntityRenderer().setupCameraTransform(partialTicks, 0);
    }

    public void stopDraw(Color color, float radius, float quality) {
        FramebufferShader.mc.getGameSettings().setEntityShadows(this.entityShadows);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        FramebufferShader.mc.getFramebuffer().bindFramebuffer(true);
        this.red = (float) color.getRed() / 255.0F;
        this.green = (float) color.getGreen() / 255.0F;
        this.blue = (float) color.getBlue() / 255.0F;
        this.alpha = (float) color.getAlpha() / 255.0F;
        this.radius = radius;
        this.quality = quality;
        FramebufferShader.mc.getEntityRenderer().disableLightmap();
        RenderHelper.disableStandardItemLighting();
        this.startShader();
        FramebufferShader.mc.getEntityRenderer().setupOverlayRendering();
        this.drawFramebuffer(FramebufferShader.framebuffer);
        this.stopShader();
        FramebufferShader.mc.getEntityRenderer().disableLightmap();
        FramebufferShader.classProvider.getGlStateManager().popMatrix();
        FramebufferShader.classProvider.getGlStateManager().popAttrib();
    }

    public Framebuffer setupFrameBuffer(Framebuffer frameBuffer) {
        if (frameBuffer != null) {
            frameBuffer.deleteFramebuffer();
        }

        frameBuffer = new Framebuffer(FramebufferShader.mc.getDisplayWidth(), FramebufferShader.mc.getDisplayHeight(), true);
        return frameBuffer;
    }

    public void drawFramebuffer(Framebuffer framebuffer) {
        IScaledResolution scaledResolution = FramebufferShader.classProvider.createScaledResolution(FramebufferShader.mc);

        GL11.glBindTexture(3553, framebuffer.framebufferTexture);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0D, 1.0D);
        GL11.glVertex2d(0.0D, 0.0D);
        GL11.glTexCoord2d(0.0D, 0.0D);
        GL11.glVertex2d(0.0D, (double) scaledResolution.getScaledHeight());
        GL11.glTexCoord2d(1.0D, 0.0D);
        GL11.glVertex2d((double) scaledResolution.getScaledWidth(), (double) scaledResolution.getScaledHeight());
        GL11.glTexCoord2d(1.0D, 1.0D);
        GL11.glVertex2d((double) scaledResolution.getScaledWidth(), 0.0D);
        GL11.glEnd();
        GL20.glUseProgram(0);
    }
}

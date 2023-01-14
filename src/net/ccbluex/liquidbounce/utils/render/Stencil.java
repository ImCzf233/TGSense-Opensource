package net.ccbluex.liquidbounce.utils.render;

import net.ccbluex.liquidbounce.utils.MinecraftInstance2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class Stencil extends MinecraftInstance2 {

    public static void dispose() {
        GL11.glDisable(2960);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }

    public static void erase(boolean invert) {
        GL11.glStencilFunc(invert ? 514 : 517, 1, '\uffff');
        GL11.glStencilOp(7680, 7680, 7681);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GL11.glAlphaFunc(516, 0.0F);
    }

    public static void write(boolean renderClipLayer) {
        checkSetupFBO();
        GL11.glClearStencil(0);
        GL11.glClear(1024);
        GL11.glEnable(2960);
        GL11.glStencilFunc(519, 1, '\uffff');
        GL11.glStencilOp(7680, 7680, 7681);
        if (!renderClipLayer) {
            GlStateManager.colorMask(false, false, false, false);
        }

    }

    public static void write(boolean renderClipLayer, Framebuffer fb, boolean clearStencil, boolean invert) {
        checkSetupFBO(fb);
        if (clearStencil) {
            GL11.glClearStencil(0);
            GL11.glClear(1024);
            GL11.glEnable(2960);
        }

        GL11.glStencilFunc(519, invert ? 0 : 1, '\uffff');
        GL11.glStencilOp(7680, 7680, 7681);
        if (!renderClipLayer) {
            GlStateManager.colorMask(false, false, false, false);
        }

    }

    public static void checkSetupFBO() {
        Framebuffer fbo = Stencil.mc.getFramebuffer();

        if (fbo.depthBuffer > -1) {
            setupFBO(fbo);
            fbo.depthBuffer = -1;
        }

    }

    public static void checkSetupFBO(Framebuffer fbo) {
        if (fbo != null && fbo.depthBuffer > -1) {
            setupFBO(fbo);
            fbo.depthBuffer = -1;
        }

    }

    public static void setupFBO(Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();

        EXTFramebufferObject.glBindRenderbufferEXT('èµ?', stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT('èµ?', 'è“?', Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT('èµ?', 'è´?', 'èµ?', stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT('èµ?', 'è´?', 'èµ?', stencil_depth_buffer_ID);
    }
}

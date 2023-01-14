package net.ccbluex.liquidbounce.utils.blur;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class StencilUtil {

    static Minecraft mc = Minecraft.getMinecraft();

    public static void checkSetupFBO(Framebuffer framebuffer) {
        if (framebuffer != null && framebuffer.depthBuffer > -1) {
            setupFBO(framebuffer);
            framebuffer.depthBuffer = -1;
        }

    }

    public static void setupFBO(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();

        EXTFramebufferObject.glBindRenderbufferEXT('èµ?', stencilDepthBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT('èµ?', 'è“?', StencilUtil.mc.displayWidth, StencilUtil.mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT('èµ?', 'è´?', 'èµ?', stencilDepthBufferID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT('èµ?', 'è´?', 'èµ?', stencilDepthBufferID);
    }

    public static void initStencilToWrite() {
        StencilUtil.mc.getFramebuffer().bindFramebuffer(false);
        checkSetupFBO(StencilUtil.mc.getFramebuffer());
        GL11.glClear(1024);
        GL11.glEnable(2960);
        GL11.glStencilFunc(519, 1, 1);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glColorMask(false, false, false, false);
    }

    public static void readStencilBuffer(int ref) {
        GL11.glColorMask(true, true, true, true);
        GL11.glStencilFunc(514, ref, 1);
        GL11.glStencilOp(7680, 7680, 7680);
    }

    public static void uninitStencilBuffer() {
        GL11.glDisable(2960);
    }
}

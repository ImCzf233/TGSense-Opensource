package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.api.enums.WDefaultVertexFormats;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

public final class ESPUtils {

    public static void renderOne() {
        checkSetupFBO();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(2.0F);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }

    public static void checkSetupFBO() {
        Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();

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

    public static void cylinder(Entity player, double x, double y, double z, double range, int s) {
        GL11.glPushMatrix();
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(true);
        GlStateManager.translate(x, y, z);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.rotate(180.0F, 90.0F, 0.0F, 2.0F);
        GlStateManager.rotate(180.0F, 0.0F, 90.0F, 90.0F);
        Cylinder c = new Cylinder();

        c.setDrawStyle(100011);
        c.draw((float) (range - 0.5D), (float) (range - 0.5D), 0.0F, s, 0);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    public static void shadow(Entity player, double x, double y, double z, double range, int s) {
        GL11.glPushMatrix();
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(true);
        GlStateManager.translate(x, y, z);
        GlStateManager.color(0.1F, 0.1F, 0.1F, 0.75F);
        GlStateManager.rotate(180.0F, 90.0F, 0.0F, 2.0F);
        GlStateManager.rotate(180.0F, 0.0F, 90.0F, 90.0F);
        Cylinder c = new Cylinder();

        c.setDrawStyle(100011);
        c.draw((float) (range - 0.45D), (float) (range - 0.5D), 0.0F, s, 0);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    public static void drawBoundingBox(IAxisAlignedBB aa) {
        ITessellator tessellator = MinecraftInstance.classProvider.getTessellatorInstance();
        IWorldRenderer worldRenderer = tessellator.getWorldRenderer();

        worldRenderer.begin(7, MinecraftInstance.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, MinecraftInstance.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, MinecraftInstance.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, MinecraftInstance.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMinZ()).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, MinecraftInstance.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, MinecraftInstance.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMinX(), aa.getMinY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMinZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMaxY(), aa.getMaxZ()).endVertex();
        worldRenderer.pos(aa.getMaxX(), aa.getMinY(), aa.getMaxZ()).endVertex();
        tessellator.draw();
    }

    public static void setColor(IEntity entity) {
        if (entity != null) {
            boolean flag = true;
        }

        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0F, -2000000.0F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
    }

    public static void drawCylinderESP(EntityLivingBase entity, int color, double x, double y, double z, int s) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(-entity.width, 0.0F, 1.0F, 0.0F);
        glColor(color);
        enableSmoothLine(1.0F);
        Cylinder c = new Cylinder();

        GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
        c.setDrawStyle(100011);
        c.draw(0.5F, 0.5F, entity.height - 0.2F, s, 1);
        disableSmoothLine();
        GL11.glPopMatrix();
    }

    public static void glColor(int hex) {
        float alpha = (float) (hex >> 24 & 255) / 255.0F;
        float red = (float) (hex >> 16 & 255) / 255.0F;
        float green = (float) (hex >> 8 & 255) / 255.0F;
        float blue = (float) (hex & 255) / 255.0F;

        GL11.glColor4f(red, green, blue, alpha == 0.0F ? 1.0F : alpha);
    }

    public static void enableSmoothLine(float width) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(width);
    }

    public static void disableSmoothLine() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
}

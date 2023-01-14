package me.utils.render;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class GLUtils {

    private static FloatBuffer colorBuffer;
    private static Map glCapMap = new HashMap();

    public static void disableStandardItemLighting() {
        GlStateManager.disableLighting();
        GlStateManager.disableLight(0);
        GlStateManager.disableLight(1);
        GlStateManager.disableColorMaterial();
    }

    public static void enableGUIStandardItemLighting() {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(165.0F, 1.0F, 0.0F, 0.0F);
        enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    public static void enableStandardItemLighting() {
        GlStateManager.enableLighting();
        GlStateManager.enableLight(0);
        GlStateManager.enableLight(1);
        GlStateManager.enableColorMaterial();
        GlStateManager.colorMaterial(1032, 5634);
        float n = 0.4F;
        float n2 = 0.6F;

        GL11.glLight(16384, 4609, setColorBuffer(0.6F, 0.6F, 0.6F, 1.0F));
        GL11.glLight(16384, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GL11.glLight(16385, 4609, setColorBuffer(0.6F, 0.6F, 0.6F, 1.0F));
        GL11.glLight(16385, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
        GlStateManager.shadeModel(7424);
        GL11.glLightModel(2899, setColorBuffer(0.4F, 0.4F, 0.4F, 1.0F));
    }

    public static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }

    public static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
    }

    private static FloatBuffer setColorBuffer(double p_setColorBuffer_0_, double p_setColorBuffer_2_, double p_setColorBuffer_4_, double p_setColorBuffer_6_) {
        return setColorBuffer((float) p_setColorBuffer_0_, (float) p_setColorBuffer_2_, (float) p_setColorBuffer_4_, (float) p_setColorBuffer_6_);
    }

    private static FloatBuffer setColorBuffer(float p_setColorBuffer_0_, float p_setColorBuffer_1_, float p_setColorBuffer_2_, float p_setColorBuffer_3_) {
        GLUtils.colorBuffer.clear();
        GLUtils.colorBuffer.put(p_setColorBuffer_0_).put(p_setColorBuffer_1_).put(p_setColorBuffer_2_).put(p_setColorBuffer_3_);
        GLUtils.colorBuffer.flip();
        return GLUtils.colorBuffer;
    }

    public static void setGLCap(int cap, boolean flag) {
        GLUtils.glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
        if (flag) {
            GL11.glEnable(cap);
        } else {
            GL11.glDisable(cap);
        }

    }

    public static void revertGLCap(int cap) {
        Boolean origCap = (Boolean) GLUtils.glCapMap.get(Integer.valueOf(cap));

        if (origCap != null) {
            if (origCap.booleanValue()) {
                GL11.glEnable(cap);
            } else {
                GL11.glDisable(cap);
            }
        }

    }

    public static void glEnable(int cap) {
        setGLCap(cap, true);
    }

    public static void glDisable(int cap) {
        setGLCap(cap, false);
    }

    public static void revertAllCaps() {
        Iterator localIterator = GLUtils.glCapMap.keySet().iterator();

        while (localIterator.hasNext()) {
            int cap = ((Integer) localIterator.next()).intValue();

            revertGLCap(cap);
        }

    }
}

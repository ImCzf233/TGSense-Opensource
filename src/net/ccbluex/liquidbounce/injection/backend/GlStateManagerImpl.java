package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager;
import net.minecraft.client.renderer.GlStateManager;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0014\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0004H\u0016J\b\u0010\b\u001a\u00020\u0004H\u0016J\b\u0010\t\u001a\u00020\u0004H\u0016J\b\u0010\n\u001a\u00020\u0004H\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016J\b\u0010\f\u001a\u00020\u0004H\u0016J\b\u0010\r\u001a\u00020\u0004H\u0016J\b\u0010\u000e\u001a\u00020\u0004H\u0016J\b\u0010\u000f\u001a\u00020\u0004H\u0016J\b\u0010\u0010\u001a\u00020\u0004H\u0016J\b\u0010\u0011\u001a\u00020\u0004H\u0016J\b\u0010\u0012\u001a\u00020\u0004H\u0016J\b\u0010\u0013\u001a\u00020\u0004H\u0016J\b\u0010\u0014\u001a\u00020\u0004H\u0016J(\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0006H\u0016¨\u0006\u001a"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/GlStateManagerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IGlStateManager;", "()V", "bindTexture", "", "textureID", "", "disableBlend", "disableCull", "disableLighting", "disableRescaleNormal", "disableTexture2D", "enableAlpha", "enableBlend", "enableColorMaterial", "enableTexture2D", "popAttrib", "popMatrix", "pushAttrib", "pushMatrix", "resetColor", "tryBlendFuncSeparate", "glSrcAlpha", "glOneMinusSrcAlpha", "glOne", "glZero", "LiquidBounce"}
)
public final class GlStateManagerImpl implements IGlStateManager {

    public static final GlStateManagerImpl INSTANCE;

    public void bindTexture(int textureID) {
        GlStateManager.bindTexture(textureID);
    }

    public void resetColor() {
        GlStateManager.resetColor();
    }

    public void enableTexture2D() {
        GlStateManager.enableTexture2D();
    }

    public void enableBlend() {
        GlStateManager.enableBlend();
    }

    public void tryBlendFuncSeparate(int glSrcAlpha, int glOneMinusSrcAlpha, int glOne, int glZero) {
        GlStateManager.tryBlendFuncSeparate(glSrcAlpha, glOneMinusSrcAlpha, glOne, glZero);
    }

    public void disableTexture2D() {
        GlStateManager.disableTexture2D();
    }

    public void disableBlend() {
        GlStateManager.disableBlend();
    }

    public void enableAlpha() {
        GlStateManager.enableAlpha();
    }

    public void disableLighting() {
        GlStateManager.disableLighting();
    }

    public void disableCull() {
        GlStateManager.disableCull();
    }

    public void enableColorMaterial() {
        GlStateManager.enableColorMaterial();
    }

    public void disableRescaleNormal() {
        GlStateManager.disableRescaleNormal();
    }

    public void pushMatrix() {
        GlStateManager.pushMatrix();
    }

    public void pushAttrib() {
        GlStateManager.pushAttrib();
    }

    public void popMatrix() {
        GlStateManager.popMatrix();
    }

    public void popAttrib() {
        GlStateManager.popAttrib();
    }

    static {
        GlStateManagerImpl glstatemanagerimpl = new GlStateManagerImpl();

        INSTANCE = glstatemanagerimpl;
    }
}

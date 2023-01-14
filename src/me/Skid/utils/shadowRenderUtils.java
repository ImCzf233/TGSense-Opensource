package me.Skid.utils;

import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0014H\u0007JH\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u0014H\u0007J0\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0014H\u0007J0\u0010\u001f\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010 \u001a\u00020!H\u0007J8\u0010\"\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0018\u001a\u00020\u0014H\u0007R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002R$\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\b\u0010\u0002\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\"\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000f0\u000e8\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0010\u0010\u0002¨\u0006#"},
    d2 = { "Lme/Skid/utils/shadowRenderUtils;", "", "()V", "DISPLAY_LISTS_2D", "", "DISPLAY_LISTS_2D$annotations", "deltaTime", "", "deltaTime$annotations", "getDeltaTime", "()I", "setDeltaTime", "(I)V", "glCapMap", "", "", "glCapMap$annotations", "drawGlowWithCustomAlpha", "", "x", "", "y", "width", "height", "alpha", "drawModalRectWithCustomSizedTexture", "u", "v", "textureWidth", "textureHeight", "drawShadowWithCustomAlpha", "drawTexturedRect", "image", "", "drawTexturedRectWithCustomAlpha", "LiquidBounce"}
)
public final class shadowRenderUtils {

    private static final Map glCapMap;
    private static int deltaTime;
    private static final int[] DISPLAY_LISTS_2D;
    public static final shadowRenderUtils INSTANCE;

    /** @deprecated */
    @JvmStatic
    private static void glCapMap$annotations() {}

    /** @deprecated */
    @JvmStatic
    public static void deltaTime$annotations() {}

    public static final int getDeltaTime() {
        return shadowRenderUtils.deltaTime;
    }

    public static final void setDeltaTime(int <set-?>) {
        shadowRenderUtils.deltaTime = <set-?>;
    }

    /** @deprecated */
    @JvmStatic
    private static void DISPLAY_LISTS_2D$annotations() {}

    @JvmStatic
    public static final void drawGlowWithCustomAlpha(float x, float y, float width, float height, float alpha) {
        drawTexturedRectWithCustomAlpha(x - (float) 9, y - (float) 9, 9.0F, 9.0F, "glowpaneltopleft", alpha);
        drawTexturedRectWithCustomAlpha(x - (float) 9, y + height, 9.0F, 9.0F, "glowpanelbottomleft", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y + height, 9.0F, 9.0F, "glowpanelbottomright", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y - (float) 9, 9.0F, 9.0F, "glowpaneltopright", alpha);
        drawTexturedRectWithCustomAlpha(x - (float) 9, y, 9.0F, height, "glowpanelleft", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y, 9.0F, height, "glowpanelright", alpha);
        drawTexturedRectWithCustomAlpha(x, y - (float) 9, width, 9.0F, "glowpaneltop", alpha);
        drawTexturedRectWithCustomAlpha(x, y + height, width, 9.0F, "glowpanelbottom", alpha);
    }

    @JvmStatic
    public static final void drawShadowWithCustomAlpha(float x, float y, float width, float height, float alpha) {
        drawTexturedRectWithCustomAlpha(x - (float) 9, y - (float) 9, 9.0F, 9.0F, "paneltopleft", alpha);
        drawTexturedRectWithCustomAlpha(x - (float) 9, y + height, 9.0F, 9.0F, "panelbottomleft", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y + height, 9.0F, 9.0F, "panelbottomright", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y - (float) 9, 9.0F, 9.0F, "paneltopright", alpha);
        drawTexturedRectWithCustomAlpha(x - (float) 9, y, 9.0F, height, "panelleft", alpha);
        drawTexturedRectWithCustomAlpha(x + width, y, 9.0F, height, "panelright", alpha);
        drawTexturedRectWithCustomAlpha(x, y - (float) 9, width, 9.0F, "paneltop", alpha);
        drawTexturedRectWithCustomAlpha(x, y + height, width, 9.0F, "panelbottom", alpha);
    }

    @JvmStatic
    public static final void drawTexturedRectWithCustomAlpha(float x, float y, float width, float height, @NotNull String image, float alpha) {
        Intrinsics.checkParameterIsNotNull(image, "image");
        GL11.glPushMatrix();
        boolean enableBlend = GL11.glIsEnabled(3042);
        boolean disableAlpha = !GL11.glIsEnabled(3008);

        if (!enableBlend) {
            GL11.glEnable(3042);
        }

        if (!disableAlpha) {
            GL11.glDisable(3008);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        Minecraft minecraft = Minecraft.getMinecraft();

        Intrinsics.checkExpressionValueIsNotNull(minecraft, "Minecraft.getMinecraft()");
        minecraft.getTextureManager().bindTexture(new ResourceLocation("liquidbounce/shadow/" + image + ".png"));
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
        if (!enableBlend) {
            GL11.glDisable(3042);
        }

        if (!disableAlpha) {
            GL11.glEnable(3008);
        }

        GlStateManager.resetColor();
        GL11.glPopMatrix();
    }

    @JvmStatic
    public static final void drawTexturedRect(float x, float y, float width, float height, @NotNull String image) {
        Intrinsics.checkParameterIsNotNull(image, "image");
        GL11.glPushMatrix();
        boolean enableBlend = GL11.glIsEnabled(3042);
        boolean disableAlpha = !GL11.glIsEnabled(3008);

        if (!enableBlend) {
            GL11.glEnable(3042);
        }

        if (!disableAlpha) {
            GL11.glDisable(3008);
        }

        Minecraft minecraft = Minecraft.getMinecraft();

        Intrinsics.checkExpressionValueIsNotNull(minecraft, "Minecraft.getMinecraft()");
        minecraft.getTextureManager().bindTexture(new ResourceLocation("langya/shadow/" + image + ".png"));
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
        if (!enableBlend) {
            GL11.glDisable(3042);
        }

        if (!disableAlpha) {
            GL11.glEnable(3008);
        }

        GL11.glPopMatrix();
    }

    @JvmStatic
    public static final void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator1 = Tessellator.getInstance();

        Intrinsics.checkExpressionValueIsNotNull(tessellator1, "tessellator1");
        BufferBuilder worldrenderer = tessellator1.getBuffer();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + width) * f), (double) ((v + height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + width) * f), (double) (v * f1)).endVertex();
        worldrenderer.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator1.draw();
    }

    static {
        shadowRenderUtils shadowrenderutils = new shadowRenderUtils();

        INSTANCE = shadowrenderutils;
        glCapMap = (Map) (new HashMap());
        DISPLAY_LISTS_2D = new int[4];
    }
}

package me.Skid.utils;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000bJ*\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u000b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00150\u001bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"},
    d2 = { "Lme/Skid/utils/ShadowUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "blurDirectory", "Lnet/minecraft/util/ResourceLocation;", "frameBuffer", "Lnet/minecraft/client/shader/Framebuffer;", "initFramebuffer", "lastHeight", "", "lastStrength", "", "lastWidth", "resultBuffer", "getResultBuffer", "()Lnet/minecraft/client/shader/Framebuffer;", "setResultBuffer", "(Lnet/minecraft/client/shader/Framebuffer;)V", "shaderGroup", "Lnet/minecraft/client/shader/ShaderGroup;", "initShaderIfRequired", "", "sc", "Lnet/minecraft/client/gui/ScaledResolution;", "strength", "shadow", "drawMethod", "Lkotlin/Function0;", "cutMethod", "LiquidBounce"}
)
public final class ShadowUtils extends MinecraftInstance {

    private static Framebuffer initFramebuffer;
    private static Framebuffer frameBuffer;
    @Nullable
    private static Framebuffer resultBuffer;
    private static ShaderGroup shaderGroup;
    private static int lastWidth;
    private static int lastHeight;
    private static float lastStrength;
    private static final ResourceLocation blurDirectory;
    public static final ShadowUtils INSTANCE;

    @Nullable
    public final Framebuffer getResultBuffer() {
        return ShadowUtils.resultBuffer;
    }

    public final void setResultBuffer(@Nullable Framebuffer <set-?>) {
        ShadowUtils.resultBuffer = <set-?>;
    }

    public final void initShaderIfRequired(@NotNull ScaledResolution sc, float strength) throws IOException {
        Intrinsics.checkParameterIsNotNull(sc, "sc");
        int width = sc.getScaledWidth();
        int height = sc.getScaledHeight();
        int factor = sc.getScaleFactor();
        int i;
        byte b0;
        ShaderGroup shadergroup;
        Object object;
        ShaderUniform shaderuniform;

        if (ShadowUtils.lastWidth != width || ShadowUtils.lastHeight != height || ShadowUtils.initFramebuffer == null || ShadowUtils.frameBuffer == null || ShadowUtils.shaderGroup == null) {
            ShadowUtils.initFramebuffer = new Framebuffer(width * factor, height * factor, true);
            Framebuffer framebuffer = ShadowUtils.initFramebuffer;

            if (ShadowUtils.initFramebuffer == null) {
                Intrinsics.throwNpe();
            }

            framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
            framebuffer = ShadowUtils.initFramebuffer;
            if (ShadowUtils.initFramebuffer == null) {
                Intrinsics.throwNpe();
            }

            framebuffer.setFramebufferFilter(9729);
            Minecraft minecraft = MinecraftInstance.mc2;

            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2, "mc2");
            ShadowUtils.shaderGroup = new ShaderGroup(minecraft.getTextureManager(), MinecraftInstance.mc2.getResourceManager(), ShadowUtils.initFramebuffer, ShadowUtils.blurDirectory);
            shadergroup = ShadowUtils.shaderGroup;
            if (ShadowUtils.shaderGroup == null) {
                Intrinsics.throwNpe();
            }

            shadergroup.createBindFramebuffers(width * factor, height * factor);
            shadergroup = ShadowUtils.shaderGroup;
            if (ShadowUtils.shaderGroup == null) {
                Intrinsics.throwNpe();
            }

            ShadowUtils.frameBuffer = shadergroup.mainFramebuffer;
            shadergroup = ShadowUtils.shaderGroup;
            if (ShadowUtils.shaderGroup == null) {
                Intrinsics.throwNpe();
            }

            ShadowUtils.resultBuffer = shadergroup.getFramebufferRaw("braindead");
            ShadowUtils.lastWidth = width;
            ShadowUtils.lastHeight = height;
            ShadowUtils.lastStrength = strength;
            i = 0;

            for (b0 = 1; i <= b0; ++i) {
                shadergroup = ShadowUtils.shaderGroup;
                if (ShadowUtils.shaderGroup == null) {
                    Intrinsics.throwNpe();
                }

                object = shadergroup.listShaders.get(i);
                Intrinsics.checkExpressionValueIsNotNull(object, "shaderGroup!!.listShaders[i]");
                shaderuniform = ((Shader) object).getShaderManager().getShaderUniform("Radius");
                if (shaderuniform == null) {
                    Intrinsics.throwNpe();
                }

                shaderuniform.set(strength);
            }
        }

        if (ShadowUtils.lastStrength != strength) {
            ShadowUtils.lastStrength = strength;
            i = 0;

            for (b0 = 1; i <= b0; ++i) {
                shadergroup = ShadowUtils.shaderGroup;
                if (ShadowUtils.shaderGroup == null) {
                    Intrinsics.throwNpe();
                }

                object = shadergroup.listShaders.get(i);
                Intrinsics.checkExpressionValueIsNotNull(object, "shaderGroup!!.listShaders[i]");
                shaderuniform = ((Shader) object).getShaderManager().getShaderUniform("Radius");
                if (shaderuniform == null) {
                    Intrinsics.throwNpe();
                }

                shaderuniform.set(strength);
            }
        }

    }

    public final void shadow(float strength, @NotNull Function0 drawMethod, @NotNull Function0 cutMethod) {
        Intrinsics.checkParameterIsNotNull(drawMethod, "drawMethod");
        Intrinsics.checkParameterIsNotNull(cutMethod, "cutMethod");
        if (OpenGlHelper.isFramebufferEnabled()) {
            ScaledResolution sc = new ScaledResolution(MinecraftInstance.mc2);
            int width = sc.getScaledWidth();
            int height = sc.getScaledHeight();

            this.initShaderIfRequired(sc, strength);
            if (ShadowUtils.initFramebuffer != null) {
                if (ShadowUtils.resultBuffer != null) {
                    if (ShadowUtils.frameBuffer != null) {
                        MinecraftInstance.mc2.getFramebuffer().unbindFramebuffer();
                        Framebuffer framebuffer = ShadowUtils.initFramebuffer;

                        if (ShadowUtils.initFramebuffer == null) {
                            Intrinsics.throwNpe();
                        }

                        framebuffer.framebufferClear();
                        framebuffer = ShadowUtils.resultBuffer;
                        if (ShadowUtils.resultBuffer == null) {
                            Intrinsics.throwNpe();
                        }

                        framebuffer.framebufferClear();
                        framebuffer = ShadowUtils.initFramebuffer;
                        if (ShadowUtils.initFramebuffer == null) {
                            Intrinsics.throwNpe();
                        }

                        framebuffer.bindFramebuffer(true);
                        drawMethod.invoke();
                        framebuffer = ShadowUtils.frameBuffer;
                        if (ShadowUtils.frameBuffer == null) {
                            Intrinsics.throwNpe();
                        }

                        framebuffer.bindFramebuffer(true);
                        ShaderGroup shadergroup = ShadowUtils.shaderGroup;

                        if (ShadowUtils.shaderGroup == null) {
                            Intrinsics.throwNpe();
                        }

                        shadergroup.render(MinecraftInstance.mc2.timer.renderPartialTicks);
                        MinecraftInstance.mc2.getFramebuffer().bindFramebuffer(true);
                        framebuffer = ShadowUtils.resultBuffer;
                        if (ShadowUtils.resultBuffer == null) {
                            Intrinsics.throwNpe();
                        }

                        double d0 = (double) framebuffer.framebufferWidth;
                        Framebuffer framebuffer1 = ShadowUtils.resultBuffer;

                        if (ShadowUtils.resultBuffer == null) {
                            Intrinsics.throwNpe();
                        }

                        double fr_width = d0 / (double) framebuffer1.framebufferTextureWidth;

                        framebuffer = ShadowUtils.resultBuffer;
                        if (ShadowUtils.resultBuffer == null) {
                            Intrinsics.throwNpe();
                        }

                        d0 = (double) framebuffer.framebufferHeight;
                        framebuffer1 = ShadowUtils.resultBuffer;
                        if (ShadowUtils.resultBuffer == null) {
                            Intrinsics.throwNpe();
                        }

                        double fr_height = d0 / (double) framebuffer1.framebufferTextureHeight;
                        Tessellator tessellator = Tessellator.getInstance();

                        Intrinsics.checkExpressionValueIsNotNull(tessellator, "tessellator");
                        BufferBuilder worldrenderer = tessellator.getBuffer();

                        GL11.glPushMatrix();
                        GlStateManager.disableLighting();
                        GlStateManager.disableAlpha();
                        GlStateManager.enableTexture2D();
                        GlStateManager.disableDepth();
                        GlStateManager.depthMask(false);
                        GlStateManager.colorMask(true, true, true, true);
                        Stencil.write(false);
                        cutMethod.invoke();
                        Stencil.erase(false);
                        GlStateManager.enableBlend();
                        GlStateManager.blendFunc(770, 771);
                        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                        framebuffer = ShadowUtils.resultBuffer;
                        if (ShadowUtils.resultBuffer == null) {
                            Intrinsics.throwNpe();
                        }

                        framebuffer.bindFramebufferTexture();
                        GL11.glTexParameteri(3553, 10242, '�?');
                        GL11.glTexParameteri(3553, 10243, '�?');
                        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                        worldrenderer.pos(0.0D, (double) height, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
                        worldrenderer.pos((double) width, (double) height, 0.0D).tex(fr_width, 0.0D).color(255, 255, 255, 255).endVertex();
                        worldrenderer.pos((double) width, 0.0D, 0.0D).tex(fr_width, fr_height).color(255, 255, 255, 255).endVertex();
                        worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, fr_height).color(255, 255, 255, 255).endVertex();
                        tessellator.draw();
                        framebuffer = ShadowUtils.resultBuffer;
                        if (ShadowUtils.resultBuffer == null) {
                            Intrinsics.throwNpe();
                        }

                        framebuffer.unbindFramebufferTexture();
                        GlStateManager.disableBlend();
                        GlStateManager.enableAlpha();
                        GlStateManager.enableDepth();
                        GlStateManager.depthMask(true);
                        Stencil.dispose();
                        GL11.glPopMatrix();
                        GlStateManager.resetColor();
                        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                        GlStateManager.enableBlend();
                        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    }
                }
            }
        }
    }

    static {
        ShadowUtils shadowutils = new ShadowUtils();

        INSTANCE = shadowutils;
        blurDirectory = new ResourceLocation("shaders/shadow.json");
    }
}

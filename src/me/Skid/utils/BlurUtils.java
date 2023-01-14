package me.Skid.utils;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JF\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00150\u001eH\u0007J0\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\nH\u0007J8\u0010$\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020\n2\u0006\u0010%\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\nH\u0007JJ\u0010&\u001a\u00020\u00152\u0006\u0010\'\u001a\u00020\n2\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\n2\u0006\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020\n2\u0006\u0010+\u001a\u00020\n2\b\b\u0002\u0010,\u001a\u00020\u001cH\u0002J\b\u0010-\u001a\u00020\u0015H\u0002J\u001e\u0010.\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\b2\u0006\u0010*\u001a\u00020\b2\u0006\u0010+\u001a\u00020\bR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"},
    d2 = { "Lme/Skid/utils/BlurUtils;", "", "()V", "framebuffer", "Lnet/minecraft/client/shader/Framebuffer;", "kotlin.jvm.PlatformType", "frbuffer", "lastFactor", "", "lastH", "", "lastHeight", "lastStrength", "lastW", "lastWeight", "lastWidth", "lastX", "lastY", "shaderGroup", "Lnet/minecraft/client/shader/ShaderGroup;", "blur", "", "posX", "posY", "posXEnd", "posYEnd", "blurStrength", "displayClipMask", "", "triggerMethod", "Lkotlin/Function0;", "blurArea", "x", "y", "x2", "y2", "blurAreaRounded", "rad", "setValues", "strength", "w", "h", "width", "height", "force", "setupFramebuffers", "sizeHasChanged", "scaleFactor", "LiquidBounce"}
)
public final class BlurUtils {

    private static final ShaderGroup shaderGroup;
    private static final Framebuffer framebuffer;
    private static final Framebuffer frbuffer;
    private static int lastFactor;
    private static int lastWidth;
    private static int lastHeight;
    private static int lastWeight;
    private static float lastX;
    private static float lastY;
    private static float lastW;
    private static float lastH;
    private static float lastStrength;
    public static final BlurUtils INSTANCE;

    private final void setupFramebuffers() {
        try {
            BlurUtils.shaderGroup.createBindFramebuffers(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        } catch (Exception exception) {
            ClientUtils.getLogger().error("Exception caught while setting up shader group", (Throwable) exception);
        }

    }

    private final void setValues(float strength, float x, float y, float w, float h, float width, float height, boolean force) {
        if (force || strength != BlurUtils.lastStrength || BlurUtils.lastX != x || BlurUtils.lastY != y || BlurUtils.lastW != w || BlurUtils.lastH != h) {
            BlurUtils.lastStrength = strength;
            BlurUtils.lastX = x;
            BlurUtils.lastY = y;
            BlurUtils.lastW = w;
            BlurUtils.lastH = h;
            int i = 0;

            for (byte b0 = 1; i <= b0; ++i) {
                Object object = BlurUtils.shaderGroup.listShaders.get(i);

                Intrinsics.checkExpressionValueIsNotNull(object, "shaderGroup.listShaders[i]");
                ShaderUniform shaderuniform = ((Shader) object).getShaderManager().getShaderUniform("Radius");

                if (shaderuniform != null) {
                    shaderuniform.set(strength);
                }

                object = BlurUtils.shaderGroup.listShaders.get(i);
                Intrinsics.checkExpressionValueIsNotNull(object, "shaderGroup.listShaders[i]");
                shaderuniform = ((Shader) object).getShaderManager().getShaderUniform("BlurXY");
                if (shaderuniform != null) {
                    shaderuniform.set(x, height - y - h);
                }

                object = BlurUtils.shaderGroup.listShaders.get(i);
                Intrinsics.checkExpressionValueIsNotNull(object, "shaderGroup.listShaders[i]");
                shaderuniform = ((Shader) object).getShaderManager().getShaderUniform("BlurCoord");
                if (shaderuniform != null) {
                    shaderuniform.set(w, h);
                }
            }

        }
    }

    static void setValues$default(BlurUtils blurutils, float f, float f1, float f2, float f3, float f4, float f5, float f6, boolean flag, int i, Object object) {
        if ((i & 128) != 0) {
            flag = false;
        }

        blurutils.setValues(f, f1, f2, f3, f4, f5, f6, flag);
    }

    @JvmStatic
    public static final void blur(float posX, float posY, float posXEnd, float posYEnd, float blurStrength, boolean displayClipMask, @NotNull Function0 triggerMethod) {
        Intrinsics.checkParameterIsNotNull(triggerMethod, "triggerMethod");
        if (OpenGlHelper.isFramebufferEnabled()) {
            float x = posX;
            float y = posY;
            float x2 = posXEnd;
            float y2 = posYEnd;

            if (posX > posXEnd) {
                x = posXEnd;
                x2 = posX;
            }

            if (posY > posYEnd) {
                y = posYEnd;
                y2 = posYEnd;
            }

            ScaledResolution sc = new ScaledResolution(Minecraft.getMinecraft());
            int scaleFactor = sc.getScaleFactor();
            int width = sc.getScaledWidth();
            int height = sc.getScaledHeight();

            if (BlurUtils.INSTANCE.sizeHasChanged(scaleFactor, width, height)) {
                BlurUtils.INSTANCE.setupFramebuffers();
                BlurUtils.INSTANCE.setValues(blurStrength, x, y, x2 - x, y2 - y, (float) width, (float) height, true);
            }

            BlurUtils.lastFactor = scaleFactor;
            BlurUtils.lastWidth = width;
            BlurUtils.lastHeight = height;
            setValues$default(BlurUtils.INSTANCE, blurStrength, x, y, x2 - x, y2 - y, (float) width, (float) height, false, 128, (Object) null);
            BlurUtils.framebuffer.bindFramebuffer(true);
            BlurUtils.shaderGroup.render(Minecraft.getMinecraft().timer.renderPartialTicks);
            Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
            Stencil.write(displayClipMask);
            triggerMethod.invoke();
            Stencil.erase(true);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.pushMatrix();
            GlStateManager.colorMask(true, true, true, false);
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableAlpha();
            BlurUtils.frbuffer.bindFramebufferTexture();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            double f2 = (double) BlurUtils.frbuffer.framebufferWidth / (double) BlurUtils.frbuffer.framebufferTextureWidth;
            double f3 = (double) BlurUtils.frbuffer.framebufferHeight / (double) BlurUtils.frbuffer.framebufferTextureHeight;
            Tessellator tessellator = Tessellator.getInstance();

            Intrinsics.checkExpressionValueIsNotNull(tessellator, "tessellator");
            BufferBuilder worldrenderer = tessellator.getBuffer();

            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos(0.0D, (double) height, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
            worldrenderer.pos((double) width, (double) height, 0.0D).tex(f2, 0.0D).color(255, 255, 255, 255).endVertex();
            worldrenderer.pos((double) width, 0.0D, 0.0D).tex(f2, f3).color(255, 255, 255, 255).endVertex();
            worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, f3).color(255, 255, 255, 255).endVertex();
            tessellator.draw();
            BlurUtils.frbuffer.unbindFramebufferTexture();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.popMatrix();
            GlStateManager.disableBlend();
            Stencil.dispose();
            GlStateManager.enableAlpha();
        }
    }

    @JvmStatic
    public static final void blurArea(final float x, final float y, final float x2, final float y2, float blurStrength) {
        blur(x, y, x2, y2, blurStrength, false, (Function0) (new Function0(0) {
            public final void invoke() {
                GlStateManager.enableBlend();
                GlStateManager.disableTexture2D();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                RenderUtils.quickDrawRect(x, y, x2, y2);
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
            }
        }));
    }

    @JvmStatic
    public static final void blurAreaRounded(final float x, final float y, final float x2, final float y2, final float rad, float blurStrength) {
        blur(x, y, x2, y2, blurStrength, false, (Function0) (new Function0(0) {
            public final void invoke() {
                GlStateManager.enableBlend();
                GlStateManager.disableTexture2D();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                RenderUtils.fastRoundedRect(x, y, x2, y2, rad);
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
            }
        }));
    }

    public final boolean sizeHasChanged(int scaleFactor, int width, int height) {
        return BlurUtils.lastFactor != scaleFactor || BlurUtils.lastWidth != width || BlurUtils.lastHeight != height;
    }

    static {
        BlurUtils blurutils = new BlurUtils();

        INSTANCE = blurutils;
        Minecraft minecraft = Minecraft.getMinecraft();

        Intrinsics.checkExpressionValueIsNotNull(minecraft, "Minecraft.getMinecraft()");
        TextureManager texturemanager = minecraft.getTextureManager();
        Minecraft minecraft1 = Minecraft.getMinecraft();

        Intrinsics.checkExpressionValueIsNotNull(minecraft1, "Minecraft.getMinecraft()");
        shaderGroup = new ShaderGroup(texturemanager, minecraft1.getResourceManager(), Minecraft.getMinecraft().getFramebuffer(), new ResourceLocation("shaders/post/blurarea.json"));
        framebuffer = BlurUtils.shaderGroup.mainFramebuffer;
        frbuffer = BlurUtils.shaderGroup.getFramebufferRaw("result");
        BlurUtils.lastStrength = 5.0F;
    }
}

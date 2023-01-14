package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.IEntityRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.shader.IShaderGroup;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u000eH\u0016J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0016H\u0016J\u0018\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\fH\u0016J\b\u0010\u001d\u001a\u00020\fH\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001e"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/EntityRendererImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IEntityRenderer;", "wrapped", "Lnet/minecraft/client/renderer/EntityRenderer;", "(Lnet/minecraft/client/renderer/EntityRenderer;)V", "shaderGroup", "Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IShaderGroup;", "getShaderGroup", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IShaderGroup;", "getWrapped", "()Lnet/minecraft/client/renderer/EntityRenderer;", "disableLightmap", "", "equals", "", "other", "", "isShaderActive", "loadShader", "resourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "loadShader2", "Lnet/minecraft/util/ResourceLocation;", "setupCameraTransform", "partialTicks", "", "pass", "", "setupOverlayRendering", "stopUseShader", "LiquidBounce"}
)
public final class EntityRendererImpl implements IEntityRenderer {

    @NotNull
    private final EntityRenderer wrapped;

    @Nullable
    public IShaderGroup getShaderGroup() {
        ShaderGroup shadergroup = this.wrapped.getShaderGroup();
        IShaderGroup ishadergroup;

        if (shadergroup != null) {
            ShaderGroup $this$wrap$iv = shadergroup;
            boolean $i$f$wrap = false;

            ishadergroup = (IShaderGroup) (new ShaderGroupImpl($this$wrap$iv));
        } else {
            ishadergroup = null;
        }

        return ishadergroup;
    }

    public void disableLightmap() {
        this.wrapped.disableLightmap();
    }

    public boolean isShaderActive() {
        return this.wrapped.isShaderActive();
    }

    public void loadShader(@NotNull IResourceLocation resourceLocation) {
        Intrinsics.checkParameterIsNotNull(resourceLocation, "resourceLocation");
        EntityRenderer entityrenderer = this.wrapped;
        boolean $i$f$unwrap = false;
        ResourceLocation resourcelocation = ((ResourceLocationImpl) resourceLocation).getWrapped();

        entityrenderer.loadShader(resourcelocation);
    }

    public void loadShader2(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkParameterIsNotNull(resourceLocation, "resourceLocation");
        EntityRenderer entityrenderer = this.wrapped;
        boolean flag = false;
        boolean flag1 = false;
        boolean $i$a$-also-EntityRendererImpl$loadShader2$1 = false;
        boolean $i$f$unwrap = false;

        if (this == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.EntityRendererImpl");
        } else {
            ((EntityRendererImpl) this).getWrapped();
            entityrenderer.loadShader(resourceLocation);
        }
    }

    public void stopUseShader() {
        this.wrapped.stopUseShader();
    }

    public void setupCameraTransform(float partialTicks, int pass) {
        this.wrapped.setupCameraTransform(partialTicks, pass);
    }

    public void setupOverlayRendering() {
        this.wrapped.setupOverlayRendering();
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof EntityRendererImpl && Intrinsics.areEqual(((EntityRendererImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final EntityRenderer getWrapped() {
        return this.wrapped;
    }

    public EntityRendererImpl(@NotNull EntityRenderer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

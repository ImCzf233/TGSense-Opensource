package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.IAbstractTexture;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.ITextureManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fH\u0016J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/TextureManagerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/ITextureManager;", "wrapped", "Lnet/minecraft/client/renderer/texture/TextureManager;", "(Lnet/minecraft/client/renderer/texture/TextureManager;)V", "getWrapped", "()Lnet/minecraft/client/renderer/texture/TextureManager;", "bindTexture", "", "image", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "bindTexture2", "Lnet/minecraft/util/ResourceLocation;", "equals", "", "other", "", "loadTexture", "textureLocation", "textureObj", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/IAbstractTexture;", "LiquidBounce"}
)
public final class TextureManagerImpl implements ITextureManager {

    @NotNull
    private final TextureManager wrapped;

    public boolean loadTexture(@NotNull IResourceLocation textureLocation, @NotNull IAbstractTexture textureObj) {
        Intrinsics.checkParameterIsNotNull(textureLocation, "textureLocation");
        Intrinsics.checkParameterIsNotNull(textureObj, "textureObj");
        TextureManager texturemanager = this.wrapped;
        boolean $i$f$unwrap = false;
        ResourceLocation resourcelocation = ((ResourceLocationImpl) textureLocation).getWrapped();

        $i$f$unwrap = false;
        AbstractTexture abstracttexture = ((AbstractTextureImpl) textureObj).getWrapped();

        return texturemanager.loadTexture(resourcelocation, (ITextureObject) abstracttexture);
    }

    public void bindTexture(@NotNull IResourceLocation image) {
        Intrinsics.checkParameterIsNotNull(image, "image");
        TextureManager texturemanager = this.wrapped;
        boolean $i$f$unwrap = false;
        ResourceLocation resourcelocation = ((ResourceLocationImpl) image).getWrapped();

        texturemanager.bindTexture(resourcelocation);
    }

    public void bindTexture2(@NotNull ResourceLocation image) {
        Intrinsics.checkParameterIsNotNull(image, "image");
        TextureManager texturemanager = this.wrapped;
        boolean flag = false;
        boolean flag1 = false;
        boolean $i$a$-also-TextureManagerImpl$bindTexture2$1 = false;
        boolean $i$f$unwrap = false;

        if (this == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.TextureManagerImpl");
        } else {
            ((TextureManagerImpl) this).getWrapped();
            texturemanager.bindTexture(image);
        }
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof TextureManagerImpl && Intrinsics.areEqual(((TextureManagerImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final TextureManager getWrapped() {
        return this.wrapped;
    }

    public TextureManagerImpl(@NotNull TextureManager wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

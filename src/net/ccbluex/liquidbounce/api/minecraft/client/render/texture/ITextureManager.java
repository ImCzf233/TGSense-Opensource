package net.ccbluex.liquidbounce.api.minecraft.client.render.texture;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH&¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/ITextureManager;", "", "bindTexture", "", "image", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "bindTexture2", "Lnet/minecraft/util/ResourceLocation;", "loadTexture", "", "textureLocation", "textureObj", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/IAbstractTexture;", "LiquidBounce"}
)
public interface ITextureManager {

    boolean loadTexture(@NotNull IResourceLocation iresourcelocation, @NotNull IAbstractTexture iabstracttexture);

    void bindTexture(@NotNull IResourceLocation iresourcelocation);

    void bindTexture2(@NotNull ResourceLocation resourcelocation);
}

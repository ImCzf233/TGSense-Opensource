package net.ccbluex.liquidbounce.api.minecraft.client.renderer;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.client.shader.IShaderGroup;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH&J\u0010\u0010\r\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u000eH&J\u0018\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H&J\b\u0010\u0014\u001a\u00020\u0007H&J\b\u0010\u0015\u001a\u00020\u0007H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0016"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IEntityRenderer;", "", "shaderGroup", "Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IShaderGroup;", "getShaderGroup", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IShaderGroup;", "disableLightmap", "", "isShaderActive", "", "loadShader", "resourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "loadShader2", "Lnet/minecraft/util/ResourceLocation;", "setupCameraTransform", "partialTicks", "", "i", "", "setupOverlayRendering", "stopUseShader", "LiquidBounce"}
)
public interface IEntityRenderer {

    @Nullable
    IShaderGroup getShaderGroup();

    void disableLightmap();

    boolean isShaderActive();

    void loadShader(@NotNull IResourceLocation iresourcelocation);

    void loadShader2(@NotNull ResourceLocation resourcelocation);

    void stopUseShader();

    void setupCameraTransform(float f, int i);

    void setupOverlayRendering();
}

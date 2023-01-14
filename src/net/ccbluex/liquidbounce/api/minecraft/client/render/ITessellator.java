package net.ccbluex.liquidbounce.api.minecraft.client.render;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\b"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/ITessellator;", "", "worldRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IWorldRenderer;", "getWorldRenderer", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IWorldRenderer;", "draw", "", "LiquidBounce"}
)
public interface ITessellator {

    @NotNull
    IWorldRenderer getWorldRenderer();

    void draw();
}

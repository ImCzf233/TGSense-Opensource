package net.ccbluex.liquidbounce.api.minecraft.client.renderer;

import kotlin.Metadata;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IRenderGlobal;", "", "loadRenderers", "", "LiquidBounce"}
)
public interface IRenderGlobal {

    void loadRenderers();
}

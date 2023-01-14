package net.ccbluex.liquidbounce.api.minecraft.block.material;

import kotlin.Metadata;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\u0005"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "", "isReplaceable", "", "()Z", "LiquidBounce"}
)
public interface IMaterial {

    boolean isReplaceable();
}

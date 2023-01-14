package net.ccbluex.liquidbounce.api.minecraft.item;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\u0005"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBucket;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "isFull", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "LiquidBounce"}
)
public interface IItemBucket extends IItem {

    @NotNull
    IBlock isFull();
}

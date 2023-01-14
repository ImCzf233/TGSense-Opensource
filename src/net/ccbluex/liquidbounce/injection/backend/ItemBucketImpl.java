package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBucket;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ItemBucketImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/ItemImpl;", "Lnet/minecraft/item/ItemBucket;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBucket;", "wrapped", "(Lnet/minecraft/item/ItemBucket;)V", "isFull", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "LiquidBounce"}
)
public final class ItemBucketImpl extends ItemImpl implements IItemBucket {

    @NotNull
    public IBlock isFull() {
        Block block = ((ItemBucket) this.getWrapped()).containedBlock;

        Intrinsics.checkExpressionValueIsNotNull(block, "wrapped.containedBlock");
        return (IBlock) (new BlockImpl(block));
    }

    public ItemBucketImpl(@NotNull ItemBucket wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Item) wrapped);
    }
}

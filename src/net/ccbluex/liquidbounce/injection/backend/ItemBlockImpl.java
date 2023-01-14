package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u000e"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ItemBlockImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/ItemImpl;", "Lnet/minecraft/item/ItemBlock;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBlock;", "wrapped", "(Lnet/minecraft/item/ItemBlock;)V", "block", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "getBlock", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "unlocalizedName", "", "getUnlocalizedName", "()Ljava/lang/String;", "LiquidBounce"}
)
public final class ItemBlockImpl extends ItemImpl implements IItemBlock {

    @NotNull
    public IBlock getBlock() {
        Block block = ((ItemBlock) this.getWrapped()).getBlock();

        Intrinsics.checkExpressionValueIsNotNull(block, "wrapped.block");
        return (IBlock) (new BlockImpl(block));
    }

    @NotNull
    public String getUnlocalizedName() {
        String s = ((ItemBlock) this.getWrapped()).getTranslationKey();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.unlocalizedName");
        return s;
    }

    public ItemBlockImpl(@NotNull ItemBlock wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Item) wrapped);
    }
}

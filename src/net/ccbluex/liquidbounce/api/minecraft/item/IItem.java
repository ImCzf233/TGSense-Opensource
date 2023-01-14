package net.ccbluex.liquidbounce.api.minecraft.item;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\rH&J\b\u0010\u000e\u001a\u00020\u000fH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0010"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "", "unlocalizedName", "", "getUnlocalizedName", "()Ljava/lang/String;", "asItemArmor", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemArmor;", "asItemBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBlock;", "asItemBucket", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBucket;", "asItemPotion", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemPotion;", "asItemSword", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemSword;", "LiquidBounce"}
)
public interface IItem {

    @NotNull
    String getUnlocalizedName();

    @NotNull
    IItemArmor asItemArmor();

    @NotNull
    IItemPotion asItemPotion();

    @NotNull
    IItemBlock asItemBlock();

    @NotNull
    IItemSword asItemSword();

    @NotNull
    IItemBucket asItemBucket();
}

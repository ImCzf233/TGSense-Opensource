package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemArmor;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBucket;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemPotion;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemSword;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000D\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\u001b"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ItemImpl;", "T", "Lnet/minecraft/item/Item;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "wrapped", "(Lnet/minecraft/item/Item;)V", "unlocalizedName", "", "getUnlocalizedName", "()Ljava/lang/String;", "getWrapped", "()Lnet/minecraft/item/Item;", "Lnet/minecraft/item/Item;", "asItemArmor", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemArmor;", "asItemBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBlock;", "asItemBucket", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBucket;", "asItemPotion", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemPotion;", "asItemSword", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemSword;", "equals", "", "other", "", "LiquidBounce"}
)
public class ItemImpl implements IItem {

    @NotNull
    private final Item wrapped;

    @NotNull
    public String getUnlocalizedName() {
        String s = this.wrapped.getTranslationKey();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.unlocalizedName");
        return s;
    }

    @NotNull
    public IItemArmor asItemArmor() {
        ItemArmorImpl itemarmorimpl = new ItemArmorImpl;
        Item item = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
        } else {
            itemarmorimpl.<init>((ItemArmor) item);
            return (IItemArmor) itemarmorimpl;
        }
    }

    @NotNull
    public IItemPotion asItemPotion() {
        ItemPotionImpl itempotionimpl = new ItemPotionImpl;
        Item item = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemPotion");
        } else {
            itempotionimpl.<init>((ItemPotion) item);
            return (IItemPotion) itempotionimpl;
        }
    }

    @NotNull
    public IItemBlock asItemBlock() {
        ItemBlockImpl itemblockimpl = new ItemBlockImpl;
        Item item = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemBlock");
        } else {
            itemblockimpl.<init>((ItemBlock) item);
            return (IItemBlock) itemblockimpl;
        }
    }

    @NotNull
    public IItemSword asItemSword() {
        ItemSwordImpl itemswordimpl = new ItemSwordImpl;
        Item item = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemSword");
        } else {
            itemswordimpl.<init>((ItemSword) item);
            return (IItemSword) itemswordimpl;
        }
    }

    @NotNull
    public IItemBucket asItemBucket() {
        ItemBucketImpl itembucketimpl = new ItemBucketImpl;
        Item item = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemBucket");
        } else {
            itembucketimpl.<init>((ItemBucket) item);
            return (IItemBucket) itembucketimpl;
        }
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ItemImpl && Intrinsics.areEqual(((ItemImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final Item getWrapped() {
        return this.wrapped;
    }

    public ItemImpl(@NotNull Item wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

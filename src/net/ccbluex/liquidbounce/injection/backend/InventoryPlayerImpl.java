package net.ccbluex.liquidbounce.injection.backend;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.util.IWrappedArray;
import net.ccbluex.liquidbounce.api.util.WrappedListArrayAdapter;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0016\u001a\u00020\u000bH\u0016J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002J\n\u0010\u001b\u001a\u0004\u0018\u00010\u0007H\u0016J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0016\u001a\u00020\u000bH\u0016R\u001c\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR$\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001d"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/InventoryPlayerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IInventoryPlayer;", "wrapped", "Lnet/minecraft/entity/player/InventoryPlayer;", "(Lnet/minecraft/entity/player/InventoryPlayer;)V", "armorInventory", "Lnet/ccbluex/liquidbounce/api/util/IWrappedArray;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "getArmorInventory", "()Lnet/ccbluex/liquidbounce/api/util/IWrappedArray;", "value", "", "currentItem", "getCurrentItem", "()I", "setCurrentItem", "(I)V", "mainInventory", "getMainInventory", "getWrapped", "()Lnet/minecraft/entity/player/InventoryPlayer;", "armorItemInSlot", "slot", "equals", "", "other", "", "getCurrentItemInHand", "getStackInSlot", "LiquidBounce"}
)
public final class InventoryPlayerImpl implements IInventoryPlayer {

    @NotNull
    private final InventoryPlayer wrapped;

    @NotNull
    public IWrappedArray getMainInventory() {
        NonNullList nonnulllist = this.wrapped.mainInventory;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.mainInventory, "wrapped.mainInventory");
        return (IWrappedArray) (new WrappedListArrayAdapter((List) nonnulllist, (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    @NotNull
    public IWrappedArray getArmorInventory() {
        NonNullList nonnulllist = this.wrapped.armorInventory;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.armorInventory, "wrapped.armorInventory");
        return (IWrappedArray) (new WrappedListArrayAdapter((List) nonnulllist, (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    public int getCurrentItem() {
        return this.wrapped.currentItem;
    }

    public void setCurrentItem(int value) {
        this.wrapped.currentItem = value;
    }

    @Nullable
    public IItemStack getStackInSlot(int slot) {
        ItemStack itemstack = this.wrapped.getStackInSlot(slot);
        IItemStack iitemstack;

        if (itemstack != null) {
            ItemStack $this$wrap$iv = itemstack;
            boolean $i$f$wrap = false;

            iitemstack = (IItemStack) (new ItemStackImpl($this$wrap$iv));
        } else {
            iitemstack = null;
        }

        return iitemstack;
    }

    @Nullable
    public IItemStack armorItemInSlot(int slot) {
        ItemStack itemstack = this.wrapped.armorItemInSlot(3 - slot);
        IItemStack iitemstack;

        if (itemstack != null) {
            ItemStack $this$wrap$iv = itemstack;
            boolean $i$f$wrap = false;

            iitemstack = (IItemStack) (new ItemStackImpl($this$wrap$iv));
        } else {
            iitemstack = null;
        }

        return iitemstack;
    }

    @Nullable
    public IItemStack getCurrentItemInHand() {
        ItemStack itemstack = this.wrapped.getCurrentItem();
        IItemStack iitemstack;

        if (itemstack != null) {
            ItemStack $this$wrap$iv = itemstack;
            boolean $i$f$wrap = false;

            iitemstack = (IItemStack) (new ItemStackImpl($this$wrap$iv));
        } else {
            iitemstack = null;
        }

        return iitemstack;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof InventoryPlayerImpl && Intrinsics.areEqual(((InventoryPlayerImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final InventoryPlayer getWrapped() {
        return this.wrapped;
    }

    public InventoryPlayerImpl(@NotNull InventoryPlayer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

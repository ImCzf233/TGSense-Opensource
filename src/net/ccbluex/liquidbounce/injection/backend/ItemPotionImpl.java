package net.ccbluex.liquidbounce.injection.backend;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemPotion;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ItemPotionImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/ItemImpl;", "Lnet/minecraft/item/ItemPotion;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemPotion;", "wrapped", "(Lnet/minecraft/item/ItemPotion;)V", "getEffects", "", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "LiquidBounce"}
)
public final class ItemPotionImpl extends ItemImpl implements IItemPotion {

    @NotNull
    public Collection getEffects(@NotNull IItemStack stack) {
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        boolean $i$f$unwrap = false;
        ItemStack itemstack = ((ItemStackImpl) stack).getWrapped();
        Collection collection = (Collection) PotionUtils.getEffectsFromStack(itemstack);
        Function1 function1 = (Function1) null.INSTANCE;
        Function1 function11 = (Function1) null.INSTANCE;
        Function1 function12 = function1;
        Collection collection1 = collection;

        return (Collection) (new WrappedCollection(collection1, function12, function11));
    }

    public ItemPotionImpl(@NotNull ItemPotion wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Item) wrapped);
    }
}

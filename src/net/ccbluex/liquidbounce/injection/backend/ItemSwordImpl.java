package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemSword;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\n"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ItemSwordImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/ItemImpl;", "Lnet/minecraft/item/ItemSword;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemSword;", "wrapped", "(Lnet/minecraft/item/ItemSword;)V", "damageVsEntity", "", "getDamageVsEntity", "()F", "LiquidBounce"}
)
public final class ItemSwordImpl extends ItemImpl implements IItemSword {

    public float getDamageVsEntity() {
        return ((ItemSword) this.getWrapped()).getAttackDamage();
    }

    public ItemSwordImpl(@NotNull ItemSword wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Item) wrapped);
    }
}

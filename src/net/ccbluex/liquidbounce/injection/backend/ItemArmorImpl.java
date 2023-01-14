package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemArmor;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.minecraft.IArmorMaterial;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0015"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ItemArmorImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/ItemImpl;", "Lnet/minecraft/item/ItemArmor;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemArmor;", "wrapped", "(Lnet/minecraft/item/ItemArmor;)V", "armorMaterial", "Lnet/ccbluex/liquidbounce/api/minecraft/minecraft/IArmorMaterial;", "getArmorMaterial", "()Lnet/ccbluex/liquidbounce/api/minecraft/minecraft/IArmorMaterial;", "armorType", "", "getArmorType", "()I", "unlocalizedName", "", "getUnlocalizedName", "()Ljava/lang/String;", "getColor", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "LiquidBounce"}
)
public final class ItemArmorImpl extends ItemImpl implements IItemArmor {

    @NotNull
    public IArmorMaterial getArmorMaterial() {
        ArmorMaterial armormaterial = ((ItemArmor) this.getWrapped()).getArmorMaterial();

        Intrinsics.checkExpressionValueIsNotNull(armormaterial, "wrapped.armorMaterial");
        ArmorMaterial $this$wrap$iv = armormaterial;
        boolean $i$f$wrap = false;

        return (IArmorMaterial) (new ArmorMaterialImpl($this$wrap$iv));
    }

    public int getArmorType() {
        EntityEquipmentSlot entityequipmentslot = ((ItemArmor) this.getWrapped()).armorType;

        Intrinsics.checkExpressionValueIsNotNull(entityequipmentslot, "wrapped.armorType");
        return entityequipmentslot.getIndex();
    }

    @NotNull
    public String getUnlocalizedName() {
        String s = ((ItemArmor) this.getWrapped()).getTranslationKey();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.unlocalizedName");
        return s;
    }

    public int getColor(@NotNull IItemStack stack) {
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        ItemArmor itemarmor = (ItemArmor) this.getWrapped();
        boolean $i$f$unwrap = false;
        ItemStack itemstack = ((ItemStackImpl) stack).getWrapped();

        return itemarmor.getColor(itemstack);
    }

    public ItemArmorImpl(@NotNull ItemArmor wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Item) wrapped);
    }
}

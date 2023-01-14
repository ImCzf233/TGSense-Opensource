package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.minecraft.IArmorMaterial;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006H\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0012"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ArmorMaterialImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/minecraft/IArmorMaterial;", "wrapped", "Lnet/minecraft/item/ItemArmor$ArmorMaterial;", "(Lnet/minecraft/item/ItemArmor$ArmorMaterial;)V", "enchantability", "", "getEnchantability", "()I", "getWrapped", "()Lnet/minecraft/item/ItemArmor$ArmorMaterial;", "equals", "", "other", "", "getDamageReductionAmount", "type", "getDurability", "LiquidBounce"}
)
public final class ArmorMaterialImpl implements IArmorMaterial {

    @NotNull
    private final ArmorMaterial wrapped;

    public int getEnchantability() {
        return this.wrapped.getEnchantability();
    }

    public int getDamageReductionAmount(int type) {
        ArmorMaterial armormaterial = this.wrapped;
        boolean $i$f$toEntityEquipmentSlot = false;
        EntityEquipmentSlot entityequipmentslot;

        switch (type) {
        case 0:
            entityequipmentslot = EntityEquipmentSlot.FEET;
            break;

        case 1:
            entityequipmentslot = EntityEquipmentSlot.LEGS;
            break;

        case 2:
            entityequipmentslot = EntityEquipmentSlot.CHEST;
            break;

        case 3:
            entityequipmentslot = EntityEquipmentSlot.HEAD;
            break;

        case 4:
            entityequipmentslot = EntityEquipmentSlot.MAINHAND;
            break;

        case 5:
            entityequipmentslot = EntityEquipmentSlot.OFFHAND;
            break;

        default:
            throw (Throwable) (new IllegalArgumentException("Invalid armorType " + type));
        }

        EntityEquipmentSlot entityequipmentslot1 = entityequipmentslot;

        return armormaterial.getDamageReductionAmount(entityequipmentslot1);
    }

    public int getDurability(int type) {
        ArmorMaterial armormaterial = this.wrapped;
        boolean $i$f$toEntityEquipmentSlot = false;
        EntityEquipmentSlot entityequipmentslot;

        switch (type) {
        case 0:
            entityequipmentslot = EntityEquipmentSlot.FEET;
            break;

        case 1:
            entityequipmentslot = EntityEquipmentSlot.LEGS;
            break;

        case 2:
            entityequipmentslot = EntityEquipmentSlot.CHEST;
            break;

        case 3:
            entityequipmentslot = EntityEquipmentSlot.HEAD;
            break;

        case 4:
            entityequipmentslot = EntityEquipmentSlot.MAINHAND;
            break;

        case 5:
            entityequipmentslot = EntityEquipmentSlot.OFFHAND;
            break;

        default:
            throw (Throwable) (new IllegalArgumentException("Invalid armorType " + type));
        }

        EntityEquipmentSlot entityequipmentslot1 = entityequipmentslot;

        return armormaterial.getDurability(entityequipmentslot1);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ArmorMaterialImpl && ((ArmorMaterialImpl) other).wrapped == this.wrapped;
    }

    @NotNull
    public final ArmorMaterial getWrapped() {
        return this.wrapped;
    }

    public ArmorMaterialImpl(@NotNull ArmorMaterial wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

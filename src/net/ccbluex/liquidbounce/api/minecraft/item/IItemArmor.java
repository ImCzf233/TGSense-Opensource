package net.ccbluex.liquidbounce.api.minecraft.item;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.minecraft.IArmorMaterial;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemArmor;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "armorMaterial", "Lnet/ccbluex/liquidbounce/api/minecraft/minecraft/IArmorMaterial;", "getArmorMaterial", "()Lnet/ccbluex/liquidbounce/api/minecraft/minecraft/IArmorMaterial;", "armorType", "", "getArmorType", "()I", "getColor", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "LiquidBounce"}
)
public interface IItemArmor extends IItem {

    @NotNull
    IArmorMaterial getArmorMaterial();

    int getArmorType();

    int getColor(@NotNull IItemStack iitemstack);
}

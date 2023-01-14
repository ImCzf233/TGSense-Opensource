package net.ccbluex.liquidbounce.api.minecraft.item;

import com.google.common.collect.Multimap;
import java.util.Collection;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTBase;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0014H&J\u0016\u00101\u001a\b\u0012\u0004\u0012\u000203022\u0006\u00104\u001a\u00020\u0004H&J\u0010\u00105\u001a\u0002062\u0006\u00107\u001a\u000208H&J\b\u00109\u001a\u00020:H&J\u0010\u0010;\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0004H&J\u0018\u0010<\u001a\u00020-2\u0006\u00104\u001a\u00020\u00042\u0006\u0010=\u001a\u00020>H&R\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u0004\u0018\u00010\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0013\u001a\u00020\u0014X¦\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0018\u0010\u001d\u001a\u00020\u0014X¦\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u0016\"\u0004\b\u001f\u0010\u0018R\u0012\u0010 \u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0016R\u0012\u0010\"\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0016R\u001a\u0010$\u001a\u0004\u0018\u00010%X¦\u000e¢\u0006\f\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R\u0012\u0010*\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\n¨\u0006?"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "", "attributeModifiers", "Lcom/google/common/collect/Multimap;", "", "Lnet/minecraft/entity/ai/attributes/AttributeModifier;", "getAttributeModifiers", "()Lcom/google/common/collect/Multimap;", "displayName", "getDisplayName", "()Ljava/lang/String;", "enchantmentTagList", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "getEnchantmentTagList", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "item", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getItem", "()Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "itemDamage", "", "getItemDamage", "()I", "setItemDamage", "(I)V", "itemDelay", "", "getItemDelay", "()J", "maxDamage", "getMaxDamage", "setMaxDamage", "maxItemUseDuration", "getMaxItemUseDuration", "stackSize", "getStackSize", "tagCompound", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "getTagCompound", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "setTagCompound", "(Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;)V", "unlocalizedName", "getUnlocalizedName", "addEnchantment", "", "enchantment", "Lnet/ccbluex/liquidbounce/api/minecraft/enchantments/IEnchantment;", "level", "getAttributeModifier", "", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/ai/attributes/IAttributeModifier;", "key", "getStrVsBlock", "", "block", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "isSplash", "", "setStackDisplayName", "setTagInfo", "nbt", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTBase;", "LiquidBounce"}
)
public interface IItemStack {

    @NotNull
    Multimap getAttributeModifiers();

    @NotNull
    String getDisplayName();

    @NotNull
    String getUnlocalizedName();

    int getMaxItemUseDuration();

    @Nullable
    INBTTagList getEnchantmentTagList();

    @Nullable
    INBTTagCompound getTagCompound();

    void setTagCompound(@Nullable INBTTagCompound inbttagcompound);

    int getStackSize();

    int getItemDamage();

    void setItemDamage(int i);

    @Nullable
    IItem getItem();

    long getItemDelay();

    int getMaxDamage();

    void setMaxDamage(int i);

    float getStrVsBlock(@NotNull IIBlockState iiblockstate);

    void setTagInfo(@NotNull String s, @NotNull INBTBase inbtbase);

    @NotNull
    IItemStack setStackDisplayName(@NotNull String s);

    void addEnchantment(@NotNull IEnchantment ienchantment, int i);

    @NotNull
    Collection getAttributeModifier(@NotNull String s);

    boolean isSplash();
}

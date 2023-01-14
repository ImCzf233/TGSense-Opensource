package net.ccbluex.liquidbounce.injection.backend;

import com.google.common.collect.Multimap;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTBase;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.ccbluex.liquidbounce.injection.implementations.IMixinItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020\u0017H\u0016J\u0016\u00107\u001a\b\u0012\u0004\u0012\u000209082\u0006\u0010:\u001a\u00020\u0007H\u0016J\u0010\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020>H\u0016J\b\u0010?\u001a\u00020@H\u0016J\u0010\u0010A\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0007H\u0016J\u0018\u0010B\u001a\u0002032\u0006\u0010:\u001a\u00020\u00072\u0006\u0010C\u001a\u00020DH\u0016R \u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R$\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u00178V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R$\u0010!\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u00178V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\"\u0010\u001a\"\u0004\b#\u0010\u001cR\u0014\u0010$\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u001aR\u0014\u0010&\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\'\u0010\u001aR(\u0010)\u001a\u0004\u0018\u00010(2\b\u0010\u0016\u001a\u0004\u0018\u00010(8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u0014\u0010.\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b0\u00101¨\u0006E"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ItemStackImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "wrapped", "Lnet/minecraft/item/ItemStack;", "(Lnet/minecraft/item/ItemStack;)V", "attributeModifiers", "Lcom/google/common/collect/Multimap;", "", "Lnet/minecraft/entity/ai/attributes/AttributeModifier;", "getAttributeModifiers", "()Lcom/google/common/collect/Multimap;", "displayName", "getDisplayName", "()Ljava/lang/String;", "enchantmentTagList", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "getEnchantmentTagList", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "item", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getItem", "()Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "value", "", "itemDamage", "getItemDamage", "()I", "setItemDamage", "(I)V", "itemDelay", "", "getItemDelay", "()J", "maxDamage", "getMaxDamage", "setMaxDamage", "maxItemUseDuration", "getMaxItemUseDuration", "stackSize", "getStackSize", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "tagCompound", "getTagCompound", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "setTagCompound", "(Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;)V", "unlocalizedName", "getUnlocalizedName", "getWrapped", "()Lnet/minecraft/item/ItemStack;", "addEnchantment", "", "enchantment", "Lnet/ccbluex/liquidbounce/api/minecraft/enchantments/IEnchantment;", "level", "getAttributeModifier", "", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/ai/attributes/IAttributeModifier;", "key", "getStrVsBlock", "", "block", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "isSplash", "", "setStackDisplayName", "setTagInfo", "nbt", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTBase;", "LiquidBounce"}
)
public final class ItemStackImpl implements IItemStack {

    @NotNull
    private final ItemStack wrapped;

    public float getStrVsBlock(@NotNull IIBlockState block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        ItemStack itemstack = this.wrapped;
        boolean $i$f$unwrap = false;
        IBlockState iblockstate = ((IBlockStateImpl) block).getWrapped();

        return itemstack.getDestroySpeed(iblockstate);
    }

    public void setTagInfo(@NotNull String key, @NotNull INBTBase nbt) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(nbt, "nbt");
        ItemStack itemstack = this.wrapped;
        boolean $i$f$unwrap = false;
        NBTBase nbtbase = ((NBTBaseImpl) nbt).getWrapped();

        itemstack.setTagInfo(key, nbtbase);
    }

    @NotNull
    public IItemStack setStackDisplayName(@NotNull String displayName) {
        Intrinsics.checkParameterIsNotNull(displayName, "displayName");
        ItemStack itemstack = this.wrapped.setStackDisplayName(displayName);

        Intrinsics.checkExpressionValueIsNotNull(itemstack, "wrapped.setStackDisplayName(displayName)");
        ItemStack $this$wrap$iv = itemstack;
        boolean $i$f$wrap = false;

        return (IItemStack) (new ItemStackImpl($this$wrap$iv));
    }

    public void addEnchantment(@NotNull IEnchantment enchantment, int level) {
        Intrinsics.checkParameterIsNotNull(enchantment, "enchantment");
        ItemStack itemstack = this.wrapped;
        boolean $i$f$unwrap = false;
        Enchantment enchantment = ((EnchantmentImpl) enchantment).getWrapped();

        itemstack.addEnchantment(enchantment, level);
    }

    @NotNull
    public Collection getAttributeModifier(@NotNull String key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        return (Collection) (new WrappedCollection(this.wrapped.getAttributeModifiers(EntityEquipmentSlot.MAINHAND).get(key), (Function1) null.INSTANCE, (Function1) null.INSTANCE));
    }

    public boolean isSplash() {
        return Intrinsics.areEqual(this.wrapped.getItem(), Items.SPLASH_POTION);
    }

    @NotNull
    public String getDisplayName() {
        String s = this.wrapped.getDisplayName();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.displayName");
        return s;
    }

    @NotNull
    public Multimap getAttributeModifiers() {
        Multimap multimap = this.wrapped.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);

        Intrinsics.checkExpressionValueIsNotNull(multimap, "wrapped.getAttributeModi…tyEquipmentSlot.MAINHAND)");
        return multimap;
    }

    @NotNull
    public String getUnlocalizedName() {
        String s = this.wrapped.getTranslationKey();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.unlocalizedName");
        return s;
    }

    public int getMaxItemUseDuration() {
        return this.wrapped.getMaxItemUseDuration();
    }

    @Nullable
    public INBTTagList getEnchantmentTagList() {
        NBTTagList nbttaglist = this.wrapped.getEnchantmentTagList();
        INBTTagList inbttaglist;

        if (nbttaglist != null) {
            NBTTagList $this$wrap$iv = nbttaglist;
            boolean $i$f$wrap = false;

            inbttaglist = (INBTTagList) (new NBTTagListImpl($this$wrap$iv));
        } else {
            inbttaglist = null;
        }

        return inbttaglist;
    }

    @Nullable
    public INBTTagCompound getTagCompound() {
        NBTTagCompound nbttagcompound = this.wrapped.getTagCompound();
        INBTTagCompound inbttagcompound;

        if (nbttagcompound != null) {
            NBTTagCompound $this$wrap$iv = nbttagcompound;
            boolean $i$f$wrap = false;

            inbttagcompound = (INBTTagCompound) (new NBTTagCompoundImpl($this$wrap$iv));
        } else {
            inbttagcompound = null;
        }

        return inbttagcompound;
    }

    public void setTagCompound(@Nullable INBTTagCompound value) {
        ItemStack itemstack = this.wrapped;
        NBTTagCompound nbttagcompound;

        if (value != null) {
            ItemStack itemstack1 = itemstack;
            boolean $i$f$unwrap = false;

            if (value == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.NBTTagCompoundImpl");
            }

            NBTTagCompound nbttagcompound1 = (NBTTagCompound) ((NBTTagCompoundImpl) value).getWrapped();

            itemstack = itemstack1;
            nbttagcompound = nbttagcompound1;
        } else {
            nbttagcompound = null;
        }

        itemstack.setTagCompound(nbttagcompound);
    }

    public int getStackSize() {
        return this.wrapped.stackSize;
    }

    public int getItemDamage() {
        return this.wrapped.getItemDamage();
    }

    public void setItemDamage(int value) {
        this.wrapped.setItemDamage(value);
    }

    public int getMaxDamage() {
        return this.wrapped.getMaxDamage();
    }

    public void setMaxDamage(int value) {}

    @Nullable
    public IItem getItem() {
        Item item = this.wrapped.getItem();
        IItem iitem;

        if (item != null) {
            Item $this$wrap$iv = item;
            boolean $i$f$wrap = false;

            iitem = (IItem) (new ItemImpl($this$wrap$iv));
        } else {
            iitem = null;
        }

        return iitem;
    }

    public long getItemDelay() {
        ItemStack itemstack = this.wrapped;

        if (this.wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.implementations.IMixinItemStack");
        } else {
            return ((IMixinItemStack) itemstack).getItemDelay();
        }
    }

    @NotNull
    public final ItemStack getWrapped() {
        return this.wrapped;
    }

    public ItemStackImpl(@NotNull ItemStack wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

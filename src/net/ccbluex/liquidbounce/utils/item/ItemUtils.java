package net.ccbluex.liquidbounce.utils.item;

import java.util.Objects;
import java.util.regex.Pattern;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.Contract;

public final class ItemUtils extends MinecraftInstance {

    public static IItemStack createItem(String itemArguments) {
        try {
            itemArguments = itemArguments.replace('&', '§');
            IItem exception = ItemUtils.classProvider.createItem();
            String[] args = null;
            int i = 1;
            int j = 0;

            for (int itemstack = 0; itemstack <= Math.min(12, itemArguments.length() - 2); ++itemstack) {
                args = itemArguments.substring(itemstack).split(Pattern.quote(" "));
                IResourceLocation NBT = ItemUtils.classProvider.createResourceLocation(args[0]);

                exception = ItemUtils.functions.getObjectFromItemRegistry(NBT);
                if (exception != null) {
                    break;
                }
            }

            if (exception == null) {
                return null;
            } else {
                if (((String[]) Objects.requireNonNull(args)).length >= 2 && args[1].matches("\\d+")) {
                    i = Integer.parseInt(args[1]);
                }

                if (args.length >= 3 && args[2].matches("\\d+")) {
                    j = Integer.parseInt(args[2]);
                }

                IItemStack iitemstack = ItemUtils.classProvider.createItemStack(exception, i, j);

                if (args.length >= 4) {
                    StringBuilder stringbuilder = new StringBuilder();

                    for (int nbtcount = 3; nbtcount < args.length; ++nbtcount) {
                        stringbuilder.append(" ").append(args[nbtcount]);
                    }

                    iitemstack.setTagCompound(ItemUtils.classProvider.getJsonToNBTInstance().getTagFromJson(stringbuilder.toString()));
                }

                return iitemstack;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static int getEnchantment(IItemStack itemStack, IEnchantment enchantment) {
        if (itemStack != null && itemStack.getEnchantmentTagList() != null && !itemStack.getEnchantmentTagList().hasNoTags()) {
            for (int i = 0; i < itemStack.getEnchantmentTagList().tagCount(); ++i) {
                INBTTagCompound tagCompound = itemStack.getEnchantmentTagList().getCompoundTagAt(i);

                if (tagCompound.hasKey("ench") && tagCompound.getShort("ench") == enchantment.getEffectId() || tagCompound.hasKey("id") && tagCompound.getShort("id") == enchantment.getEffectId()) {
                    return tagCompound.getShort("lvl");
                }
            }

            return 0;
        } else {
            return 0;
        }
    }

    public static int getEnchantmentCount(IItemStack itemStack) {
        if (itemStack != null && itemStack.getEnchantmentTagList() != null && !itemStack.getEnchantmentTagList().hasNoTags()) {
            int c = 0;

            for (int i = 0; i < itemStack.getEnchantmentTagList().tagCount(); ++i) {
                INBTTagCompound tagCompound = itemStack.getEnchantmentTagList().getCompoundTagAt(i);

                if (tagCompound.hasKey("ench") || tagCompound.hasKey("id")) {
                    ++c;
                }
            }

            return c;
        } else {
            return 0;
        }
    }

    @Contract("null -> true")
    public static boolean isStackEmpty(IItemStack stack) {
        return stack == null || ItemUtils.classProvider.isItemAir(stack.getItem());
    }
}

package net.ccbluex.liquidbounce.utils.item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import net.ccbluex.liquidbounce.api.enums.EnchantmentType;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemArmor;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

public class ArmorComparator extends MinecraftInstance implements Comparator {

    private static final IEnchantment[] DAMAGE_REDUCTION_ENCHANTMENTS = new IEnchantment[] { ArmorComparator.classProvider.getEnchantmentEnum(EnchantmentType.PROTECTION), ArmorComparator.classProvider.getEnchantmentEnum(EnchantmentType.PROJECTILE_PROTECTION), ArmorComparator.classProvider.getEnchantmentEnum(EnchantmentType.FIRE_PROTECTION), ArmorComparator.classProvider.getEnchantmentEnum(EnchantmentType.BLAST_PROTECTION)};
    private static final float[] ENCHANTMENT_FACTORS = new float[] { 1.5F, 0.4F, 0.39F, 0.38F};
    private static final float[] ENCHANTMENT_DAMAGE_REDUCTION_FACTOR = new float[] { 0.04F, 0.08F, 0.15F, 0.08F};
    private static final IEnchantment[] OTHER_ENCHANTMENTS = new IEnchantment[] { ArmorComparator.classProvider.getEnchantmentEnum(EnchantmentType.FEATHER_FALLING), ArmorComparator.classProvider.getEnchantmentEnum(EnchantmentType.THORNS), ArmorComparator.classProvider.getEnchantmentEnum(EnchantmentType.RESPIRATION), ArmorComparator.classProvider.getEnchantmentEnum(EnchantmentType.AQUA_AFFINITY), ArmorComparator.classProvider.getEnchantmentEnum(EnchantmentType.UNBREAKING)};
    private static final float[] OTHER_ENCHANTMENT_FACTORS = new float[] { 3.0F, 1.0F, 0.1F, 0.05F, 0.01F};

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        } else {
            BigDecimal bd = BigDecimal.valueOf(value);

            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }

    public int compare(ArmorPiece o1, ArmorPiece o2) {
        int compare = Double.compare(round((double) this.getThresholdedDamageReduction(o2.getItemStack()), 3), round((double) this.getThresholdedDamageReduction(o1.getItemStack()), 3));

        if (compare == 0) {
            int otherEnchantmentCmp = Double.compare(round((double) this.getEnchantmentThreshold(o1.getItemStack()), 3), round((double) this.getEnchantmentThreshold(o2.getItemStack()), 3));

            if (otherEnchantmentCmp == 0) {
                int enchantmentCountCmp = Integer.compare(ItemUtils.getEnchantmentCount(o1.getItemStack()), ItemUtils.getEnchantmentCount(o2.getItemStack()));

                if (enchantmentCountCmp != 0) {
                    return enchantmentCountCmp;
                } else {
                    IItemArmor o1a = o1.getItemStack().getItem().asItemArmor();
                    IItemArmor o2a = o2.getItemStack().getItem().asItemArmor();
                    int durabilityCmp = Integer.compare(o1a.getArmorMaterial().getDurability(o1a.getArmorType()), o2a.getArmorMaterial().getDurability(o2a.getArmorType()));

                    return durabilityCmp != 0 ? durabilityCmp : Integer.compare(o1a.getArmorMaterial().getEnchantability(), o2a.getArmorMaterial().getEnchantability());
                }
            } else {
                return otherEnchantmentCmp;
            }
        } else {
            return compare;
        }
    }

    private float getThresholdedDamageReduction(IItemStack itemStack) {
        IItemArmor item = itemStack.getItem().asItemArmor();

        return this.getDamageReduction(item.getArmorMaterial().getDamageReductionAmount(item.getArmorType()), 0) * (1.0F - this.getThresholdedEnchantmentDamageReduction(itemStack));
    }

    private float getDamageReduction(int defensePoints, int toughness) {
        return 1.0F - Math.min(20.0F, Math.max((float) defensePoints / 5.0F, (float) defensePoints - 1.0F / (2.0F + (float) toughness / 4.0F))) / 25.0F;
    }

    private float getThresholdedEnchantmentDamageReduction(IItemStack itemStack) {
        float sum = 0.0F;

        for (int i = 0; i < ArmorComparator.DAMAGE_REDUCTION_ENCHANTMENTS.length; ++i) {
            sum += (float) ItemUtils.getEnchantment(itemStack, ArmorComparator.DAMAGE_REDUCTION_ENCHANTMENTS[i]) * ArmorComparator.ENCHANTMENT_FACTORS[i] * ArmorComparator.ENCHANTMENT_DAMAGE_REDUCTION_FACTOR[i];
        }

        return sum;
    }

    private float getEnchantmentThreshold(IItemStack itemStack) {
        float sum = 0.0F;

        for (int i = 0; i < ArmorComparator.OTHER_ENCHANTMENTS.length; ++i) {
            sum += (float) ItemUtils.getEnchantment(itemStack, ArmorComparator.OTHER_ENCHANTMENTS[i]) * ArmorComparator.OTHER_ENCHANTMENT_FACTORS[i];
        }

        return sum;
    }
}

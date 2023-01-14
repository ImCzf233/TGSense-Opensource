package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.FoodStats;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ EntityPlayer.class})
public abstract class MixinEntityPlayer extends MixinEntityLivingBase {

    @Shadow
    @Final
    protected static DataParameter MAIN_HAND;
    @Shadow
    public PlayerCapabilities capabilities;
    @Shadow
    protected int flyToggleTimer;

    @Shadow
    public abstract ItemStack getItemStackFromSlot(EntityEquipmentSlot entityequipmentslot);

    @Shadow
    public abstract GameProfile getGameProfile();

    @Shadow
    protected abstract boolean canTriggerWalking();

    @Shadow
    protected abstract SoundEvent getSwimSound();

    @Shadow
    public abstract FoodStats getFoodStats();
}

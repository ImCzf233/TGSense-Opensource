package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import net.ccbluex.liquidbounce.injection.implementations.IMixinItemStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ ItemStack.class})
public class MixinMixinItemStack implements IMixinItemStack {

    private long itemDelay;

    @Inject(
        method = { "<init>(Lnet/minecraft/item/Item;IILnet/minecraft/nbt/NBTTagCompound;)V"},
        at = {             @At("RETURN")}
    )
    private void init(CallbackInfo callbackInfo) {
        this.itemDelay = System.currentTimeMillis();
    }

    public long getItemDelay() {
        return this.itemDelay;
    }
}

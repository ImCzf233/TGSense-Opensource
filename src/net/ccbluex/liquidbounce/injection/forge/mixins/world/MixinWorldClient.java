package net.ccbluex.liquidbounce.injection.forge.mixins.world;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.TrueSight;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At.Shift;

@Mixin({ WorldClient.class})
public class MixinWorldClient {

    @ModifyVariable(
        method = { "showBarrierParticles"},
        at =             @At(
                value = "INVOKE",
                target = "Lnet/minecraft/block/Block;randomDisplayTick(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
                shift = Shift.AFTER
            ),
        ordinal = 0
    )
    private boolean handleBarriers(boolean flag) {
        TrueSight trueSight = (TrueSight) LiquidBounce.moduleManager.getModule(TrueSight.class);

        return flag || trueSight.getState() && ((Boolean) trueSight.getBarriersValue().get()).booleanValue();
    }
}

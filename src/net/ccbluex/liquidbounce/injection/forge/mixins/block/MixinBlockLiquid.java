package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.world.Liquids;
import net.minecraft.block.BlockLiquid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({ BlockLiquid.class})
public class MixinBlockLiquid {

    @Inject(
        method = { "canCollideCheck"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void onCollideCheck(CallbackInfoReturnable callbackInfoReturnable) {
        if (((Module) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(Liquids.class))).getState()) {
            callbackInfoReturnable.setReturnValue(Boolean.valueOf(true));
        }

    }
}

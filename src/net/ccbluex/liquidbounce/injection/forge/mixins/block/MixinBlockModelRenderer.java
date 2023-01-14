package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.injection.backend.BlockImplKt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ BlockModelRenderer.class})
public class MixinBlockModelRenderer {

    @Inject(
        method = { "renderModelSmooth"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    public void renderModelSmooth(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand, CallbackInfoReturnable cir) {
        XRay xray = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);

        if (((XRay) Objects.requireNonNull(xray)).getState() && !xray.getXrayBlocks().contains(BlockImplKt.wrap(stateIn.getBlock()))) {
            cir.setReturnValue(Boolean.valueOf(false));
        }

    }

    @Inject(
        method = { "renderModelFlat"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void renderModelStandard(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand, CallbackInfoReturnable booleanCallbackInfoReturnable) {
        XRay xray = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);

        if (((XRay) Objects.requireNonNull(xray)).getState() && !xray.getXrayBlocks().contains(BlockImplKt.wrap(stateIn.getBlock()))) {
            booleanCallbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
        }

    }
}

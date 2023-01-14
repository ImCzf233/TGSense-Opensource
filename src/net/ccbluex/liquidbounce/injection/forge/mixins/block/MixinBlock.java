package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.exploit.GhostHand;
import net.ccbluex.liquidbounce.features.module.modules.player.NoFall;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.features.module.modules.world.NoSlowBreak;
import net.ccbluex.liquidbounce.injection.backend.AxisAlignedBBImplKt;
import net.ccbluex.liquidbounce.injection.backend.BlockImplKt;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({ Block.class})
public abstract class MixinBlock {

    @Shadow
    @Final
    protected BlockStateContainer blockState;

    @Overwrite
    protected static void addCollisionBoxToList(BlockPos pos, AxisAlignedBB entityBox, List collidingBoxes, @Nullable AxisAlignedBB blockBox) {
        if (blockBox != null) {
            AxisAlignedBB axisalignedbb = blockBox.offset(pos);
            WorldClient world = Minecraft.getMinecraft().world;

            if (world != null) {
                BlockBBEvent blockBBEvent = new BlockBBEvent(BackendExtentionsKt.wrap(pos), BlockImplKt.wrap(world.getBlockState(pos).getBlock()), AxisAlignedBBImplKt.wrap(axisalignedbb));

                LiquidBounce.eventManager.callEvent(blockBBEvent);
                axisalignedbb = blockBBEvent.getBoundingBox() == null ? null : AxisAlignedBBImplKt.unwrap(blockBBEvent.getBoundingBox());
            }

            if (axisalignedbb != null && entityBox.intersects(axisalignedbb)) {
                collidingBoxes.add(axisalignedbb);
            }
        }

    }

    @Shadow
    public abstract AxisAlignedBB getCollisionBoundingBox(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos);

    @Shadow
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return null;
    }

    @Inject(
        method = { "shouldSideBeRendered"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void shouldSideBeRendered(CallbackInfoReturnable callbackInfoReturnable) {
        XRay xray = (XRay) LiquidBounce.moduleManager.getModule(XRay.class);

        if (((XRay) Objects.requireNonNull(xray)).getState()) {
            callbackInfoReturnable.setReturnValue(Boolean.valueOf(xray.getXrayBlocks().contains(BlockImplKt.wrap((Block) this))));
        }

    }

    @Inject(
        method = { "isCollidable"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void isCollidable(CallbackInfoReturnable callbackInfoReturnable) {
        GhostHand ghostHand = (GhostHand) LiquidBounce.moduleManager.getModule(GhostHand.class);

        if (((GhostHand) Objects.requireNonNull(ghostHand)).getState() && ((Integer) ghostHand.getBlockValue().get()).intValue() != Block.getIdFromBlock((Block) this)) {
            callbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
        }

    }

    @Inject(
        method = { "getAmbientOcclusionLightValue"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void getAmbientOcclusionLightValue(CallbackInfoReturnable floatCallbackInfoReturnable) {
        if (((Module) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(XRay.class))).getState()) {
            floatCallbackInfoReturnable.setReturnValue(Float.valueOf(1.0F));
        }

    }

    @Inject(
        method = { "getPlayerRelativeBlockHardness"},
        at = {             @At("RETURN")},
        cancellable = true
    )
    public void modifyBreakSpeed(IBlockState state, EntityPlayer playerIn, World worldIn, BlockPos pos, CallbackInfoReturnable callbackInfo) {
        float f = ((Float) callbackInfo.getReturnValue()).floatValue();
        NoSlowBreak noSlowBreak = (NoSlowBreak) LiquidBounce.moduleManager.getModule(NoSlowBreak.class);

        if (((NoSlowBreak) Objects.requireNonNull(noSlowBreak)).getState()) {
            if (((Boolean) noSlowBreak.getWaterValue().get()).booleanValue() && playerIn.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier(playerIn)) {
                f *= 5.0F;
            }

            if (((Boolean) noSlowBreak.getAirValue().get()).booleanValue() && !playerIn.onGround) {
                f *= 5.0F;
            }
        } else if (playerIn.onGround) {
            NoFall noFall = (NoFall) LiquidBounce.moduleManager.getModule(NoFall.class);
            Criticals criticals = (Criticals) LiquidBounce.moduleManager.getModule(Criticals.class);

            if (((NoFall) Objects.requireNonNull(noFall)).getState() && ((String) noFall.modeValue.get()).equalsIgnoreCase("NoGround") || ((Criticals) Objects.requireNonNull(criticals)).getState() && ((String) criticals.getModeValue().get()).equalsIgnoreCase("NoGround")) {
                f /= 5.0F;
            }
        }

        callbackInfo.setReturnValue(Float.valueOf(f));
    }
}

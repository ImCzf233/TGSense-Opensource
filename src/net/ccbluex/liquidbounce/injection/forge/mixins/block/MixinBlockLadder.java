package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastClimb;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({ BlockLadder.class})
public abstract class MixinBlockLadder extends MixinBlock {

    @Shadow
    @Final
    public static PropertyDirection FACING;
    @Shadow
    @Final
    protected static AxisAlignedBB LADDER_NORTH_AABB;
    @Shadow
    @Final
    protected static AxisAlignedBB LADDER_SOUTH_AABB;
    @Shadow
    @Final
    protected static AxisAlignedBB LADDER_WEST_AABB;
    @Shadow
    @Final
    protected static AxisAlignedBB LADDER_EAST_AABB;

    @Overwrite
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (state.getBlock() instanceof BlockLadder) {
            FastClimb fastClimb = (FastClimb) LiquidBounce.moduleManager.getModule(FastClimb.class);
            boolean fastLadder = ((FastClimb) Objects.requireNonNull(fastClimb)).getState() && ((String) fastClimb.getModeValue().get()).equalsIgnoreCase("AAC3.0.0");
            float f = 0.99F;

            if (fastLadder) {
                switch ((EnumFacing) state.getValue(MixinBlockLadder.FACING)) {
                case NORTH:
                    return new AxisAlignedBB(0.0D, 0.0D, 0.009999990463256836D, 1.0D, 1.0D, 1.0D);

                case SOUTH:
                    return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.9900000095367432D);

                case WEST:
                    return new AxisAlignedBB(0.009999990463256836D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

                case EAST:
                default:
                    return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.9900000095367432D, 1.0D, 1.0D);
                }
            }
        }

        switch ((EnumFacing) state.getValue(MixinBlockLadder.FACING)) {
        case NORTH:
            return MixinBlockLadder.LADDER_NORTH_AABB;

        case SOUTH:
            return MixinBlockLadder.LADDER_SOUTH_AABB;

        case WEST:
            return MixinBlockLadder.LADDER_WEST_AABB;

        case EAST:
        default:
            return MixinBlockLadder.LADDER_EAST_AABB;
        }
    }
}

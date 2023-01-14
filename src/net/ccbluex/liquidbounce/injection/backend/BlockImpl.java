package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001b\u001a\u00020\u0019H\u0016J\u0013\u0010\u001c\u001a\u00020\u00192\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002J\"\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u001a\u001a\u00020\u0006H\u0016J\u0010\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u0001H\u0016J \u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020$H\u0016J\u0012\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010\u001a\u001a\u00020\u0006H\u0016J \u0010/\u001a\u00020\u00102\u0006\u00100\u001a\u0002012\u0006\u0010*\u001a\u00020\"2\u0006\u00102\u001a\u00020$H\u0016J \u00103\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010)\u001a\u00020\u00062\u0006\u00102\u001a\u00020$H\u0016J\u0010\u00104\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0006H\u0016J\u0010\u00105\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u0006H\u0016J\u0018\u00106\u001a\u0002072\u0006\u0010!\u001a\u00020\"2\u0006\u00102\u001a\u00020$H\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\fR$\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u00068"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/BlockImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "wrapped", "Lnet/minecraft/block/Block;", "(Lnet/minecraft/block/Block;)V", "defaultState", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "getDefaultState", "()Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "localizedName", "", "getLocalizedName", "()Ljava/lang/String;", "registryName", "getRegistryName", "value", "", "slipperiness", "getSlipperiness", "()F", "setSlipperiness", "(F)V", "getWrapped", "()Lnet/minecraft/block/Block;", "canCollideCheck", "", "state", "hitIfLiquid", "equals", "other", "", "getCollisionBoundingBox", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "world", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "pos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getIdFromBlock", "", "block", "getMapColor", "blockState", "theWorld", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "bp", "getMaterial", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "getPlayerRelativeBlockHardness", "thePlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "blockPos", "getSelectedBoundingBox", "isFullCube", "isTranslucent", "setBlockBoundsBasedOnState", "", "LiquidBounce"}
)
public final class BlockImpl implements IBlock {

    @NotNull
    private final Block wrapped;

    @NotNull
    public String getRegistryName() {
        String s = this.wrapped.getTranslationKey();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.unlocalizedName");
        return s;
    }

    public float getSlipperiness() {
        return this.wrapped.slipperiness;
    }

    public void setSlipperiness(float value) {
        this.wrapped.slipperiness = value;
    }

    @Nullable
    public IIBlockState getDefaultState() {
        IBlockState iblockstate = this.wrapped.getDefaultState();

        Intrinsics.checkExpressionValueIsNotNull(iblockstate, "wrapped.defaultState");
        return (IIBlockState) (new IBlockStateImpl(iblockstate));
    }

    @NotNull
    public String getLocalizedName() {
        String s = this.wrapped.getLocalizedName();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.localizedName");
        return s;
    }

    @NotNull
    public IAxisAlignedBB getSelectedBoundingBox(@NotNull IWorld world, @NotNull IIBlockState blockState, @NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(world, "world");
        Intrinsics.checkParameterIsNotNull(blockState, "blockState");
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Block block = this.wrapped;
        boolean $i$f$unwrap = false;
        IBlockState iblockstate = ((IBlockStateImpl) blockState).getWrapped();

        $i$f$unwrap = false;
        World world = ((WorldImpl) world).getWrapped();
        IBlockAccess iblockaccess = (IBlockAccess) world;

        $i$f$unwrap = false;
        BlockPos blockpos = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        AxisAlignedBB axisalignedbb = block.getBoundingBox(iblockstate, iblockaccess, blockpos);

        $i$f$unwrap = false;
        BlockPos blockpos1 = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        AxisAlignedBB axisalignedbb1 = axisalignedbb.offset(blockpos1);

        Intrinsics.checkExpressionValueIsNotNull(axisalignedbb1, "wrapped.getBoundingBox(b…offset(blockPos.unwrap())");
        AxisAlignedBB axisalignedbb2 = axisalignedbb1;

        return (IAxisAlignedBB) (new AxisAlignedBBImpl(axisalignedbb2));
    }

    @Nullable
    public IAxisAlignedBB getCollisionBoundingBox(@NotNull IWorld world, @NotNull WBlockPos pos, @NotNull IIBlockState state) {
        Intrinsics.checkParameterIsNotNull(world, "world");
        Intrinsics.checkParameterIsNotNull(pos, "pos");
        Intrinsics.checkParameterIsNotNull(state, "state");
        Block block = this.wrapped;
        boolean $i$f$wrap = false;
        IBlockState iblockstate = ((IBlockStateImpl) state).getWrapped();

        $i$f$wrap = false;
        World world = ((WorldImpl) world).getWrapped();
        IBlockAccess iblockaccess = (IBlockAccess) world;

        $i$f$wrap = false;
        BlockPos blockpos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
        AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(iblockstate, iblockaccess, blockpos);
        IAxisAlignedBB iaxisalignedbb;

        if (axisalignedbb != null) {
            AxisAlignedBB $this$wrap$iv = axisalignedbb;

            $i$f$wrap = false;
            iaxisalignedbb = (IAxisAlignedBB) (new AxisAlignedBBImpl($this$wrap$iv));
        } else {
            iaxisalignedbb = null;
        }

        return iaxisalignedbb;
    }

    public boolean canCollideCheck(@Nullable IIBlockState state, boolean hitIfLiquid) {
        Block block = this.wrapped;
        IBlockState iblockstate;

        if (state != null) {
            Block block1 = block;
            boolean $i$f$unwrap = false;

            if (state == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.IBlockStateImpl");
            }

            IBlockState iblockstate1 = ((IBlockStateImpl) state).getWrapped();

            block = block1;
            iblockstate = iblockstate1;
        } else {
            iblockstate = null;
        }

        return block.canCollideCheck(iblockstate, hitIfLiquid);
    }

    @NotNull
    public Void setBlockBoundsBasedOnState(@NotNull IWorld world, @NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(world, "world");
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Backend this_$iv = Backend.INSTANCE;
        boolean $i$f$BACKEND_UNSUPPORTED = false;

        throw (Throwable) (new NotImplementedError("1.12.2 doesn\'t support this feature\'"));
    }

    public float getPlayerRelativeBlockHardness(@NotNull IEntityPlayerSP thePlayer, @NotNull IWorld theWorld, @NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(thePlayer, "thePlayer");
        Intrinsics.checkParameterIsNotNull(theWorld, "theWorld");
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Block block = this.wrapped;
        IIBlockState $this$unwrap$iv = theWorld.getBlockState(blockPos);
        Block block1 = block;
        boolean $i$f$unwrap = false;

        if ($this$unwrap$iv == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.IBlockStateImpl");
        } else {
            IBlockState iblockstate = ((IBlockStateImpl) $this$unwrap$iv).getWrapped();

            $i$f$unwrap = false;
            EntityPlayerSP entityplayersp = (EntityPlayerSP) ((EntityPlayerSPImpl) thePlayer).getWrapped();
            EntityPlayer entityplayer = (EntityPlayer) entityplayersp;

            $i$f$unwrap = false;
            World world = ((WorldImpl) theWorld).getWrapped();

            $i$f$unwrap = false;
            BlockPos blockpos = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());

            return block1.getPlayerRelativeBlockHardness(iblockstate, entityplayer, world, blockpos);
        }
    }

    public int getIdFromBlock(@NotNull IBlock block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        boolean $i$f$unwrap = false;

        return Block.getIdFromBlock(((BlockImpl) block).getWrapped());
    }

    public boolean isTranslucent(@NotNull IIBlockState blockState) {
        Intrinsics.checkParameterIsNotNull(blockState, "blockState");
        Block block = this.wrapped;
        boolean $i$f$unwrap = false;
        IBlockState iblockstate = ((IBlockStateImpl) blockState).getWrapped();

        return block.isTranslucent(iblockstate);
    }

    public int getMapColor(@NotNull IIBlockState blockState, @NotNull IWorldClient theWorld, @NotNull WBlockPos bp) {
        Intrinsics.checkParameterIsNotNull(blockState, "blockState");
        Intrinsics.checkParameterIsNotNull(theWorld, "theWorld");
        Intrinsics.checkParameterIsNotNull(bp, "bp");
        Block block = this.wrapped;
        boolean $i$f$unwrap = false;
        IBlockState iblockstate = ((IBlockStateImpl) blockState).getWrapped();

        $i$f$unwrap = false;
        WorldClient worldclient = (WorldClient) ((WorldClientImpl) theWorld).getWrapped();
        IBlockAccess iblockaccess = (IBlockAccess) worldclient;

        $i$f$unwrap = false;
        BlockPos blockpos = new BlockPos(bp.getX(), bp.getY(), bp.getZ());

        return block.getMapColor(iblockstate, iblockaccess, blockpos).colorValue;
    }

    @Nullable
    public IMaterial getMaterial(@NotNull IIBlockState state) {
        Intrinsics.checkParameterIsNotNull(state, "state");
        Block block = this.wrapped;
        boolean $i$f$wrap = false;
        IBlockState iblockstate = ((IBlockStateImpl) state).getWrapped();
        Material material = block.getMaterial(iblockstate);

        Intrinsics.checkExpressionValueIsNotNull(material, "wrapped.getMaterial(state.unwrap())");
        Material $this$wrap$iv = material;

        $i$f$wrap = false;
        return (IMaterial) (new MaterialImpl($this$wrap$iv));
    }

    public boolean isFullCube(@NotNull IIBlockState state) {
        Intrinsics.checkParameterIsNotNull(state, "state");
        Block block = this.wrapped;
        boolean $i$f$unwrap = false;
        IBlockState iblockstate = ((IBlockStateImpl) state).getWrapped();

        return block.isFullCube(iblockstate);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof BlockImpl && Intrinsics.areEqual(((BlockImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final Block getWrapped() {
        return this.wrapped;
    }

    public BlockImpl(@NotNull Block wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

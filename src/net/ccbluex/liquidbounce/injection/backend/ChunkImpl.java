package net.ccbluex.liquidbounce.injection.backend;

import com.google.common.base.Predicate;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IChunk;
import net.ccbluex.liquidbounce.api.util.WrappedMutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J0\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u0018\u0010 \u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\n¨\u0006!"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ChunkImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IChunk;", "wrapped", "Lnet/minecraft/world/chunk/Chunk;", "(Lnet/minecraft/world/chunk/Chunk;)V", "getWrapped", "()Lnet/minecraft/world/chunk/Chunk;", "x", "", "getX", "()I", "z", "getZ", "equals", "", "other", "", "getBlockState", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getEntitiesWithinAABBForEntity", "", "thePlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "arrowBox", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "collidedEntities", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "nothing", "", "getHeightValue", "LiquidBounce"}
)
public final class ChunkImpl implements IChunk {

    @NotNull
    private final Chunk wrapped;

    public int getX() {
        return this.wrapped.x;
    }

    public int getZ() {
        return this.wrapped.z;
    }

    public void getEntitiesWithinAABBForEntity(@NotNull IEntityPlayerSP thePlayer, @NotNull IAxisAlignedBB arrowBox, @NotNull List collidedEntities, @Nullable Void nothing) {
        Intrinsics.checkParameterIsNotNull(thePlayer, "thePlayer");
        Intrinsics.checkParameterIsNotNull(arrowBox, "arrowBox");
        Intrinsics.checkParameterIsNotNull(collidedEntities, "collidedEntities");
        Chunk chunk = this.wrapped;
        boolean $i$f$unwrap = false;
        EntityPlayerSP entityplayersp = (EntityPlayerSP) ((EntityPlayerSPImpl) thePlayer).getWrapped();
        Entity entity = (Entity) entityplayersp;

        $i$f$unwrap = false;
        AxisAlignedBB axisalignedbb = ((AxisAlignedBBImpl) arrowBox).getWrapped();

        chunk.getEntitiesWithinAABBForEntity(entity, axisalignedbb, (List) (new WrappedMutableList(collidedEntities, (Function1) null.INSTANCE, (Function1) null.INSTANCE)), (Predicate) null);
    }

    public int getHeightValue(int x, int z) {
        return this.wrapped.getHeightValue(x, z);
    }

    @NotNull
    public IIBlockState getBlockState(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Chunk chunk = this.wrapped;
        boolean $i$f$wrap = false;
        BlockPos blockpos = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        IBlockState iblockstate = chunk.getBlockState(blockpos);

        Intrinsics.checkExpressionValueIsNotNull(iblockstate, "wrapped.getBlockState(blockPos.unwrap())");
        IBlockState $this$wrap$iv = iblockstate;

        $i$f$wrap = false;
        return (IIBlockState) (new IBlockStateImpl($this$wrap$iv));
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ChunkImpl && Intrinsics.areEqual(((ChunkImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final Chunk getWrapped() {
        return this.wrapped;
    }

    public ChunkImpl(@NotNull Chunk wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

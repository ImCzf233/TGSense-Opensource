package net.ccbluex.liquidbounce.injection.backend;

import com.google.common.base.Predicate;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreboard;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.world.IChunk;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.api.minecraft.world.border.IWorldBorder;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000v\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0096\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!H\u0016J\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00160$2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u0016H\u0016J\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00160$2\u0006\u0010\'\u001a\u00020\u0016H\u0016J6\u0010)\u001a\b\u0012\u0004\u0012\u00020&0$2\b\u0010*\u001a\u0004\u0018\u00010&2\u0006\u0010+\u001a\u00020\u00162\u0014\u0010,\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010&\u0012\u0004\u0012\u00020\u00070-H\u0016J \u0010.\u001a\b\u0012\u0004\u0012\u00020&0$2\b\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010\'\u001a\u00020\u0016H\u0016J\u0012\u0010/\u001a\u0004\u0018\u00010&2\u0006\u00100\u001a\u00020!H\u0016J\u001a\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000204H\u0016J\"\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u0002042\u0006\u00106\u001a\u00020\u0007H\u0016J2\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u0002042\u0006\u00106\u001a\u00020\u00072\u0006\u00107\u001a\u00020\u00072\u0006\u00108\u001a\u00020\u0007H\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012¨\u00069"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/WorldImpl;", "T", "Lnet/minecraft/world/World;", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "wrapped", "(Lnet/minecraft/world/World;)V", "isRemote", "", "()Z", "scoreboard", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScoreboard;", "getScoreboard", "()Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScoreboard;", "worldBorder", "Lnet/ccbluex/liquidbounce/api/minecraft/world/border/IWorldBorder;", "getWorldBorder", "()Lnet/ccbluex/liquidbounce/api/minecraft/world/border/IWorldBorder;", "getWrapped", "()Lnet/minecraft/world/World;", "Lnet/minecraft/world/World;", "checkBlockCollision", "aabb", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "equals", "other", "", "getBlockState", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getChunkFromChunkCoords", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IChunk;", "x", "", "z", "getCollidingBoundingBoxes", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "bb", "getCollisionBoxes", "getEntitiesInAABBexcluding", "entityIn", "boundingBox", "predicate", "Lkotlin/Function1;", "getEntitiesWithinAABBExcludingEntity", "getEntityByID", "id", "rayTraceBlocks", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "start", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "end", "stopOnLiquid", "ignoreBlockWithoutBoundingBox", "returnLastUncollidableBlock", "LiquidBounce"}
)
public class WorldImpl implements IWorld {

    @NotNull
    private final World wrapped;

    public boolean isRemote() {
        return this.wrapped.isRemote;
    }

    @NotNull
    public IScoreboard getScoreboard() {
        Scoreboard scoreboard = this.wrapped.getScoreboard();

        Intrinsics.checkExpressionValueIsNotNull(scoreboard, "wrapped.scoreboard");
        Scoreboard $this$wrap$iv = scoreboard;
        boolean $i$f$wrap = false;

        return (IScoreboard) (new ScoreboardImpl($this$wrap$iv));
    }

    @NotNull
    public IWorldBorder getWorldBorder() {
        WorldBorder worldborder = this.wrapped.getWorldBorder();

        Intrinsics.checkExpressionValueIsNotNull(worldborder, "wrapped.worldBorder");
        WorldBorder $this$wrap$iv = worldborder;
        boolean $i$f$wrap = false;

        return (IWorldBorder) (new WorldBorderImpl($this$wrap$iv));
    }

    @Nullable
    public IEntity getEntityByID(int id) {
        Entity entity = this.wrapped.getEntityByID(id);
        IEntity ientity;

        if (entity != null) {
            Entity $this$wrap$iv = entity;
            boolean $i$f$wrap = false;

            ientity = (IEntity) (new EntityImpl($this$wrap$iv));
        } else {
            ientity = null;
        }

        return ientity;
    }

    @Nullable
    public IMovingObjectPosition rayTraceBlocks(@NotNull WVec3 start, @NotNull WVec3 end) {
        Intrinsics.checkParameterIsNotNull(start, "start");
        Intrinsics.checkParameterIsNotNull(end, "end");
        World world = this.wrapped;
        boolean $i$f$wrap = false;
        Vec3d vec3d = new Vec3d(start.getXCoord(), start.getYCoord(), start.getZCoord());

        $i$f$wrap = false;
        Vec3d vec3d1 = new Vec3d(end.getXCoord(), end.getYCoord(), end.getZCoord());
        RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d1);
        IMovingObjectPosition imovingobjectposition;

        if (raytraceresult != null) {
            RayTraceResult $this$wrap$iv = raytraceresult;

            $i$f$wrap = false;
            imovingobjectposition = (IMovingObjectPosition) (new MovingObjectPositionImpl($this$wrap$iv));
        } else {
            imovingobjectposition = null;
        }

        return imovingobjectposition;
    }

    @Nullable
    public IMovingObjectPosition rayTraceBlocks(@NotNull WVec3 start, @NotNull WVec3 end, boolean stopOnLiquid) {
        Intrinsics.checkParameterIsNotNull(start, "start");
        Intrinsics.checkParameterIsNotNull(end, "end");
        World world = this.wrapped;
        boolean $i$f$wrap = false;
        Vec3d vec3d = new Vec3d(start.getXCoord(), start.getYCoord(), start.getZCoord());

        $i$f$wrap = false;
        Vec3d vec3d1 = new Vec3d(end.getXCoord(), end.getYCoord(), end.getZCoord());
        RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d1, stopOnLiquid);
        IMovingObjectPosition imovingobjectposition;

        if (raytraceresult != null) {
            RayTraceResult $this$wrap$iv = raytraceresult;

            $i$f$wrap = false;
            imovingobjectposition = (IMovingObjectPosition) (new MovingObjectPositionImpl($this$wrap$iv));
        } else {
            imovingobjectposition = null;
        }

        return imovingobjectposition;
    }

    @Nullable
    public IMovingObjectPosition rayTraceBlocks(@NotNull WVec3 start, @NotNull WVec3 end, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        Intrinsics.checkParameterIsNotNull(start, "start");
        Intrinsics.checkParameterIsNotNull(end, "end");
        World world = this.wrapped;
        boolean $i$f$wrap = false;
        Vec3d vec3d = new Vec3d(start.getXCoord(), start.getYCoord(), start.getZCoord());

        $i$f$wrap = false;
        Vec3d vec3d1 = new Vec3d(end.getXCoord(), end.getYCoord(), end.getZCoord());
        RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d1, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        IMovingObjectPosition imovingobjectposition;

        if (raytraceresult != null) {
            RayTraceResult $this$wrap$iv = raytraceresult;

            $i$f$wrap = false;
            imovingobjectposition = (IMovingObjectPosition) (new MovingObjectPositionImpl($this$wrap$iv));
        } else {
            imovingobjectposition = null;
        }

        return imovingobjectposition;
    }

    @NotNull
    public Collection getEntitiesInAABBexcluding(@Nullable IEntity entityIn, @NotNull IAxisAlignedBB boundingBox, @NotNull final Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(boundingBox, "boundingBox");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        World world = this.wrapped;
        boolean $i$f$unwrap;
        World world1;
        Entity entity;
        Entity entity1;

        if (entityIn != null) {
            world1 = world;
            $i$f$unwrap = false;
            if (entityIn == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.EntityImpl<*>");
            }

            entity = ((EntityImpl) entityIn).getWrapped();
            world = world1;
            entity1 = entity;
        } else {
            entity1 = null;
        }

        entity = entity1;
        world1 = world;
        $i$f$unwrap = false;
        AxisAlignedBB axisalignedbb = ((AxisAlignedBBImpl) boundingBox).getWrapped();
        Collection collection = (Collection) world1.getEntitiesInAABBexcluding(entity, axisalignedbb, (Predicate) (new Predicate() {
            public final boolean apply(@Nullable Entity it) {
                Function1 function1 = predicate;
                IEntity ientity;

                if (it != null) {
                    Function1 function11 = function1;
                    boolean $i$f$wrap = false;
                    IEntity ientity1 = (IEntity) (new EntityImpl(it));

                    function1 = function11;
                    ientity = ientity1;
                } else {
                    ientity = null;
                }

                return ((Boolean) function1.invoke(ientity)).booleanValue();
            }
        }));
        Function1 function1 = (Function1) null.INSTANCE;
        Function1 function11 = (Function1) null.INSTANCE;
        Function1 function12 = function1;
        Collection collection1 = collection;

        return (Collection) (new WrappedCollection(collection1, function12, function11));
    }

    @NotNull
    public IIBlockState getBlockState(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        World world = this.wrapped;
        boolean $i$f$wrap = false;
        BlockPos blockpos = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        IBlockState iblockstate = world.getBlockState(blockpos);

        Intrinsics.checkExpressionValueIsNotNull(iblockstate, "wrapped.getBlockState(blockPos.unwrap())");
        IBlockState $this$wrap$iv = iblockstate;

        $i$f$wrap = false;
        return (IIBlockState) (new IBlockStateImpl($this$wrap$iv));
    }

    @NotNull
    public Collection getEntitiesWithinAABBExcludingEntity(@Nullable IEntity entity, @NotNull IAxisAlignedBB bb) {
        Intrinsics.checkParameterIsNotNull(bb, "bb");
        World world = this.wrapped;
        boolean $i$f$unwrap;
        World world1;
        Entity entity;
        Entity entity1;

        if (entity != null) {
            world1 = world;
            $i$f$unwrap = false;
            if (entity == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.EntityImpl<*>");
            }

            entity = ((EntityImpl) entity).getWrapped();
            world = world1;
            entity1 = entity;
        } else {
            entity1 = null;
        }

        entity = entity1;
        world1 = world;
        $i$f$unwrap = false;
        AxisAlignedBB axisalignedbb = ((AxisAlignedBBImpl) bb).getWrapped();
        Collection collection = (Collection) world1.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb);
        Function1 function1 = (Function1) null.INSTANCE;
        Function1 function11 = (Function1) null.INSTANCE;
        Function1 function12 = function1;
        Collection collection1 = collection;

        return (Collection) (new WrappedCollection(collection1, function12, function11));
    }

    @NotNull
    public Collection getCollidingBoundingBoxes(@NotNull IEntity entity, @NotNull IAxisAlignedBB bb) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        Intrinsics.checkParameterIsNotNull(bb, "bb");
        World world = this.wrapped;
        boolean $i$f$unwrap = false;
        Entity entity = ((EntityImpl) entity).getWrapped();

        $i$f$unwrap = false;
        AxisAlignedBB axisalignedbb = ((AxisAlignedBBImpl) bb).getWrapped();
        Collection collection = (Collection) world.getCollisionBoxes(entity, axisalignedbb);
        Function1 function1 = (Function1) null.INSTANCE;
        Function1 function11 = (Function1) null.INSTANCE;
        Function1 function12 = function1;
        Collection collection1 = collection;

        return (Collection) (new WrappedCollection(collection1, function12, function11));
    }

    public boolean checkBlockCollision(@NotNull IAxisAlignedBB aabb) {
        Intrinsics.checkParameterIsNotNull(aabb, "aabb");
        World world = this.wrapped;
        boolean $i$f$unwrap = false;
        AxisAlignedBB axisalignedbb = ((AxisAlignedBBImpl) aabb).getWrapped();

        return world.checkBlockCollision(axisalignedbb);
    }

    @NotNull
    public Collection getCollisionBoxes(@NotNull IAxisAlignedBB bb) {
        Intrinsics.checkParameterIsNotNull(bb, "bb");
        Object object = null;
        World world = this.wrapped;
        boolean $i$f$unwrap = false;
        AxisAlignedBB axisalignedbb = ((AxisAlignedBBImpl) bb).getWrapped();
        Collection collection = (Collection) world.getCollisionBoxes((Entity) object, axisalignedbb);
        Function1 function1 = (Function1) null.INSTANCE;
        Function1 function11 = (Function1) null.INSTANCE;
        Function1 function12 = function1;
        Collection collection1 = collection;

        return (Collection) (new WrappedCollection(collection1, function12, function11));
    }

    @NotNull
    public IChunk getChunkFromChunkCoords(int x, int z) {
        Chunk chunk = this.wrapped.getChunk(x, z);

        Intrinsics.checkExpressionValueIsNotNull(chunk, "wrapped.getChunkFromChunkCoords(x, z)");
        Chunk $this$wrap$iv = chunk;
        boolean $i$f$wrap = false;

        return (IChunk) (new ChunkImpl($this$wrap$iv));
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof WorldImpl && Intrinsics.areEqual(((WorldImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final World getWrapped() {
        return this.wrapped;
    }

    public WorldImpl(@NotNull World wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

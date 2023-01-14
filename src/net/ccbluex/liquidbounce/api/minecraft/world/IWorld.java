package net.ccbluex.liquidbounce.api.minecraft.world;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreboard;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.world.border.IWorldBorder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH&J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H&J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000fH&J\u0016\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u001a2\u0006\u0010\u001d\u001a\u00020\u000fH&J6\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001a2\b\u0010 \u001a\u0004\u0018\u00010\u001c2\u0006\u0010!\u001a\u00020\u000f2\u0014\u0010\"\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u001c\u0012\u0004\u0012\u00020\u00030#H&J \u0010$\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u000fH&J\u0012\u0010%\u001a\u0004\u0018\u00010\u001c2\u0006\u0010&\u001a\u00020\u0017H&J\u001a\u0010\'\u001a\u0004\u0018\u00010(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*H&J\"\u0010\'\u001a\u0004\u0018\u00010(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020\u0003H&J2\u0010\'\u001a\u0004\u0018\u00010(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020\u00032\u0006\u0010-\u001a\u00020\u00032\u0006\u0010.\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006/"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "", "isRemote", "", "()Z", "scoreboard", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScoreboard;", "getScoreboard", "()Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScoreboard;", "worldBorder", "Lnet/ccbluex/liquidbounce/api/minecraft/world/border/IWorldBorder;", "getWorldBorder", "()Lnet/ccbluex/liquidbounce/api/minecraft/world/border/IWorldBorder;", "checkBlockCollision", "aabb", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "getBlockState", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getChunkFromChunkCoords", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IChunk;", "x", "", "z", "getCollidingBoundingBoxes", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "bb", "getCollisionBoxes", "getEntitiesInAABBexcluding", "entityIn", "boundingBox", "predicate", "Lkotlin/Function1;", "getEntitiesWithinAABBExcludingEntity", "getEntityByID", "id", "rayTraceBlocks", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "start", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "end", "stopOnLiquid", "ignoreBlockWithoutBoundingBox", "returnLastUncollidableBlock", "LiquidBounce"}
)
public interface IWorld {

    boolean isRemote();

    @NotNull
    IScoreboard getScoreboard();

    @NotNull
    IWorldBorder getWorldBorder();

    @Nullable
    IEntity getEntityByID(int i);

    @Nullable
    IMovingObjectPosition rayTraceBlocks(@NotNull WVec3 wvec3, @NotNull WVec3 wvec31);

    @Nullable
    IMovingObjectPosition rayTraceBlocks(@NotNull WVec3 wvec3, @NotNull WVec3 wvec31, boolean flag);

    @Nullable
    IMovingObjectPosition rayTraceBlocks(@NotNull WVec3 wvec3, @NotNull WVec3 wvec31, boolean flag, boolean flag1, boolean flag2);

    @NotNull
    Collection getEntitiesInAABBexcluding(@Nullable IEntity ientity, @NotNull IAxisAlignedBB iaxisalignedbb, @NotNull Function1 function1);

    @NotNull
    IIBlockState getBlockState(@NotNull WBlockPos wblockpos);

    @NotNull
    Collection getEntitiesWithinAABBExcludingEntity(@Nullable IEntity ientity, @NotNull IAxisAlignedBB iaxisalignedbb);

    @NotNull
    Collection getCollidingBoundingBoxes(@NotNull IEntity ientity, @NotNull IAxisAlignedBB iaxisalignedbb);

    boolean checkBlockCollision(@NotNull IAxisAlignedBB iaxisalignedbb);

    @NotNull
    Collection getCollisionBoxes(@NotNull IAxisAlignedBB iaxisalignedbb);

    @NotNull
    IChunk getChunkFromChunkCoords(int i, int j);
}

package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt$WhenMappings;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001f"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/MovingObjectPositionImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "wrapped", "Lnet/minecraft/util/math/RayTraceResult;", "(Lnet/minecraft/util/math/RayTraceResult;)V", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getBlockPos", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "entityHit", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "getEntityHit", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "hitVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "getHitVec", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "sideHit", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "getSideHit", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "typeOfHit", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition$WMovingObjectType;", "getTypeOfHit", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition$WMovingObjectType;", "getWrapped", "()Lnet/minecraft/util/math/RayTraceResult;", "equals", "", "other", "", "LiquidBounce"}
)
public final class MovingObjectPositionImpl implements IMovingObjectPosition {

    @NotNull
    private final RayTraceResult wrapped;

    @Nullable
    public IEntity getEntityHit() {
        Entity entity = this.wrapped.entityHit;
        IEntity ientity;

        if (this.wrapped.entityHit != null) {
            Entity $this$wrap$iv = entity;
            boolean $i$f$wrap = false;

            ientity = (IEntity) (new EntityImpl($this$wrap$iv));
        } else {
            ientity = null;
        }

        return ientity;
    }

    @Nullable
    public WBlockPos getBlockPos() {
        BlockPos blockpos = this.wrapped.getBlockPos();
        WBlockPos wblockpos;

        if (blockpos != null) {
            BlockPos $this$wrap$iv = blockpos;
            boolean $i$f$wrap = false;

            wblockpos = new WBlockPos($this$wrap$iv.getX(), $this$wrap$iv.getY(), $this$wrap$iv.getZ());
        } else {
            wblockpos = null;
        }

        return wblockpos;
    }

    @Nullable
    public IEnumFacing getSideHit() {
        EnumFacing enumfacing = this.wrapped.sideHit;
        IEnumFacing ienumfacing;

        if (this.wrapped.sideHit != null) {
            EnumFacing $this$wrap$iv = enumfacing;
            boolean $i$f$wrap = false;

            ienumfacing = (IEnumFacing) (new EnumFacingImpl($this$wrap$iv));
        } else {
            ienumfacing = null;
        }

        return ienumfacing;
    }

    @NotNull
    public WVec3 getHitVec() {
        Vec3d vec3d = this.wrapped.hitVec;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.hitVec, "wrapped.hitVec");
        Vec3d $this$wrap$iv = vec3d;
        boolean $i$f$wrap = false;

        return new WVec3($this$wrap$iv.x, $this$wrap$iv.y, $this$wrap$iv.z);
    }

    @NotNull
    public IMovingObjectPosition.WMovingObjectType getTypeOfHit() {
        Type type = this.wrapped.typeOfHit;

        Intrinsics.checkExpressionValueIsNotNull(this.wrapped.typeOfHit, "wrapped.typeOfHit");
        Type $this$wrap$iv = type;
        boolean $i$f$wrap = false;
        IMovingObjectPosition.WMovingObjectType imovingobjectposition_wmovingobjecttype;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$0[$this$wrap$iv.ordinal()]) {
        case 1:
            imovingobjectposition_wmovingobjecttype = IMovingObjectPosition.WMovingObjectType.MISS;
            break;

        case 2:
            imovingobjectposition_wmovingobjecttype = IMovingObjectPosition.WMovingObjectType.BLOCK;
            break;

        case 3:
            imovingobjectposition_wmovingobjecttype = IMovingObjectPosition.WMovingObjectType.ENTITY;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return imovingobjectposition_wmovingobjecttype;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof MovingObjectPositionImpl && Intrinsics.areEqual(((MovingObjectPositionImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final RayTraceResult getWrapped() {
        return this.wrapped;
    }

    public MovingObjectPositionImpl(@NotNull RayTraceResult wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

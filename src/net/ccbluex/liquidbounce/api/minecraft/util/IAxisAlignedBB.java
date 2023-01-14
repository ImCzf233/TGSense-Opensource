package net.ccbluex.liquidbounce.api.minecraft.util;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\bf\u0018\u00002\u00020\u0001J \u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0003H&J\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H&J \u0010\u0019\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0003H&J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0000H&J\u0010\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u0017H&J \u0010\u001f\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005R\u0012\u0010\n\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u0012\u0010\f\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005R\u0012\u0010\u000e\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005¨\u0006#"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "", "maxX", "", "getMaxX", "()D", "maxY", "getMaxY", "maxZ", "getMaxZ", "minX", "getMinX", "minY", "getMinY", "minZ", "getMinZ", "addCoord", "x", "y", "z", "calculateIntercept", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "from", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "to", "expand", "intersectsWith", "", "boundingBox", "isVecInside", "vec", "offset", "sx", "sy", "sz", "LiquidBounce"}
)
public interface IAxisAlignedBB {

    @NotNull
    IAxisAlignedBB addCoord(double d0, double d1, double d2);

    @NotNull
    IAxisAlignedBB expand(double d0, double d1, double d2);

    @Nullable
    IMovingObjectPosition calculateIntercept(@NotNull WVec3 wvec3, @NotNull WVec3 wvec31);

    boolean isVecInside(@NotNull WVec3 wvec3);

    @NotNull
    IAxisAlignedBB offset(double d0, double d1, double d2);

    boolean intersectsWith(@NotNull IAxisAlignedBB iaxisalignedbb);

    double getMinX();

    double getMinY();

    double getMinZ();

    double getMaxX();

    double getMaxY();

    double getMaxZ();
}

package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\n\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006H\u0016J\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH\u0016J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0096\u0002J \u0010\"\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006H\u0016J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u0001H\u0016J\u0010\u0010%\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\u001cH\u0016J \u0010\'\u001a\u00020\u00012\u0006\u0010(\u001a\u00020\u00062\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\bR\u0014\u0010\u0011\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006+"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/AxisAlignedBBImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "wrapped", "Lnet/minecraft/util/math/AxisAlignedBB;", "(Lnet/minecraft/util/math/AxisAlignedBB;)V", "maxX", "", "getMaxX", "()D", "maxY", "getMaxY", "maxZ", "getMaxZ", "minX", "getMinX", "minY", "getMinY", "minZ", "getMinZ", "getWrapped", "()Lnet/minecraft/util/math/AxisAlignedBB;", "addCoord", "x", "y", "z", "calculateIntercept", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "from", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "to", "equals", "", "other", "", "expand", "intersectsWith", "boundingBox", "isVecInside", "vec", "offset", "sx", "sy", "sz", "LiquidBounce"}
)
public final class AxisAlignedBBImpl implements IAxisAlignedBB {

    @NotNull
    private final AxisAlignedBB wrapped;

    @NotNull
    public IAxisAlignedBB addCoord(double x, double y, double z) {
        AxisAlignedBB axisalignedbb = this.wrapped.expand(x, y, z);

        Intrinsics.checkExpressionValueIsNotNull(axisalignedbb, "wrapped.expand(x, y, z)");
        AxisAlignedBB $this$wrap$iv = axisalignedbb;
        boolean $i$f$wrap = false;

        return (IAxisAlignedBB) (new AxisAlignedBBImpl($this$wrap$iv));
    }

    @NotNull
    public IAxisAlignedBB expand(double x, double y, double z) {
        AxisAlignedBB axisalignedbb = this.wrapped.grow(x, y, z);

        Intrinsics.checkExpressionValueIsNotNull(axisalignedbb, "wrapped.grow(x, y, z)");
        AxisAlignedBB $this$wrap$iv = axisalignedbb;
        boolean $i$f$wrap = false;

        return (IAxisAlignedBB) (new AxisAlignedBBImpl($this$wrap$iv));
    }

    @Nullable
    public IMovingObjectPosition calculateIntercept(@NotNull WVec3 from, @NotNull WVec3 to) {
        Intrinsics.checkParameterIsNotNull(from, "from");
        Intrinsics.checkParameterIsNotNull(to, "to");
        AxisAlignedBB axisalignedbb = this.wrapped;
        boolean $i$f$wrap = false;
        Vec3d vec3d = new Vec3d(from.getXCoord(), from.getYCoord(), from.getZCoord());

        $i$f$wrap = false;
        Vec3d vec3d1 = new Vec3d(to.getXCoord(), to.getYCoord(), to.getZCoord());
        RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d1);
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

    public boolean isVecInside(@NotNull WVec3 vec) {
        Intrinsics.checkParameterIsNotNull(vec, "vec");
        AxisAlignedBB axisalignedbb = this.wrapped;
        boolean $i$f$unwrap = false;
        Vec3d vec3d = new Vec3d(vec.getXCoord(), vec.getYCoord(), vec.getZCoord());

        return axisalignedbb.contains(vec3d);
    }

    @NotNull
    public IAxisAlignedBB offset(double sx, double sy, double sz) {
        AxisAlignedBB axisalignedbb = this.wrapped.offset(sx, sy, sz);

        Intrinsics.checkExpressionValueIsNotNull(axisalignedbb, "wrapped.offset(sx, sy, sz)");
        AxisAlignedBB $this$wrap$iv = axisalignedbb;
        boolean $i$f$wrap = false;

        return (IAxisAlignedBB) (new AxisAlignedBBImpl($this$wrap$iv));
    }

    public boolean intersectsWith(@NotNull IAxisAlignedBB boundingBox) {
        Intrinsics.checkParameterIsNotNull(boundingBox, "boundingBox");
        AxisAlignedBB axisalignedbb = this.wrapped;
        boolean $i$f$unwrap = false;
        AxisAlignedBB axisalignedbb1 = ((AxisAlignedBBImpl) boundingBox).getWrapped();

        return axisalignedbb.intersects(axisalignedbb1);
    }

    public double getMinX() {
        return this.wrapped.minX;
    }

    public double getMinY() {
        return this.wrapped.minY;
    }

    public double getMinZ() {
        return this.wrapped.minZ;
    }

    public double getMaxX() {
        return this.wrapped.maxX;
    }

    public double getMaxY() {
        return this.wrapped.maxY;
    }

    public double getMaxZ() {
        return this.wrapped.maxZ;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof AxisAlignedBBImpl && Intrinsics.areEqual(((AxisAlignedBBImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final AxisAlignedBB getWrapped() {
        return this.wrapped;
    }

    public AxisAlignedBBImpl(@NotNull AxisAlignedBB wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

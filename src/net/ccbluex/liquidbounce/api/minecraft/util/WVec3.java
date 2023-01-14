package net.ccbluex.liquidbounce.api.minecraft.util;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006¢\u0006\u0002\u0010\tJ\u0011\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0000H\u0086\bJ!\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0006H\u0086\bJ\u000e\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0000J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001cJ\u0011\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0000H\u0086\bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000b¨\u0006 "},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;)V", "xCoord", "", "yCoord", "zCoord", "(DDD)V", "getXCoord", "()D", "getYCoord", "getZCoord", "add", "vec", "addVector", "x", "y", "z", "distanceTo", "equals", "", "other", "hashCode", "", "rotatePitch", "pitch", "", "rotateYaw", "yaw", "squareDistanceTo", "LiquidBounce"}
)
public final class WVec3 {

    private final double xCoord;
    private final double yCoord;
    private final double zCoord;

    @NotNull
    public final WVec3 addVector(double x, double y, double z) {
        byte $i$f$addVector = 0;

        return new WVec3(this.getXCoord() + x, this.getYCoord() + y, this.getZCoord() + z);
    }

    public final double distanceTo(@NotNull WVec3 vec) {
        Intrinsics.checkParameterIsNotNull(vec, "vec");
        double d0 = vec.xCoord - this.xCoord;
        double d1 = vec.yCoord - this.yCoord;
        double d2 = vec.zCoord - this.zCoord;
        double d0 = d0 * d0 + d1 * d1 + d2 * d2;
        boolean flag = false;

        return Math.sqrt(d0);
    }

    public final double squareDistanceTo(@NotNull WVec3 vec) {
        byte $i$f$squareDistanceTo = 0;

        Intrinsics.checkParameterIsNotNull(vec, "vec");
        double d0 = vec.getXCoord() - this.getXCoord();
        double d1 = vec.getYCoord() - this.getYCoord();
        double d2 = vec.getZCoord() - this.getZCoord();

        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    @NotNull
    public final WVec3 add(@NotNull WVec3 vec) {
        byte $i$f$add = 0;

        Intrinsics.checkParameterIsNotNull(vec, "vec");
        double x$iv = vec.getXCoord();
        double y$iv = vec.getYCoord();
        double z$iv = vec.getZCoord();
        boolean $i$f$addVector = false;

        return new WVec3(this.getXCoord() + x$iv, this.getYCoord() + y$iv, this.getZCoord() + z$iv);
    }

    @NotNull
    public final WVec3 rotatePitch(float pitch) {
        boolean f1 = false;
        float f = (float) Math.cos((double) pitch);
        boolean d0 = false;
        float f11 = (float) Math.sin((double) pitch);
        double d01 = this.xCoord;
        double d1 = this.yCoord * (double) f + this.zCoord * (double) f11;
        double d2 = this.zCoord * (double) f - this.yCoord * (double) f11;

        return new WVec3(d01, d1, d2);
    }

    @NotNull
    public final WVec3 rotateYaw(float yaw) {
        boolean f1 = false;
        float f = (float) Math.cos((double) yaw);
        boolean d0 = false;
        float f11 = (float) Math.sin((double) yaw);
        double d01 = this.xCoord * (double) f + this.zCoord * (double) f11;
        double d1 = this.yCoord;
        double d2 = this.zCoord * (double) f - this.xCoord * (double) f11;

        return new WVec3(d01, d1, d2);
    }

    public boolean equals(@Nullable Object other) {
        if ((WVec3) this == other) {
            return true;
        } else if (Intrinsics.areEqual(this.getClass(), other != null ? other.getClass() : null) ^ true) {
            return false;
        } else if (other == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.api.minecraft.util.WVec3");
        } else {
            WVec3 wvec3 = (WVec3) other;

            return this.xCoord != ((WVec3) other).xCoord ? false : (this.yCoord != ((WVec3) other).yCoord ? false : this.zCoord == ((WVec3) other).zCoord);
        }
    }

    public int hashCode() {
        int result = Double.hashCode(this.xCoord);

        result = 31 * result + Double.hashCode(this.yCoord);
        result = 31 * result + Double.hashCode(this.zCoord);
        return result;
    }

    public final double getXCoord() {
        return this.xCoord;
    }

    public final double getYCoord() {
        return this.yCoord;
    }

    public final double getZCoord() {
        return this.zCoord;
    }

    public WVec3(double xCoord, double yCoord, double zCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
    }

    public WVec3(@NotNull WVec3i blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        this((double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ());
    }
}

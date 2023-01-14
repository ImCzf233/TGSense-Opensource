package net.ccbluex.liquidbounce.api.minecraft.util;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0007\u0012\u0006\u0010\u0005\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0007H\u0016R\u0011\u0010\u0002\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\n¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "", "x", "", "y", "z", "(DDD)V", "", "(III)V", "getX", "()I", "getY", "getZ", "equals", "", "other", "hashCode", "LiquidBounce"}
)
public class WVec3i {

    private final int x;
    private final int y;
    private final int z;

    public boolean equals(@Nullable Object other) {
        if ((WVec3i) this == other) {
            return true;
        } else if (Intrinsics.areEqual(this.getClass(), other != null ? other.getClass() : null) ^ true) {
            return false;
        } else if (other == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.api.minecraft.util.WVec3i");
        } else {
            WVec3i wvec3i = (WVec3i) other;

            return this.x != ((WVec3i) other).x ? false : (this.y != ((WVec3i) other).y ? false : this.z == ((WVec3i) other).z);
        }
    }

    public int hashCode() {
        int result = this.x;

        result = 31 * result + this.y;
        result = 31 * result + this.z;
        return result;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final int getZ() {
        return this.z;
    }

    public WVec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public WVec3i(double x, double y, double z) {
        boolean flag = false;
        double d0 = Math.floor(x);
        int i = (int) d0;

        flag = false;
        double d1 = Math.floor(y);
        int j = (int) d1;

        flag = false;
        double d2 = Math.floor(z);

        this(i, j, (int) d2);
    }
}

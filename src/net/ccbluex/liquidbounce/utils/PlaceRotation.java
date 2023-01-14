package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/PlaceRotation;", "", "placeInfo", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "(Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;Lnet/ccbluex/liquidbounce/utils/Rotation;)V", "getPlaceInfo", "()Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "getRotation", "()Lnet/ccbluex/liquidbounce/utils/Rotation;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "LiquidBounce"}
)
public final class PlaceRotation {

    @NotNull
    private final PlaceInfo placeInfo;
    @NotNull
    private final Rotation rotation;

    @NotNull
    public final PlaceInfo getPlaceInfo() {
        return this.placeInfo;
    }

    @NotNull
    public final Rotation getRotation() {
        return this.rotation;
    }

    public PlaceRotation(@NotNull PlaceInfo placeInfo, @NotNull Rotation rotation) {
        Intrinsics.checkParameterIsNotNull(placeInfo, "placeInfo");
        Intrinsics.checkParameterIsNotNull(rotation, "rotation");
        super();
        this.placeInfo = placeInfo;
        this.rotation = rotation;
    }

    @NotNull
    public final PlaceInfo component1() {
        return this.placeInfo;
    }

    @NotNull
    public final Rotation component2() {
        return this.rotation;
    }

    @NotNull
    public final PlaceRotation copy(@NotNull PlaceInfo placeInfo, @NotNull Rotation rotation) {
        Intrinsics.checkParameterIsNotNull(placeInfo, "placeInfo");
        Intrinsics.checkParameterIsNotNull(rotation, "rotation");
        return new PlaceRotation(placeInfo, rotation);
    }

    public static PlaceRotation copy$default(PlaceRotation placerotation, PlaceInfo placeinfo, Rotation rotation, int i, Object object) {
        if ((i & 1) != 0) {
            placeinfo = placerotation.placeInfo;
        }

        if ((i & 2) != 0) {
            rotation = placerotation.rotation;
        }

        return placerotation.copy(placeinfo, rotation);
    }

    @NotNull
    public String toString() {
        return "PlaceRotation(placeInfo=" + this.placeInfo + ", rotation=" + this.rotation + ")";
    }

    public int hashCode() {
        return (this.placeInfo != null ? this.placeInfo.hashCode() : 0) * 31 + (this.rotation != null ? this.rotation.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof PlaceRotation) {
                PlaceRotation placerotation = (PlaceRotation) object;

                if (Intrinsics.areEqual(this.placeInfo, placerotation.placeInfo) && Intrinsics.areEqual(this.rotation, placerotation.rotation)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}

package net.ccbluex.liquidbounce.cape;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0007\"\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"},
    d2 = { "Lnet/ccbluex/liquidbounce/cape/CapeInfo;", "", "resourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "isCapeAvailable", "", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;Z)V", "()Z", "setCapeAvailable", "(Z)V", "getResourceLocation", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "", "LiquidBounce"}
)
public final class CapeInfo {

    @NotNull
    private final IResourceLocation resourceLocation;
    private boolean isCapeAvailable;

    @NotNull
    public final IResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public final boolean isCapeAvailable() {
        return this.isCapeAvailable;
    }

    public final void setCapeAvailable(boolean <set-?>) {
        this.isCapeAvailable = <set-?>;
    }

    public CapeInfo(@NotNull IResourceLocation resourceLocation, boolean isCapeAvailable) {
        Intrinsics.checkParameterIsNotNull(resourceLocation, "resourceLocation");
        super();
        this.resourceLocation = resourceLocation;
        this.isCapeAvailable = isCapeAvailable;
    }

    public CapeInfo(IResourceLocation iresourcelocation, boolean flag, int i, DefaultConstructorMarker defaultconstructormarker) {
        if ((i & 2) != 0) {
            flag = false;
        }

        this(iresourcelocation, flag);
    }

    @NotNull
    public final IResourceLocation component1() {
        return this.resourceLocation;
    }

    public final boolean component2() {
        return this.isCapeAvailable;
    }

    @NotNull
    public final CapeInfo copy(@NotNull IResourceLocation resourceLocation, boolean isCapeAvailable) {
        Intrinsics.checkParameterIsNotNull(resourceLocation, "resourceLocation");
        return new CapeInfo(resourceLocation, isCapeAvailable);
    }

    public static CapeInfo copy$default(CapeInfo capeinfo, IResourceLocation iresourcelocation, boolean flag, int i, Object object) {
        if ((i & 1) != 0) {
            iresourcelocation = capeinfo.resourceLocation;
        }

        if ((i & 2) != 0) {
            flag = capeinfo.isCapeAvailable;
        }

        return capeinfo.copy(iresourcelocation, flag);
    }

    @NotNull
    public String toString() {
        return "CapeInfo(resourceLocation=" + this.resourceLocation + ", isCapeAvailable=" + this.isCapeAvailable + ")";
    }

    public int hashCode() {
        int i = (this.resourceLocation != null ? this.resourceLocation.hashCode() : 0) * 31;
        byte b0 = this.isCapeAvailable;

        if (this.isCapeAvailable) {
            b0 = 1;
        }

        return i + b0;
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof CapeInfo) {
                CapeInfo capeinfo = (CapeInfo) object;

                if (Intrinsics.areEqual(this.resourceLocation, capeinfo.resourceLocation) && this.isCapeAvailable == capeinfo.isCapeAvailable) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}

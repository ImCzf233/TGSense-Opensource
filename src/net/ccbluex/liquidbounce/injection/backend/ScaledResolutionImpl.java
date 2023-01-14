package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.minecraft.client.gui.ScaledResolution;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/ScaledResolutionImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IScaledResolution;", "wrapped", "Lnet/minecraft/client/gui/ScaledResolution;", "(Lnet/minecraft/client/gui/ScaledResolution;)V", "scaleFactor", "", "getScaleFactor", "()I", "scaledHeight", "getScaledHeight", "scaledWidth", "getScaledWidth", "getWrapped", "()Lnet/minecraft/client/gui/ScaledResolution;", "equals", "", "other", "", "LiquidBounce"}
)
public final class ScaledResolutionImpl implements IScaledResolution {

    @NotNull
    private final ScaledResolution wrapped;

    public int getScaledWidth() {
        return this.wrapped.getScaledWidth();
    }

    public int getScaledHeight() {
        return this.wrapped.getScaledHeight();
    }

    public int getScaleFactor() {
        return this.wrapped.getScaleFactor();
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ScaledResolutionImpl && Intrinsics.areEqual(((ScaledResolutionImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final ScaledResolution getWrapped() {
        return this.wrapped;
    }

    public ScaledResolutionImpl(@NotNull ScaledResolution wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

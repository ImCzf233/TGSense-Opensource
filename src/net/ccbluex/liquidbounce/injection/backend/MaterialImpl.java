package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.minecraft.block.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\n\u001a\u00020\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/MaterialImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "wrapped", "Lnet/minecraft/block/material/Material;", "(Lnet/minecraft/block/material/Material;)V", "isReplaceable", "", "()Z", "getWrapped", "()Lnet/minecraft/block/material/Material;", "equals", "other", "", "LiquidBounce"}
)
public final class MaterialImpl implements IMaterial {

    @NotNull
    private final Material wrapped;

    public boolean isReplaceable() {
        return this.wrapped.isReplaceable();
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof MaterialImpl && Intrinsics.areEqual(((MaterialImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final Material getWrapped() {
        return this.wrapped;
    }

    public MaterialImpl(@NotNull Material wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

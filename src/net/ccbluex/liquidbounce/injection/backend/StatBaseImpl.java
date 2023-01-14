package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.stats.IStatBase;
import net.minecraft.stats.StatBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/StatBaseImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/stats/IStatBase;", "wrapped", "Lnet/minecraft/stats/StatBase;", "(Lnet/minecraft/stats/StatBase;)V", "getWrapped", "()Lnet/minecraft/stats/StatBase;", "equals", "", "other", "", "LiquidBounce"}
)
public final class StatBaseImpl implements IStatBase {

    @NotNull
    private final StatBase wrapped;

    public boolean equals(@Nullable Object other) {
        return other instanceof StatBaseImpl && Intrinsics.areEqual(((StatBaseImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final StatBase getWrapped() {
        return this.wrapped;
    }

    public StatBaseImpl(@NotNull StatBase wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

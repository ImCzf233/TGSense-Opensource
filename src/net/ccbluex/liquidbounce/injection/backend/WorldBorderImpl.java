package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.border.IWorldBorder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.border.WorldBorder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0013\u0010\u000b\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0096\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/WorldBorderImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/world/border/IWorldBorder;", "wrapped", "Lnet/minecraft/world/border/WorldBorder;", "(Lnet/minecraft/world/border/WorldBorder;)V", "getWrapped", "()Lnet/minecraft/world/border/WorldBorder;", "contains", "", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "equals", "other", "", "LiquidBounce"}
)
public final class WorldBorderImpl implements IWorldBorder {

    @NotNull
    private final WorldBorder wrapped;

    public boolean contains(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        WorldBorder worldborder = this.wrapped;
        boolean $i$f$unwrap = false;
        BlockPos blockpos = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        return worldborder.contains(blockpos);
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof WorldBorderImpl && Intrinsics.areEqual(((WorldBorderImpl) other).wrapped, this.wrapped);
    }

    @NotNull
    public final WorldBorder getWrapped() {
        return this.wrapped;
    }

    public WorldBorderImpl(@NotNull WorldBorder wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super();
        this.wrapped = wrapped;
    }
}

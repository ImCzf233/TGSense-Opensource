package net.ccbluex.liquidbounce.api.minecraft.util;

import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\n\u0012\u0006\u0010\u0004\u001a\u00020\n\u0012\u0006\u0010\u0005\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\nJ\u0006\u0010\r\u001a\u00020\u0000J\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nJ\u0006\u0010\u000f\u001a\u00020\u0000J\u000e\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nJ\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u0006\u0010\u0012\u001a\u00020\u0000J\u000e\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nJ\u001a\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u000e\u001a\u00020\nH\u0007J\u0006\u0010\u0016\u001a\u00020\u0000J\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nJ\u0006\u0010\u0017\u001a\u00020\u0000J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nJ\u0006\u0010\u0018\u001a\u00020\u0000J\u000e\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\n¨\u0006\u001a"},
    d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "x", "", "y", "z", "(DDD)V", "source", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;)V", "", "(III)V", "add", "down", "n", "east", "getBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "north", "offset", "side", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "south", "up", "west", "Companion", "LiquidBounce"}
)
public final class WBlockPos extends WVec3i {

    @NotNull
    private static final WBlockPos ORIGIN = new WBlockPos(0, 0, 0);
    public static final WBlockPos.Companion Companion = new WBlockPos.Companion((DefaultConstructorMarker) null);

    @NotNull
    public final WBlockPos add(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0 ? this : new WBlockPos(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    @JvmOverloads
    @NotNull
    public final WBlockPos offset(@NotNull IEnumFacing side, int n) {
        Intrinsics.checkParameterIsNotNull(side, "side");
        return n == 0 ? this : new WBlockPos(this.getX() + side.getDirectionVec().getX() * n, this.getY() + side.getDirectionVec().getY() * n, this.getZ() + side.getDirectionVec().getZ() * n);
    }

    public static WBlockPos offset$default(WBlockPos wblockpos, IEnumFacing ienumfacing, int i, int j, Object object) {
        if ((j & 2) != 0) {
            i = 1;
        }

        return wblockpos.offset(ienumfacing, i);
    }

    @JvmOverloads
    @NotNull
    public final WBlockPos offset(@NotNull IEnumFacing side) {
        return offset$default(this, side, 0, 2, (Object) null);
    }

    @NotNull
    public final WBlockPos up() {
        return this.up(1);
    }

    @NotNull
    public final WBlockPos up(int n) {
        return this.offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.UP), n);
    }

    @NotNull
    public final WBlockPos down() {
        return this.down(1);
    }

    @NotNull
    public final WBlockPos down(int n) {
        return this.offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.DOWN), n);
    }

    @NotNull
    public final WBlockPos west() {
        return this.west(1);
    }

    @NotNull
    public final WBlockPos west(int n) {
        return this.offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.WEST), n);
    }

    @NotNull
    public final WBlockPos east() {
        return this.east(1);
    }

    @NotNull
    public final WBlockPos east(int n) {
        return this.offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.EAST), n);
    }

    @NotNull
    public final WBlockPos north() {
        return this.north(1);
    }

    @NotNull
    public final WBlockPos north(int n) {
        return this.offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.NORTH), n);
    }

    @NotNull
    public final WBlockPos south() {
        return this.south(1);
    }

    @NotNull
    public final WBlockPos south(int n) {
        return this.offset(WrapperImpl.INSTANCE.getClassProvider().getEnumFacing(EnumFacingType.SOUTH), n);
    }

    @Nullable
    public final IBlock getBlock() {
        return BlockUtils.getBlock(this);
    }

    public WBlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    public WBlockPos(double x, double y, double z) {
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

    public WBlockPos(@NotNull IEntity source) {
        Intrinsics.checkParameterIsNotNull(source, "source");
        this(source.getPosX(), source.getPosY(), source.getPosZ());
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
        d2 = { "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos$Companion;", "", "()V", "ORIGIN", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getORIGIN", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "LiquidBounce"}
    )
    public static final class Companion {

        @NotNull
        public final WBlockPos getORIGIN() {
            return WBlockPos.ORIGIN;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

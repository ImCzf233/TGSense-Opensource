package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt$WhenMappings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/CPacketPlayerDiggingImpl;", "T", "Lnet/minecraft/network/play/client/CPacketPlayerDigging;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging;", "wrapped", "(Lnet/minecraft/network/play/client/CPacketPlayerDigging;)V", "action", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging$WAction;", "getAction", "()Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging$WAction;", "facing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "getFacing", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "position", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getPosition", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "LiquidBounce"}
)
public final class CPacketPlayerDiggingImpl extends PacketImpl implements ICPacketPlayerDigging {

    @NotNull
    public WBlockPos getPosition() {
        BlockPos blockpos = ((CPacketPlayerDigging) this.getWrapped()).getPosition();

        Intrinsics.checkExpressionValueIsNotNull(blockpos, "wrapped.position");
        int i = blockpos.getX();
        BlockPos blockpos1 = ((CPacketPlayerDigging) this.getWrapped()).getPosition();

        Intrinsics.checkExpressionValueIsNotNull(blockpos1, "wrapped.position");
        int j = blockpos1.getY();
        BlockPos blockpos2 = ((CPacketPlayerDigging) this.getWrapped()).getPosition();

        Intrinsics.checkExpressionValueIsNotNull(blockpos2, "wrapped.position");
        return new WBlockPos(i, j, blockpos2.getZ());
    }

    @NotNull
    public IEnumFacing getFacing() {
        EnumFacing enumfacing = ((CPacketPlayerDigging) this.getWrapped()).getFacing();

        Intrinsics.checkExpressionValueIsNotNull(enumfacing, "wrapped.facing");
        EnumFacing $this$wrap$iv = enumfacing;
        boolean $i$f$wrap = false;

        return (IEnumFacing) (new EnumFacingImpl($this$wrap$iv));
    }

    @NotNull
    public ICPacketPlayerDigging.WAction getAction() {
        Action action = ((CPacketPlayerDigging) this.getWrapped()).getAction();

        Intrinsics.checkExpressionValueIsNotNull(action, "wrapped.action");
        Action $this$wrap$iv = action;
        boolean $i$f$wrap = false;
        ICPacketPlayerDigging.WAction icpacketplayerdigging_waction;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$9[$this$wrap$iv.ordinal()]) {
        case 1:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.ABORT_DESTROY_BLOCK;
            break;

        case 2:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.DROP_ALL_ITEMS;
            break;

        case 3:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.DROP_ITEM;
            break;

        case 4:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM;
            break;

        case 5:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK;
            break;

        case 6:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK;
            break;

        case 7:
            icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.SWAP_HELD_ITEMS;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return icpacketplayerdigging_waction;
    }

    public CPacketPlayerDiggingImpl(@NotNull CPacketPlayerDigging wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Packet) wrapped);
    }
}

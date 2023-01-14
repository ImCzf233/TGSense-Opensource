package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt$WhenMappings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity.Action;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/CPacketUseEntityImpl;", "T", "Lnet/minecraft/network/play/client/CPacketUseEntity;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity;", "wrapped", "(Lnet/minecraft/network/play/client/CPacketUseEntity;)V", "action", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity$WAction;", "getAction", "()Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity$WAction;", "LiquidBounce"}
)
public final class CPacketUseEntityImpl extends PacketImpl implements ICPacketUseEntity {

    @NotNull
    public ICPacketUseEntity.WAction getAction() {
        Action action = ((CPacketUseEntity) this.getWrapped()).getAction();

        Intrinsics.checkExpressionValueIsNotNull(action, "wrapped.action");
        Action $this$wrap$iv = action;
        boolean $i$f$wrap = false;
        ICPacketUseEntity.WAction icpacketuseentity_waction;

        switch (BackendExtentionsKt$WhenMappings.$EnumSwitchMapping$7[$this$wrap$iv.ordinal()]) {
        case 1:
            icpacketuseentity_waction = ICPacketUseEntity.WAction.INTERACT;
            break;

        case 2:
            icpacketuseentity_waction = ICPacketUseEntity.WAction.ATTACK;
            break;

        case 3:
            icpacketuseentity_waction = ICPacketUseEntity.WAction.INTERACT_AT;
            break;

        default:
            throw new NoWhenBranchMatchedException();
        }

        return icpacketuseentity_waction;
    }

    public CPacketUseEntityImpl(@NotNull CPacketUseEntity wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Packet) wrapped);
    }
}

package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.network.IEnumConnectionState;
import net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006R$\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0016"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/CPacketHandshakeImpl;", "T", "Lnet/minecraft/network/handshake/client/C00Handshake;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/handshake/client/ICPacketHandshake;", "wrapped", "(Lnet/minecraft/network/handshake/client/C00Handshake;)V", "value", "", "ip", "getIp", "()Ljava/lang/String;", "setIp", "(Ljava/lang/String;)V", "port", "", "getPort", "()I", "requestedState", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IEnumConnectionState;", "getRequestedState", "()Lnet/ccbluex/liquidbounce/api/minecraft/network/IEnumConnectionState;", "LiquidBounce"}
)
public final class CPacketHandshakeImpl extends PacketImpl implements ICPacketHandshake {

    public int getPort() {
        return ((C00Handshake) this.getWrapped()).port;
    }

    @NotNull
    public String getIp() {
        String s = ((C00Handshake) this.getWrapped()).ip;

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.ip");
        return s;
    }

    public void setIp(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        ((C00Handshake) this.getWrapped()).ip = value;
    }

    @NotNull
    public IEnumConnectionState getRequestedState() {
        EnumConnectionState enumconnectionstate = ((C00Handshake) this.getWrapped()).getRequestedState();

        Intrinsics.checkExpressionValueIsNotNull(enumconnectionstate, "wrapped.requestedState");
        EnumConnectionState $this$wrap$iv = enumconnectionstate;
        boolean $i$f$wrap = false;

        return (IEnumConnectionState) (new EnumConnectionStateImpl($this$wrap$iv));
    }

    public CPacketHandshakeImpl(@NotNull C00Handshake wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Packet) wrapped);
    }
}

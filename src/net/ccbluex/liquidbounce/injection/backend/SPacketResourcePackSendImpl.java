package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketResourcePackSend;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\n¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/SPacketResourcePackSendImpl;", "T", "Lnet/minecraft/network/play/server/SPacketResourcePackSend;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketResourcePackSend;", "wrapped", "(Lnet/minecraft/network/play/server/SPacketResourcePackSend;)V", "hash", "", "getHash", "()Ljava/lang/String;", "url", "getUrl", "LiquidBounce"}
)
public final class SPacketResourcePackSendImpl extends PacketImpl implements ISPacketResourcePackSend {

    @NotNull
    public String getUrl() {
        String s = ((SPacketResourcePackSend) this.getWrapped()).getURL();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.url");
        return s;
    }

    @NotNull
    public String getHash() {
        String s = ((SPacketResourcePackSend) this.getWrapped()).getHash();

        Intrinsics.checkExpressionValueIsNotNull(s, "wrapped.hash");
        return s;
    }

    public SPacketResourcePackSendImpl(@NotNull SPacketResourcePackSend wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Packet) wrapped);
    }
}

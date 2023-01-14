package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
    d2 = { "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "packet", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "(Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;)V", "getPacket", "()Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "LiquidBounce"}
)
public final class PacketEvent extends CancellableEvent {

    @NotNull
    private final IPacket packet;

    @NotNull
    public final IPacket getPacket() {
        return this.packet;
    }

    public PacketEvent(@NotNull IPacket packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        super();
        this.packet = packet;
    }
}

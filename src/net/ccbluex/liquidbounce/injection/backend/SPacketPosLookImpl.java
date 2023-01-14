package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\t\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006R$\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR$\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\r¨\u0006\u0011"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/SPacketPosLookImpl;", "T", "Lnet/minecraft/network/play/server/SPacketPlayerPosLook;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketPosLook;", "wrapped", "(Lnet/minecraft/network/play/server/SPacketPlayerPosLook;)V", "value", "", "pitch", "getPitch", "()F", "setPitch", "(F)V", "yaw", "getYaw", "setYaw", "LiquidBounce"}
)
public final class SPacketPosLookImpl extends PacketImpl implements ISPacketPosLook {

    public float getYaw() {
        return ((SPacketPlayerPosLook) this.getWrapped()).yaw;
    }

    public void setYaw(float value) {
        ((SPacketPlayerPosLook) this.getWrapped()).yaw = value;
    }

    public float getPitch() {
        return ((SPacketPlayerPosLook) this.getWrapped()).pitch;
    }

    public void setPitch(float value) {
        ((SPacketPlayerPosLook) this.getWrapped()).pitch = value;
    }

    public SPacketPosLookImpl(@NotNull SPacketPlayerPosLook wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Packet) wrapped);
    }
}

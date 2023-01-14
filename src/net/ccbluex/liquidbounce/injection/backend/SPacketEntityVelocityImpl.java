package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000e\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR$\u0010\f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\u000fR$\u0010\u0013\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0014\u0010\n\"\u0004\b\u0015\u0010\u000f¨\u0006\u0016"},
    d2 = { "Lnet/ccbluex/liquidbounce/injection/backend/SPacketEntityVelocityImpl;", "T", "Lnet/minecraft/network/play/server/SPacketEntityVelocity;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketEntityVelocity;", "wrapped", "(Lnet/minecraft/network/play/server/SPacketEntityVelocity;)V", "entityID", "", "getEntityID", "()I", "value", "motionX", "getMotionX", "setMotionX", "(I)V", "motionY", "getMotionY", "setMotionY", "motionZ", "getMotionZ", "setMotionZ", "LiquidBounce"}
)
public final class SPacketEntityVelocityImpl extends PacketImpl implements ISPacketEntityVelocity {

    public int getMotionX() {
        return ((SPacketEntityVelocity) this.getWrapped()).motionX;
    }

    public void setMotionX(int value) {
        ((SPacketEntityVelocity) this.getWrapped()).motionX = value;
    }

    public int getMotionY() {
        return ((SPacketEntityVelocity) this.getWrapped()).motionY;
    }

    public void setMotionY(int value) {
        ((SPacketEntityVelocity) this.getWrapped()).motionY = value;
    }

    public int getMotionZ() {
        return ((SPacketEntityVelocity) this.getWrapped()).motionZ;
    }

    public void setMotionZ(int value) {
        ((SPacketEntityVelocity) this.getWrapped()).motionZ = value;
    }

    public int getEntityID() {
        return ((SPacketEntityVelocity) this.getWrapped()).getEntityID();
    }

    public SPacketEntityVelocityImpl(@NotNull SPacketEntityVelocity wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        super((Packet) wrapped);
    }
}

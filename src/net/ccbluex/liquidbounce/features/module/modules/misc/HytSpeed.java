package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "HytSpeed",
    description = "AsOne & 司马�?",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\u001bH\u0016J\b\u0010\u001d\u001a\u00020\u001bH\u0016J\u0010\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 H\u0007J\u0012\u0010!\u001a\u00020\u001b2\b\u0010\u001f\u001a\u0004\u0018\u00010\"H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016¨\u0006#"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/HytSpeed;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "doAsFly", "", "getDoAsFly", "()Z", "setDoAsFly", "(Z)V", "motionY", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "packets", "Ljava/util/concurrent/LinkedBlockingQueue;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "getPackets", "()Ljava/util/concurrent/LinkedBlockingQueue;", "speed", "stage", "", "getStage", "()I", "setStage", "(I)V", "timer", "getTimer", "setTimer", "move", "", "onDisable", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class HytSpeed extends Module {

    private final FloatValue speed = new FloatValue("Speed", 0.5F, 0.15F, 8.0F);
    private final FloatValue motionY = new FloatValue("MotionY", 0.42F, 0.1F, 2.0F);
    private boolean doAsFly;
    private int stage;
    private int timer;
    @NotNull
    private final LinkedBlockingQueue packets = new LinkedBlockingQueue();

    public final boolean getDoAsFly() {
        return this.doAsFly;
    }

    public final void setDoAsFly(boolean <set-?>) {
        this.doAsFly = <set-?>;
    }

    public final int getStage() {
        return this.stage;
    }

    public final void setStage(int <set-?>) {
        this.stage = <set-?>;
    }

    public final int getTimer() {
        return this.timer;
    }

    public final void setTimer(int <set-?>) {
        this.timer = <set-?>;
    }

    @NotNull
    public final LinkedBlockingQueue getPackets() {
        return this.packets;
    }

    public final void move() {
        if (MovementUtils.isMoving()) {
            MovementUtils.strafe(((Number) this.speed.get()).floatValue());
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            ientityplayersp.setMotionY((double) ((Number) this.motionY.get()).floatValue());
        }

    }

    public void onEnable() {
        this.timer = 0;
    }

    public void onDisable() {
        this.doAsFly = false;
        if (this.packets.size() > 0) {
            Iterator iterator = this.packets.iterator();

            while (iterator.hasNext()) {
                IPacket packet = (IPacket) iterator.next();
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                IINetHandlerPlayClient iinethandlerplayclient = ientityplayersp.getSendQueue();

                Intrinsics.checkExpressionValueIsNotNull(packet, "packet");
                iinethandlerplayclient.addToSendQueue(packet);
                this.packets.remove(packet);
            }
        }

    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        if (!MovementUtils.isMoving()) {
            this.timer = 0;
        } else {
            int packet = this.timer++;
        }

        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getOnGround() && this.timer > 1) {
            this.doAsFly = true;
            this.stage = 0;
            this.move();
        }

        if (this.stage >= 1) {
            this.doAsFly = false;
            if (this.packets.size() > 0) {
                Iterator iterator = this.packets.iterator();

                while (iterator.hasNext()) {
                    IPacket ipacket = (IPacket) iterator.next();

                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    IINetHandlerPlayClient iinethandlerplayclient = ientityplayersp.getSendQueue();

                    Intrinsics.checkExpressionValueIsNotNull(ipacket, "packet");
                    iinethandlerplayclient.addToSendQueue(ipacket);
                    this.packets.remove(ipacket);
                }
            }
        }

    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.doAsFly) {
            IPacket packet = event.getPacket();

            if (MinecraftInstance.classProvider.isCPacketPlayerPosition(packet) || MinecraftInstance.classProvider.isCPacketPlayerPosLook(packet) || MinecraftInstance.classProvider.isCPacketPlayer(packet)) {
                event.cancelEvent();
                this.packets.add(packet);
                int i = this.stage++;
            }

        }
    }
}

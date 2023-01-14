package net.ccbluex.liquidbounce.features.module.modules.hyt;

import java.util.ArrayList;
import java.util.LinkedList;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import me.utils.PacketUtils;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketKeepAlive;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "HytDisabler",
    description = "HYT",
    category = ModuleCategory.EXPLOIT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000fJ\b\u0010\u0019\u001a\u00020\u0017H\u0016J\b\u0010\u001a\u001a\u00020\u0017H\u0016J\u0010\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0010\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001fH\u0007J\u0010\u0010 \u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020!H\u0007R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u00130\u0004j\b\u0012\u0004\u0012\u00020\u0013`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/hyt/HYTDisabler;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "keepAlives", "Ljava/util/ArrayList;", "Lnet/minecraft/network/play/client/CPacketKeepAlive;", "Lkotlin/collections/ArrayList;", "lagTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "msTimer", "packetBuffer", "Ljava/util/LinkedList;", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "tag", "", "getTag", "()Ljava/lang/String;", "transactions", "Lnet/minecraft/network/play/client/CPacketConfirmTransaction;", "vulTickCounterUID", "", "debug", "", "text", "onDisable", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"}
)
public final class HYTDisabler extends Module {

    private final LinkedList packetBuffer = new LinkedList();
    private final ArrayList keepAlives;
    private int vulTickCounterUID;
    private final MSTimer lagTimer;
    private final MSTimer msTimer;
    private final ArrayList transactions;

    public void onEnable() {
        this.msTimer.reset();
        this.transactions.clear();
        this.keepAlives.clear();
    }

    public void onDisable() {
        this.msTimer.reset();
        this.transactions.clear();
        this.keepAlives.clear();
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.msTimer.reset();
        this.transactions.clear();
        this.keepAlives.clear();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket $this$unwrap$iv = event.getPacket();
        boolean $i$f$unwrap = false;

        if ($this$unwrap$iv == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.PacketImpl<*>");
        } else {
            Packet packet = ((PacketImpl) $this$unwrap$iv).getWrapped();
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.isOnLadder() && MinecraftInstance.mc.getGameSettings().getKeyBindJump().getPressed()) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp.setMotionY(0.11D);
            }

            if (packet instanceof CPacketKeepAlive && (this.keepAlives.size() <= 0 || Intrinsics.areEqual(packet, (CPacketKeepAlive) this.keepAlives.get(this.keepAlives.size() - 1)) ^ true)) {
                this.debug("c00 canceled");
                this.keepAlives.add(packet);
                event.cancelEvent();
            }

            if (packet instanceof CPacketConfirmTransaction) {
                short $this$unwrap$iv1 = ((CPacketConfirmTransaction) packet).getUid();

                $i$f$unwrap = false;
                int i = Math.abs($this$unwrap$iv1);
                int $this$unwrap$iv2 = this.vulTickCounterUID;
                int j = i;

                $i$f$unwrap = false;
                int k = Math.abs($this$unwrap$iv2);

                $this$unwrap$iv2 = j - k;
                $i$f$unwrap = false;
                if (Math.abs($this$unwrap$iv2) <= 4) {
                    this.vulTickCounterUID = ((CPacketConfirmTransaction) packet).getUid();
                    this.packetBuffer.add(packet);
                    event.cancelEvent();
                } else {
                    $this$unwrap$iv1 = ((CPacketConfirmTransaction) packet).getUid();
                    $i$f$unwrap = false;
                    $this$unwrap$iv2 = Math.abs($this$unwrap$iv1) - 25767;
                    $i$f$unwrap = false;
                    if (Math.abs($this$unwrap$iv2) <= 4) {
                        this.vulTickCounterUID = ((CPacketConfirmTransaction) packet).getUid();
                    }
                }
            }

        }
    }

    public final void debug(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Disabler", text, NotifyType.INFO, 0, 0, 24, (DefaultConstructorMarker) null));
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.lagTimer.hasTimePassed(5000L) && this.packetBuffer.size() > 4) {
            this.lagTimer.reset();

            while (this.packetBuffer.size() > 4) {
                Object object = this.packetBuffer.poll();

                Intrinsics.checkExpressionValueIsNotNull(object, "packetBuffer.poll()");
                PacketUtils.sendPacketNoEvent((Packet) object);
            }
        }

    }

    @NotNull
    public String getTag() {
        return "Beta";
    }

    public HYTDisabler() {
        boolean flag = false;
        ArrayList arraylist = new ArrayList();

        this.keepAlives = arraylist;
        this.lagTimer = new MSTimer();
        this.msTimer = new MSTimer();
        flag = false;
        arraylist = new ArrayList();
        this.transactions = arraylist;
    }
}

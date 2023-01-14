package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeHelper;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AntiVoid",
    description = "./",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0013\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0017H\u0002J\b\u0010\"\u001a\u00020#H\u0017J\u0010\u0010$\u001a\u00020 2\u0006\u0010%\u001a\u00020&H\u0007J\u0010\u0010\'\u001a\u00020 2\u0006\u0010%\u001a\u00020&H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR*\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00020\u000e`\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f¨\u0006("},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/AntiVoid;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoScaffoldValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "debug", "lastGroundPos", "", "getLastGroundPos", "()[D", "setLastGroundPos", "([D)V", "packets", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "Lkotlin/collections/ArrayList;", "getPackets", "()Ljava/util/ArrayList;", "setPackets", "(Ljava/util/ArrayList;)V", "pullbackTime", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/TimeHelper;", "getTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/TimeHelper;", "setTimer", "(Lnet/ccbluex/liquidbounce/utils/timer/TimeHelper;)V", "", "str", "isInVoid", "", "onPacket", "e", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRevPacket", "LiquidBounce"}
)
public final class AntiVoid extends Module {

    private final IntegerValue pullbackTime = new IntegerValue("PullbackTime", 850, 800, 1800);
    private final BoolValue autoScaffoldValue = new BoolValue("AutoScaffold", false);
    private final BoolValue debug = new BoolValue("Debug", false);
    @NotNull
    private TimeHelper timer = new TimeHelper();
    @NotNull
    private double[] lastGroundPos = new double[3];
    @NotNull
    private ArrayList packets = new ArrayList();

    @NotNull
    public final TimeHelper getTimer() {
        return this.timer;
    }

    public final void setTimer(@NotNull TimeHelper <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.timer = <set-?>;
    }

    @NotNull
    public final double[] getLastGroundPos() {
        return this.lastGroundPos;
    }

    public final void setLastGroundPos(@NotNull double[] <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.lastGroundPos = <set-?>;
    }

    @NotNull
    public final ArrayList getPackets() {
        return this.packets;
    }

    public final void setPackets(@NotNull ArrayList <set-?>) {
        Intrinsics.checkParameterIsNotNull(<set-?>, "<set-?>");
        this.packets = <set-?>;
    }

    @EventTarget
    public boolean isInVoid() {
        int i = 0;

        for (short short0 = 128; i <= short0; ++i) {
            if (MovementUtils.INSTANCE.isOnGround((double) i)) {
                return false;
            }
        }

        return true;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        IPacket ipacket = e.getPacket();
        boolean p = false;

        if (ipacket == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.PacketImpl<*>");
        } else {
            Packet packet = ((PacketImpl) ipacket).getWrapped();
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(Fly.class);

            if (module == null) {
                Intrinsics.throwNpe();
            }

            if (!module.getState()) {
                module = LiquidBounce.INSTANCE.getModuleManager().get(Scaffold.class);
                if (module == null) {
                    Intrinsics.throwNpe();
                }

                if (!module.getState()) {
                    if (!this.packets.isEmpty()) {
                        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (ientityplayersp.getTicksExisted() < 100) {
                            this.packets.clear();
                        }
                    }

                    if (packet instanceof CPacketPlayer) {
                        if (this.isInVoid()) {
                            e.cancelEvent();
                            this.packets.add(e.getPacket());
                            if (this.timer.delay((long) ((Number) this.pullbackTime.get()).intValue())) {
                                e.cancelEvent();
                            }
                        } else {
                            double[] adouble = this.lastGroundPos;
                            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            adouble[0] = ientityplayersp1.getPosX();
                            adouble = this.lastGroundPos;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            adouble[1] = ientityplayersp1.getPosY();
                            adouble = this.lastGroundPos;
                            ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp1 == null) {
                                Intrinsics.throwNpe();
                            }

                            adouble[2] = ientityplayersp1.getPosZ();
                            if (!this.packets.isEmpty()) {
                                if (((Boolean) this.autoScaffoldValue.get()).booleanValue()) {
                                    module = LiquidBounce.INSTANCE.getModuleManager().get(Scaffold.class);
                                    if (module == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    module.setState(true);
                                }

                                Iterator iterator = this.packets.iterator();

                                Intrinsics.checkExpressionValueIsNotNull(iterator, "packets.iterator()");
                                Iterator iterator1 = iterator;

                                this.debug("[AntiVoid] Release Packets - " + this.packets.size());

                                while (iterator1.hasNext()) {
                                    Object object = iterator1.next();

                                    if (object == null) {
                                        throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketPlayer");
                                    }

                                    CPacketPlayer p1 = (CPacketPlayer) object;

                                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayer(false));
                                }

                                this.packets.clear();
                            }

                            this.timer.reset();
                        }
                    }
                }
            }

        }
    }

    @EventTarget
    public final void onRevPacket(@NotNull PacketEvent e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        IPacket $this$unwrap$iv = e.getPacket();
        boolean $i$f$unwrap = false;

        if ($this$unwrap$iv == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.PacketImpl<*>");
        } else {
            if (((PacketImpl) $this$unwrap$iv).getWrapped() instanceof SPacketPlayerPosLook && this.packets.size() > 1) {
                this.debug("[AntiVoid] Pullbacks Detected, clear packets list!");
                this.packets.clear();
            }

        }
    }

    private final void debug(String str) {
        if (((Boolean) this.debug.get()).booleanValue()) {
            ClientUtils.displayChatMessage(str);
        }

    }

    @NotNull
    public String getTag() {
        return String.valueOf(((Number) this.pullbackTime.get()).intValue());
    }
}

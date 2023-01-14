package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketChatMessage;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "AtAllProvider",
    description = "Automatically mentions everyone on the server when using \'@a\' in your message.",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0012\u0010\u0016\u001a\u00020\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\u0017H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/AtAllProvider;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "", "maxDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "minDelayValue", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "retryQueue", "", "", "retryValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "sendQueue", "Ljava/util/concurrent/LinkedBlockingQueue;", "onDisable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class AtAllProvider extends Module {

    private final IntegerValue minDelayValue = (IntegerValue) (new IntegerValue("MinDelay", 500, 0, 20000) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) AtAllProvider.this.maxDelayValue.get()).intValue();

            if (i < newValue) {
                this.set((Object) Integer.valueOf(i));
            }

        }
    });
    private final IntegerValue maxDelayValue = (IntegerValue) (new IntegerValue("MaxDelay", 1000, 0, 20000) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) AtAllProvider.this.minDelayValue.get()).intValue();

            if (i > newValue) {
                this.set((Object) Integer.valueOf(i));
            }

        }
    });
    private final BoolValue retryValue = new BoolValue("Retry", false);
    private final LinkedBlockingQueue sendQueue = new LinkedBlockingQueue();
    private final List retryQueue = (List) (new ArrayList());
    private final MSTimer msTimer = new MSTimer();
    private long delay;

    public void onDisable() {
        LinkedBlockingQueue linkedblockingqueue = this.sendQueue;
        boolean flag = false;
        boolean flag1 = false;

        synchronized (linkedblockingqueue){}

        boolean $i$a$-synchronized-AtAllProvider$onDisable$2;
        Unit unit;

        try {
            $i$a$-synchronized-AtAllProvider$onDisable$2 = false;
            this.sendQueue.clear();
            unit = Unit.INSTANCE;
        } finally {
            ;
        }

        List list = this.retryQueue;

        flag = false;
        flag1 = false;

        synchronized (list){}

        try {
            $i$a$-synchronized-AtAllProvider$onDisable$2 = false;
            this.retryQueue.clear();
            unit = Unit.INSTANCE;
        } finally {
            ;
        }

        super.onDisable();
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        if (this.msTimer.hasTimePassed(this.delay)) {
            try {
                LinkedBlockingQueue e = this.sendQueue;
                boolean flag = false;
                boolean flag1 = false;

                synchronized (e){}

                try {
                    boolean $i$a$-synchronized-AtAllProvider$onUpdate$1 = false;

                    if (this.sendQueue.isEmpty()) {
                        if (!((Boolean) this.retryValue.get()).booleanValue() || this.retryQueue.isEmpty()) {
                            return;
                        }

                        this.sendQueue.addAll((Collection) this.retryQueue);
                    }

                    IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    Object object = this.sendQueue.take();

                    Intrinsics.checkExpressionValueIsNotNull(object, "sendQueue.take()");
                    ientityplayersp.sendChatMessage((String) object);
                    this.msTimer.reset();
                    this.delay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
                    Unit unit = Unit.INSTANCE;
                } finally {
                    ;
                }
            } catch (InterruptedException interruptedexception) {
                interruptedexception.printStackTrace();
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isCPacketChatMessage(event.getPacket())) {
            ICPacketChatMessage packetChatMessage = event.getPacket().asCPacketChatMessage();
            String message = packetChatMessage.getMessage();

            if (StringsKt.contains$default((CharSequence) message, (CharSequence) "@a", false, 2, (Object) null)) {
                LinkedBlockingQueue linkedblockingqueue = this.sendQueue;
                boolean flag = false;
                boolean flag1 = false;

                synchronized (linkedblockingqueue) {
                    boolean $i$a$-synchronized-AtAllProvider$onPacket$1 = false;
                    Iterator iterator = MinecraftInstance.mc.getNetHandler().getPlayerInfoMap().iterator();

                    while (iterator.hasNext()) {
                        INetworkPlayerInfo playerInfo = (INetworkPlayerInfo) iterator.next();
                        String playerName = playerInfo.getGameProfile().getName();
                        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (!Intrinsics.areEqual(playerName, ientityplayersp.getName())) {
                            LinkedBlockingQueue linkedblockingqueue1 = this.sendQueue;

                            Intrinsics.checkExpressionValueIsNotNull(playerName, "playerName");
                            linkedblockingqueue1.add(StringsKt.replace$default(message, "@a", playerName, false, 4, (Object) null));
                        }
                    }

                    if (((Boolean) this.retryValue.get()).booleanValue()) {
                        List playerInfo1 = this.retryQueue;
                        boolean flag2 = false;
                        boolean playerName1 = false;

                        synchronized (playerInfo1){}

                        try {
                            boolean $i$a$-synchronized-AtAllProvider$onPacket$1$1 = false;

                            this.retryQueue.clear();
                            Collection $this$toTypedArray$iv = (Collection) this.sendQueue;
                            List list = this.retryQueue;
                            boolean $i$f$toTypedArray = false;
                            Object[] aobject = $this$toTypedArray$iv.toArray(new String[0]);

                            if (aobject == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                            }

                            Object[] aobject1 = aobject;

                            list.addAll((Collection) CollectionsKt.listOf((String[]) Arrays.copyOf((String[]) aobject1, ((String[]) aobject1).length)));
                        } finally {
                            ;
                        }
                    }

                    Unit unit = Unit.INSTANCE;
                }

                event.cancelEvent();
            }
        }

    }

    public AtAllProvider() {
        this.delay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
    }
}

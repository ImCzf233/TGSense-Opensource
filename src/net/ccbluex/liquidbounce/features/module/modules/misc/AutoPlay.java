package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AutoPlay",
    category = ModuleCategory.PLAYER,
    description = "idk"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0006H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0017H\u0007J\u0016\u0010\u0018\u001a\u00020\u00122\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00120\u001aH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/AutoPlay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "clickState", "", "clicking", "", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "queued", "tag", "", "getTag", "()Ljava/lang/String;", "handleEvents", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "queueAutoPlay", "runnable", "Lkotlin/Function0;", "LiquidBounce"}
)
public final class AutoPlay extends Module {

    private int clickState;
    private final ListValue modeValue = new ListValue("Server", new String[] { "RedeSky", "Minemora", "HuaYuTingGG", "HuaYuTingSw", "HuaYuTing16"}, "HuaYuTingGG");
    private final IntegerValue delayValue = new IntegerValue("JoinDelay", 3, 0, 7);
    private boolean clicking;
    private boolean queued;

    public void onEnable() {
        this.clickState = 0;
        this.clicking = false;
        this.queued = false;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket text = event.getPacket();
        boolean $i$f$unwrap = false;

        if (text == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.PacketImpl<*>");
        } else {
            Packet packet = ((PacketImpl) text).getWrapped();
            String text1 = (String) this.modeValue.get();

            $i$f$unwrap = false;
            if (text1 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s = text1.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                text1 = s;
                switch (text1.hashCode()) {
                case 1083223725:
                    if (text1.equals("redesky")) {
                        if (this.clicking && (packet instanceof CPacketClickWindow || packet instanceof CPacketPlayerDigging)) {
                            event.cancelEvent();
                            return;
                        }

                        if (this.clickState == 2 && packet instanceof SPacketOpenWindow) {
                            event.cancelEvent();
                        }
                    }
                    break;

                case 1381910549:
                    if (text1.equals("hypixel") && this.clickState == 1 && packet instanceof SPacketOpenWindow) {
                        event.cancelEvent();
                    }
                }

                if (packet instanceof SPacketChat) {
                    ITextComponent itextcomponent = ((SPacketChat) packet).getChatComponent();

                    Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "packet.chatComponent");
                    text1 = itextcomponent.getUnformattedText();
                    String $i$f$unwrap1 = (String) this.modeValue.get();
                    boolean matcher = false;

                    if ($i$f$unwrap1 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }

                    s = $i$f$unwrap1.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(s, "(this as java.lang.String).toLowerCase()");
                    $i$f$unwrap1 = s;
                    IEntityPlayerSP ientityplayersp;

                    switch ($i$f$unwrap1.hashCode()) {
                    case -1362756060:
                        if ($i$f$unwrap1.equals("minemora")) {
                            Intrinsics.checkExpressionValueIsNotNull(text1, "text");
                            if (StringsKt.contains((CharSequence) text1, (CharSequence) "Has click en alguna de las siguientes opciones", true)) {
                                this.queueAutoPlay((Function0) null.INSTANCE);
                            }
                        }
                        break;

                    case 1660682403:
                        if ($i$f$unwrap1.equals("huayuting16")) {
                            Intrinsics.checkExpressionValueIsNotNull(text1, "text");
                            if (StringsKt.contains((CharSequence) text1, (CharSequence) "[起床战争] Game 结束！感谢您的参与！", true)) {
                                LiquidBounce.INSTANCE.getHud().addNotification(new Notification("AutoPlay", "Game Over", NotifyType.INFO, 0, 0, 24, (DefaultConstructorMarker) null));
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.sendChatMessage("@[TGSense]GG");
                                (new MiscUtils()).playSound(MiscUtils.SoundType.VICTORY, -8.0F);
                            }
                        }
                        break;

                    case 1660684126:
                        if ($i$f$unwrap1.equals("huayutinggg")) {
                            Intrinsics.checkExpressionValueIsNotNull(text1, "text");
                            if (StringsKt.contains((CharSequence) text1, (CharSequence) "      喜欢      �?�?      不喜�?", true)) {
                                LiquidBounce.INSTANCE.getHud().addNotification(new Notification("AutoPlay", "Game Over", NotifyType.INFO, 0, 0, 24, (DefaultConstructorMarker) null));
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.sendChatMessage("@[TGSense]GG");
                                (new MiscUtils()).playSound(MiscUtils.SoundType.VICTORY, -8.0F);
                            }
                        }
                        break;

                    case 1660684514:
                        if ($i$f$unwrap1.equals("huayutingsw")) {
                            Pattern pattern = Pattern.compile("你在地图 (.*?)\\(");
                            ITextComponent itextcomponent1 = ((SPacketChat) packet).getChatComponent();

                            Intrinsics.checkExpressionValueIsNotNull(itextcomponent1, "packet.chatComponent");
                            Matcher matcher1 = pattern.matcher((CharSequence) itextcomponent1.getUnformattedText());

                            Intrinsics.checkExpressionValueIsNotNull(text1, "text");
                            if (StringsKt.contains((CharSequence) text1, (CharSequence) "你现在是观察者状�?. 按E打开菜单.", true)) {
                                LiquidBounce.INSTANCE.getHud().addNotification(new Notification("AutoPlay", "Game Over", NotifyType.INFO, 0, 0, 24, (DefaultConstructorMarker) null));
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.sendChatMessage("@[TGSense]GG");
                                (new MiscUtils()).playSound(MiscUtils.SoundType.VICTORY, -8.0F);
                            }
                        }
                    }
                }

            }
        }
    }

    private final void queueAutoPlay(Function0 runnable) {
        if (!this.queued) {
            this.queued = true;
            if (this.getState()) {
                Timer timer = new Timer();
                long i = (long) ((Number) this.delayValue.get()).intValue() * (long) 1000;
                boolean flag = false;
                boolean flag1 = false;
                TimerTask timertask = (TimerTask) (new AutoPlay$queueAutoPlay$$inlined$schedule$1(this, runnable));

                timer.schedule(timertask, i);
                (new MiscUtils()).playSound(MiscUtils.SoundType.VICTORY, -8.0F);
                LiquidBounce.INSTANCE.getHud().addNotification(new Notification("AutoPlay", "Sending you to next game in " + ((Number) this.delayValue.get()).intValue() + "s...", NotifyType.INFO, 0, 0, 24, (DefaultConstructorMarker) null));
            }

        }
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.clicking = false;
        this.clickState = 0;
        this.queued = false;
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }

    public boolean handleEvents() {
        return true;
    }
}

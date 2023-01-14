package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AutoGG",
    description = "AutoGG. ",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e¨\u0006\u0016"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/AutoGG;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "tag", "", "getTag", "()Ljava/lang/String;", "textValue", "Lnet/ccbluex/liquidbounce/value/TextValue;", "totalPlayed", "", "getTotalPlayed", "()I", "setTotalPlayed", "(I)V", "win", "getWin", "setWin", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidBounce"}
)
public final class AutoGG extends Module {

    private final TextValue textValue = new TextValue("Text", "GG");
    private int totalPlayed;
    private int win;

    public final int getTotalPlayed() {
        return this.totalPlayed;
    }

    public final void setTotalPlayed(int <set-?>) {
        this.totalPlayed = <set-?>;
    }

    public final int getWin() {
        return this.win;
    }

    public final void setWin(int <set-?>) {
        this.win = <set-?>;
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

            if (packet instanceof SPacketChat) {
                ITextComponent itextcomponent = ((SPacketChat) packet).getChatComponent();

                Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "packet.chatComponent");
                String s = itextcomponent.getUnformattedText();

                Intrinsics.checkExpressionValueIsNotNull(s, "text");
                int i;

                if (StringsKt.contains((CharSequence) s, (CharSequence) "恭喜", true)) {
                    LiquidBounce.INSTANCE.getHud().addNotification(new Notification("AutoGG", "Text sent Successfully", NotifyType.SUCCESS, 0, 0, 24, (DefaultConstructorMarker) null));
                    IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    ientityplayersp.sendChatMessage((String) this.textValue.get());
                    i = this.win++;
                }

                if (StringsKt.contains((CharSequence) s, (CharSequence) "游戏�?�?", true)) {
                    LiquidBounce.INSTANCE.getHud().addNotification(new Notification("AutoGG", "游戏�?始！�?", NotifyType.SUCCESS, 0, 0, 24, (DefaultConstructorMarker) null));
                    i = this.totalPlayed++;
                }
            }

        }
    }

    @NotNull
    public String getTag() {
        return "HuaYuTing";
    }
}

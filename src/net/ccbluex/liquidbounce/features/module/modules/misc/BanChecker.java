package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "BanChecker",
    description = "Hyt",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/BanChecker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "ban", "", "getBan", "()I", "setBan", "(I)V", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidBounce"}
)
public final class BanChecker extends Module {

    private int ban;

    public final int getBan() {
        return this.ban;
    }

    public final void setBan(int <set-?>) {
        this.ban = <set-?>;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket matcher = event.getPacket();
        boolean banname = false;

        if (matcher == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.PacketImpl<*>");
        } else {
            Packet packet = ((PacketImpl) matcher).getWrapped();

            if (packet instanceof SPacketChat) {
                Pattern pattern = Pattern.compile("玩家(.*?)在本�?游戏中行为异�?");
                ITextComponent itextcomponent = ((SPacketChat) packet).getChatComponent();

                Intrinsics.checkExpressionValueIsNotNull(itextcomponent, "packet.chatComponent");
                Matcher matcher1 = pattern.matcher((CharSequence) itextcomponent.getUnformattedText());

                if (matcher1.find()) {
                    int banname1 = this.ban++;
                    String banname2 = matcher1.group(1);

                    LiquidBounce.INSTANCE.getHud().addNotification(new Notification("BanChecker", banname2 + " was banned. (banned:" + this.ban + ')', NotifyType.INFO, 0, 0, 24, (DefaultConstructorMarker) null));
                }
            }

        }
    }
}

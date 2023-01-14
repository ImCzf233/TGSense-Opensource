package net.ccbluex.liquidbounce.features.special;

import kotlin.Metadata;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDonatorCape;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007¨\u0006\n"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/special/DonatorCape;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "handleEvents", "", "onSession", "", "event", "Lnet/ccbluex/liquidbounce/event/SessionEvent;", "LiquidBounce"}
)
public final class DonatorCape extends MinecraftInstance implements Listenable {

    @EventTarget
    public final void onSession(@NotNull SessionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (GuiDonatorCape.Companion.getCapeEnabled()) {
            CharSequence charsequence = (CharSequence) GuiDonatorCape.Companion.getTransferCode();
            boolean flag = false;

            if (charsequence.length() != 0 && UserUtils.INSTANCE.isValidTokenOffline(MinecraftInstance.mc.getSession().getToken())) {
                ThreadsKt.thread$default(false, false, (ClassLoader) null, (String) null, 0, (Function0) null.INSTANCE, 31, (Object) null);
                return;
            }
        }

    }

    public boolean handleEvents() {
        return true;
    }
}

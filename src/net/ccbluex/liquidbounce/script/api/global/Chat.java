package net.ccbluex.liquidbounce.script.api.global;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
    d2 = { "Lnet/ccbluex/liquidbounce/script/api/global/Chat;", "", "()V", "print", "", "message", "", "LiquidBounce"}
)
public final class Chat {

    public static final Chat INSTANCE;

    @JvmStatic
    public static final void print(@NotNull String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        ClientUtils.displayChatMessage(message);
    }

    static {
        Chat chat = new Chat();

        INSTANCE = chat;
    }
}

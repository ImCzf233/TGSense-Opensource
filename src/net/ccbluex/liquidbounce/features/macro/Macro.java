package net.ccbluex.liquidbounce.features.macro;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u000b\u001a\u00020\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/macro/Macro;", "", "key", "", "command", "", "(ILjava/lang/String;)V", "getCommand", "()Ljava/lang/String;", "getKey", "()I", "exec", "", "LiquidBounce"}
)
public final class Macro {

    private final int key;
    @NotNull
    private final String command;

    public final void exec() {
        LiquidBounce.INSTANCE.getCommandManager().executeCommands(this.command);
    }

    public final int getKey() {
        return this.key;
    }

    @NotNull
    public final String getCommand() {
        return this.command;
    }

    public Macro(int key, @NotNull String command) {
        Intrinsics.checkParameterIsNotNull(command, "command");
        super();
        this.key = key;
        this.command = command;
    }
}

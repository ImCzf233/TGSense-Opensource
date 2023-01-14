package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/ShortcutCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"}
)
public final class ShortcutCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 3 && StringsKt.equals(args[1], "add", true)) {
            String s;

            try {
                CommandManager commandmanager = LiquidBounce.INSTANCE.getCommandManager();

                s = args[2];
                String s1 = StringUtils.toCompleteString(args, 3);

                Intrinsics.checkExpressionValueIsNotNull(s1, "StringUtils.toCompleteString(args, 3)");
                commandmanager.registerShortcut(s, s1);
                this.chat("Successfully added shortcut.");
            } catch (IllegalArgumentException illegalargumentexception) {
                s = illegalargumentexception.getMessage();
                if (s == null) {
                    Intrinsics.throwNpe();
                }

                this.chat(s);
            }
        } else if (args.length >= 3 && StringsKt.equals(args[1], "remove", true)) {
            if (LiquidBounce.INSTANCE.getCommandManager().unregisterShortcut(args[2])) {
                this.chat("Successfully removed shortcut.");
            } else {
                this.chat("Shortcut does not exist.");
            }
        } else {
            this.chat("shortcut <add <shortcut_name> <script>/remove <shortcut_name>>");
        }

    }

    public ShortcutCommand() {
        super("shortcut", new String[0]);
    }
}

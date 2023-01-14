package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/BindsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidBounce"}
)
public final class BindsCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1 && StringsKt.equals(args[1], "clear", true)) {
            Iterator $i$f$forEach1 = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();

            while ($i$f$forEach1.hasNext()) {
                Module $this$forEach$iv1 = (Module) $i$f$forEach1.next();

                $this$forEach$iv1.setKeyBind(0);
            }

            this.chat("Removed all binds.");
        } else {
            this.chat("§c§lBinds");
            Iterable $this$forEach$iv = (Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules();
            boolean $i$f$forEach = false;
            Collection element$iv = (Collection) (new ArrayList());
            boolean it = false;
            Iterator $i$a$-forEach-BindsCommand$execute$2 = $this$forEach$iv.iterator();

            while ($i$a$-forEach-BindsCommand$execute$2.hasNext()) {
                Object element$iv$iv = $i$a$-forEach-BindsCommand$execute$2.next();
                Module it1 = (Module) element$iv$iv;
                boolean $i$a$-filter-BindsCommand$execute$1 = false;

                if (it1.getKeyBind() != 0) {
                    element$iv.add(element$iv$iv);
                }
            }

            $this$forEach$iv = (Iterable) ((List) element$iv);
            $i$f$forEach = false;
            Iterator $this$filterTo$iv$iv = $this$forEach$iv.iterator();

            while ($this$filterTo$iv$iv.hasNext()) {
                Object element$iv1 = $this$filterTo$iv$iv.next();
                Module it2 = (Module) element$iv1;
                boolean $i$a$-forEach-BindsCommand$execute$21 = false;

                ClientUtils.displayChatMessage("§6> §c" + it2.getName() + ": §a§l" + Keyboard.getKeyName(it2.getKeyBind()));
            }

            this.chatSyntax("binds clear");
        }
    }

    public BindsCommand() {
        super("binds", new String[0]);
    }
}

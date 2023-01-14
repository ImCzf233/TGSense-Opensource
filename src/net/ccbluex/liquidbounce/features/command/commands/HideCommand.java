package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/HideCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class HideCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length <= 1) {
            this.chatSyntax("hide <module/list/clear/reset>");
        } else if (StringsKt.equals(args[1], "list", true)) {
            this.chat("§c§lHidden");
            Iterable module1 = (Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules();
            boolean $i$f$forEach1 = false;
            Collection element$iv = (Collection) (new ArrayList());
            boolean it = false;
            Iterator $i$a$-forEach-HideCommand$execute$2 = module1.iterator();

            while ($i$a$-forEach-HideCommand$execute$2.hasNext()) {
                Object element$iv$iv = $i$a$-forEach-HideCommand$execute$2.next();
                Module it1 = (Module) element$iv$iv;
                boolean $i$a$-filter-HideCommand$execute$1 = false;

                if (!it1.getArray()) {
                    element$iv.add(element$iv$iv);
                }
            }

            module1 = (Iterable) ((List) element$iv);
            $i$f$forEach1 = false;
            Iterator $this$filterTo$iv$iv = module1.iterator();

            while ($this$filterTo$iv$iv.hasNext()) {
                Object element$iv1 = $this$filterTo$iv$iv.next();
                Module it2 = (Module) element$iv1;
                boolean $i$a$-forEach-HideCommand$execute$21 = false;

                ClientUtils.displayChatMessage("§6> §c" + it2.getName());
            }

        } else {
            Module module;
            Iterator $i$f$forEach;

            if (StringsKt.equals(args[1], "clear", true)) {
                $i$f$forEach = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();

                while ($i$f$forEach.hasNext()) {
                    module = (Module) $i$f$forEach.next();
                    module.setArray(true);
                }

                this.chat("Cleared hidden modules.");
            } else if (!StringsKt.equals(args[1], "reset", true)) {
                module = LiquidBounce.INSTANCE.getModuleManager().getModule(args[1]);
                if (module == null) {
                    this.chat("Module §a§l" + args[1] + "§3 not found.");
                } else {
                    module.setArray(!module.getArray());
                    this.chat("Module §a§l" + module.getName() + "§3 is now §a§l" + (module.getArray() ? "visible" : "invisible") + "§3 on the array list.");
                    this.playEdit();
                }
            } else {
                $i$f$forEach = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();

                while ($i$f$forEach.hasNext()) {
                    module = (Module) $i$f$forEach.next();
                    module.setArray(((ModuleInfo) module.getClass().getAnnotation(ModuleInfo.class)).array());
                }

                this.chat("Reset hidden modules.");
            }
        }
    }

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        boolean $this$filter$iv = false;

        if (args.length == 0) {
            return CollectionsKt.emptyList();
        } else {
            String moduleName = args[0];
            List list;

            switch (args.length) {
            case 1:
                Iterable $this$filter$iv1 = (Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules();
                boolean $i$f$filter = false;
                Collection destination$iv$iv = (Collection) (new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv1, 10)));
                boolean $i$f$filterTo = false;
                Iterator iterator = $this$filter$iv1.iterator();

                Object element$iv$iv;
                boolean $i$a$-filter-HideCommand$tabComplete$2;

                while (iterator.hasNext()) {
                    element$iv$iv = iterator.next();
                    Module it = (Module) element$iv$iv;

                    $i$a$-filter-HideCommand$tabComplete$2 = false;
                    String s = it.getName();

                    destination$iv$iv.add(s);
                }

                $this$filter$iv1 = (Iterable) ((List) destination$iv$iv);
                $i$f$filter = false;
                destination$iv$iv = (Collection) (new ArrayList());
                $i$f$filterTo = false;
                iterator = $this$filter$iv1.iterator();

                while (iterator.hasNext()) {
                    element$iv$iv = iterator.next();
                    String it1 = (String) element$iv$iv;

                    $i$a$-filter-HideCommand$tabComplete$2 = false;
                    if (StringsKt.startsWith(it1, moduleName, true)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }

                list = CollectionsKt.toList((Iterable) ((List) destination$iv$iv));
                break;

            default:
                list = CollectionsKt.emptyList();
            }

            return list;
        }
    }

    public HideCommand() {
        super("hide", new String[0]);
    }
}

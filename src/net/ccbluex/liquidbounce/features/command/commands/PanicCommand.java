package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/command/commands/PanicCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidBounce"}
)
public final class PanicCommand extends Command {

    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        Iterable msg = (Iterable) LiquidBounce.INSTANCE.getModuleManager().getModules();
        boolean module = false;
        Collection category = (Collection) (new ArrayList());
        boolean $this$filter$iv = false;
        Iterator $i$f$filter = msg.iterator();

        boolean $i$f$filterTo;

        while ($i$f$filter.hasNext()) {
            Object $this$filterTo$iv$iv = $i$f$filter.next();
            Module destination$iv$iv = (Module) $this$filterTo$iv$iv;

            $i$f$filterTo = false;
            if (destination$iv$iv.getState()) {
                category.add($this$filterTo$iv$iv);
            }
        }

        List modules = (List) category;

        msg = null;
        if (args.length > 1) {
            CharSequence charsequence = (CharSequence) args[1];
            boolean categories = false;

            if (charsequence.length() > 0) {
                String s = args[1];

                categories = false;
                if (s == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }

                String s1;
                label107: {
                    String s2 = s.toLowerCase();

                    Intrinsics.checkExpressionValueIsNotNull(s2, "(this as java.lang.String).toLowerCase()");
                    s = s2;
                    switch (s.hashCode()) {
                    case 96673:
                        if (s.equals("all")) {
                            s1 = "all";
                            break label107;
                        }
                        break;

                    case 1128455843:
                        if (s.equals("nonrender")) {
                            Iterable iterable = (Iterable) modules;
                            boolean flag = false;
                            Collection collection = (Collection) (new ArrayList());
                            boolean flag1 = false;
                            Iterator iterator = iterable.iterator();

                            while (iterator.hasNext()) {
                                Object object = iterator.next();
                                Module it = (Module) object;
                                boolean element$iv$iv = false;

                                if (it.getCategory() != ModuleCategory.RENDER) {
                                    collection.add(object);
                                }
                            }

                            modules = (List) collection;
                            s1 = "all non-render";
                            break label107;
                        }
                    }

                    ModuleCategory[] amodulecategory = ModuleCategory.values();

                    $this$filter$iv = false;
                    Collection collection1 = (Collection) (new ArrayList());
                    boolean flag2 = false;
                    ModuleCategory[] amodulecategory1 = amodulecategory;
                    int i = amodulecategory.length;

                    for (int j = 0; j < i; ++j) {
                        ModuleCategory it1 = amodulecategory1[j];
                        boolean $i$a$-filter-PanicCommand$execute$categories$1 = false;

                        if (StringsKt.equals(it1.getDisplayName(), args[1], true)) {
                            collection1.add(it1);
                        }
                    }

                    List list = (List) collection1;

                    if (list.isEmpty()) {
                        this.chat("Category " + args[1] + " not found");
                        return;
                    }

                    ModuleCategory modulecategory = (ModuleCategory) list.get(0);
                    Iterable iterable1 = (Iterable) modules;
                    boolean flag3 = false;
                    Collection collection2 = (Collection) (new ArrayList());

                    $i$f$filterTo = false;
                    Iterator iterator1 = iterable1.iterator();

                    while (iterator1.hasNext()) {
                        Object object1 = iterator1.next();
                        Module module = (Module) object1;
                        boolean $i$a$-filter-PanicCommand$execute$2 = false;

                        if (module.getCategory() == modulecategory) {
                            collection2.add(object1);
                        }
                    }

                    modules = (List) collection2;
                    s1 = "all " + modulecategory.getDisplayName();
                }

                Iterator iterator2 = modules.iterator();

                while (iterator2.hasNext()) {
                    Module module1 = (Module) iterator2.next();

                    module1.setState(false);
                }

                this.chat("Disabled " + s1 + " modules.");
                return;
            }
        }

        this.chatSyntax("panic <all/nonrender/combat/player/movement/render/world/misc/exploit/fun>");
    }

    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        boolean $i$f$filter = false;

        if (args.length == 0) {
            return CollectionsKt.emptyList();
        } else {
            List list;

            switch (args.length) {
            case 1:
                Iterable $this$filter$iv = (Iterable) CollectionsKt.listOf(new String[] { "all", "nonrender", "combat", "player", "movement", "render", "world", "misc", "exploit", "fun"});

                $i$f$filter = false;
                Collection destination$iv$iv = (Collection) (new ArrayList());
                boolean $i$f$filterTo = false;
                Iterator iterator = $this$filter$iv.iterator();

                while (iterator.hasNext()) {
                    Object element$iv$iv = iterator.next();
                    String it = (String) element$iv$iv;
                    boolean $i$a$-filter-PanicCommand$tabComplete$1 = false;

                    if (StringsKt.startsWith(it, args[0], true)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }

                list = (List) destination$iv$iv;
                break;

            default:
                list = CollectionsKt.emptyList();
            }

            return list;
        }
    }

    public PanicCommand() {
        super("panic", new String[0]);
    }
}

package net.ccbluex.liquidbounce.features.macro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0010"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/macro/MacroManager;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "macros", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/features/macro/Macro;", "Lkotlin/collections/ArrayList;", "getMacros", "()Ljava/util/ArrayList;", "handleEvents", "", "onKey", "", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "LiquidBounce"}
)
public final class MacroManager extends MinecraftInstance implements Listenable {

    @NotNull
    private final ArrayList macros = new ArrayList();

    @NotNull
    public final ArrayList getMacros() {
        return this.macros;
    }

    @EventTarget
    public final void onKey(@NotNull KeyEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        Iterable $this$forEach$iv = (Iterable) this.macros;
        boolean $i$f$forEach = false;
        Collection element$iv = (Collection) (new ArrayList());
        boolean it = false;
        Iterator $i$a$-forEach-MacroManager$onKey$2 = $this$forEach$iv.iterator();

        while ($i$a$-forEach-MacroManager$onKey$2.hasNext()) {
            Object element$iv$iv = $i$a$-forEach-MacroManager$onKey$2.next();
            Macro it1 = (Macro) element$iv$iv;
            boolean $i$a$-filter-MacroManager$onKey$1 = false;

            if (it1.getKey() == event.getKey()) {
                element$iv.add(element$iv$iv);
            }
        }

        $this$forEach$iv = (Iterable) ((List) element$iv);
        $i$f$forEach = false;
        Iterator $this$filterTo$iv$iv = $this$forEach$iv.iterator();

        while ($this$filterTo$iv$iv.hasNext()) {
            Object element$iv1 = $this$filterTo$iv$iv.next();
            Macro it2 = (Macro) element$iv1;
            boolean $i$a$-forEach-MacroManager$onKey$21 = false;

            it2.exec();
        }

    }

    public boolean handleEvents() {
        return true;
    }
}

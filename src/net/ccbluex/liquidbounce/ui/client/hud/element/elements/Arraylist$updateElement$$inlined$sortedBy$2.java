package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.module.Module;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 3,
    d1 = { "\u0000\u001e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"},
    d2 = { "<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2"}
)
public final class Arraylist$updateElement$$inlined$sortedBy$2 implements Comparator {

    final Arraylist this$0;

    public Arraylist$updateElement$$inlined$sortedBy$2(Arraylist arraylist) {
        this.this$0 = arraylist;
    }

    public final int compare(Object a, Object b) {
        boolean flag = false;
        Module it = (Module) a;
        boolean $i$a$-sortedBy-Arraylist$updateElement$4 = false;
        Comparable comparable = (Comparable) Integer.valueOf(-((IFontRenderer) Arraylist.access$getFontValue$p(this.this$0).get()).getStringWidth(this.this$0.getModName(it)));

        it = (Module) b;
        Comparable comparable1 = comparable;

        $i$a$-sortedBy-Arraylist$updateElement$4 = false;
        Integer integer = Integer.valueOf(-((IFontRenderer) Arraylist.access$getFontValue$p(this.this$0).get()).getStringWidth(this.this$0.getModName(it)));

        return ComparisonsKt.compareValues(comparable1, (Comparable) integer);
    }
}

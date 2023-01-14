package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 3,
    d1 = { "\u0000\u001e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"},
    d2 = { "<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2"}
)
public final class KillAura2$updateTarget$$inlined$sortBy$5 implements Comparator {

    public final int compare(Object a, Object b) {
        boolean flag = false;
        IEntityLivingBase it = (IEntityLivingBase) a;
        boolean $i$a$-sortBy-KillAura2$updateTarget$5 = false;
        Comparable comparable = (Comparable) Integer.valueOf(it.getHurtResistantTime());

        it = (IEntityLivingBase) b;
        Comparable comparable1 = comparable;

        $i$a$-sortBy-KillAura2$updateTarget$5 = false;
        Integer integer = Integer.valueOf(it.getHurtResistantTime());

        return ComparisonsKt.compareValues(comparable1, (Comparable) integer);
    }
}

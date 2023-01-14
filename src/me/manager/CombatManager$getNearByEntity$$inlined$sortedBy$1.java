package me.manager;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 3,
    d1 = { "\u0000\u001e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"},
    d2 = { "<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2"}
)
public final class CombatManager$getNearByEntity$$inlined$sortedBy$1 implements Comparator {

    public final int compare(Object a, Object b) {
        boolean flag = false;
        IEntity it = (IEntity) a;
        boolean $i$a$-sortedBy-CombatManager$getNearByEntity$2 = false;
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        Comparable comparable = (Comparable) Float.valueOf(it.getDistanceToEntity((IEntity) ientityplayersp));

        it = (IEntity) b;
        Comparable comparable1 = comparable;

        $i$a$-sortedBy-CombatManager$getNearByEntity$2 = false;
        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        Float ofloat = Float.valueOf(it.getDistanceToEntity((IEntity) ientityplayersp));

        return ComparisonsKt.compareValues(comparable1, (Comparable) ofloat);
    }
}

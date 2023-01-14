package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "TGSenseSense",
    description = "已修�?:) TGSense God !",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/TGSenseSense;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "mode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "LiquidBounce"}
)
public final class TGSenseSense extends Module {

    private final ListValue mode = new ListValue("ShowMode", new String[] { "Release", "DEV.QQ.2144024332", "Reborn"}, "4V4/1V1");

    @Nullable
    public String getTag() {
        return (String) this.mode.get();
    }
}

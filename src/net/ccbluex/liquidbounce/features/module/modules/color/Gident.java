package net.ccbluex.liquidbounce.features.module.modules.color;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Gident",
    description = "Custom",
    category = ModuleCategory.COLOR
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/color/Gident;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "Companion", "LiquidBounce"}
)
public final class Gident extends Module {

    @JvmField
    @NotNull
    public static final IntegerValue gidentspeed = new IntegerValue("GidentSpeed", 100, 1, 1000);
    @JvmField
    @NotNull
    public static final IntegerValue redValue = new IntegerValue("Red", 255, 0, 255);
    @JvmField
    @NotNull
    public static final IntegerValue greenValue = new IntegerValue("Green", 255, 0, 255);
    @JvmField
    @NotNull
    public static final IntegerValue blueValue = new IntegerValue("Blue", 255, 0, 255);
    @JvmField
    @NotNull
    public static final IntegerValue redValue2 = new IntegerValue("Red2", 255, 0, 255);
    @JvmField
    @NotNull
    public static final IntegerValue greenValue2 = new IntegerValue("Green2", 255, 0, 255);
    @JvmField
    @NotNull
    public static final IntegerValue blueValue2 = new IntegerValue("Blue2", 255, 0, 255);
    public static final Gident.Companion Companion = new Gident.Companion((DefaultConstructorMarker) null);

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/color/Gident$Companion;", "", "()V", "blueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blueValue2", "gidentspeed", "greenValue", "greenValue2", "redValue", "redValue2", "LiquidBounce"}
    )
    public static final class Companion {

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

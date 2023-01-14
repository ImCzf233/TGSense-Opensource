package net.ccbluex.liquidbounce.features.module.modules.color;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Rainbow",
    category = ModuleCategory.COLOR,
    canEnable = false,
    description = "Custom"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/color/Rainbow;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "Companion", "LiquidBounce"}
)
public final class Rainbow extends Module {

    @JvmField
    @NotNull
    public static final FloatValue rainbowStart = new FloatValue("Start", 0.1F, 0.0F, 1.0F);
    @JvmField
    @NotNull
    public static final FloatValue rainbowStop = new FloatValue("Stop", 0.2F, 0.0F, 1.0F);
    @JvmField
    @NotNull
    public static final FloatValue rainbowSaturation = new FloatValue("Saturation", 0.7F, 0.0F, 1.0F);
    @JvmField
    @NotNull
    public static final FloatValue rainbowBrightness = new FloatValue("Brightness", 1.0F, 0.0F, 1.0F);
    @JvmField
    @NotNull
    public static final IntegerValue rainbowSpeed = new IntegerValue("Speed", 1500, 500, 7000);
    public static final Rainbow.Companion Companion = new Rainbow.Companion((DefaultConstructorMarker) null);

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\n"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/color/Rainbow$Companion;", "", "()V", "rainbowBrightness", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rainbowSaturation", "rainbowSpeed", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "rainbowStart", "rainbowStop", "LiquidBounce"}
    )
    public static final class Companion {

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

package net.ccbluex.liquidbounce.features.module.modules.color;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.slf4j.helpers.BasicMarker;

@ModuleInfo(
    name = "VisualColor",
    category = ModuleCategory.COLOR,
    array = false,
    description = "Tomk"
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/color/VisualColor;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "Companion", "LiquidSense"}
)
public final class VisualColor extends Module {

    @NotNull
    private static final BoolValue blur = new BoolValue("Blur", true);
    @NotNull
    private static final IntegerValue r = new IntegerValue("ClientRed", 0, 0, 255);
    @NotNull
    private static final IntegerValue b = new IntegerValue("ClientGreen", 255, 0, 255);
    @NotNull
    private static final IntegerValue g = new IntegerValue("ClientBlue", 255, 0, 255);
    @NotNull
    private static final IntegerValue r2 = new IntegerValue("ClientRed2", 255, 0, 255);
    @NotNull
    private static final IntegerValue b2 = new IntegerValue("ClientGreen2", 40, 0, 255);
    @NotNull
    private static final IntegerValue g2 = new IntegerValue("ClientBlue2", 255, 0, 255);
    @NotNull
    private static final ListValue shadowValue = new ListValue("ShadowMode", new String[] { "LiquidBounce", "Test", "Test2", "Test3", "Outline", "Default", "Autumn"}, "LiquidBounce");
    public static final VisualColor.Companion Companion = new VisualColor.Companion((BasicMarker) null);

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u0019"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/color/VisualColor$Companion;", "", "()V", "b", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getB", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "b2", "getB2", "blur", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getBlur", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "g", "getG", "g2", "getG2", "r", "getR", "r2", "getR2", "shadowValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getShadowValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "LiquidSense"}
    )
    public static final class Companion {

        @NotNull
        public final BoolValue getBlur() {
            return VisualColor.blur;
        }

        @NotNull
        public final IntegerValue getR() {
            return VisualColor.r;
        }

        @NotNull
        public final IntegerValue getB() {
            return VisualColor.b;
        }

        @NotNull
        public final IntegerValue getG() {
            return VisualColor.g;
        }

        @NotNull
        public final IntegerValue getR2() {
            return VisualColor.r2;
        }

        @NotNull
        public final IntegerValue getB2() {
            return VisualColor.b2;
        }

        @NotNull
        public final IntegerValue getG2() {
            return VisualColor.g2;
        }

        @NotNull
        public final ListValue getShadowValue() {
            return VisualColor.shadowValue;
        }

        private Companion() {}

        public Companion(BasicMarker $constructor_marker) {
            this();
        }
    }
}

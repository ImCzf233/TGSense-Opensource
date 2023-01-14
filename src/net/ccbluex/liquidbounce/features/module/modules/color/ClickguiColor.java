package net.ccbluex.liquidbounce.features.module.modules.color;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "ClickguiColor",
    description = "你好",
    category = ModuleCategory.COLOR,
    canEnable = false
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006¨\u0006\u0017"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/color/ClickguiColor;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "b", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getB", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "b2", "getB2", "b3", "getB3", "g", "getG", "g2", "getG2", "g3", "getG3", "r", "getR", "r2", "getR2", "r3", "getR3", "LiquidBounce"}
)
public final class ClickguiColor extends Module {

    @NotNull
    private final IntegerValue r = new IntegerValue("Red", 229, 0, 255);
    @NotNull
    private final IntegerValue g = new IntegerValue("Green", 100, 0, 255);
    @NotNull
    private final IntegerValue b = new IntegerValue("Blue", 173, 0, 255);
    @NotNull
    private final IntegerValue r2 = new IntegerValue("Red2", 109, 0, 255);
    @NotNull
    private final IntegerValue g2 = new IntegerValue("Green2", 255, 0, 255);
    @NotNull
    private final IntegerValue b2 = new IntegerValue("Blue2", 255, 0, 255);
    @NotNull
    private final IntegerValue r3 = new IntegerValue("Red3", 109, 0, 255);
    @NotNull
    private final IntegerValue g3 = new IntegerValue("Green3", 255, 0, 255);
    @NotNull
    private final IntegerValue b3 = new IntegerValue("Blue3", 255, 0, 255);

    @NotNull
    public final IntegerValue getR() {
        return this.r;
    }

    @NotNull
    public final IntegerValue getG() {
        return this.g;
    }

    @NotNull
    public final IntegerValue getB() {
        return this.b;
    }

    @NotNull
    public final IntegerValue getR2() {
        return this.r2;
    }

    @NotNull
    public final IntegerValue getG2() {
        return this.g2;
    }

    @NotNull
    public final IntegerValue getB2() {
        return this.b2;
    }

    @NotNull
    public final IntegerValue getR3() {
        return this.r3;
    }

    @NotNull
    public final IntegerValue getG3() {
        return this.g3;
    }

    @NotNull
    public final IntegerValue getB3() {
        return this.b3;
    }
}

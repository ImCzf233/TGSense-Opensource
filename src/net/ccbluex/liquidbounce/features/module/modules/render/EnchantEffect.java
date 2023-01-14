package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "EnchantEffect",
    description = "Change Sword Color",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u0004\u0018\u00010\u0004J\b\u0010\u000b\u001a\u0004\u0018\u00010\u0004J\b\u0010\f\u001a\u0004\u0018\u00010\tJ\b\u0010\r\u001a\u0004\u0018\u00010\u0004J\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/EnchantEffect;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorBlueValue", "colorGreenValue", "colorRedValue", "rainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getBlueValue", "getGreenValue", "getRainbow", "getRedValue", "getalphaValue", "LiquidBounce"}
)
public final class EnchantEffect extends Module {

    private final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 160, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    private final IntegerValue alphaValue = new IntegerValue("Alpha", 255, 0, 255);
    private final BoolValue rainbow = new BoolValue("RainBow", false);

    @Nullable
    public final IntegerValue getRedValue() {
        return this.colorRedValue;
    }

    @Nullable
    public final BoolValue getRainbow() {
        return this.rainbow;
    }

    @Nullable
    public final IntegerValue getGreenValue() {
        return this.colorGreenValue;
    }

    @Nullable
    public final IntegerValue getBlueValue() {
        return this.colorBlueValue;
    }

    @Nullable
    public final IntegerValue getalphaValue() {
        return this.alphaValue;
    }
}

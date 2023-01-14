package net.ccbluex.liquidbounce.features.module.modules.fun;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Derp",
    description = "Makes it look like you were derping around.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/fun/Derp;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "currentSpin", "", "headlessValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "incrementValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotation", "", "getRotation", "()[F", "spinnyValue", "LiquidBounce"}
)
public final class Derp extends Module {

    private final BoolValue headlessValue = new BoolValue("Headless", false);
    private final BoolValue spinnyValue = new BoolValue("Spinny", false);
    private final FloatValue incrementValue = new FloatValue("Increment", 1.0F, 0.0F, 50.0F);
    private float currentSpin;

    @NotNull
    public final float[] getRotation() {
        float[] afloat = new float[2];
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        afloat[0] = ientityplayersp.getRotationYaw() + (float) (Math.random() * (double) 360 - (double) 180);
        afloat[1] = (float) (Math.random() * (double) 180 - (double) 90);
        float[] derpRotations = afloat;

        if (((Boolean) this.headlessValue.get()).booleanValue()) {
            derpRotations[1] = 180.0F;
        }

        if (((Boolean) this.spinnyValue.get()).booleanValue()) {
            derpRotations[0] = this.currentSpin + ((Number) this.incrementValue.get()).floatValue();
            this.currentSpin = derpRotations[0];
        }

        return derpRotations;
    }
}

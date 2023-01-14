package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "FastBreak",
    description = "Allows you to break blocks faster.",
    category = ModuleCategory.WORLD
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/world/FastBreak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "breakDamage", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class FastBreak extends Module {

    private final FloatValue breakDamage = new FloatValue("BreakDamage", 0.8F, 0.1F, 1.0F);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        MinecraftInstance.mc.getPlayerController().setBlockHitDelay(0);
        if (MinecraftInstance.mc.getPlayerController().getCurBlockDamageMP() > ((Number) this.breakDamage.get()).floatValue()) {
            MinecraftInstance.mc.getPlayerController().setCurBlockDamageMP(1.0F);
        }

        if (Fucker.INSTANCE.getCurrentDamage() > ((Number) this.breakDamage.get()).floatValue()) {
            Fucker.INSTANCE.setCurrentDamage(1.0F);
        }

        if (Nuker.Companion.getCurrentDamage() > ((Number) this.breakDamage.get()).floatValue()) {
            Nuker.Companion.setCurrentDamage(1.0F);
        }

    }
}

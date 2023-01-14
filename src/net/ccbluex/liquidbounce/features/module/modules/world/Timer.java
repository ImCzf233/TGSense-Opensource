package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Timer",
    description = "Changes the speed of the entire game.",
    category = ModuleCategory.WORLD
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\rH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/world/Timer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onMoveValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "speedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidBounce"}
)
public final class Timer extends Module {

    private final FloatValue speedValue = new FloatValue("Speed", 2.0F, 0.1F, 10.0F);
    private final BoolValue onMoveValue = new BoolValue("OnMove", true);

    public void onDisable() {
        if (MinecraftInstance.mc.getThePlayer() != null) {
            MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (!MovementUtils.isMoving() && ((Boolean) this.onMoveValue.get()).booleanValue()) {
            MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
        } else {
            MinecraftInstance.mc.getTimer().setTimerSpeed(((Number) this.speedValue.get()).floatValue());
        }
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (event.getWorldClient() == null) {
            this.setState(false);
        }
    }
}

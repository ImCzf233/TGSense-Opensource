package net.ccbluex.liquidbounce.features.module.modules.fun;

import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.WEnumPlayerModelParts;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "SkinDerp",
    description = "Makes your skin blink (Requires multi-layer skin).",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0016J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/fun/SkinDerp;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "hatValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "jacketValue", "leftPantsValue", "leftSleeveValue", "prevModelParts", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/WEnumPlayerModelParts;", "rightPantsValue", "rightSleeveValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onDisable", "", "onEnable", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class SkinDerp extends Module {

    private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, 1000);
    private final BoolValue hatValue = new BoolValue("Hat", true);
    private final BoolValue jacketValue = new BoolValue("Jacket", true);
    private final BoolValue leftPantsValue = new BoolValue("LeftPants", true);
    private final BoolValue rightPantsValue = new BoolValue("RightPants", true);
    private final BoolValue leftSleeveValue = new BoolValue("LeftSleeve", true);
    private final BoolValue rightSleeveValue = new BoolValue("RightSleeve", true);
    private Set prevModelParts = SetsKt.emptySet();
    private final MSTimer timer = new MSTimer();

    public void onEnable() {
        this.prevModelParts = MinecraftInstance.mc.getGameSettings().getModelParts();
        super.onEnable();
    }

    public void onDisable() {
        Iterator iterator = MinecraftInstance.mc.getGameSettings().getModelParts().iterator();

        WEnumPlayerModelParts modelPart;

        while (iterator.hasNext()) {
            modelPart = (WEnumPlayerModelParts) iterator.next();
            MinecraftInstance.mc.getGameSettings().setModelPartEnabled(modelPart, false);
        }

        iterator = this.prevModelParts.iterator();

        while (iterator.hasNext()) {
            modelPart = (WEnumPlayerModelParts) iterator.next();
            MinecraftInstance.mc.getGameSettings().setModelPartEnabled(modelPart, true);
        }

        super.onDisable();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.timer.hasTimePassed((long) ((Number) this.delayValue.get()).intValue())) {
            if (((Boolean) this.hatValue.get()).booleanValue()) {
                MinecraftInstance.mc.getGameSettings().setModelPartEnabled(WEnumPlayerModelParts.HAT, Random.Default.nextBoolean());
            }

            if (((Boolean) this.jacketValue.get()).booleanValue()) {
                MinecraftInstance.mc.getGameSettings().setModelPartEnabled(WEnumPlayerModelParts.JACKET, Random.Default.nextBoolean());
            }

            if (((Boolean) this.leftPantsValue.get()).booleanValue()) {
                MinecraftInstance.mc.getGameSettings().setModelPartEnabled(WEnumPlayerModelParts.LEFT_PANTS_LEG, Random.Default.nextBoolean());
            }

            if (((Boolean) this.rightPantsValue.get()).booleanValue()) {
                MinecraftInstance.mc.getGameSettings().setModelPartEnabled(WEnumPlayerModelParts.RIGHT_PANTS_LEG, Random.Default.nextBoolean());
            }

            if (((Boolean) this.leftSleeveValue.get()).booleanValue()) {
                MinecraftInstance.mc.getGameSettings().setModelPartEnabled(WEnumPlayerModelParts.LEFT_SLEEVE, Random.Default.nextBoolean());
            }

            if (((Boolean) this.rightSleeveValue.get()).booleanValue()) {
                MinecraftInstance.mc.getGameSettings().setModelPartEnabled(WEnumPlayerModelParts.RIGHT_SLEEVE, Random.Default.nextBoolean());
            }

            this.timer.reset();
        }

    }
}

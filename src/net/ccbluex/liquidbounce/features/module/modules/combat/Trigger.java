package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Trigger",
    description = "Automatically attacks the entity you are looking at.",
    category = ModuleCategory.COMBAT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/combat/Trigger;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "", "lastSwing", "maxCPS", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "minCPS", "onRender", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidBounce"}
)
public final class Trigger extends Module {

    private final IntegerValue maxCPS = (IntegerValue) (new IntegerValue("MaxCPS", 8, 1, 20) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) Trigger.this.minCPS.get()).intValue();

            if (i > newValue) {
                this.set((Object) Integer.valueOf(i));
            }

            Trigger.this.delay = TimeUtils.randomClickDelay(((Number) Trigger.this.minCPS.get()).intValue(), ((Number) this.get()).intValue());
        }
    });
    private final IntegerValue minCPS = (IntegerValue) (new IntegerValue("MinCPS", 5, 1, 20) {
        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number) Trigger.this.maxCPS.get()).intValue();

            if (i < newValue) {
                this.set((Object) Integer.valueOf(i));
            }

            Trigger.this.delay = TimeUtils.randomClickDelay(((Number) this.get()).intValue(), ((Number) Trigger.this.maxCPS.get()).intValue());
        }
    });
    private long delay;
    private long lastSwing;

    @EventTarget
    public final void onRender(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IMovingObjectPosition objectMouseOver = MinecraftInstance.mc.getObjectMouseOver();

        if (objectMouseOver != null && System.currentTimeMillis() - this.lastSwing >= this.delay && EntityUtils.isSelected(objectMouseOver.getEntityHit(), true)) {
            MinecraftInstance.mc.getGameSettings().getKeyBindAttack().onTick(MinecraftInstance.mc.getGameSettings().getKeyBindAttack().getKeyCode());
            this.lastSwing = System.currentTimeMillis();
            this.delay = TimeUtils.randomClickDelay(((Number) this.minCPS.get()).intValue(), ((Number) this.maxCPS.get()).intValue());
        }

    }

    public Trigger() {
        this.delay = TimeUtils.randomClickDelay(((Number) this.minCPS.get()).intValue(), ((Number) this.maxCPS.get()).intValue());
    }

    public static final long access$getDelay$p(Trigger $this) {
        return $this.delay;
    }
}

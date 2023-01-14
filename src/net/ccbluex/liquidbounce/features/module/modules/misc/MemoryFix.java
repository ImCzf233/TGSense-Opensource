package net.ccbluex.liquidbounce.features.module.modules.misc;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.FloatValue;

@ModuleInfo(
    name = "MemoryFix",
    description = "修复主播少得可�?�的内存",
    category = ModuleCategory.MISC
)
public class MemoryFix extends Module {

    private final FloatValue delay = new FloatValue("Delay", 120.0F, 10.0F, 600.0F);
    private final FloatValue limit = new FloatValue("Limit", 80.0F, 20.0F, 95.0F);
    private final FloatValue Speed = new FloatValue("Speed", 0.05F, 0.0F, 1.0F);
    public MSTimer timer = new MSTimer();

    @EventTarget
    public void onTick(TickEvent event) {
        long maxMem = Runtime.getRuntime().maxMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long freeMem = Runtime.getRuntime().freeMemory();
        long usedMem = totalMem - freeMem;
        float pct = (float) (usedMem * 100L / maxMem);

        if (this.timer.hasReached((double) ((Float) this.delay.get()).floatValue() * 1000.0D) && (double) ((Float) this.limit.get()).floatValue() <= (double) pct) {
            Runtime.getRuntime().gc();
            this.timer.resetTwo();
        }

    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        if (!MemoryFix.mc.getGameSettings().getKeyBindForward().getPressed() && !MemoryFix.mc.getGameSettings().getKeyBindLeft().getPressed() && !MemoryFix.mc.getGameSettings().getKeyBindRight().getPressed() && !MemoryFix.mc.getGameSettings().getKeyBindBack().getPressed()) {
            MemoryFix.mc.getThePlayer().setCameraPitch(0.0F);
        } else if (MemoryFix.mc.getThePlayer().getOnGround()) {
            MemoryFix.mc.getThePlayer().setCameraPitch(((Float) this.Speed.get()).floatValue());
        }

    }
}

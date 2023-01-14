package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.vulcan;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

public class VulcanHop2 extends SpeedMode {

    private int groundTick = 0;

    public VulcanHop2() {
        super("VulcanHop2");
    }

    public void onMotion(@NotNull MotionEvent event) {
        Speed speed = (Speed) LiquidBounce.moduleManager.getModule(Speed.class);

        if (speed != null && event.getEventState() == EventState.PRE) {
            byte L = 0;

            if (MovementUtils.isMoving()) {
                VulcanHop2.mc.getTimer().setTimerSpeed(((IEntityPlayerSP) Objects.requireNonNull(VulcanHop2.mc.getThePlayer())).getMotionY() > 0.0D ? 1.65F : 0.73F);
                if (VulcanHop2.mc.getThePlayer().getOnGround()) {
                    if (this.groundTick >= 0) {
                        MovementUtils.strafe(0.483F);
                    }

                    VulcanHop2.mc.getThePlayer().setMotionY(0.42D);
                    ++this.groundTick;
                } else {
                    this.groundTick = 0;
                    int i = (int) ((double) L + 0.0D);

                    VulcanHop2.mc.getThePlayer().setMotionY((double) i);
                }
            }

        }
    }

    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        VulcanHop2.mc.getTimer().setTimerSpeed(1.0F);
        super.onDisable();
    }

    public void onUpdate() {}

    public void onMove(MoveEvent event) {}
}

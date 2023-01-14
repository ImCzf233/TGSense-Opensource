package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.vulcan;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\bH\u0016J\b\u0010\u000e\u001a\u00020\bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/vulcan/VulcanYPort;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "ticks", "", "wasTimer", "", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onTick", "onUpdate", "LiquidBounce"}
)
public final class VulcanYPort extends SpeedMode {

    private boolean wasTimer;
    private int ticks;

    public void onTick() {}

    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }

    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }

    public void onUpdate() {
        int i = this.ticks++;

        if (this.wasTimer) {
            MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
            this.wasTimer = false;
        }

        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        ientityplayersp.setJumpMovementFactor(0.0245F);
        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (!ientityplayersp.getOnGround() && this.ticks > 3) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getMotionY() > (double) 0) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp.setMotionY(-0.27D);
            }
        }

        MinecraftInstance.mc.getGameSettings().getKeyBindJump().setPressed(MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindJump()));
        if (MovementUtils.INSTANCE.getSpeed() < 0.215F) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (!ientityplayersp.getOnGround()) {
                MovementUtils.strafe(0.215F);
            }
        }

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getOnGround() && MovementUtils.isMoving()) {
            this.ticks = 0;
            MinecraftInstance.mc.getGameSettings().getKeyBindJump().setPressed(false);
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            ientityplayersp.jump();
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (!ientityplayersp.isAirBorne()) {
                return;
            }

            MinecraftInstance.mc.getTimer().setTimerSpeed(1.4F);
            this.wasTimer = true;
            if (MovementUtils.INSTANCE.getSpeed() < 0.48F) {
                MovementUtils.strafe(0.48F);
            } else {
                MovementUtils.strafe((float) ((double) MovementUtils.INSTANCE.getSpeed() * 0.985D));
            }
        } else if (!MovementUtils.isMoving()) {
            MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            ientityplayersp.setMotionX(0.0D);
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            ientityplayersp.setMotionZ(0.0D);
        }

    }

    public VulcanYPort() {
        super("VulcanYPort");
    }
}

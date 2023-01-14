package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016¨\u0006\f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/other/CustomSpeed;", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "()V", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "LiquidBounce"}
)
public final class CustomSpeed extends SpeedMode {

    public void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp;

        if (MovementUtils.isMoving()) {
            Speed speed = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);

            if (speed == null) {
                return;
            }

            Speed speed = speed;

            MinecraftInstance.mc.getTimer().setTimerSpeed(((Number) speed.getCustomTimerValue().get()).floatValue());
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getOnGround()) {
                MovementUtils.strafe(((Number) speed.getCustomSpeedValue().get()).floatValue());
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp.setMotionY((double) ((Number) speed.getCustomYValue().get()).floatValue());
            } else if (((Boolean) speed.getCustomStrafeValue().get()).booleanValue()) {
                MovementUtils.strafe(((Number) speed.getCustomSpeedValue().get()).floatValue());
            } else {
                MovementUtils.strafe$default(0.0F, 1, (Object) null);
            }
        } else {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            ientityplayersp.setMotionZ(0.0D);
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            ientityplayersp.setMotionX(ientityplayersp1.getMotionZ());
        }

    }

    public void onEnable() {
        Speed speed = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);

        if (speed != null) {
            Speed speed = speed;
            IEntityPlayerSP ientityplayersp;

            if (((Boolean) speed.getResetXZValue().get()).booleanValue()) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp.setMotionZ(0.0D);
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp.setMotionX(ientityplayersp1.getMotionZ());
            }

            if (((Boolean) speed.getResetYValue().get()).booleanValue()) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                ientityplayersp.setMotionY(0.0D);
            }

            super.onEnable();
        }
    }

    public void onDisable() {
        MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
        super.onDisable();
    }

    public void onUpdate() {}

    public void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
    }

    public CustomSpeed() {
        super("Custom");
    }
}

package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Strafe",
    description = "Allows you to freely move in mid air.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0016H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/Strafe;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "allDirectionsJumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "jump", "", "noMoveStopValue", "onGroundStrafeValue", "strengthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "wasDown", "getMoveYaw", "", "onEnable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Strafe extends Module {

    private FloatValue strengthValue = new FloatValue("Strength", 0.5F, 0.0F, 1.0F);
    private BoolValue noMoveStopValue = new BoolValue("NoMoveStop", false);
    private BoolValue onGroundStrafeValue = new BoolValue("OnGroundStrafe", false);
    private BoolValue allDirectionsJumpValue = new BoolValue("AllDirectionsJump", false);
    private boolean wasDown;
    private boolean jump;

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.jump) {
            event.cancelEvent();
        }

    }

    public void onEnable() {
        this.wasDown = false;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getOnGround() && MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown() && ((Boolean) this.allDirectionsJumpValue.get()).booleanValue()) {
            label81: {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getMovementInput().getMoveForward() == 0.0F) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getMovementInput().getMoveStrafe() == 0.0F) {
                        break label81;
                    }
                }

                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (!ientityplayersp.isInWater()) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (!ientityplayersp.isInLava()) {
                        ientityplayersp = MinecraftInstance.mc.getThePlayer();
                        if (ientityplayersp == null) {
                            Intrinsics.throwNpe();
                        }

                        if (!ientityplayersp.isOnLadder()) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            if (!ientityplayersp.isInWeb()) {
                                if (MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                                    MinecraftInstance.mc.getGameSettings().getKeyBindJump().setPressed(false);
                                    this.wasDown = true;
                                }

                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                float yaw = ientityplayersp.getRotationYaw();

                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setRotationYaw(this.getMoveYaw());
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.jump();
                                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                                if (ientityplayersp == null) {
                                    Intrinsics.throwNpe();
                                }

                                ientityplayersp.setRotationYaw(yaw);
                                this.jump = true;
                                if (this.wasDown) {
                                    MinecraftInstance.mc.getGameSettings().getKeyBindJump().setPressed(true);
                                    this.wasDown = false;
                                }

                                return;
                            }
                        }
                    }
                }
            }
        }

        this.jump = false;
    }

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double d0 = ientityplayersp.getMotionX();
        IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        d0 *= ientityplayersp1.getMotionX();
        ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        double d1 = ientityplayersp1.getMotionZ();
        IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp2 == null) {
            Intrinsics.throwNpe();
        }

        double speed = d0 + d1 * ientityplayersp2.getMotionZ();
        boolean motionX = false;
        double shotSpeed = Math.sqrt(speed);

        speed = shotSpeed * ((Number) this.strengthValue.get()).doubleValue();
        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double motionX1 = ientityplayersp.getMotionX() * (double) ((float) 1 - ((Number) this.strengthValue.get()).floatValue());

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double motionZ = ientityplayersp.getMotionZ() * (double) ((float) 1 - ((Number) this.strengthValue.get()).floatValue());

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getMovementInput().getMoveForward() == 0.0F) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getMovementInput().getMoveStrafe() == 0.0F) {
                if (((Boolean) this.noMoveStopValue.get()).booleanValue()) {
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

                return;
            }
        }

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (!ientityplayersp.getOnGround() || ((Boolean) this.onGroundStrafeValue.get()).booleanValue()) {
            float yaw = this.getMoveYaw();

            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            double d2 = Math.toRadians((double) yaw);
            IEntityPlayerSP ientityplayersp3 = ientityplayersp;
            boolean flag = false;
            double d3 = Math.sin(d2);

            ientityplayersp3.setMotionX(-d3 * speed + motionX1);
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            d2 = Math.toRadians((double) yaw);
            ientityplayersp3 = ientityplayersp;
            flag = false;
            d3 = Math.cos(d2);
            ientityplayersp3.setMotionZ(d3 * speed + motionZ);
        }

    }

    private final float getMoveYaw() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        float moveYaw = ientityplayersp.getRotationYaw();

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP ientityplayersp1;

        if (ientityplayersp.getMoveForward() != 0.0F) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getMoveStrafing() == 0.0F) {
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                moveYaw += (float) (ientityplayersp1.getMoveForward() > (float) 0 ? 0 : 180);
                return moveYaw;
            }
        }

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getMoveForward() != 0.0F) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getMoveStrafing() != 0.0F) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getMoveForward() > (float) 0) {
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    moveYaw += (float) (ientityplayersp1.getMoveStrafing() > (float) 0 ? -45 : 45);
                } else {
                    ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    moveYaw -= (float) (ientityplayersp1.getMoveStrafing() > (float) 0 ? -45 : 45);
                }

                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                moveYaw += (float) (ientityplayersp1.getMoveForward() > (float) 0 ? 0 : 180);
                return moveYaw;
            }
        }

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        if (ientityplayersp.getMoveStrafing() != 0.0F) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getMoveForward() == 0.0F) {
                ientityplayersp1 = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp1 == null) {
                    Intrinsics.throwNpe();
                }

                moveYaw += (float) (ientityplayersp1.getMoveStrafing() > (float) 0 ? -90 : 90);
            }
        }

        return moveYaw;
    }
}

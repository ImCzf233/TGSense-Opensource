package net.ccbluex.liquidbounce.utils;

import java.math.BigDecimal;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0004H\u0007J\u000e\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\rJ\u000e\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\u0004J\u0016\u0010\u001e\u001a\u00020\r2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u001f\u001a\u00020\u0004J\u000e\u0010\"\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u0004J\u0012\u0010#\u001a\u00020\u00182\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0007J\u0006\u0010$\u001a\u00020\u0018R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u00048FX\u0087\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u0002\u001a\u0004\b\u000b\u0010\u0006R\u001a\u0010\f\u001a\u00020\r8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u000e\u0010\u0002\u001a\u0004\b\f\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006%"},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/MovementUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "bps", "", "getBps", "()D", "setBps", "(D)V", "direction", "direction$annotations", "getDirection", "isMoving", "", "isMoving$annotations", "()Z", "lastX", "lastY", "lastZ", "speed", "", "getSpeed", "()F", "forward", "", "length", "getBlockSpeed", "entityIn", "Lnet/minecraft/entity/EntityLivingBase;", "hasMotion", "isOnGround", "height", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "setMotion", "strafe", "updateBlocksPerSecond", "LiquidBounce"}
)
public final class MovementUtils extends MinecraftInstance {

    private static double bps;
    private static double lastX;
    private static double lastY;
    private static double lastZ;
    public static final MovementUtils INSTANCE;

    public final double getBps() {
        return MovementUtils.bps;
    }

    public final void setBps(double <set-?>) {
        MovementUtils.bps = <set-?>;
    }

    public final float getSpeed() {
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

        double d2 = d0 + d1 * ientityplayersp2.getMotionZ();
        boolean flag = false;

        return (float) Math.sqrt(d2);
    }

    public final void setMotion(double speed) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double forward = (double) ientityplayersp.getMovementInput().getMoveForward();

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double strafe = (double) ientityplayersp.getMovementInput().getMoveStrafe();

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        float yaw = ientityplayersp.getRotationYaw();

        if (forward == 0.0D && strafe == 0.0D) {
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
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (float) (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (float) (forward > 0.0D ? 45 : -45);
                }

                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1.0D;
                } else if (forward < 0.0D) {
                    forward = -1.0D;
                }
            }

            double cos = Math.cos(Math.toRadians((double) yaw + (double) 90.0F));
            double sin = Math.sin(Math.toRadians((double) yaw + (double) 90.0F));

            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            ientityplayersp.setMotionX(forward * speed * cos + strafe * speed * sin);
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            ientityplayersp.setMotionZ(forward * speed * sin - strafe * speed * cos);
        }

    }

    /** @deprecated */
    @JvmStatic
    public static void isMoving$annotations() {}

    public static final boolean isMoving() {
        boolean flag;

        if (MinecraftInstance.mc.getThePlayer() != null) {
            label31: {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getMovementInput().getMoveForward() == 0.0F) {
                    ientityplayersp = MinecraftInstance.mc.getThePlayer();
                    if (ientityplayersp == null) {
                        Intrinsics.throwNpe();
                    }

                    if (ientityplayersp.getMovementInput().getMoveStrafe() == 0.0F) {
                        break label31;
                    }
                }

                flag = true;
                return flag;
            }
        }

        flag = false;
        return flag;
    }

    public final boolean hasMotion() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        boolean flag;

        if (ientityplayersp.getMotionX() != 0.0D) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            if (ientityplayersp.getMotionZ() != 0.0D) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getMotionY() != 0.0D) {
                    flag = true;
                    return flag;
                }
            }
        }

        flag = false;
        return flag;
    }

    @JvmStatic
    @JvmOverloads
    public static final void strafe(float speed) {
        if (isMoving()) {
            double yaw = getDirection();
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            IEntityPlayerSP thePlayer = ientityplayersp;
            boolean flag = false;
            double d0 = Math.sin(yaw);

            thePlayer.setMotionX(-d0 * (double) speed);
            flag = false;
            d0 = Math.cos(yaw);
            thePlayer.setMotionZ(d0 * (double) speed);
        }
    }

    public static void strafe$default(float f, int i, Object object) {
        if ((i & 1) != 0) {
            f = MovementUtils.INSTANCE.getSpeed();
        }

        strafe(f);
    }

    @JvmStatic
    @JvmOverloads
    public static final void strafe() {
        strafe$default(0.0F, 1, (Object) null);
    }

    @JvmStatic
    public static final void forward(double length) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;
        double yaw = Math.toRadians((double) thePlayer.getRotationYaw());
        double d0 = thePlayer.getPosX();
        boolean flag = false;
        double d1 = Math.sin(yaw);
        double d2 = d0 + -d1 * length;
        double d3 = thePlayer.getPosY();
        double d4 = thePlayer.getPosZ();

        d1 = d3;
        d0 = d2;
        flag = false;
        double d5 = Math.cos(yaw);

        thePlayer.setPosition(d0, d1, d4 + d5 * length);
    }

    public final void updateBlocksPerSecond() {
        IEntityPlayerSP ientityplayersp;
        label31: {
            if (MinecraftInstance.mc.getThePlayer() != null) {
                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (ientityplayersp.getTicksExisted() >= 1) {
                    break label31;
                }
            }

            MovementUtils.bps = 0.0D;
        }

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        double distance = ientityplayersp.getDistance(MovementUtils.lastX, MovementUtils.lastY, MovementUtils.lastZ);

        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        MovementUtils.lastX = ientityplayersp.getPosX();
        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        MovementUtils.lastY = ientityplayersp.getPosY();
        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        MovementUtils.lastZ = ientityplayersp.getPosZ();
        MovementUtils.bps = distance * (double) ((float) 20 * MinecraftInstance.mc.getTimer().getTimerSpeed());
    }

    public final double getBlockSpeed(@NotNull EntityLivingBase entityIn) {
        Intrinsics.checkParameterIsNotNull(entityIn, "entityIn");
        return BigDecimal.valueOf(Math.sqrt(Math.pow(entityIn.posX - entityIn.prevPosX, 2.0D) + Math.pow(entityIn.posZ - entityIn.prevPosZ, 2.0D)) * (double) 20).setScale(1, 4).doubleValue();
    }

    public final boolean isOnGround(double height) {
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntity ientity = (IEntity) ientityplayersp;
        IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp1 == null) {
            Intrinsics.throwNpe();
        }

        return !iworldclient.getCollidingBoundingBoxes(ientity, ientityplayersp1.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    public final boolean isOnGround(@NotNull IEntity entity, double height) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        return !iworldclient.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    /** @deprecated */
    @JvmStatic
    public static void direction$annotations() {}

    public static final double getDirection() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;
        float rotationYaw = thePlayer.getRotationYaw();

        if (thePlayer.getMoveForward() < 0.0F) {
            rotationYaw += 180.0F;
        }

        float forward = 1.0F;

        if (thePlayer.getMoveForward() < 0.0F) {
            forward = -0.5F;
        } else if (thePlayer.getMoveForward() > 0.0F) {
            forward = 0.5F;
        }

        if (thePlayer.getMoveStrafing() > 0.0F) {
            rotationYaw -= 90.0F * forward;
        }

        if (thePlayer.getMoveStrafing() < 0.0F) {
            rotationYaw += 90.0F * forward;
        }

        return Math.toRadians((double) rotationYaw);
    }

    static {
        MovementUtils movementutils = new MovementUtils();

        INSTANCE = movementutils;
    }
}

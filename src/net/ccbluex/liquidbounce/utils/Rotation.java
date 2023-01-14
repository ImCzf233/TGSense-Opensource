package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.WMathHelper;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\u000e\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\u000e\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001dJ\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006 "},
    d2 = { "Lnet/ccbluex/liquidbounce/utils/Rotation;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "yaw", "", "pitch", "(FF)V", "getPitch", "()F", "setPitch", "(F)V", "getYaw", "setYaw", "applyStrafeToPlayer", "", "event", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "component1", "component2", "copy", "equals", "", "other", "", "fixedSensitivity", "sensitivity", "hashCode", "", "toPlayer", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "toString", "", "LiquidBounce"}
)
public final class Rotation extends MinecraftInstance {

    private float yaw;
    private float pitch;

    public final void toPlayer(@NotNull IEntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        float f = this.yaw;
        boolean flag = false;

        if (!Float.isNaN(f)) {
            f = this.pitch;
            flag = false;
            if (!Float.isNaN(f)) {
                this.fixedSensitivity(MinecraftInstance.mc.getGameSettings().getMouseSensitivity());
                player.setRotationYaw(this.yaw);
                player.setRotationPitch(this.pitch);
                return;
            }
        }

    }

    public final void fixedSensitivity(float sensitivity) {
        float f = sensitivity * 0.6F + 0.2F;
        float gcd = f * f * f * 1.2F;
        Rotation rotation = RotationUtils.serverRotation;
        float deltaYaw = this.yaw - rotation.yaw;

        deltaYaw -= deltaYaw % gcd;
        this.yaw = rotation.yaw + deltaYaw;
        float deltaPitch = this.pitch - rotation.pitch;

        deltaPitch -= deltaPitch % gcd;
        this.pitch = rotation.pitch + deltaPitch;
    }

    public final void applyStrafeToPlayer(@NotNull StrafeEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP player = ientityplayersp;
        int dif = (int) ((WMathHelper.wrapAngleTo180_float(player.getRotationYaw() - this.yaw - 23.5F - (float) 135) + (float) 180) / (float) 45);
        float yaw = this.yaw;
        float strafe = event.getStrafe();
        float forward = event.getForward();
        float friction = event.getFriction();
        float calcForward = 0.0F;
        float calcStrafe = 0.0F;

        switch (dif) {
        case 0:
            calcForward = forward;
            calcStrafe = strafe;
            break;

        case 1:
            calcForward += forward;
            calcStrafe -= forward;
            calcForward += strafe;
            calcStrafe += strafe;
            break;

        case 2:
            calcForward = strafe;
            calcStrafe = -forward;
            break;

        case 3:
            calcForward -= forward;
            calcStrafe -= forward;
            calcForward += strafe;
            calcStrafe -= strafe;
            break;

        case 4:
            calcForward = -forward;
            calcStrafe = -strafe;
            break;

        case 5:
            calcForward -= forward;
            calcStrafe += forward;
            calcForward -= strafe;
            calcStrafe -= strafe;
            break;

        case 6:
            calcForward = -strafe;
            calcStrafe = forward;
            break;

        case 7:
            calcForward += forward;
            calcStrafe += forward;
            calcForward -= strafe;
            calcStrafe += strafe;
        }

        if (calcForward > 1.0F || calcForward < 0.9F && calcForward > 0.3F || calcForward < -1.0F || calcForward > -0.9F && calcForward < -0.3F) {
            calcForward *= 0.5F;
        }

        if (calcStrafe > 1.0F || calcStrafe < 0.9F && calcStrafe > 0.3F || calcStrafe < -1.0F || calcStrafe > -0.9F && calcStrafe < -0.3F) {
            calcStrafe *= 0.5F;
        }

        float d = calcStrafe * calcStrafe + calcForward * calcForward;

        if (d >= 1.0E-4F) {
            boolean yawSin = false;

            d = (float) Math.sqrt((double) d);
            if (d < 1.0F) {
                d = 1.0F;
            }

            d = friction / d;
            calcStrafe *= d;
            calcForward *= d;
            float yawCos = (float) ((double) yaw * 3.141592653589793D / (double) 180.0F);
            boolean flag = false;
            float yawSin1 = (float) Math.sin((double) yawCos);
            float f = (float) ((double) yaw * 3.141592653589793D / (double) 180.0F);
            boolean flag1 = false;

            yawCos = (float) Math.cos((double) f);
            player.setMotionX(player.getMotionX() + ((double) (calcStrafe * yawCos) - (double) calcForward * (double) yawSin1));
            player.setMotionZ(player.getMotionZ() + (double) (calcForward * yawCos) + (double) calcStrafe * (double) yawSin1);
        }

    }

    public final float getYaw() {
        return this.yaw;
    }

    public final void setYaw(float <set-?>) {
        this.yaw = <set-?>;
    }

    public final float getPitch() {
        return this.pitch;
    }

    public final void setPitch(float <set-?>) {
        this.pitch = <set-?>;
    }

    public Rotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public final float component1() {
        return this.yaw;
    }

    public final float component2() {
        return this.pitch;
    }

    @NotNull
    public final Rotation copy(float yaw, float pitch) {
        return new Rotation(yaw, pitch);
    }

    public static Rotation copy$default(Rotation rotation, float f, float f1, int i, Object object) {
        if ((i & 1) != 0) {
            f = rotation.yaw;
        }

        if ((i & 2) != 0) {
            f1 = rotation.pitch;
        }

        return rotation.copy(f, f1);
    }

    @NotNull
    public String toString() {
        return "Rotation(yaw=" + this.yaw + ", pitch=" + this.pitch + ")";
    }

    public int hashCode() {
        return Float.hashCode(this.yaw) * 31 + Float.hashCode(this.pitch);
    }

    public boolean equals(@Nullable Object object) {
        if (this != object) {
            if (object instanceof Rotation) {
                Rotation rotation = (Rotation) object;

                if (Float.compare(this.yaw, rotation.yaw) == 0 && Float.compare(this.pitch, rotation.pitch) == 0) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}

package me.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004BU\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0006\u0010\n\u001a\u00020\u0006\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\r¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"},
    d2 = { "Lme/utils/FallingPlayer;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "player", "Lnet/minecraft/entity/player/EntityPlayer;", "(Lnet/minecraft/entity/player/EntityPlayer;)V", "x", "", "y", "z", "motionX", "motionY", "motionZ", "yaw", "", "strafe", "forward", "jumpMovementFactor", "(DDDDDDFFFF)V", "calculateForTick", "", "findCollision", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "ticks", "", "rayTrace", "start", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "end", "LiquidBounce"}
)
public final class FallingPlayer extends MinecraftInstance {

    private double x;
    private double y;
    private double z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private final float yaw;
    private float strafe;
    private float forward;
    private final float jumpMovementFactor;

    private final void calculateForTick() {
        this.strafe *= 0.98F;
        this.forward *= 0.98F;
        float v = this.strafe * this.strafe + this.forward * this.forward;

        if (v >= 1.0E-4F) {
            v = MathHelper.sqrt(v);
            if (v < 1.0F) {
                v = 1.0F;
            }

            float fixedJumpFactor = this.jumpMovementFactor;
            EntityPlayerSP entityplayersp = MinecraftInstance.mc2.player;

            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc2.player, "mc2.player");
            if (entityplayersp.isSprinting()) {
                fixedJumpFactor = (float) ((double) fixedJumpFactor * 1.3D);
            }

            v = fixedJumpFactor / v;
            this.strafe *= v;
            this.forward *= v;
            float f1 = MathHelper.sin(this.yaw * (float) 3.141592653589793D / 180.0F);
            float f2 = MathHelper.cos(this.yaw * (float) 3.141592653589793D / 180.0F);

            this.motionX += (double) (this.strafe * f2 - this.forward * f1);
            this.motionZ += (double) (this.forward * f2 + this.strafe * f1);
        }

        this.motionY -= 0.08D;
        this.motionX *= 0.91D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.91D;
        this.x += this.motionX;
        this.y += this.motionY;
        this.z += this.motionZ;
    }

    @Nullable
    public final WBlockPos findCollision(int ticks) {
        int i = 0;

        for (int i = ticks; i < i; ++i) {
            WVec3 start = new WVec3(this.x, this.y, this.z);

            this.calculateForTick();
            WVec3 end = new WVec3(this.x, this.y, this.z);
            ObjectRef raytracedBlock = new ObjectRef();
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            float w = ientityplayersp.getWidth() / 2.0F;
            WBlockPos this_$iv = this.rayTrace(start, end);
            boolean x$iv = false;
            boolean flag = false;
            boolean $i$a$-also-FallingPlayer$findCollision$9 = false;

            raytracedBlock.element = this_$iv;
            if (this_$iv != null) {
                return (WBlockPos) raytracedBlock.element;
            }

            double d0 = (double) w;
            double it = 0.0D;
            double z$iv = (double) w;
            boolean $i$f$addVector = false;
            WVec3 wvec3 = new WVec3(start.getXCoord() + d0, start.getYCoord() + it, start.getZCoord() + z$iv);

            this_$iv = this.rayTrace(wvec3, end);
            x$iv = false;
            flag = false;
            $i$a$-also-FallingPlayer$findCollision$9 = false;
            raytracedBlock.element = this_$iv;
            if (this_$iv != null) {
                return (WBlockPos) raytracedBlock.element;
            }

            d0 = -((double) w);
            it = 0.0D;
            z$iv = (double) w;
            $i$f$addVector = false;
            wvec3 = new WVec3(start.getXCoord() + d0, start.getYCoord() + it, start.getZCoord() + z$iv);
            this_$iv = this.rayTrace(wvec3, end);
            x$iv = false;
            flag = false;
            $i$a$-also-FallingPlayer$findCollision$9 = false;
            raytracedBlock.element = this_$iv;
            if (this_$iv != null) {
                return (WBlockPos) raytracedBlock.element;
            }

            d0 = (double) w;
            it = 0.0D;
            z$iv = -((double) w);
            $i$f$addVector = false;
            wvec3 = new WVec3(start.getXCoord() + d0, start.getYCoord() + it, start.getZCoord() + z$iv);
            this_$iv = this.rayTrace(wvec3, end);
            x$iv = false;
            flag = false;
            $i$a$-also-FallingPlayer$findCollision$9 = false;
            raytracedBlock.element = this_$iv;
            if (this_$iv != null) {
                return (WBlockPos) raytracedBlock.element;
            }

            d0 = -((double) w);
            it = 0.0D;
            z$iv = -((double) w);
            $i$f$addVector = false;
            wvec3 = new WVec3(start.getXCoord() + d0, start.getYCoord() + it, start.getZCoord() + z$iv);
            this_$iv = this.rayTrace(wvec3, end);
            x$iv = false;
            flag = false;
            $i$a$-also-FallingPlayer$findCollision$9 = false;
            raytracedBlock.element = this_$iv;
            if (this_$iv != null) {
                return (WBlockPos) raytracedBlock.element;
            }

            d0 = (double) w;
            it = 0.0D;
            z$iv = (double) (w / 2.0F);
            $i$f$addVector = false;
            wvec3 = new WVec3(start.getXCoord() + d0, start.getYCoord() + it, start.getZCoord() + z$iv);
            this_$iv = this.rayTrace(wvec3, end);
            x$iv = false;
            flag = false;
            $i$a$-also-FallingPlayer$findCollision$9 = false;
            raytracedBlock.element = this_$iv;
            if (this_$iv != null) {
                return (WBlockPos) raytracedBlock.element;
            }

            d0 = -((double) w);
            it = 0.0D;
            z$iv = (double) (w / 2.0F);
            $i$f$addVector = false;
            wvec3 = new WVec3(start.getXCoord() + d0, start.getYCoord() + it, start.getZCoord() + z$iv);
            this_$iv = this.rayTrace(wvec3, end);
            x$iv = false;
            flag = false;
            $i$a$-also-FallingPlayer$findCollision$9 = false;
            raytracedBlock.element = this_$iv;
            if (this_$iv != null) {
                return (WBlockPos) raytracedBlock.element;
            }

            d0 = (double) (w / 2.0F);
            it = 0.0D;
            z$iv = (double) w;
            $i$f$addVector = false;
            wvec3 = new WVec3(start.getXCoord() + d0, start.getYCoord() + it, start.getZCoord() + z$iv);
            this_$iv = this.rayTrace(wvec3, end);
            x$iv = false;
            flag = false;
            $i$a$-also-FallingPlayer$findCollision$9 = false;
            raytracedBlock.element = this_$iv;
            if (this_$iv != null) {
                return (WBlockPos) raytracedBlock.element;
            }

            d0 = (double) (w / 2.0F);
            it = 0.0D;
            z$iv = -((double) w);
            $i$f$addVector = false;
            wvec3 = new WVec3(start.getXCoord() + d0, start.getYCoord() + it, start.getZCoord() + z$iv);
            this_$iv = this.rayTrace(wvec3, end);
            x$iv = false;
            flag = false;
            $i$a$-also-FallingPlayer$findCollision$9 = false;
            raytracedBlock.element = this_$iv;
            if (this_$iv != null) {
                return (WBlockPos) raytracedBlock.element;
            }
        }

        return null;
    }

    private final WBlockPos rayTrace(WVec3 start, WVec3 end) {
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        IMovingObjectPosition result = iworldclient.rayTraceBlocks(start, end, true);

        return result != null && result.getTypeOfHit() == RangesKt.rangeTo((Comparable) IMovingObjectPosition.WMovingObjectType.MISS, (Comparable) IMovingObjectPosition.WMovingObjectType.BLOCK) && result.getSideHit() == EnumFacing.UP ? result.getBlockPos() : null;
    }

    public FallingPlayer(double x, double y, double z, double motionX, double motionY, double motionZ, float yaw, float strafe, float forward, float jumpMovementFactor) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.yaw = yaw;
        this.strafe = strafe;
        this.forward = forward;
        this.jumpMovementFactor = jumpMovementFactor;
    }

    public FallingPlayer(@NotNull EntityPlayer player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        this(player.posX, player.posY, player.posZ, player.motionX, player.motionY, player.motionZ, player.rotationYaw, player.moveStrafing, player.moveForward, player.jumpMovementFactor);
    }
}

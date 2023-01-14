package net.ccbluex.liquidbounce.utils.misc;

import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.Nullable;

public class FallingPlayer extends MinecraftInstance {

    private double x;
    private double y;
    private double z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private final float yaw;
    private float strafe;
    private float forward;

    public FallingPlayer(double x, double y, double z, double motionX, double motionY, double motionZ, float yaw, float strafe, float forward) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.yaw = yaw;
        this.strafe = strafe;
        this.forward = forward;
    }

    private void calculateForTick() {
        this.strafe *= 0.98F;
        this.forward *= 0.98F;
        float v = this.strafe * this.strafe + this.forward * this.forward;

        if (v >= 1.0E-4F) {
            v = (float) Math.sqrt((double) v);
            if (v < 1.0F) {
                v = 1.0F;
            }

            v = FallingPlayer.mc.getThePlayer().getJumpMovementFactor() / v;
            this.strafe *= v;
            this.forward *= v;
            float f1 = (float) Math.sin((double) (this.yaw * 3.1415927F / 180.0F));
            float f2 = (float) Math.cos((double) (this.yaw * 3.1415927F / 180.0F));

            this.motionX += (double) (this.strafe * f2 - this.forward * f1);
            this.motionZ += (double) (this.forward * f2 + this.strafe * f1);
        }

        this.motionY -= 0.08D;
        this.motionX *= 0.91D;
        this.motionY *= 0.9800000190734863D;
        this.motionY *= 0.91D;
        this.motionZ *= 0.91D;
        this.x += this.motionX;
        this.y += this.motionY;
        this.z += this.motionZ;
    }

    public FallingPlayer.CollisionResult findCollision(int ticks) {
        for (int i = 0; i < ticks; ++i) {
            WVec3 start = new WVec3(this.x, this.y, this.z);

            this.calculateForTick();
            WVec3 end = new WVec3(this.x, this.y, this.z);
            float w = FallingPlayer.mc.getThePlayer().getWidth() / 2.0F;
            WBlockPos raytracedBlock;

            if ((raytracedBlock = this.rayTrace(start, end)) != null) {
                return new FallingPlayer.CollisionResult(raytracedBlock, i);
            }

            if ((raytracedBlock = this.rayTrace(start.addVector((double) w, 0.0D, (double) w), end)) != null) {
                return new FallingPlayer.CollisionResult(raytracedBlock, i);
            }

            if ((raytracedBlock = this.rayTrace(start.addVector((double) (-w), 0.0D, (double) w), end)) != null) {
                return new FallingPlayer.CollisionResult(raytracedBlock, i);
            }

            if ((raytracedBlock = this.rayTrace(start.addVector((double) w, 0.0D, (double) (-w)), end)) != null) {
                return new FallingPlayer.CollisionResult(raytracedBlock, i);
            }

            if ((raytracedBlock = this.rayTrace(start.addVector((double) (-w), 0.0D, (double) (-w)), end)) != null) {
                return new FallingPlayer.CollisionResult(raytracedBlock, i);
            }

            if ((raytracedBlock = this.rayTrace(start.addVector((double) w, 0.0D, (double) (w / 2.0F)), end)) != null) {
                return new FallingPlayer.CollisionResult(raytracedBlock, i);
            }

            if ((raytracedBlock = this.rayTrace(start.addVector((double) (-w), 0.0D, (double) (w / 2.0F)), end)) != null) {
                return new FallingPlayer.CollisionResult(raytracedBlock, i);
            }

            if ((raytracedBlock = this.rayTrace(start.addVector((double) (w / 2.0F), 0.0D, (double) w), end)) != null) {
                return new FallingPlayer.CollisionResult(raytracedBlock, i);
            }

            if ((raytracedBlock = this.rayTrace(start.addVector((double) (w / 2.0F), 0.0D, (double) (-w)), end)) != null) {
                return new FallingPlayer.CollisionResult(raytracedBlock, i);
            }
        }

        return null;
    }

    @Nullable
    private WBlockPos rayTrace(WVec3 start, WVec3 end) {
        IMovingObjectPosition result = FallingPlayer.mc.getTheWorld().rayTraceBlocks(start, end, true);

        return result != null && result.getTypeOfHit() == IMovingObjectPosition.WMovingObjectType.BLOCK && result.getSideHit().isUp() ? result.getBlockPos() : null;
    }

    public static class CollisionResult {

        private final WBlockPos pos;
        private final int tick;

        public CollisionResult(WBlockPos pos, int tick) {
            this.pos = pos;
            this.tick = tick;
        }

        public WBlockPos getPos() {
            return this.pos;
        }

        public int getTick() {
            return this.tick;
        }
    }
}

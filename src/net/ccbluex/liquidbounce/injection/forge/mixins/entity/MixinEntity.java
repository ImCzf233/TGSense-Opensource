package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.combat.HitBox;
import net.ccbluex.liquidbounce.features.module.modules.exploit.NoPitchLimit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({ Entity.class})
public abstract class MixinEntity {

    @Shadow
    public double posX;
    @Shadow
    public double posY;
    @Shadow
    public double posZ;
    @Shadow
    public float rotationPitch;
    @Shadow
    public float rotationYaw;
    @Shadow
    public Entity ridingEntity;
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Shadow
    public boolean onGround;
    @Shadow
    public boolean isAirBorne;
    @Shadow
    public boolean noClip;
    @Shadow
    public World world;
    @Shadow
    public boolean isInWeb;
    @Shadow
    public float stepHeight;
    @Shadow
    public boolean collidedHorizontally;
    @Shadow
    public boolean collidedVertically;
    @Shadow
    public boolean collided;
    @Shadow
    public float distanceWalkedModified;
    @Shadow
    public float distanceWalkedOnStepModified;
    @Shadow
    public int timeUntilPortal;
    @Shadow
    public float width;
    @Shadow
    public int nextStepDistance;
    @Shadow
    public int fire;
    @Shadow
    public float prevRotationPitch;
    @Shadow
    public float prevRotationYaw;
    @Shadow
    public long pistonDeltasGameTime;
    @Shadow
    @Final
    public double[] pistonDeltas;
    @Shadow
    public float nextFlap;
    @Shadow
    protected Random rand;
    @Shadow
    protected boolean inPortal;

    @Shadow
    public abstract boolean isSprinting();

    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow
    public abstract void setEntityBoundingBox(AxisAlignedBB axisalignedbb);

    @Shadow
    public void move(MoverType p_move_1_, double p_move_2_, double p_move_4_, double p_move_4_2) {}

    @Shadow
    public abstract boolean isInWater();

    @Shadow
    protected abstract int getFireImmuneTicks();

    @Shadow
    public abstract boolean isRiding();

    @Shadow
    protected abstract void dealFireDamage(int i);

    @Shadow
    public abstract boolean isWet();

    @Shadow
    public abstract void addEntityCrashInfo(CrashReportCategory crashreportcategory);

    @Shadow
    protected abstract void doBlockCollisions();

    @Shadow
    protected abstract void playStepSound(BlockPos blockpos, Block block);

    @Shadow
    protected abstract Vec3d getVectorForRotation(float f, float f1);

    @Shadow
    public abstract UUID getUniqueID();

    @Shadow
    public abstract boolean isSneaking();

    @Shadow
    public abstract boolean isInsideOfMaterial(Material material);

    @Shadow
    @Nullable
    public abstract Entity getRidingEntity();

    @Shadow
    public abstract void resetPositionToBB();

    @Shadow
    protected abstract void updateFallState(double d0, boolean flag, IBlockState iblockstate, BlockPos blockpos);

    @Shadow
    protected abstract boolean canTriggerWalking();

    @Shadow
    public abstract boolean isBeingRidden();

    @Shadow
    @Nullable
    public abstract Entity getControllingPassenger();

    @Shadow
    public abstract void playSound(SoundEvent soundevent, float f, float f1);

    @Shadow
    protected abstract SoundEvent getSwimSound();

    @Shadow
    protected abstract boolean makeFlySound();

    @Shadow
    protected abstract float playFlySound(float f);

    @Shadow
    public abstract boolean isBurning();

    public int getNextStepDistance() {
        return this.nextStepDistance;
    }

    public void setNextStepDistance(int nextStepDistance) {
        this.nextStepDistance = nextStepDistance;
    }

    public int getFire() {
        return this.fire;
    }

    @Shadow
    public abstract void setFire(int i);

    @Inject(
        method = { "getCollisionBorderSize"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void getCollisionBorderSize(CallbackInfoReturnable callbackInfoReturnable) {
        HitBox hitBox = (HitBox) LiquidBounce.moduleManager.getModule(HitBox.class);

        if (((HitBox) Objects.requireNonNull(hitBox)).getState()) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(0.1F + ((Float) hitBox.getSizeValue().get()).floatValue()));
        }

    }

    @Inject(
        method = { "turn"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void setAngles(float yaw, float pitch, CallbackInfo callbackInfo) {
        if (((Module) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(NoPitchLimit.class))).getState()) {
            callbackInfo.cancel();
            float f = this.rotationPitch;
            float f1 = this.rotationYaw;

            this.rotationYaw = (float) ((double) this.rotationYaw + (double) yaw * 0.15D);
            this.rotationPitch = (float) ((double) this.rotationPitch - (double) pitch * 0.15D);
            this.prevRotationPitch += this.rotationPitch - f;
            this.prevRotationYaw += this.rotationYaw - f1;
        }

    }
}

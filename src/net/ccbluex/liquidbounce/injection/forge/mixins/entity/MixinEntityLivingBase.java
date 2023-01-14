package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.movement.AirJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.LiquidWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoJumpDelay;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ EntityLivingBase.class})
public abstract class MixinEntityLivingBase extends MixinEntity {

    @Shadow
    public int activeItemStackUseCount;
    @Shadow
    protected boolean isJumping;
    @Shadow
    private int jumpTicks;

    @Shadow
    public abstract boolean isHandActive();

    @Shadow
    public abstract ItemStack getActiveItemStack();

    @Shadow
    protected abstract float getJumpUpwardsMotion();

    @Shadow
    public abstract PotionEffect getActivePotionEffect(Potion potion);

    @Shadow
    public abstract boolean isPotionActive(Potion potion);

    @Shadow
    public void onLivingUpdate() {}

    @Shadow
    protected abstract void updateFallState(double d0, boolean flag, IBlockState iblockstate, BlockPos blockpos);

    @Shadow
    public abstract float getHealth();

    @Shadow
    public abstract ItemStack getHeldItem(EnumHand enumhand);

    @Shadow
    protected abstract void updateEntityActionState();

    @Shadow
    protected abstract void handleJumpWater();

    @Shadow
    public abstract boolean isElytraFlying();

    @Shadow
    public abstract int getItemInUseCount();

    @Overwrite
    protected void jump() {
        JumpEvent jumpEvent = new JumpEvent(this.getJumpUpwardsMotion());

        LiquidBounce.eventManager.callEvent(jumpEvent);
        if (!jumpEvent.isCancelled()) {
            this.motionY = (double) jumpEvent.getMotion();
            if (this.isPotionActive(MobEffects.JUMP_BOOST)) {
                this.motionY += (double) ((float) (this.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
            }

            if (this.isSprinting()) {
                float f = this.rotationYaw * 0.017453292F;

                this.motionX -= (double) (MathHelper.sin(f) * 0.2F);
                this.motionZ += (double) (MathHelper.cos(f) * 0.2F);
            }

            this.isAirBorne = true;
            ForgeHooks.onLivingJump((EntityLivingBase) this);
        }
    }

    @Inject(
        method = { "onLivingUpdate"},
        at = {             @At("HEAD")}
    )
    private void headLiving(CallbackInfo callbackInfo) {
        if (((Module) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(NoJumpDelay.class))).getState()) {
            this.jumpTicks = 0;
        }

    }

    @Inject(
        method = { "onLivingUpdate"},
        at = {             @At(
                value = "FIELD",
                target = "Lnet/minecraft/entity/EntityLivingBase;isJumping:Z",
                ordinal = 1
            )}
    )
    private void onJumpSection(CallbackInfo callbackInfo) {
        if (((Module) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(AirJump.class))).getState() && this.isJumping && this.jumpTicks == 0) {
            this.jump();
            this.jumpTicks = 10;
        }

        LiquidWalk liquidWalk = (LiquidWalk) LiquidBounce.moduleManager.getModule(LiquidWalk.class);

        if (((LiquidWalk) Objects.requireNonNull(liquidWalk)).getState() && !this.isJumping && !this.isSneaking() && this.isInWater() && ((String) liquidWalk.getModeValue().get()).equalsIgnoreCase("Swim")) {
            this.handleJumpWater();
        }

    }

    @Inject(
        method = { "getLook"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void getLook(CallbackInfoReturnable callbackInfoReturnable) {
        if ((EntityLivingBase) this instanceof EntityPlayerSP) {
            callbackInfoReturnable.setReturnValue(this.getVectorForRotation(this.rotationPitch, this.rotationYaw));
        }

    }

    @Inject(
        method = { "isPotionActive(Lnet/minecraft/potion/Potion;)Z"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void isPotionActive(Potion p_isPotionActive_1_, CallbackInfoReturnable callbackInfoReturnable) {
        AntiBlind antiBlind = (AntiBlind) LiquidBounce.moduleManager.getModule(AntiBlind.class);

        if ((p_isPotionActive_1_ == MobEffects.NAUSEA || p_isPotionActive_1_ == MobEffects.BLINDNESS) && ((AntiBlind) Objects.requireNonNull(antiBlind)).getState() && ((Boolean) antiBlind.getConfusionEffect().get()).booleanValue()) {
            callbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
        }

    }

    @Inject(
        method = { "moveRelative"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void handleRotations(float strafe, float up, float forward, float friction, CallbackInfo callbackInfo) {
        if (this == Minecraft.getMinecraft().player) {
            StrafeEvent strafeEvent = new StrafeEvent(strafe, forward, friction);

            LiquidBounce.eventManager.callEvent(strafeEvent);
            if (strafeEvent.isCancelled()) {
                callbackInfo.cancel();
            }

        }
    }
}

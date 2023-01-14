package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.Reach;
import net.ccbluex.liquidbounce.features.module.modules.render.CameraClip;
import net.ccbluex.liquidbounce.features.module.modules.render.NoHurtCam;
import net.ccbluex.liquidbounce.features.module.modules.render.Tracers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({ EntityRenderer.class})
public abstract class MixinEntityRenderer {

    @Shadow
    private Entity pointedEntity;
    @Final
    @Shadow
    private Minecraft mc;
    @Final
    @Shadow
    private float thirdPersonDistance;
    @Shadow
    private boolean cloudFog;
    @Shadow
    private float thirdPersonDistancePrev;

    @Shadow
    public abstract void loadShader(ResourceLocation resourcelocation);

    @Shadow
    public abstract void setupCameraTransform(float f, int i);

    @Inject(
        method = { "renderWorldPass"},
        at = {             @At(
                value = "FIELD",
                target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z",
                shift = Shift.BEFORE
            )}
    )
    private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new Render3DEvent(partialTicks));
    }

    @Inject(
        method = { "hurtCameraEffect"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void injectHurtCameraEffect(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(NoHurtCam.class).getState()) {
            callbackInfo.cancel();
        }

    }

    @Inject(
        method = { "orientCamera"},
        at = {             @At(
                value = "INVOKE",
                target = "Lnet/minecraft/util/math/Vec3d;distanceTo(Lnet/minecraft/util/math/Vec3d;)D"
            )},
        cancellable = true
    )
    private void cameraClip(float partialTicks, CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(CameraClip.class).getState()) {
            callbackInfo.cancel();
            Entity entity = this.mc.getRenderViewEntity();
            float f = entity.getEyeHeight();
            double d0;
            float d1;

            if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPlayerSleeping()) {
                f = (float) ((double) f + 1.0D);
                GlStateManager.translate(0.0F, 0.3F, 0.0F);
                if (!this.mc.gameSettings.debugCamEnable) {
                    BlockPos d01 = new BlockPos(entity);
                    IBlockState pitch = this.mc.world.getBlockState(d01);

                    ForgeHooksClient.orientBedCamera(this.mc.world, d01, pitch, entity);
                    GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, -1.0F, 0.0F);
                    GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
                }
            } else if (this.mc.gameSettings.thirdPersonView > 0) {
                d0 = (double) (this.thirdPersonDistancePrev + (this.thirdPersonDistance - this.thirdPersonDistancePrev) * partialTicks);
                if (this.mc.gameSettings.debugCamEnable) {
                    GlStateManager.translate(0.0F, 0.0F, (float) (-d0));
                } else {
                    d1 = entity.rotationYaw;
                    float block = entity.rotationPitch;

                    if (this.mc.gameSettings.thirdPersonView == 2) {
                        block += 180.0F;
                    }

                    if (this.mc.gameSettings.thirdPersonView == 2) {
                        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                    }

                    GlStateManager.rotate(entity.rotationPitch - block, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(entity.rotationYaw - d1, 0.0F, 1.0F, 0.0F);
                    GlStateManager.translate(0.0F, 0.0F, (float) (-d0));
                    GlStateManager.rotate(d1 - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(block - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
                }
            } else {
                GlStateManager.translate(0.0F, 0.0F, -0.1F);
            }

            if (!this.mc.gameSettings.debugCamEnable) {
                float d02 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F;
                float pitch1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;

                d1 = 0.0F;
                if (entity instanceof EntityAnimal) {
                    EntityAnimal block1 = (EntityAnimal) entity;

                    d02 = block1.prevRotationYawHead + (block1.rotationYawHead - block1.prevRotationYawHead) * partialTicks + 180.0F;
                }

                IBlockState block2 = ActiveRenderInfo.getBlockStateAtEntityViewpoint(this.mc.world, entity, partialTicks);
                CameraSetup d2 = new CameraSetup((EntityRenderer) this, entity, block2, (double) partialTicks, d02, pitch1, d1);

                MinecraftForge.EVENT_BUS.post(d2);
                GlStateManager.rotate(d2.getRoll(), 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(d2.getPitch(), 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(d2.getYaw(), 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.translate(0.0F, -f, 0.0F);
            d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks;
            double d11 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks + (double) f;
            double d21 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks;

            this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d11, d21, partialTicks);
        }

    }

    @Inject(
        method = { "setupCameraTransform"},
        at = {             @At(
                value = "INVOKE",
                target = "Lnet/minecraft/client/renderer/EntityRenderer;applyBobbing(F)V",
                shift = Shift.BEFORE
            )}
    )
    private void setupCameraViewBobbingBefore(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(Tracers.class).getState()) {
            GL11.glPushMatrix();
        }

    }

    @Inject(
        method = { "setupCameraTransform"},
        at = {             @At(
                value = "INVOKE",
                target = "Lnet/minecraft/client/renderer/EntityRenderer;applyBobbing(F)V",
                shift = Shift.AFTER
            )}
    )
    private void setupCameraViewBobbingAfter(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(Tracers.class).getState()) {
            GL11.glPopMatrix();
        }

    }

    @Overwrite
    public void getMouseOver(float partialTicks) {
        Entity entity = this.mc.getRenderViewEntity();

        if (entity != null && this.mc.world != null) {
            this.mc.profiler.startSection("pick");
            this.mc.pointedEntity = null;
            Reach reach = (Reach) LiquidBounce.moduleManager.getModule(Reach.class);
            double d0 = reach.getState() ? (double) reach.getMaxRange() : (double) this.mc.playerController.getBlockReachDistance();

            this.mc.objectMouseOver = entity.rayTrace(reach.getState() ? (double) ((Float) reach.getBuildReachValue().get()).floatValue() : d0, partialTicks);
            Vec3d vec3d = entity.getPositionEyes(partialTicks);
            boolean flag = false;
            boolean i = true;
            double d1 = d0;

            if (this.mc.playerController.extendedReach()) {
                d1 = 6.0D;
                d0 = d1;
            } else if (d0 > 3.0D) {
                flag = true;
            }

            if (this.mc.objectMouseOver != null) {
                d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3d);
            }

            if (reach.getState()) {
                d1 = (double) ((Float) reach.getCombatReachValue().get()).floatValue();
                RayTraceResult vec3d1 = entity.rayTrace(d1, partialTicks);

                if (vec3d1 != null) {
                    d1 = vec3d1.hitVec.distanceTo(vec3d);
                }
            }

            Vec3d vec3d11 = entity.getLook(1.0F);
            Vec3d vec3d2 = vec3d.add(vec3d11.x * d0, vec3d11.y * d0, vec3d11.z * d0);

            this.pointedEntity = null;
            Vec3d vec3d3 = null;
            float f = 1.0F;
            List list = this.mc.world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(vec3d11.x * d0, vec3d11.y * d0, vec3d11.z * d0).grow(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, (p_apply_1_) -> {
                return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
            }));
            double d2 = d1;
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                Entity entity1 = (Entity) iterator.next();
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow((double) entity1.getCollisionBorderSize());
                RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);

                if (axisalignedbb.contains(vec3d)) {
                    if (d2 >= 0.0D) {
                        this.pointedEntity = entity1;
                        vec3d3 = raytraceresult == null ? vec3d : raytraceresult.hitVec;
                        d2 = 0.0D;
                    }
                } else if (raytraceresult != null) {
                    double d3 = vec3d.distanceTo(raytraceresult.hitVec);

                    if (d3 < d2 || d2 == 0.0D) {
                        if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity1.canRiderInteract()) {
                            if (d2 == 0.0D) {
                                this.pointedEntity = entity1;
                                vec3d3 = raytraceresult.hitVec;
                            }
                        } else {
                            this.pointedEntity = entity1;
                            vec3d3 = raytraceresult.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }

            if (this.pointedEntity != null && flag && vec3d.distanceTo(vec3d3) > (reach.getState() ? (double) ((Float) reach.getCombatReachValue().get()).floatValue() : 3.0D)) {
                this.pointedEntity = null;
                this.mc.objectMouseOver = new RayTraceResult(Type.MISS, vec3d3, (EnumFacing) null, new BlockPos(vec3d3));
            }

            if (this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new RayTraceResult(this.pointedEntity, vec3d3);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }

            this.mc.profiler.endSection();
        }

    }
}

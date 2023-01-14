package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Arrays;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PushOutEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AntiHunger;
import net.ccbluex.liquidbounce.features.module.modules.exploit.PortalMenu;
import net.ccbluex.liquidbounce.features.module.modules.fun.Derp;
import net.ccbluex.liquidbounce.features.module.modules.movement.InventoryMove;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sneak;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.features.module.modules.render.NoSwing;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketPlayer.PositionRotation;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ReportedException;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({ EntityPlayerSP.class})
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer {

    @Shadow
    public boolean serverSprintState;
    @Shadow
    public int sprintingTicksLeft;
    @Shadow
    public float timeInPortal;
    @Shadow
    public float prevTimeInPortal;
    @Shadow
    public MovementInput movementInput;
    @Shadow
    public float horseJumpPower;
    @Shadow
    public int horseJumpPowerCounter;
    @Shadow
    @Final
    public NetHandlerPlayClient connection;
    @Shadow
    protected int sprintToggleTimer;
    @Shadow
    protected Minecraft mc;
    @Shadow
    private boolean serverSneakState;
    @Shadow
    private double lastReportedPosX;
    @Shadow
    private int positionUpdateTicks;
    @Shadow
    private double lastReportedPosY;
    @Shadow
    private double lastReportedPosZ;
    @Shadow
    private float lastReportedYaw;
    @Shadow
    private float lastReportedPitch;
    @Shadow
    private int autoJumpTime;
    @Shadow
    private boolean wasFallFlying;
    @Shadow
    private boolean prevOnGround;
    @Shadow
    private boolean autoJumpEnabled;

    @Shadow
    public abstract void playSound(SoundEvent soundevent, float f, float f1);

    @Shadow
    public abstract void setSprinting(boolean flag);

    @Shadow
    protected abstract boolean pushOutOfBlocks(double d0, double d1, double d2);

    @Shadow
    public abstract void sendPlayerAbilities();

    @Shadow
    protected abstract void sendHorseJump();

    @Shadow
    public abstract boolean isRidingHorse();

    @Shadow
    public abstract boolean isSneaking();

    @Shadow
    protected abstract boolean isCurrentViewEntity();

    @Shadow
    public abstract void closeScreen();

    @Shadow
    public abstract boolean isHandActive();

    @Shadow
    public abstract float getHorseJumpPower();

    @Shadow
    protected abstract void updateAutoJump(float f, float f1);

    @Overwrite
    public void onUpdateWalkingPlayer() {
        LiquidBounce.eventManager.callEvent(new MotionEvent(EventState.PRE));
        InventoryMove inventoryMove = (InventoryMove) LiquidBounce.moduleManager.getModule(InventoryMove.class);
        Sneak sneak = (Sneak) LiquidBounce.moduleManager.getModule(Sneak.class);
        boolean fakeSprint = inventoryMove.getState() && ((Boolean) inventoryMove.getAacAdditionProValue().get()).booleanValue() || LiquidBounce.moduleManager.getModule(AntiHunger.class).getState() || sneak.getState() && (!MovementUtils.isMoving() || !((Boolean) sneak.stopMoveValue.get()).booleanValue()) && ((String) sneak.modeValue.get()).equalsIgnoreCase("MineSecure");
        boolean clientSprintState = this.isSprinting() && !fakeSprint;

        if (clientSprintState != this.serverSprintState) {
            if (clientSprintState) {
                this.connection.sendPacket(new CPacketEntityAction((EntityPlayerSP) this, Action.START_SPRINTING));
            } else {
                this.connection.sendPacket(new CPacketEntityAction((EntityPlayerSP) this, Action.STOP_SPRINTING));
            }

            this.serverSprintState = clientSprintState;
        }

        boolean flag1 = this.isSneaking();

        if (flag1 != this.serverSneakState && (!sneak.getState() || ((String) sneak.modeValue.get()).equalsIgnoreCase("Legit"))) {
            if (flag1) {
                this.connection.sendPacket(new CPacketEntityAction((EntityPlayerSP) this, Action.START_SNEAKING));
            } else {
                this.connection.sendPacket(new CPacketEntityAction((EntityPlayerSP) this, Action.STOP_SNEAKING));
            }

            this.serverSneakState = flag1;
        }

        if (this.isCurrentViewEntity()) {
            float yaw = this.rotationYaw;
            float pitch = this.rotationPitch;
            float lastReportedYaw = RotationUtils.serverRotation.getYaw();
            float lastReportedPitch = RotationUtils.serverRotation.getPitch();
            Derp derp = (Derp) LiquidBounce.moduleManager.getModule(Derp.class);

            if (derp.getState()) {
                float[] axisalignedbb = derp.getRotation();

                yaw = axisalignedbb[0];
                pitch = axisalignedbb[1];
            }

            if (RotationUtils.targetRotation != null) {
                yaw = RotationUtils.targetRotation.getYaw();
                pitch = RotationUtils.targetRotation.getPitch();
            }

            AxisAlignedBB axisalignedbb1 = this.getEntityBoundingBox();
            double xDiff = this.posX - this.lastReportedPosX;
            double yDiff = axisalignedbb1.minY - this.lastReportedPosY;
            double zDiff = this.posZ - this.lastReportedPosZ;
            double yawDiff = (double) (yaw - lastReportedYaw);
            double pitchDiff = (double) (pitch - lastReportedPitch);

            ++this.positionUpdateTicks;
            boolean flag2 = xDiff * xDiff + yDiff * yDiff + zDiff * zDiff > 9.0E-4D || this.positionUpdateTicks >= 20;
            boolean flag3 = yawDiff != 0.0D || pitchDiff != 0.0D;

            if (this.isRiding()) {
                this.connection.sendPacket(new PositionRotation(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
                flag2 = false;
            } else if (flag2 && flag3) {
                this.connection.sendPacket(new PositionRotation(this.posX, axisalignedbb1.minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
            } else if (flag2) {
                this.connection.sendPacket(new Position(this.posX, axisalignedbb1.minY, this.posZ, this.onGround));
            } else if (flag3) {
                this.connection.sendPacket(new Rotation(this.rotationYaw, this.rotationPitch, this.onGround));
            } else if (this.prevOnGround != this.onGround) {
                this.connection.sendPacket(new CPacketPlayer(this.onGround));
            }

            if (flag2) {
                this.lastReportedPosX = this.posX;
                this.lastReportedPosY = axisalignedbb1.minY;
                this.lastReportedPosZ = this.posZ;
                this.positionUpdateTicks = 0;
            }

            if (flag3) {
                this.lastReportedYaw = this.rotationYaw;
                this.lastReportedPitch = this.rotationPitch;
            }

            this.prevOnGround = this.onGround;
            this.autoJumpEnabled = this.mc.gameSettings.autoJump;
        }

        LiquidBounce.eventManager.callEvent(new MotionEvent(EventState.POST));
    }

    @Inject(
        method = { "swingArm"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void swingItem(EnumHand hand, CallbackInfo callbackInfo) {
        NoSwing noSwing = (NoSwing) LiquidBounce.moduleManager.getModule(NoSwing.class);

        if (noSwing.getState()) {
            callbackInfo.cancel();
            if (!((Boolean) noSwing.getServerSideValue().get()).booleanValue()) {
                this.connection.sendPacket(new CPacketAnimation(hand));
            }
        }

    }

    @Inject(
        method = { "pushOutOfBlocks"},
        at = {             @At("HEAD")},
        cancellable = true
    )
    private void onPushOutOfBlocks(CallbackInfoReturnable callbackInfoReturnable) {
        PushOutEvent event = new PushOutEvent();

        if (this.noClip) {
            event.cancelEvent();
        }

        LiquidBounce.eventManager.callEvent(event);
        if (event.isCancelled()) {
            callbackInfoReturnable.setReturnValue(Boolean.valueOf(false));
        }

    }

    @Overwrite
    public void onLivingUpdate() {
        try {
            LiquidBounce.eventManager.callEvent(new UpdateEvent());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        ++this.sprintingTicksLeft;
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }

        this.prevTimeInPortal = this.timeInPortal;
        if (this.inPortal) {
            if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame() && !LiquidBounce.moduleManager.getModule(PortalMenu.class).getState()) {
                if (this.mc.currentScreen instanceof GuiContainer) {
                    this.closeScreen();
                }

                this.mc.displayGuiScreen((GuiScreen) null);
            }

            if (this.timeInPortal == 0.0F) {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_PORTAL_TRIGGER, this.rand.nextFloat() * 0.4F + 0.8F));
            }

            this.timeInPortal += 0.0125F;
            if (this.timeInPortal >= 1.0F) {
                this.timeInPortal = 1.0F;
            }

            this.inPortal = false;
        } else if (this.isPotionActive(MobEffects.NAUSEA) && this.getActivePotionEffect(MobEffects.NAUSEA).getDuration() > 60) {
            this.timeInPortal += 0.006666667F;
            if (this.timeInPortal > 1.0F) {
                this.timeInPortal = 1.0F;
            }
        } else {
            if (this.timeInPortal > 0.0F) {
                this.timeInPortal -= 0.05F;
            }

            if (this.timeInPortal < 0.0F) {
                this.timeInPortal = 0.0F;
            }
        }

        if (this.timeUntilPortal > 0) {
            --this.timeUntilPortal;
        }

        boolean flag = this.movementInput.jump;
        boolean flag1 = this.movementInput.sneak;
        float f = 0.8F;
        boolean flag2 = this.movementInput.moveForward >= 0.8F;

        this.movementInput.updatePlayerMoveState();
        NoSlow noSlow = (NoSlow) LiquidBounce.moduleManager.getModule(NoSlow.class);
        KillAura killAura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);

        ForgeHooksClient.onInputUpdate((EntityPlayerSP) this, this.movementInput);
        this.mc.getTutorial().handleMovement(this.movementInput);
        if (this.isHandActive() || this.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSword && killAura.getBlockingStatus() && !this.isRiding()) {
            SlowDownEvent flag3 = new SlowDownEvent(0.2F, 0.2F);

            LiquidBounce.eventManager.callEvent(flag3);
            this.movementInput.moveStrafe *= flag3.getStrafe();
            this.movementInput.moveForward *= flag3.getForward();
            this.sprintToggleTimer = 0;
        }

        boolean flag = false;

        if (this.autoJumpTime > 0) {
            --this.autoJumpTime;
            flag = true;
            this.movementInput.jump = true;
        }

        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
        PlayerSPPushOutOfBlocksEvent event = new PlayerSPPushOutOfBlocksEvent((EntityPlayerSP) this, axisalignedbb);

        if (!MinecraftForge.EVENT_BUS.post(event)) {
            axisalignedbb = event.getEntityBoundingBox();
            this.pushOutOfBlocks(this.posX - (double) this.width * 0.35D, axisalignedbb.minY + 0.5D, this.posZ + (double) this.width * 0.35D);
            this.pushOutOfBlocks(this.posX - (double) this.width * 0.35D, axisalignedbb.minY + 0.5D, this.posZ - (double) this.width * 0.35D);
            this.pushOutOfBlocks(this.posX + (double) this.width * 0.35D, axisalignedbb.minY + 0.5D, this.posZ - (double) this.width * 0.35D);
            this.pushOutOfBlocks(this.posX + (double) this.width * 0.35D, axisalignedbb.minY + 0.5D, this.posZ + (double) this.width * 0.35D);
        }

        Sprint sprint = (Sprint) LiquidBounce.moduleManager.getModule(Sprint.class);
        boolean flag4 = !((Boolean) sprint.foodValue.get()).booleanValue() || (float) this.getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying;

        if (this.onGround && !flag1 && !flag2 && this.movementInput.moveForward >= 0.8F && !this.isSprinting() && flag4 && !this.isHandActive() && !this.isPotionActive(MobEffects.BLINDNESS)) {
            if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                this.sprintToggleTimer = 7;
            } else {
                this.setSprinting(true);
            }
        }

        if (!this.isSprinting() && this.movementInput.moveForward >= 0.8F && flag4 && (noSlow.getState() || !this.isHandActive()) && !this.isPotionActive(MobEffects.BLINDNESS) && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.setSprinting(true);
        }

        Scaffold scaffold = (Scaffold) LiquidBounce.moduleManager.getModule(Scaffold.class);

        if (scaffold.getState() && !((Boolean) scaffold.sprintValue.get()).booleanValue() || sprint.getState() && ((Boolean) sprint.checkServerSide.get()).booleanValue() && (this.onGround || !((Boolean) sprint.checkServerSideGround.get()).booleanValue()) && !((Boolean) sprint.allDirectionsValue.get()).booleanValue() && RotationUtils.targetRotation != null && RotationUtils.getRotationDifference(new net.ccbluex.liquidbounce.utils.Rotation(this.mc.player.rotationYaw, this.mc.player.rotationPitch)) > 30.0D) {
            this.setSprinting(false);
        }

        if (this.isSprinting() && (this.movementInput.moveForward < 0.8F || this.collidedHorizontally || !flag4)) {
            this.setSprinting(false);
        }

        if (this.capabilities.allowFlying) {
            if (this.mc.playerController.isSpectatorMode()) {
                if (!this.capabilities.isFlying) {
                    this.capabilities.isFlying = true;
                    this.sendPlayerAbilities();
                }
            } else if (!flag && this.movementInput.jump && !flag) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = 7;
                } else {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }

        if (this.movementInput.jump && !flag && !this.onGround && this.motionY < 0.0D && !this.isElytraFlying() && !this.capabilities.isFlying) {
            ItemStack ijumpingmount = this.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

            if (ijumpingmount.getItem() == Items.ELYTRA && ItemElytra.isUsable(ijumpingmount)) {
                this.connection.sendPacket(new CPacketEntityAction((EntityPlayerSP) this, Action.START_FALL_FLYING));
            }
        }

        this.wasFallFlying = this.isElytraFlying();
        if (this.capabilities.isFlying && this.isCurrentViewEntity()) {
            if (this.movementInput.sneak) {
                this.movementInput.moveStrafe = (float) ((double) this.movementInput.moveStrafe / 0.3D);
                this.movementInput.moveForward = (float) ((double) this.movementInput.moveForward / 0.3D);
                this.motionY -= (double) (this.capabilities.getFlySpeed() * 3.0F);
            }

            if (this.movementInput.jump) {
                this.motionY += (double) (this.capabilities.getFlySpeed() * 3.0F);
            }
        }

        if (this.isRidingHorse()) {
            IJumpingMount ijumpingmount = (IJumpingMount) this.getRidingEntity();

            if (this.horseJumpPowerCounter < 0) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter == 0) {
                    this.horseJumpPower = 0.0F;
                }
            }

            if (flag && !this.movementInput.jump) {
                this.horseJumpPowerCounter = -10;
                ijumpingmount.setJumpPower(MathHelper.floor(this.getHorseJumpPower() * 100.0F));
                this.sendHorseJump();
            } else if (!flag && this.movementInput.jump) {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0F;
            } else if (flag) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter < 10) {
                    this.horseJumpPower = (float) this.horseJumpPowerCounter * 0.1F;
                } else {
                    this.horseJumpPower = 0.8F + 2.0F / (float) (this.horseJumpPowerCounter - 9) * 0.1F;
                }
            }
        } else {
            this.horseJumpPower = 0.0F;
        }

        super.onLivingUpdate();
        if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
        }

    }

    @Overwrite
    public void move(MoverType type, double x, double y, double z) {
        MoveEvent moveEvent = new MoveEvent(x, y, z);

        LiquidBounce.eventManager.callEvent(moveEvent);
        if (!moveEvent.isCancelled()) {
            x = moveEvent.getX();
            y = moveEvent.getY();
            z = moveEvent.getZ();
            if (this.noClip) {
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
                this.resetPositionToBB();
            } else {
                if (type == MoverType.PISTON) {
                    long d10 = this.world.getTotalWorldTime();

                    if (d10 != this.pistonDeltasGameTime) {
                        Arrays.fill(this.pistonDeltas, 0.0D);
                        this.pistonDeltasGameTime = d10;
                    }

                    int d11;
                    double d13;

                    if (x != 0.0D) {
                        d11 = Axis.X.ordinal();
                        d13 = MathHelper.clamp(x + this.pistonDeltas[d11], -0.51D, 0.51D);
                        x = d13 - this.pistonDeltas[d11];
                        this.pistonDeltas[d11] = d13;
                        if (Math.abs(x) <= 9.999999747378752E-6D) {
                            return;
                        }
                    } else if (y != 0.0D) {
                        d11 = Axis.Y.ordinal();
                        d13 = MathHelper.clamp(y + this.pistonDeltas[d11], -0.51D, 0.51D);
                        y = d13 - this.pistonDeltas[d11];
                        this.pistonDeltas[d11] = d13;
                        if (Math.abs(y) <= 9.999999747378752E-6D) {
                            return;
                        }
                    } else {
                        if (z == 0.0D) {
                            return;
                        }

                        d11 = Axis.Z.ordinal();
                        d13 = MathHelper.clamp(z + this.pistonDeltas[d11], -0.51D, 0.51D);
                        z = d13 - this.pistonDeltas[d11];
                        this.pistonDeltas[d11] = d13;
                        if (Math.abs(z) <= 9.999999747378752E-6D) {
                            return;
                        }
                    }
                }

                this.world.profiler.startSection("move");
                double d0 = this.posX;
                double d1 = this.posY;
                double d1 = this.posZ;

                if (this.isInWeb) {
                    this.isInWeb = false;
                    x *= 0.25D;
                    y *= 0.05000000074505806D;
                    z *= 0.25D;
                    this.motionX = 0.0D;
                    this.motionY = 0.0D;
                    this.motionZ = 0.0D;
                }

                double d2 = x;
                double d3 = y;
                double d4 = z;

                if ((type == MoverType.SELF || type == MoverType.PLAYER) && (this.onGround && this.isSneaking() || moveEvent.isSafeWalk()) && this instanceof EntityPlayer) {
                    for (double list1 = 0.05D; x != 0.0D && this.world.getCollisionBoxes((EntityPlayerSP) this, this.getEntityBoundingBox().offset(x, (double) (-this.stepHeight), 0.0D)).isEmpty(); d2 = x) {
                        if (x < 0.05D && x >= -0.05D) {
                            x = 0.0D;
                        } else if (x > 0.0D) {
                            x -= 0.05D;
                        } else {
                            x += 0.05D;
                        }
                    }

                    for (; z != 0.0D && this.world.getCollisionBoxes((EntityPlayerSP) this, this.getEntityBoundingBox().offset(0.0D, (double) (-this.stepHeight), z)).isEmpty(); d4 = z) {
                        if (z < 0.05D && z >= -0.05D) {
                            z = 0.0D;
                        } else if (z > 0.0D) {
                            z -= 0.05D;
                        } else {
                            z += 0.05D;
                        }
                    }

                    for (; x != 0.0D && z != 0.0D && this.world.getCollisionBoxes((EntityPlayerSP) this, this.getEntityBoundingBox().offset(x, (double) (-this.stepHeight), z)).isEmpty(); d4 = z) {
                        if (x < 0.05D && x >= -0.05D) {
                            x = 0.0D;
                        } else if (x > 0.0D) {
                            x -= 0.05D;
                        } else {
                            x += 0.05D;
                        }

                        d2 = x;
                        if (z < 0.05D && z >= -0.05D) {
                            z = 0.0D;
                        } else if (z > 0.0D) {
                            z -= 0.05D;
                        } else {
                            z += 0.05D;
                        }
                    }
                }

                List list = this.world.getCollisionBoxes((EntityPlayerSP) this, this.getEntityBoundingBox().expand(x, y, z));
                AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
                int flag;
                int j6;

                if (y != 0.0D) {
                    flag = 0;

                    for (j6 = list.size(); flag < j6; ++flag) {
                        y = ((AxisAlignedBB) list.get(flag)).calculateYOffset(this.getEntityBoundingBox(), y);
                    }

                    this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));
                }

                if (x != 0.0D) {
                    flag = 0;

                    for (j6 = list.size(); flag < j6; ++flag) {
                        x = ((AxisAlignedBB) list.get(flag)).calculateXOffset(this.getEntityBoundingBox(), x);
                    }

                    if (x != 0.0D) {
                        this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, 0.0D, 0.0D));
                    }
                }

                if (z != 0.0D) {
                    flag = 0;

                    for (j6 = list.size(); flag < j6; ++flag) {
                        z = ((AxisAlignedBB) list.get(flag)).calculateZOffset(this.getEntityBoundingBox(), z);
                    }

                    if (z != 0.0D) {
                        this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, 0.0D, z));
                    }
                }

                boolean flag = this.onGround || y != y && y < 0.0D;

                if (this.stepHeight > 0.0F && flag && (d2 != x || d4 != z)) {
                    StepEvent stepevent = new StepEvent(this.stepHeight);

                    LiquidBounce.eventManager.callEvent(stepevent);
                    double i1 = x;
                    double blockpos = y;
                    double block = z;
                    AxisAlignedBB crashreport = this.getEntityBoundingBox();

                    this.setEntityBoundingBox(axisalignedbb);
                    y = (double) stepevent.getStepHeight();
                    List crashreportcategory = this.world.getCollisionBoxes((EntityPlayerSP) this, this.getEntityBoundingBox().expand(d2, y, d4));
                    AxisAlignedBB axisalignedbb2 = this.getEntityBoundingBox();
                    AxisAlignedBB d17 = axisalignedbb2.expand(d2, 0.0D, d4);
                    double d8 = y;
                    int f = 0;

                    for (int f1 = crashreportcategory.size(); f < f1; ++f) {
                        d8 = ((AxisAlignedBB) crashreportcategory.get(f)).calculateYOffset(d17, d8);
                    }

                    axisalignedbb2 = axisalignedbb2.offset(0.0D, d8, 0.0D);
                    double d2 = d2;
                    int l1 = 0;

                    for (int d19 = crashreportcategory.size(); l1 < d19; ++l1) {
                        d2 = ((AxisAlignedBB) crashreportcategory.get(l1)).calculateXOffset(axisalignedbb2, d2);
                    }

                    axisalignedbb2 = axisalignedbb2.offset(d2, 0.0D, 0.0D);
                    double d3 = d4;
                    int j2 = 0;

                    for (int axisalignedbb4 = crashreportcategory.size(); j2 < axisalignedbb4; ++j2) {
                        d3 = ((AxisAlignedBB) crashreportcategory.get(j2)).calculateZOffset(axisalignedbb2, d3);
                    }

                    axisalignedbb2 = axisalignedbb2.offset(0.0D, 0.0D, d3);
                    AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
                    double d20 = y;
                    int l2 = 0;

                    for (int d21 = crashreportcategory.size(); l2 < d21; ++l2) {
                        d20 = ((AxisAlignedBB) crashreportcategory.get(l2)).calculateYOffset(axisalignedbb, d20);
                    }

                    axisalignedbb = axisalignedbb.offset(0.0D, d20, 0.0D);
                    double d4 = d2;
                    int j3 = 0;

                    for (int d22 = crashreportcategory.size(); j3 < d22; ++j3) {
                        d4 = ((AxisAlignedBB) crashreportcategory.get(j3)).calculateXOffset(axisalignedbb, d4);
                    }

                    axisalignedbb = axisalignedbb.offset(d4, 0.0D, 0.0D);
                    double d5 = d4;
                    int l3 = 0;

                    for (int d23 = crashreportcategory.size(); l3 < d23; ++l3) {
                        d5 = ((AxisAlignedBB) crashreportcategory.get(l3)).calculateZOffset(axisalignedbb, d5);
                    }

                    axisalignedbb = axisalignedbb.offset(0.0D, 0.0D, d5);
                    double d6 = d2 * d2 + d3 * d3;
                    double d9 = d4 * d4 + d5 * d5;

                    if (d6 > d9) {
                        x = d2;
                        z = d3;
                        y = -d8;
                        this.setEntityBoundingBox(axisalignedbb2);
                    } else {
                        x = d4;
                        z = d5;
                        y = -d20;
                        this.setEntityBoundingBox(axisalignedbb);
                    }

                    int j4 = 0;

                    for (int k4 = crashreportcategory.size(); j4 < k4; ++j4) {
                        y = ((AxisAlignedBB) crashreportcategory.get(j4)).calculateYOffset(this.getEntityBoundingBox(), y);
                    }

                    this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));
                    if (i1 * i1 + block * block >= x * x + z * z) {
                        x = i1;
                        y = blockpos;
                        z = block;
                        this.setEntityBoundingBox(crashreport);
                    } else {
                        LiquidBounce.eventManager.callEvent(new StepConfirmEvent());
                    }
                }

                this.world.profiler.endSection();
                this.world.profiler.startSection("rest");
                this.resetPositionToBB();
                this.collidedHorizontally = d2 != x || d4 != z;
                this.collidedVertically = y != y;
                this.onGround = this.collidedVertically && d3 < 0.0D;
                this.collided = this.collidedHorizontally || this.collidedVertically;
                j6 = MathHelper.floor(this.posX);
                int i = MathHelper.floor(this.posY - 0.20000000298023224D);
                int k6 = MathHelper.floor(this.posZ);
                BlockPos blockpos = new BlockPos(j6, i, k6);
                IBlockState iblockstate = this.world.getBlockState(blockpos);

                if (iblockstate.getMaterial() == Material.AIR) {
                    BlockPos blockpos1 = blockpos.down();
                    IBlockState flag1 = this.world.getBlockState(blockpos1);
                    Block block = flag1.getBlock();

                    if (block instanceof BlockFence || block instanceof BlockWall || block instanceof BlockFenceGate) {
                        iblockstate = flag1;
                        blockpos = blockpos1;
                    }
                }

                this.updateFallState(y, this.onGround, iblockstate, blockpos);
                if (d2 != x) {
                    this.motionX = 0.0D;
                }

                if (d4 != z) {
                    this.motionZ = 0.0D;
                }

                Block block1 = iblockstate.getBlock();

                if (d3 != y) {
                    block1.onLanded(this.world, (EntityPlayerSP) this);
                }

                if (this.canTriggerWalking() && (!this.onGround || !this.isSneaking() || !(this instanceof EntityPlayer)) && !this.isRiding()) {
                    double d7 = this.posX - d0;
                    double d8 = this.posY - d1;
                    double d9 = this.posZ - d1;

                    if (block1 != Blocks.LADDER) {
                        d8 = 0.0D;
                    }

                    if (block1 != null && this.onGround) {
                        block1.onEntityWalk(this.world, blockpos, (EntityPlayerSP) this);
                    }

                    this.distanceWalkedModified = (float) ((double) this.distanceWalkedModified + (double) MathHelper.sqrt(d7 * d7 + d9 * d9) * 0.6D);
                    this.distanceWalkedOnStepModified = (float) ((double) this.distanceWalkedOnStepModified + (double) MathHelper.sqrt(d7 * d7 + d8 * d8 + d9 * d9) * 0.6D);
                    if (this.distanceWalkedOnStepModified > (float) this.nextStepDistance && iblockstate.getMaterial() != Material.AIR) {
                        this.nextStepDistance = (int) this.distanceWalkedOnStepModified + 1;
                        if (this.isInWater()) {
                            Object entity = this.isBeingRidden() && this.getControllingPassenger() != null ? this.getControllingPassenger() : (EntityPlayerSP) this;
                            float f = entity == this ? 0.35F : 0.4F;
                            float f1 = MathHelper.sqrt(((Entity) entity).motionX * ((Entity) entity).motionX * 0.20000000298023224D + ((Entity) entity).motionY * ((Entity) entity).motionY + ((Entity) entity).motionZ * ((Entity) entity).motionZ * 0.20000000298023224D) * f;

                            if (f1 > 1.0F) {
                                f1 = 1.0F;
                            }

                            this.playSound(this.getSwimSound(), f1, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
                        } else {
                            this.playStepSound(blockpos, block1);
                        }
                    } else if (this.distanceWalkedOnStepModified > this.nextFlap && this.makeFlySound() && iblockstate.getMaterial() == Material.AIR) {
                        this.nextFlap = this.playFlySound(this.distanceWalkedOnStepModified);
                    }
                }

                try {
                    this.doBlockCollisions();
                } catch (Throwable throwable) {
                    CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
                    CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");

                    this.addEntityCrashInfo(crashreportcategory);
                    throw new ReportedException(crashreport);
                }

                boolean flag1 = this.isWet();

                if (this.world.isFlammableWithin(this.getEntityBoundingBox().shrink(0.001D))) {
                    this.dealFireDamage(1);
                    if (!flag1) {
                        ++this.fire;
                        if (this.fire == 0) {
                            this.setFire(8);
                        }
                    }
                } else if (this.fire <= 0) {
                    this.fire = -this.getFireImmuneTicks();
                }

                if (flag1 && this.isBurning()) {
                    this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
                    this.fire = -this.getFireImmuneTicks();
                }

                this.world.profiler.endSection();
            }

            this.updateAutoJump((float) (this.posX - this.posX), (float) (this.posZ - this.posZ));
        }
    }
}

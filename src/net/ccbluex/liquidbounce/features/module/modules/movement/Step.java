package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.StatType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Phase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Step",
    description = "Allows you to step up blocks.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001a\u001a\u00020\bH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u001cH\u0016J\u0010\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020&H\u0007J\u0010\u0010\'\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020(H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/Step;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "heightValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "isAACStep", "", "isStep", "jumpHeightValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "ncpNextStep", "", "spartanSwitch", "stepX", "", "stepY", "stepZ", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "couldStep", "fakeJump", "", "onDisable", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onStepConfirm", "Lnet/ccbluex/liquidbounce/event/StepConfirmEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Step extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Vanilla", "Jump", "NCP", "MotionNCP", "OldNCP", "AAC", "LAAC", "AAC3.3.4", "Spartan", "Rewinside"}, "NCP");
    private final FloatValue heightValue = new FloatValue("Height", 1.0F, 0.6F, 10.0F);
    private final FloatValue jumpHeightValue = new FloatValue("JumpHeight", 0.42F, 0.37F, 0.42F);
    private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, 500);
    private boolean isStep;
    private double stepX;
    private double stepY;
    private double stepZ;
    private int ncpNextStep;
    private boolean spartanSwitch;
    private boolean isAACStep;
    private final MSTimer timer = new MSTimer();

    public void onDisable() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            thePlayer.setStepHeight(0.5F);
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String mode = (String) this.modeValue.get();
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (StringsKt.equals(mode, "jump", true) && thePlayer.isCollidedHorizontally() && thePlayer.getOnGround() && !MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                this.fakeJump();
                thePlayer.setMotionY((double) ((Number) this.jumpHeightValue.get()).floatValue());
            } else if (StringsKt.equals(mode, "laac", true)) {
                if (thePlayer.isCollidedHorizontally() && !thePlayer.isOnLadder() && !thePlayer.isInWater() && !thePlayer.isInLava() && !thePlayer.isInWeb()) {
                    if (thePlayer.getOnGround() && this.timer.hasTimePassed((long) ((Number) this.delayValue.get()).intValue())) {
                        this.isStep = true;
                        this.fakeJump();
                        thePlayer.setMotionY(thePlayer.getMotionY() + 0.620000001490116D);
                        float f = thePlayer.getRotationYaw() * 0.017453292F;
                        double d0 = thePlayer.getMotionX();
                        boolean flag = false;
                        float f = (float) Math.sin((double) f);

                        thePlayer.setMotionX(d0 - (double) f * 0.2D);
                        d0 = thePlayer.getMotionZ();
                        flag = false;
                        f = (float) Math.cos((double) f);
                        thePlayer.setMotionZ(d0 + (double) f * 0.2D);
                        this.timer.reset();
                    }

                    thePlayer.setOnGround(true);
                } else {
                    this.isStep = false;
                }
            } else if (StringsKt.equals(mode, "aac3.3.4", true)) {
                if (thePlayer.isCollidedHorizontally() && MovementUtils.isMoving()) {
                    if (thePlayer.getOnGround() && this.couldStep()) {
                        thePlayer.setMotionX(thePlayer.getMotionX() * 1.26D);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.26D);
                        thePlayer.jump();
                        this.isAACStep = true;
                    }

                    if (this.isAACStep) {
                        thePlayer.setMotionY(thePlayer.getMotionY() - 0.015D);
                        if (!thePlayer.isUsingItem() && thePlayer.getMovementInput().getMoveStrafe() == 0.0F) {
                            thePlayer.setJumpMovementFactor(0.3F);
                        }
                    }
                } else {
                    this.isAACStep = false;
                }
            }

        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String mode = (String) this.modeValue.get();
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            if (StringsKt.equals(mode, "motionncp", true) && thePlayer.isCollidedHorizontally() && !MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                if (thePlayer.getOnGround() && this.couldStep()) {
                    this.fakeJump();
                    thePlayer.setMotionY(0.0D);
                    event.setY(0.41999998688698D);
                    this.ncpNextStep = 1;
                } else if (this.ncpNextStep == 1) {
                    event.setY(0.33319999363422D);
                    this.ncpNextStep = 2;
                } else if (this.ncpNextStep == 2) {
                    double yaw = MovementUtils.getDirection();

                    event.setY(0.24813599859094704D);
                    boolean flag = false;
                    double d0 = Math.sin(yaw);

                    event.setX(-d0 * 0.7D);
                    flag = false;
                    d0 = Math.cos(yaw);
                    event.setZ(d0 * 0.7D);
                    this.ncpNextStep = 0;
                }
            }

        }
    }

    @EventTarget
    public final void onStep(@NotNull StepEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(Phase.class);

            if (module == null) {
                Intrinsics.throwNpe();
            }

            if (module.getState()) {
                event.setStepHeight(0.0F);
            } else {
                module = LiquidBounce.INSTANCE.getModuleManager().get(Fly.class);
                if (module == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.Fly");
                } else {
                    Fly fly = (Fly) module;
                    String mode;

                    if (fly.getState()) {
                        mode = (String) fly.getModeValue().get();
                        if (StringsKt.equals(mode, "Hypixel", true) || StringsKt.equals(mode, "OtherHypixel", true) || StringsKt.equals(mode, "LatestHypixel", true) || StringsKt.equals(mode, "Rewinside", true) || StringsKt.equals(mode, "Mineplex", true) && thePlayer.getInventory().getCurrentItemInHand() == null) {
                            event.setStepHeight(0.0F);
                            return;
                        }
                    }

                    mode = (String) this.modeValue.get();
                    if (thePlayer.getOnGround() && this.timer.hasTimePassed((long) ((Number) this.delayValue.get()).intValue()) && !StringsKt.equals(mode, "Jump", true) && !StringsKt.equals(mode, "MotionNCP", true) && !StringsKt.equals(mode, "LAAC", true) && !StringsKt.equals(mode, "AAC3.3.4", true)) {
                        float height = ((Number) this.heightValue.get()).floatValue();

                        thePlayer.setStepHeight(height);
                        event.setStepHeight(height);
                        if (event.getStepHeight() > 0.5F) {
                            this.isStep = true;
                            this.stepX = thePlayer.getPosX();
                            this.stepY = thePlayer.getPosY();
                            this.stepZ = thePlayer.getPosZ();
                        }

                    } else {
                        thePlayer.setStepHeight(0.5F);
                        event.setStepHeight(0.5F);
                    }
                }
            }
        }
    }

    @EventTarget(
        ignoreCondition = true
    )
    public final void onStepConfirm(@NotNull StepConfirmEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();

        if (thePlayer != null && this.isStep) {
            if (thePlayer.getEntityBoundingBox().getMinY() - this.stepY > 0.5D) {
                String mode = (String) this.modeValue.get();

                if (!StringsKt.equals(mode, "NCP", true) && !StringsKt.equals(mode, "AAC", true)) {
                    if (StringsKt.equals(mode, "Spartan", true)) {
                        this.fakeJump();
                        if (this.spartanSwitch) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698D, this.stepZ, false));
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212D, this.stepZ, false));
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 1.001335979112147D, this.stepZ, false));
                        } else {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.6D, this.stepZ, false));
                        }

                        this.spartanSwitch = !this.spartanSwitch;
                        this.timer.reset();
                    } else if (StringsKt.equals(mode, "Rewinside", true)) {
                        this.fakeJump();
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698D, this.stepZ, false));
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212D, this.stepZ, false));
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 1.001335979112147D, this.stepZ, false));
                        this.timer.reset();
                    }
                } else {
                    this.fakeJump();
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.41999998688698D, this.stepZ, false));
                    MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(this.stepX, this.stepY + 0.7531999805212D, this.stepZ, false));
                    this.timer.reset();
                }
            }

            this.isStep = false;
            this.stepX = 0.0D;
            this.stepY = 0.0D;
            this.stepZ = 0.0D;
        }
    }

    @EventTarget(
        ignoreCondition = true
    )
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();

        if (MinecraftInstance.classProvider.isCPacketPlayer(packet) && this.isStep && StringsKt.equals((String) this.modeValue.get(), "OldNCP", true)) {
            ICPacketPlayer icpacketplayer = packet.asCPacketPlayer();

            icpacketplayer.setY(icpacketplayer.getY() + 0.07D);
            this.isStep = false;
        }

    }

    private final void fakeJump() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            thePlayer.setAirBorne(true);
            thePlayer.triggerAchievement(MinecraftInstance.classProvider.getStatEnum(StatType.JUMP_STAT));
        }
    }

    private final boolean couldStep() {
        double yaw = MovementUtils.getDirection();
        boolean z = false;
        double x = -Math.sin(yaw) * 0.4D;
        boolean flag = false;
        double z1 = Math.cos(yaw) * 0.4D;
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        return iworldclient.getCollisionBoxes(ientityplayersp.getEntityBoundingBox().offset(x, 1.001335979112147D, z1)).isEmpty();
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}

package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.StatType;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WMathHelper;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3i;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.render.BlockOverlay;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PlaceRotation;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "Tower",
    description = "Automatically builds a tower beneath you.",
    category = ModuleCategory.WORLD,
    keyBind = 24
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010+\u001a\u00020,H\u0002J\b\u0010-\u001a\u00020,H\u0002J\b\u0010.\u001a\u00020,H\u0016J\b\u0010/\u001a\u00020,H\u0016J\u0010\u00100\u001a\u00020,2\u0006\u00101\u001a\u000202H\u0007J\u0010\u00103\u001a\u00020,2\u0006\u00101\u001a\u000204H\u0007J\u0010\u00105\u001a\u00020,2\u0006\u00101\u001a\u000206H\u0007J\u0010\u00107\u001a\u00020,2\u0006\u00101\u001a\u000208H\u0007J\b\u00109\u001a\u00020,H\u0002J\u0010\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010 \u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u000e\u0010$\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006>"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/world/Tower;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoBlockValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "blocksAmount", "", "getBlocksAmount", "()I", "constantMotionJumpGroundValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "constantMotionValue", "counterDisplayValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "jumpDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "jumpGround", "", "jumpMotionValue", "keepRotationValue", "lockRotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "matrixValue", "modeValue", "onJumpValue", "placeInfo", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "placeModeValue", "rotationsValue", "slot", "stopWhenBlockAbove", "swingValue", "tag", "", "getTag", "()Ljava/lang/String;", "teleportDelayValue", "teleportGroundValue", "teleportHeightValue", "teleportNoMotionValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "timerValue", "fakeJump", "", "move", "onDisable", "onEnable", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "place", "search", "", "blockPosition", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "LiquidBounce"}
)
public final class Tower extends Module {

    private final ListValue modeValue = new ListValue("Mode", new String[] { "Jump", "Motion", "ConstantMotion", "MotionTP", "Packet", "Teleport", "AAC3.3.9", "AAC3.6.4"}, "Motion");
    private final ListValue autoBlockValue = new ListValue("AutoBlock", new String[] { "Off", "Pick", "Spoof", "Switch"}, "Spoof");
    private final BoolValue swingValue = new BoolValue("Swing", true);
    private final BoolValue stopWhenBlockAbove = new BoolValue("StopWhenBlockAbove", false);
    private final BoolValue rotationsValue = new BoolValue("Rotations", true);
    private final BoolValue keepRotationValue = new BoolValue("KeepRotation", false);
    private final BoolValue onJumpValue = new BoolValue("OnJump", false);
    private final BoolValue matrixValue = new BoolValue("Matrix", false);
    private final ListValue placeModeValue = new ListValue("PlaceTiming", new String[] { "Pre", "Post"}, "Post");
    private final FloatValue timerValue = new FloatValue("Timer", 1.0F, 0.01F, 10.0F);
    private final FloatValue jumpMotionValue = new FloatValue("JumpMotion", 0.42F, 0.3681289F, 0.79F);
    private final IntegerValue jumpDelayValue = new IntegerValue("JumpDelay", 0, 0, 20);
    private final FloatValue constantMotionValue = new FloatValue("ConstantMotion", 0.42F, 0.1F, 1.0F);
    private final FloatValue constantMotionJumpGroundValue = new FloatValue("ConstantMotionJumpGround", 0.79F, 0.76F, 1.0F);
    private final FloatValue teleportHeightValue = new FloatValue("TeleportHeight", 1.15F, 0.1F, 5.0F);
    private final IntegerValue teleportDelayValue = new IntegerValue("TeleportDelay", 0, 0, 20);
    private final BoolValue teleportGroundValue = new BoolValue("TeleportGround", true);
    private final BoolValue teleportNoMotionValue = new BoolValue("TeleportNoMotion", false);
    private final BoolValue counterDisplayValue = new BoolValue("Counter", true);
    private PlaceInfo placeInfo;
    private Rotation lockRotation;
    private final TickTimer timer = new TickTimer();
    private double jumpGround;
    private int slot;

    public void onEnable() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            this.slot = thePlayer.getInventory().getCurrentItem();
        }
    }

    public void onDisable() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
            this.lockRotation = (Rotation) null;
            if (this.slot != thePlayer.getInventory().getCurrentItem()) {
                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
            }

        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (!((Boolean) this.onJumpValue.get()).booleanValue() || MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp != null) {
                IEntityPlayerSP thePlayer = ientityplayersp;

                if (((Boolean) this.rotationsValue.get()).booleanValue() && ((Boolean) this.keepRotationValue.get()).booleanValue() && this.lockRotation != null) {
                    RotationUtils.setTargetRotation(this.lockRotation);
                }

                MinecraftInstance.mc.getTimer().setTimerSpeed(((Number) this.timerValue.get()).floatValue());
                EventState eventState = event.getEventState();

                if (StringsKt.equals((String) this.placeModeValue.get(), eventState.getStateName(), true)) {
                    this.place();
                }

                if (eventState == EventState.PRE) {
                    this.placeInfo = (PlaceInfo) null;
                    this.timer.update();
                    IClassProvider iclassprovider;
                    boolean flag;
                    IItemStack iitemstack;

                    if (!StringsKt.equals((String) this.autoBlockValue.get(), "Off", true)) {
                        label78: {
                            if (InventoryUtils.findAutoBlockBlock() == -1) {
                                label76: {
                                    if (thePlayer.getHeldItem() != null) {
                                        iclassprovider = MinecraftInstance.classProvider;
                                        iitemstack = thePlayer.getHeldItem();
                                        if (iitemstack == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        if (iclassprovider.isItemBlock(iitemstack.getItem())) {
                                            break label76;
                                        }
                                    }

                                    flag = false;
                                    break label78;
                                }
                            }

                            flag = true;
                        }
                    } else {
                        label70: {
                            if (thePlayer.getHeldItem() != null) {
                                iclassprovider = MinecraftInstance.classProvider;
                                iitemstack = thePlayer.getHeldItem();
                                if (iitemstack == null) {
                                    Intrinsics.throwNpe();
                                }

                                if (iclassprovider.isItemBlock(iitemstack.getItem())) {
                                    flag = true;
                                    break label70;
                                }
                            }

                            flag = false;
                        }
                    }

                    boolean update = flag;

                    if (update) {
                        if (!((Boolean) this.stopWhenBlockAbove.get()).booleanValue() || MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + (double) 2, thePlayer.getPosZ())))) {
                            this.move();
                        }

                        WBlockPos blockPos = new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - 1.0D, thePlayer.getPosZ());

                        iclassprovider = MinecraftInstance.classProvider;
                        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                        if (iworldclient == null) {
                            Intrinsics.throwNpe();
                        }

                        if (iclassprovider.isBlockAir(iworldclient.getBlockState(blockPos).getBlock()) && this.search(blockPos) && ((Boolean) this.rotationsValue.get()).booleanValue()) {
                            VecRotation vecRotation = RotationUtils.faceBlock(blockPos);

                            if (vecRotation != null) {
                                RotationUtils.setTargetRotation(vecRotation.getRotation());
                                PlaceInfo placeinfo = this.placeInfo;

                                if (this.placeInfo == null) {
                                    Intrinsics.throwNpe();
                                }

                                placeinfo.setVec3(vecRotation.getVec());
                            }
                        }
                    }
                }

            }
        }
    }

    private final void fakeJump() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        ientityplayersp.setAirBorne(true);
        ientityplayersp = MinecraftInstance.mc.getThePlayer();
        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        ientityplayersp.triggerAchievement(MinecraftInstance.classProvider.getStatEnum(StatType.JUMP_STAT));
    }

    private final void move() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            String s = (String) this.modeValue.get();
            boolean flag = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                switch (s.hashCode()) {
                case -1360201941:
                    if (s.equals("teleport")) {
                        if (((Boolean) this.teleportNoMotionValue.get()).booleanValue()) {
                            thePlayer.setMotionY(0.0D);
                        }

                        if ((thePlayer.getOnGround() || !((Boolean) this.teleportGroundValue.get()).booleanValue()) && this.timer.hasTimePassed(((Number) this.teleportDelayValue.get()).intValue())) {
                            this.fakeJump();
                            thePlayer.setPositionAndUpdate(thePlayer.getPosX(), thePlayer.getPosY() + ((Number) this.teleportHeightValue.get()).doubleValue(), thePlayer.getPosZ());
                            this.timer.reset();
                        }
                    }
                    break;

                case -1068318794:
                    if (s.equals("motion")) {
                        if (thePlayer.getOnGround()) {
                            this.fakeJump();
                            thePlayer.setMotionY(0.42D);
                        } else if (thePlayer.getMotionY() < 0.1D) {
                            thePlayer.setMotionY(-0.3D);
                        }
                    }
                    break;

                case -995865464:
                    if (s.equals("packet") && thePlayer.getOnGround() && this.timer.hasTimePassed(2)) {
                        this.fakeJump();
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.42D, thePlayer.getPosZ(), false));
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.753D, thePlayer.getPosZ(), false));
                        thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + 1.0D, thePlayer.getPosZ());
                        this.timer.reset();
                    }
                    break;

                case -157173582:
                    if (s.equals("motiontp")) {
                        if (thePlayer.getOnGround()) {
                            this.fakeJump();
                            thePlayer.setMotionY(0.42D);
                        } else if (thePlayer.getMotionY() < 0.23D) {
                            thePlayer.setPosition(thePlayer.getPosX(), MathKt.truncate(thePlayer.getPosY()), thePlayer.getPosZ());
                        }
                    }
                    break;

                case 3273774:
                    if (s.equals("jump") && thePlayer.getOnGround() && this.timer.hasTimePassed(((Number) this.jumpDelayValue.get()).intValue())) {
                        this.fakeJump();
                        thePlayer.setMotionY((double) ((Number) this.jumpMotionValue.get()).floatValue());
                        this.timer.reset();
                    }
                    break;

                case 325228192:
                    if (s.equals("aac3.3.9")) {
                        if (thePlayer.getOnGround()) {
                            this.fakeJump();
                            thePlayer.setMotionY(0.4001D);
                        }

                        MinecraftInstance.mc.getTimer().setTimerSpeed(1.0F);
                        if (thePlayer.getMotionY() < (double) 0) {
                            thePlayer.setMotionY(thePlayer.getMotionY() - 9.45E-6D);
                            MinecraftInstance.mc.getTimer().setTimerSpeed(1.6F);
                        }
                    }
                    break;

                case 325231070:
                    if (s.equals("aac3.6.4")) {
                        if (thePlayer.getTicksExisted() % 4 == 1) {
                            thePlayer.setMotionY(0.4195464D);
                            thePlayer.setPosition(thePlayer.getPosX() - 0.035D, thePlayer.getPosY(), thePlayer.getPosZ());
                        } else if (thePlayer.getTicksExisted() % 4 == 0) {
                            thePlayer.setMotionY(-0.5D);
                            thePlayer.setPosition(thePlayer.getPosX() + 0.035D, thePlayer.getPosY(), thePlayer.getPosZ());
                        }
                    }
                    break;

                case 792877146:
                    if (s.equals("constantmotion")) {
                        if (thePlayer.getOnGround()) {
                            this.fakeJump();
                            this.jumpGround = thePlayer.getPosY();
                            thePlayer.setMotionY((double) ((Number) this.constantMotionValue.get()).floatValue());
                        }

                        if (thePlayer.getPosY() > this.jumpGround + ((Number) this.constantMotionJumpGroundValue.get()).doubleValue()) {
                            this.fakeJump();
                            thePlayer.setPosition(thePlayer.getPosX(), MathKt.truncate(thePlayer.getPosY()), thePlayer.getPosZ());
                            thePlayer.setMotionY((double) ((Number) this.constantMotionValue.get()).floatValue());
                            this.jumpGround = thePlayer.getPosY();
                        }
                    }
                }

            }
        }
    }

    private final void place() {
        if (this.placeInfo != null) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp != null) {
                IEntityPlayerSP thePlayer;
                IItemStack itemStack;
                label108: {
                    thePlayer = ientityplayersp;
                    itemStack = thePlayer.getHeldItem();
                    if (itemStack != null && MinecraftInstance.classProvider.isItemBlock(itemStack.getItem())) {
                        IClassProvider iclassprovider;
                        IBlock iblock;
                        label96: {
                            iclassprovider = MinecraftInstance.classProvider;
                            IItem iitem = itemStack.getItem();

                            if (iitem != null) {
                                IItemBlock iitemblock = iitem.asItemBlock();

                                if (iitemblock != null) {
                                    iblock = iitemblock.getBlock();
                                    break label96;
                                }
                            }

                            iblock = null;
                        }

                        if (!iclassprovider.isBlockBush(iblock)) {
                            break label108;
                        }
                    }

                    int blockSlot = InventoryUtils.findAutoBlockBlock();

                    if (blockSlot == -1) {
                        return;
                    }

                    String s = (String) this.autoBlockValue.get();

                    switch (s.hashCode()) {
                    case -1805606060:
                        if (s.equals("Switch") && blockSlot - 36 != this.slot) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(blockSlot - 36));
                        }
                        break;

                    case 79183:
                        if (s.equals("Off")) {
                            return;
                        }
                        break;

                    case 2487361:
                        if (s.equals("Pick")) {
                            ientityplayersp = MinecraftInstance.mc.getThePlayer();
                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.getInventory().setCurrentItem(blockSlot - 36);
                            MinecraftInstance.mc.getPlayerController().updateController();
                        }
                        break;

                    case 80099049:
                        if (s.equals("Spoof") && blockSlot - 36 != this.slot) {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketHeldItemChange(blockSlot - 36));
                        }
                    }

                    itemStack = thePlayer.getInventoryContainer().getSlot(blockSlot).getStack();
                }

                IPlayerControllerMP iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                if (itemStack == null) {
                    Intrinsics.throwNpe();
                }

                PlaceInfo placeinfo = this.placeInfo;

                if (this.placeInfo == null) {
                    Intrinsics.throwNpe();
                }

                WBlockPos wblockpos = placeinfo.getBlockPos();
                PlaceInfo placeinfo1 = this.placeInfo;

                if (this.placeInfo == null) {
                    Intrinsics.throwNpe();
                }

                IEnumFacing ienumfacing = placeinfo1.getEnumFacing();
                PlaceInfo placeinfo2 = this.placeInfo;

                if (this.placeInfo == null) {
                    Intrinsics.throwNpe();
                }

                if (iplayercontrollermp.onPlayerRightClick(thePlayer, iworldclient, itemStack, wblockpos, ienumfacing, placeinfo2.getVec3())) {
                    if (((Boolean) this.swingValue.get()).booleanValue()) {
                        thePlayer.swingItem();
                    } else {
                        MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketAnimation());
                    }
                }

                if (StringsKt.equals((String) this.autoBlockValue.get(), "Switch", true)) {
                    int i = this.slot;
                    IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

                    if (ientityplayersp1 == null) {
                        Intrinsics.throwNpe();
                    }

                    if (i != ientityplayersp1.getInventory().getCurrentItem()) {
                        IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                        IClassProvider iclassprovider1 = MinecraftInstance.classProvider;
                        IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

                        if (ientityplayersp2 == null) {
                            Intrinsics.throwNpe();
                        }

                        iinethandlerplayclient.addToSendQueue((IPacket) iclassprovider1.createCPacketHeldItemChange(ientityplayersp2.getInventory().getCurrentItem()));
                    }
                }

                this.placeInfo = (PlaceInfo) null;
            }
        }
    }

    private final boolean search(WBlockPos blockPosition) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            return false;
        } else {
            IEntityPlayerSP thePlayer = ientityplayersp;
            boolean eyesPos = false;
            IMaterial imaterial = BlockUtils.getMaterial(blockPosition);

            if (!(imaterial != null ? imaterial.isReplaceable() : false)) {
                return false;
            } else {
                WVec3 wvec3 = new WVec3(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + (double) thePlayer.getEyeHeight(), thePlayer.getPosZ());
                PlaceRotation placeRotation = (PlaceRotation) null;
                EnumFacingType[] aenumfacingtype = EnumFacingType.values();
                int i = aenumfacingtype.length;

                for (int j = 0; j < i; ++j) {
                    EnumFacingType facingType = aenumfacingtype[j];
                    IEnumFacing side = MinecraftInstance.classProvider.getEnumFacing(facingType);
                    WBlockPos neighbor = WBlockPos.offset$default(blockPosition, side, 0, 2, (Object) null);

                    if (BlockUtils.canBeClicked(neighbor)) {
                        WVec3 dirVec = new WVec3(side.getDirectionVec());
                        boolean matrix = ((Boolean) this.matrixValue.get()).booleanValue();

                        for (double xSearch = 0.1D; xSearch < 0.9D; xSearch += 0.1D) {
                            for (double ySearch = 0.1D; ySearch < 0.9D; ySearch += 0.1D) {
                                double zSearch = 0.1D;

                                while (zSearch < 0.9D) {
                                    WVec3 distanceSqPosVec = new WVec3((WVec3i) blockPosition);
                                    double x$iv = matrix ? 0.5D : xSearch;
                                    double diffX = matrix ? 0.5D : ySearch;
                                    double diffY = matrix ? 0.5D : zSearch;
                                    boolean diffZ = false;
                                    WVec3 posVec = new WVec3(distanceSqPosVec.getXCoord() + x$iv, distanceSqPosVec.getYCoord() + diffX, distanceSqPosVec.getZCoord() + diffY);
                                    boolean flag = false;
                                    double vec$iv = posVec.getXCoord() - wvec3.getXCoord();
                                    double d0$iv = posVec.getYCoord() - wvec3.getYCoord();
                                    double d1$iv = posVec.getZCoord() - wvec3.getZCoord();
                                    double d0 = vec$iv * vec$iv + d0$iv * d0$iv + d1$iv * d1$iv;
                                    WVec3 wvec31 = new WVec3(dirVec.getXCoord() * 0.5D, dirVec.getYCoord() * 0.5D, dirVec.getZCoord() * 0.5D);
                                    boolean flag1 = false;
                                    double d1 = wvec31.getXCoord();
                                    double diffXZ = wvec31.getYCoord();
                                    double rotation = wvec31.getZCoord();
                                    boolean vector = false;
                                    WVec3 hitVec = new WVec3(posVec.getXCoord() + d1, posVec.getYCoord() + diffXZ, posVec.getZCoord() + rotation);
                                    boolean flag2 = false;

                                    diffY = hitVec.getXCoord() - wvec3.getXCoord();
                                    d1 = hitVec.getYCoord() - wvec3.getYCoord();
                                    diffXZ = hitVec.getZCoord() - wvec3.getZCoord();
                                    if (diffY * diffY + d1 * d1 + diffXZ * diffXZ <= 18.0D) {
                                        flag1 = false;
                                        d1 = dirVec.getXCoord();
                                        diffXZ = dirVec.getYCoord();
                                        rotation = dirVec.getZCoord();
                                        vector = false;
                                        WVec3 wvec32 = new WVec3(posVec.getXCoord() + d1, posVec.getYCoord() + diffXZ, posVec.getZCoord() + rotation);

                                        flag1 = false;
                                        d0$iv = wvec32.getXCoord() - wvec3.getXCoord();
                                        d1$iv = wvec32.getYCoord() - wvec3.getYCoord();
                                        double d2$iv = wvec32.getZCoord() - wvec3.getZCoord();
                                        double d2 = d0$iv * d0$iv + d1$iv * d1$iv + d2$iv * d2$iv;

                                        if (d0 <= d2) {
                                            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                                            if (iworldclient == null) {
                                                Intrinsics.throwNpe();
                                            }

                                            if (iworldclient.rayTraceBlocks(wvec3, hitVec, false, true, false) == null) {
                                                diffX = hitVec.getXCoord() - wvec3.getXCoord();
                                                diffY = hitVec.getYCoord() - wvec3.getYCoord();
                                                d1 = hitVec.getZCoord() - wvec3.getZCoord();
                                                rotation = diffX * diffX + d1 * d1;
                                                vector = false;
                                                diffXZ = Math.sqrt(rotation);
                                                boolean rotationVector = false;

                                                d2 = Math.atan2(d1, diffX);
                                                float f = WMathHelper.wrapAngleTo180_float((float) Math.toDegrees(d2) - 90.0F);

                                                rotationVector = false;
                                                double d3 = Math.atan2(diffY, diffXZ);
                                                float f1 = WMathHelper.wrapAngleTo180_float((float) (-Math.toDegrees(d3)));
                                                Rotation rotation = new Rotation(f, f1);
                                                WVec3 wvec33 = RotationUtils.getVectorForRotation(rotation);
                                                double x$iv1 = wvec33.getXCoord() * d0;
                                                double y$iv = wvec33.getYCoord() * d0;
                                                double z$iv = wvec33.getZCoord() * d0;
                                                boolean $i$f$addVector = false;
                                                WVec3 wvec34 = new WVec3(wvec3.getXCoord() + x$iv1, wvec3.getYCoord() + y$iv, wvec3.getZCoord() + z$iv);

                                                iworldclient = MinecraftInstance.mc.getTheWorld();
                                                if (iworldclient == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                IMovingObjectPosition obj = iworldclient.rayTraceBlocks(wvec3, wvec34, false, false, true);

                                                if (obj == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                if (obj.getTypeOfHit() == IMovingObjectPosition.WMovingObjectType.BLOCK && Intrinsics.areEqual(obj.getBlockPos(), neighbor)) {
                                                    if (placeRotation == null || RotationUtils.getRotationDifference(rotation) < RotationUtils.getRotationDifference(placeRotation.getRotation())) {
                                                        placeRotation = new PlaceRotation(new PlaceInfo(neighbor, side.getOpposite(), hitVec), rotation);
                                                    }

                                                    zSearch += 0.1D;
                                                } else {
                                                    zSearch += 0.1D;
                                                }
                                                continue;
                                            }
                                        }
                                    }

                                    zSearch += 0.1D;
                                }
                            }
                        }
                    }
                }

                if (placeRotation == null) {
                    return false;
                } else {
                    if (((Boolean) this.rotationsValue.get()).booleanValue()) {
                        RotationUtils.setTargetRotation(placeRotation.getRotation(), 0);
                        this.lockRotation = placeRotation.getRotation();
                    }

                    this.placeInfo = placeRotation.getPlaceInfo();
                    return true;
                }
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getThePlayer() != null) {
            IPacket packet = event.getPacket();

            if (MinecraftInstance.classProvider.isCPacketHeldItemChange(packet)) {
                this.slot = packet.asCPacketHeldItemChange().getSlotId();
            }

        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (((Boolean) this.counterDisplayValue.get()).booleanValue()) {
            GL11.glPushMatrix();
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(BlockOverlay.class);

            if (module == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.BlockOverlay");
            }

            BlockOverlay blockOverlay = (BlockOverlay) module;

            if (blockOverlay.getState() && ((Boolean) blockOverlay.getInfoValue().get()).booleanValue() && blockOverlay.getCurrentBlock() != null) {
                GL11.glTranslatef(0.0F, 15.0F, 0.0F);
            }

            String info = "Blocks: §7" + this.getBlocksAmount();
            IClassProvider iclassprovider = MinecraftInstance.classProvider;
            IMinecraft iminecraft = MinecraftInstance.mc;

            Intrinsics.checkExpressionValueIsNotNull(MinecraftInstance.mc, "mc");
            IScaledResolution scaledResolution = iclassprovider.createScaledResolution(iminecraft);
            float f = (float) (scaledResolution.getScaledWidth() / 2) - (float) 2;
            float f1 = (float) (scaledResolution.getScaledHeight() / 2) + (float) 5;
            float f2 = (float) (scaledResolution.getScaledWidth() / 2 + Fonts.font40.getStringWidth(info)) + (float) 2;
            float f3 = (float) (scaledResolution.getScaledHeight() / 2) + (float) 16;
            Color color = Color.BLACK;

            Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
            int i = color.getRGB();
            Color color1 = Color.BLACK;

            Intrinsics.checkExpressionValueIsNotNull(Color.BLACK, "Color.BLACK");
            RenderUtils.drawBorderedRect(f, f1, f2, f3, 3.0F, i, color1.getRGB());
            MinecraftInstance.classProvider.getGlStateManager().resetColor();
            IFontRenderer ifontrenderer = Fonts.font40;

            f2 = (float) scaledResolution.getScaledWidth() / (float) 2;
            f3 = (float) (scaledResolution.getScaledHeight() / 2) + (float) 7;
            Color color2 = Color.WHITE;

            Intrinsics.checkExpressionValueIsNotNull(Color.WHITE, "Color.WHITE");
            ifontrenderer.drawString(info, f2, f3, color2.getRGB());
            GL11.glPopMatrix();
        }

    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (((Boolean) this.onJumpValue.get()).booleanValue()) {
            event.cancelEvent();
        }

    }

    private final int getBlocksAmount() {
        int amount = 0;
        int i = 36;

        for (byte b0 = 44; i <= b0; ++i) {
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            IItemStack itemStack = ientityplayersp.getInventoryContainer().getSlot(i).getStack();

            if (itemStack != null && MinecraftInstance.classProvider.isItemBlock(itemStack.getItem())) {
                IItem iitem = itemStack.getItem();

                if (iitem == null) {
                    Intrinsics.throwNpe();
                }

                IBlock block = iitem.asItemBlock().getBlock();

                ientityplayersp = MinecraftInstance.mc.getThePlayer();
                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                if (Intrinsics.areEqual(ientityplayersp.getHeldItem(), itemStack) || !InventoryUtils.BLOCK_BLACKLIST.contains(block)) {
                    amount += itemStack.getStackSize();
                }
            }
        }

        return amount;
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}

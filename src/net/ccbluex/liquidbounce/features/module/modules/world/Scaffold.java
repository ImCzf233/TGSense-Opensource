package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketHeldItemChange;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WMathHelper;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlaceRotation;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
    name = "Scaffold",
    description = "自动在你脚下放置方块",
    category = ModuleCategory.WORLD,
    keyBind = 34
)
public class Scaffold extends Module {

    public final ListValue modeValue = new ListValue("Mode", new String[] { "Normal", "Rewinside", "Expand"}, "Normal");
    private final IntegerValue maxDelayValue = new IntegerValue("MaxDelay", 0, 0, 1000) {
        protected void onChanged(Integer oldValue, Integer newValue) {
            int i = ((Integer) Scaffold.this.minDelayValue.get()).intValue();

            if (i > newValue.intValue()) {
                this.set((Object) Integer.valueOf(i));
            }

        }
    };
    private final IntegerValue minDelayValue = new IntegerValue("MinDelay", 0, 0, 1000) {
        protected void onChanged(Integer oldValue, Integer newValue) {
            int i = ((Integer) Scaffold.this.maxDelayValue.get()).intValue();

            if (i < newValue.intValue()) {
                this.set((Object) Integer.valueOf(i));
            }

        }
    };
    private final BoolValue placeableDelay = new BoolValue("PlaceableDelay", false);
    private final ListValue autoBlockValue = new ListValue("AutoBlock", new String[] { "Off", "Spoof", "Pick", "Switch"}, "Spoof");
    public final BoolValue sprintValue = new BoolValue("Sprint", true);
    private final BoolValue swingValue = new BoolValue("Swing", true);
    private final BoolValue searchValue = new BoolValue("Search", true);
    private final BoolValue downValue = new BoolValue("Down", true);
    private final BoolValue picker = new BoolValue("Picker", false);
    private final ListValue placeModeValue = new ListValue("PlaceTiming", new String[] { "Pre", "Post"}, "Post");
    private final ListValue eagleValue = new ListValue("Eagle", new String[] { "Normal", "EdgeDistance", "Silent", "Off"}, "Off");
    private final IntegerValue blocksToEagleValue = new IntegerValue("BlocksToEagle", 0, 0, 10);
    private final FloatValue edgeDistanceValue = new FloatValue("EagleEdgeDistance", 0.2F, 0.0F, 0.5F);
    private final IntegerValue expandLengthValue = new IntegerValue("ExpandLength", 5, 1, 6);
    private final BoolValue rotationStrafeValue = new BoolValue("RotationStrafe", false);
    private final ListValue rotationModeValue = new ListValue("RotationMode", new String[] { "Normal", "Static", "StaticPitch", "StaticYaw", "Off"}, "Normal");
    private final BoolValue silentRotation = new BoolValue("SilentRotation", true);
    private final BoolValue keepRotationValue = new BoolValue("KeepRotation", false);
    private final IntegerValue keepLengthValue = new IntegerValue("KeepRotationLength", 0, 0, 20);
    private final FloatValue staticPitchValue = new FloatValue("StaticPitchOffset", 86.0F, 70.0F, 90.0F);
    private final FloatValue staticYawOffsetValue = new FloatValue("StaticYawOffset", 0.0F, 0.0F, 90.0F);
    private final FloatValue xzRangeValue = new FloatValue("xzRange", 0.8F, 0.1F, 1.0F);
    private final FloatValue yRangeValue = new FloatValue("yRange", 0.8F, 0.1F, 1.0F);
    private final IntegerValue searchAccuracyValue = new IntegerValue("SearchAccuracy", 8, 1, 24) {
        protected void onChanged(Integer oldValue, Integer newValue) {
            if (this.getMaximum() < newValue.intValue()) {
                this.set((Object) Integer.valueOf(this.getMaximum()));
            } else if (this.getMinimum() > newValue.intValue()) {
                this.set((Object) Integer.valueOf(this.getMinimum()));
            }

        }
    };
    private final FloatValue maxTurnSpeedValue = new FloatValue("MaxTurnSpeed", 180.0F, 1.0F, 180.0F) {
        protected void onChanged(Float oldValue, Float newValue) {
            float v = ((Float) Scaffold.this.minTurnSpeedValue.get()).floatValue();

            if (v > newValue.floatValue()) {
                this.set((Object) Float.valueOf(v));
            }

            if (this.getMaximum() < newValue.floatValue()) {
                this.set((Object) Float.valueOf(this.getMaximum()));
            } else if (this.getMinimum() > newValue.floatValue()) {
                this.set((Object) Float.valueOf(this.getMinimum()));
            }

        }
    };
    private final FloatValue minTurnSpeedValue = new FloatValue("MinTurnSpeed", 180.0F, 1.0F, 180.0F) {
        protected void onChanged(Float oldValue, Float newValue) {
            float v = ((Float) Scaffold.this.maxTurnSpeedValue.get()).floatValue();

            if (v < newValue.floatValue()) {
                this.set((Object) Float.valueOf(v));
            }

            if (this.getMaximum() < newValue.floatValue()) {
                this.set((Object) Float.valueOf(this.getMaximum()));
            } else if (this.getMinimum() > newValue.floatValue()) {
                this.set((Object) Float.valueOf(this.getMinimum()));
            }

        }
    };
    private final BoolValue zitterValue = new BoolValue("Zitter", false);
    private final ListValue zitterModeValue = new ListValue("ZitterMode", new String[] { "Teleport", "Smooth"}, "Teleport");
    private final FloatValue zitterSpeed = new FloatValue("ZitterSpeed", 0.13F, 0.1F, 0.3F);
    private final FloatValue zitterStrength = new FloatValue("ZitterStrength", 0.072F, 0.05F, 0.2F);
    private final FloatValue timerValue = new FloatValue("Timer", 1.0F, 0.1F, 10.0F);
    private final FloatValue speedModifierValue = new FloatValue("SpeedModifier", 1.0F, 0.0F, 2.0F);
    private final BoolValue slowValue = new BoolValue("Slow", false) {
        protected void onChanged(Boolean oldValue, Boolean newValue) {
            if (newValue.booleanValue()) {
                Scaffold.this.sprintValue.set(Boolean.valueOf(false));
            }

        }
    };
    private final FloatValue slowSpeed = new FloatValue("SlowSpeed", 0.6F, 0.2F, 0.8F);
    private long lastMS = 0L;
    private float progress = 0.0F;
    private final BoolValue sameYValue = new BoolValue("SameY", false);
    private final BoolValue smartSpeedValue = new BoolValue("SmartSpeed", false);
    private final BoolValue autoJumpValue = new BoolValue("AutoJump", false);
    private final BoolValue safeWalkValue = new BoolValue("SafeWalk", true);
    private final BoolValue airSafeValue = new BoolValue("AirSafe", false);
    private final BoolValue counterDisplayValue = new BoolValue("Counter", true);
    private final BoolValue markValue = new BoolValue("Mark", false);
    private final BoolValue autoTimer = new BoolValue("AutoTimer", false);
    private PlaceInfo targetPlace;
    private int launchY;
    private Rotation lockRotation;
    private Rotation limitedRotation;
    private boolean facesBlock = false;
    private int slot;
    private boolean zitterDirection;
    private final MSTimer delayTimer = new MSTimer();
    private final MSTimer zitterTimer = new MSTimer();
    private long delay;
    private int placedBlocksWithoutEagle = 0;
    private boolean eagleSneaking;
    private boolean shouldGoDown = false;

    public void onEnable() {
        if (Scaffold.mc.getThePlayer() != null) {
            if (((Boolean) this.autoTimer.get()).booleanValue()) {
                Timer timer = (Timer) LiquidBounce.INSTANCE.getModuleManager().getModule(Timer.class);

                timer.setState(true);
            }

            this.progress = 0.0F;
            this.launchY = (int) Scaffold.mc.getThePlayer().getPosY();
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (this.getBlocksAmount() == 0) {
            LiquidBounce.hud.addNotification(new Notification("Scaffold", "ZhuBoNoBlockOpenYourMother", NotifyType.ERROR, 2000, 500));
            this.toggle();
        }

        this.getBestBlocks();
        Scaffold.mc.getTimer().setTimerSpeed(((Float) this.timerValue.get()).floatValue());
        this.shouldGoDown = ((Boolean) this.downValue.get()).booleanValue() && !((Boolean) this.sameYValue.get()).booleanValue() && Scaffold.mc.getGameSettings().getKeyBindSneak().isKeyDown() && this.getBlocksAmount() > 1;
        if (this.shouldGoDown) {
            Scaffold.mc.getGameSettings().getKeyBindSneak().setPressed(false);
        }

        if (((Boolean) this.slowValue.get()).booleanValue()) {
            Scaffold.mc.getThePlayer().setMotionX(Scaffold.mc.getThePlayer().getMotionX() * (double) ((Float) this.slowSpeed.get()).floatValue());
            Scaffold.mc.getThePlayer().setMotionZ(Scaffold.mc.getThePlayer().getMotionZ() * (double) ((Float) this.slowSpeed.get()).floatValue());
        }

        if (((Boolean) this.sprintValue.get()).booleanValue()) {
            if (!Scaffold.mc.getGameSettings().isKeyDown(Scaffold.mc.getGameSettings().getKeyBindSprint())) {
                Scaffold.mc.getGameSettings().getKeyBindSprint().setPressed(false);
            }

            if (Scaffold.mc.getGameSettings().isKeyDown(Scaffold.mc.getGameSettings().getKeyBindSprint())) {
                Scaffold.mc.getGameSettings().getKeyBindSprint().setPressed(true);
            }

            if (Scaffold.mc.getGameSettings().getKeyBindSprint().isKeyDown()) {
                Scaffold.mc.getThePlayer().setSprinting(true);
            }

            if (!Scaffold.mc.getGameSettings().getKeyBindSprint().isKeyDown()) {
                Scaffold.mc.getThePlayer().setSprinting(false);
            }
        }

        if (Scaffold.mc.getThePlayer().getOnGround()) {
            String mode = (String) this.modeValue.get();

            if (mode.equalsIgnoreCase("Rewinside")) {
                MovementUtils.strafe(0.2F);
                Scaffold.mc.getThePlayer().setMotionY(0.0D);
            }

            if (((Boolean) this.zitterValue.get()).booleanValue() && ((String) this.zitterModeValue.get()).equalsIgnoreCase("smooth")) {
                if (!Scaffold.mc.getGameSettings().isKeyDown(Scaffold.mc.getGameSettings().getKeyBindRight())) {
                    Scaffold.mc.getGameSettings().getKeyBindRight().setPressed(false);
                }

                if (!Scaffold.mc.getGameSettings().isKeyDown(Scaffold.mc.getGameSettings().getKeyBindLeft())) {
                    Scaffold.mc.getGameSettings().getKeyBindLeft().setPressed(false);
                }

                if (this.zitterTimer.hasTimePassed(100L)) {
                    this.zitterDirection = !this.zitterDirection;
                    this.zitterTimer.reset();
                }

                if (this.zitterDirection) {
                    Scaffold.mc.getGameSettings().getKeyBindRight().setPressed(true);
                    Scaffold.mc.getGameSettings().getKeyBindLeft().setPressed(false);
                } else {
                    Scaffold.mc.getGameSettings().getKeyBindRight().setPressed(false);
                    Scaffold.mc.getGameSettings().getKeyBindLeft().setPressed(true);
                }
            }

            double yaw;

            if (!((String) this.eagleValue.get()).equalsIgnoreCase("Off") && !this.shouldGoDown) {
                yaw = 0.5D;
                if (((String) this.eagleValue.get()).equalsIgnoreCase("EdgeDistance") && !this.shouldGoDown) {
                    int shouldEagle = 0;

                    while (shouldEagle < 4) {
                        WBlockPos blockPos;
                        PlaceInfo placeInfo;
                        double calcDif;

                        switch (shouldEagle) {
                        case 0:
                            blockPos = new WBlockPos(Scaffold.mc.getThePlayer().getPosX() - 1.0D, Scaffold.mc.getThePlayer().getPosY() - (Scaffold.mc.getThePlayer().getPosY() == (double) ((int) Scaffold.mc.getThePlayer().getPosY()) + 0.5D ? 0.0D : 1.0D), Scaffold.mc.getThePlayer().getPosZ());
                            placeInfo = PlaceInfo.get(blockPos);
                            if (BlockUtils.isReplaceable(blockPos) && placeInfo != null) {
                                calcDif = Scaffold.mc.getThePlayer().getPosX() - (double) blockPos.getX();
                                calcDif -= 0.5D;
                                if (calcDif < 0.0D) {
                                    calcDif *= -1.0D;
                                }

                                calcDif -= 0.5D;
                                if (calcDif < yaw) {
                                    yaw = calcDif;
                                }
                            }

                        case 1:
                            blockPos = new WBlockPos(Scaffold.mc.getThePlayer().getPosX() + 1.0D, Scaffold.mc.getThePlayer().getPosY() - (Scaffold.mc.getThePlayer().getPosY() == (double) ((int) Scaffold.mc.getThePlayer().getPosY()) + 0.5D ? 0.0D : 1.0D), Scaffold.mc.getThePlayer().getPosZ());
                            placeInfo = PlaceInfo.get(blockPos);
                            if (BlockUtils.isReplaceable(blockPos) && placeInfo != null) {
                                calcDif = Scaffold.mc.getThePlayer().getPosX() - (double) blockPos.getX();
                                calcDif -= 0.5D;
                                if (calcDif < 0.0D) {
                                    calcDif *= -1.0D;
                                }

                                calcDif -= 0.5D;
                                if (calcDif < yaw) {
                                    yaw = calcDif;
                                }
                            }

                        case 2:
                            blockPos = new WBlockPos(Scaffold.mc.getThePlayer().getPosX(), Scaffold.mc.getThePlayer().getPosY() - (Scaffold.mc.getThePlayer().getPosY() == (double) ((int) Scaffold.mc.getThePlayer().getPosY()) + 0.5D ? 0.0D : 1.0D), Scaffold.mc.getThePlayer().getPosZ() - 1.0D);
                            placeInfo = PlaceInfo.get(blockPos);
                            if (BlockUtils.isReplaceable(blockPos) && placeInfo != null) {
                                calcDif = Scaffold.mc.getThePlayer().getPosZ() - (double) blockPos.getZ();
                                calcDif -= 0.5D;
                                if (calcDif < 0.0D) {
                                    calcDif *= -1.0D;
                                }

                                calcDif -= 0.5D;
                                if (calcDif < yaw) {
                                    yaw = calcDif;
                                }
                            }

                        case 3:
                            blockPos = new WBlockPos(Scaffold.mc.getThePlayer().getPosX(), Scaffold.mc.getThePlayer().getPosY() - (Scaffold.mc.getThePlayer().getPosY() == (double) ((int) Scaffold.mc.getThePlayer().getPosY()) + 0.5D ? 0.0D : 1.0D), Scaffold.mc.getThePlayer().getPosZ() + 1.0D);
                            placeInfo = PlaceInfo.get(blockPos);
                            if (BlockUtils.isReplaceable(blockPos) && placeInfo != null) {
                                calcDif = Scaffold.mc.getThePlayer().getPosZ() - (double) blockPos.getZ();
                                calcDif -= 0.5D;
                                if (calcDif < 0.0D) {
                                    calcDif *= -1.0D;
                                }

                                calcDif -= 0.5D;
                                if (calcDif < yaw) {
                                    yaw = calcDif;
                                }
                            }

                        default:
                            ++shouldEagle;
                        }
                    }
                }

                if (this.placedBlocksWithoutEagle >= ((Integer) this.blocksToEagleValue.get()).intValue()) {
                    boolean flag = Scaffold.mc.getTheWorld().getBlockState(new WBlockPos(Scaffold.mc.getThePlayer().getPosX(), Scaffold.mc.getThePlayer().getPosY() - 1.0D, Scaffold.mc.getThePlayer().getPosZ())).getBlock().equals(Scaffold.classProvider.getBlockEnum(BlockType.AIR)) || yaw < (double) ((Float) this.edgeDistanceValue.get()).floatValue() && ((String) this.eagleValue.get()).equalsIgnoreCase("EdgeDistance");

                    if (((String) this.eagleValue.get()).equalsIgnoreCase("Silent") && !this.shouldGoDown) {
                        if (this.eagleSneaking != flag) {
                            Scaffold.mc.getNetHandler().addToSendQueue(Scaffold.classProvider.createCPacketEntityAction(Scaffold.mc.getThePlayer(), flag ? ICPacketEntityAction.WAction.START_SNEAKING : ICPacketEntityAction.WAction.STOP_SNEAKING));
                        }

                        this.eagleSneaking = flag;
                    } else {
                        Scaffold.mc.getGameSettings().getKeyBindSneak().setPressed(flag);
                    }

                    this.placedBlocksWithoutEagle = 0;
                } else {
                    ++this.placedBlocksWithoutEagle;
                }
            }

            if (((Boolean) this.zitterValue.get()).booleanValue() && ((String) this.zitterModeValue.get()).equalsIgnoreCase("teleport")) {
                MovementUtils.strafe(((Float) this.zitterSpeed.get()).floatValue());
                yaw = Math.toRadians((double) Scaffold.mc.getThePlayer().getRotationYaw() + (this.zitterDirection ? 90.0D : -90.0D));
                Scaffold.mc.getThePlayer().setMotionX(Scaffold.mc.getThePlayer().getMotionX() - Math.sin(yaw) * (double) ((Float) this.zitterStrength.get()).floatValue());
                Scaffold.mc.getThePlayer().setMotionZ(Scaffold.mc.getThePlayer().getMotionZ() + Math.cos(yaw) * (double) ((Float) this.zitterStrength.get()).floatValue());
                this.zitterDirection = !this.zitterDirection;
            }
        }

        if (this.shouldGoDown) {
            this.launchY = (int) Scaffold.mc.getThePlayer().getPosY() - 1;
        } else if (!((Boolean) this.sameYValue.get()).booleanValue()) {
            Scaffold.mc.getThePlayer().jump();
        }

    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (Scaffold.mc.getThePlayer() != null) {
            IPacket packet = event.getPacket();

            if (Scaffold.classProvider.isCPacketHeldItemChange(packet)) {
                ICPacketHeldItemChange packetHeldItemChange = packet.asCPacketHeldItemChange();

                this.slot = packetHeldItemChange.getSlotId();
            }

        }
    }

    @EventTarget
    private void onStrafe(StrafeEvent event) {
        if (((Boolean) this.rotationStrafeValue.get()).booleanValue()) {
            RotationUtils.serverRotation.applyStrafeToPlayer(event);
            event.cancelEvent();
        }
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        EventState eventState = event.getEventState();

        if (!((String) this.rotationModeValue.get()).equalsIgnoreCase("Off") && ((Boolean) this.keepRotationValue.get()).booleanValue() && this.lockRotation != null) {
            this.setRotation(this.lockRotation);
        }

        if ((this.facesBlock || ((String) this.rotationModeValue.get()).equalsIgnoreCase("Off")) && ((String) this.placeModeValue.get()).equalsIgnoreCase(eventState.getStateName())) {
            this.place();
        }

        if (eventState == EventState.PRE) {
            this.update();
        }

        if (this.targetPlace == null && ((Boolean) this.placeableDelay.get()).booleanValue()) {
            this.delayTimer.reset();
        }

    }

    private void update() {
        boolean isHeldItemBlock = Scaffold.mc.getThePlayer().getHeldItem() != null && Scaffold.classProvider.isItemBlock(Scaffold.mc.getThePlayer().getHeldItem().getItem());

        if (!((String) this.autoBlockValue.get()).equalsIgnoreCase("Off")) {
            if (InventoryUtils.findAutoBlockBlock() == -1 && !isHeldItemBlock) {
                return;
            }
        } else if (!isHeldItemBlock) {
            return;
        }

        this.findBlock(((String) this.modeValue.get()).equalsIgnoreCase("expand"));
    }

    private void setRotation(Rotation rotation, int keepRotation) {
        if (((Boolean) this.silentRotation.get()).booleanValue()) {
            RotationUtils.setTargetRotation(rotation, keepRotation);
        } else {
            Scaffold.mc.getThePlayer().setRotationYaw(rotation.getYaw());
            Scaffold.mc.getThePlayer().setRotationPitch(rotation.getPitch());
        }

    }

    private void setRotation(Rotation rotation) {
        this.setRotation(rotation, 0);
    }

    private void findBlock(boolean expand) {
        WBlockPos blockPosition = this.shouldGoDown ? (Scaffold.mc.getThePlayer().getPosY() == (double) ((int) Scaffold.mc.getThePlayer().getPosY()) + 0.5D ? new WBlockPos(Scaffold.mc.getThePlayer().getPosX(), Scaffold.mc.getThePlayer().getPosY() - 0.6D, Scaffold.mc.getThePlayer().getPosZ()) : (new WBlockPos(Scaffold.mc.getThePlayer().getPosX(), Scaffold.mc.getThePlayer().getPosY() - 0.6D, Scaffold.mc.getThePlayer().getPosZ())).down()) : (!((Boolean) this.sameYValue.get()).booleanValue() && (!((Boolean) this.autoJumpValue.get()).booleanValue() && (!((Boolean) this.smartSpeedValue.get()).booleanValue() || !LiquidBounce.moduleManager.getModule(Speed.class).getState()) || Scaffold.mc.getGameSettings().getKeyBindJump().isKeyDown() || (double) this.launchY > Scaffold.mc.getThePlayer().getPosY()) ? (Scaffold.mc.getThePlayer().getPosY() == (double) ((int) Scaffold.mc.getThePlayer().getPosY()) + 0.5D ? new WBlockPos(Scaffold.mc.getThePlayer()) : (new WBlockPos(Scaffold.mc.getThePlayer().getPosX(), Scaffold.mc.getThePlayer().getPosY(), Scaffold.mc.getThePlayer().getPosZ())).down()) : new WBlockPos(Scaffold.mc.getThePlayer().getPosX(), (double) (this.launchY - 1), Scaffold.mc.getThePlayer().getPosZ()));

        if (expand || BlockUtils.isReplaceable(blockPosition) && !this.search(blockPosition, !this.shouldGoDown)) {
            int x;

            if (expand) {
                for (x = 0; x < ((Integer) this.expandLengthValue.get()).intValue(); ++x) {
                    if (this.search(blockPosition.add(Scaffold.mc.getThePlayer().getHorizontalFacing().equals(Scaffold.classProvider.getEnumFacing(EnumFacingType.WEST)) ? -x : (Scaffold.mc.getThePlayer().getHorizontalFacing().equals(Scaffold.classProvider.getEnumFacing(EnumFacingType.EAST)) ? x : 0), 0, Scaffold.mc.getThePlayer().getHorizontalFacing().equals(Scaffold.classProvider.getEnumFacing(EnumFacingType.NORTH)) ? -x : (Scaffold.mc.getThePlayer().getHorizontalFacing().equals(Scaffold.classProvider.getEnumFacing(EnumFacingType.SOUTH)) ? x : 0)), false)) {
                        return;
                    }
                }
            } else if (((Boolean) this.searchValue.get()).booleanValue()) {
                for (x = -1; x <= 1; ++x) {
                    for (int z = -1; z <= 1; ++z) {
                        if (this.search(blockPosition.add(x, 0, z), !this.shouldGoDown)) {
                            return;
                        }
                    }
                }
            }

        }
    }

    private void place() {
        if (this.targetPlace == null) {
            if (((Boolean) this.placeableDelay.get()).booleanValue()) {
                this.delayTimer.reset();
            }

        } else if (this.delayTimer.hasTimePassed(this.delay) && (!((Boolean) this.sameYValue.get()).booleanValue() || this.launchY - 1 == (int) this.targetPlace.getVec3().getYCoord())) {
            boolean blockSlot = true;
            IItemStack itemStack = Scaffold.mc.getThePlayer().getHeldItem();

            if (itemStack == null || !Scaffold.classProvider.isItemBlock(itemStack.getItem()) || Scaffold.classProvider.isBlockBush(itemStack.getItem().asItemBlock().getBlock()) || Scaffold.mc.getThePlayer().getHeldItem().getStackSize() <= 0) {
                if (((String) this.autoBlockValue.get()).equalsIgnoreCase("Off")) {
                    return;
                }

                int blockSlot1 = InventoryUtils.findAutoBlockBlock();

                if (blockSlot1 == -1) {
                    return;
                }

                if (((String) this.autoBlockValue.get()).equalsIgnoreCase("Pick")) {
                    Scaffold.mc.getNetHandler().addToSendQueue(Scaffold.classProvider.createCPacketHeldItemChange(blockSlot1 - 36));
                }

                Scaffold.mc.getPlayerController().updateController();
                if (((String) this.autoBlockValue.get()).equalsIgnoreCase("Spoof")) {
                    if (blockSlot1 - 36 != this.slot) {
                        Scaffold.mc.getNetHandler().addToSendQueue(Scaffold.classProvider.createCPacketHeldItemChange(blockSlot1 - 36));
                    }
                } else if (((String) this.autoBlockValue.get()).equalsIgnoreCase("Switch")) {
                    Scaffold.mc.getThePlayer().getInventory().setCurrentItem(blockSlot1 - 36);
                    Scaffold.mc.getPlayerController().updateController();
                } else {
                    Scaffold.mc.getThePlayer().getInventory().setCurrentItem(blockSlot1 - 36);
                    Scaffold.mc.getPlayerController().updateController();
                }

                itemStack = Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(blockSlot1).getStack();
            }

            if (Scaffold.mc.getPlayerController().onPlayerRightClick(Scaffold.mc.getThePlayer(), Scaffold.mc.getTheWorld(), itemStack, this.targetPlace.getBlockPos(), this.targetPlace.getEnumFacing(), this.targetPlace.getVec3())) {
                this.delayTimer.reset();
                this.delay = TimeUtils.randomDelay(((Integer) this.minDelayValue.get()).intValue(), ((Integer) this.maxDelayValue.get()).intValue());
                if (Scaffold.mc.getThePlayer().getOnGround()) {
                    float modifier = ((Float) this.speedModifierValue.get()).floatValue();

                    Scaffold.mc.getThePlayer().setMotionX(Scaffold.mc.getThePlayer().getMotionX() * (double) modifier);
                    Scaffold.mc.getThePlayer().setMotionZ(Scaffold.mc.getThePlayer().getMotionZ() * (double) modifier);
                }

                if (((Boolean) this.swingValue.get()).booleanValue()) {
                    Scaffold.mc.getThePlayer().swingItem();
                } else {
                    Scaffold.mc.getNetHandler().addToSendQueue(Scaffold.classProvider.createCPacketAnimation());
                }
            }

            this.targetPlace = null;
        }
    }

    public void onDisable() {
        if (Scaffold.mc.getThePlayer() != null) {
            if (!Scaffold.mc.getGameSettings().isKeyDown(Scaffold.mc.getGameSettings().getKeyBindSneak())) {
                Scaffold.mc.getGameSettings().getKeyBindSneak().setPressed(false);
                if (this.eagleSneaking) {
                    Scaffold.mc.getNetHandler().addToSendQueue(Scaffold.classProvider.createCPacketEntityAction(Scaffold.mc.getThePlayer(), ICPacketEntityAction.WAction.STOP_SNEAKING));
                }
            }

            if (!Scaffold.mc.getGameSettings().isKeyDown(Scaffold.mc.getGameSettings().getKeyBindRight())) {
                Scaffold.mc.getGameSettings().getKeyBindRight().setPressed(false);
            }

            if (!Scaffold.mc.getGameSettings().isKeyDown(Scaffold.mc.getGameSettings().getKeyBindLeft())) {
                Scaffold.mc.getGameSettings().getKeyBindLeft().setPressed(false);
            }

            if (((Boolean) this.autoTimer.get()).booleanValue()) {
                Timer timer = (Timer) LiquidBounce.INSTANCE.getModuleManager().getModule(Timer.class);

                timer.setState(false);
            }

            this.lockRotation = null;
            this.limitedRotation = null;
            this.facesBlock = false;
            Scaffold.mc.getTimer().setTimerSpeed(1.0F);
            this.shouldGoDown = false;
            if (this.slot != Scaffold.mc.getThePlayer().getInventory().getCurrentItem()) {
                Scaffold.mc.getNetHandler().addToSendQueue(Scaffold.classProvider.createCPacketHeldItemChange(Scaffold.mc.getThePlayer().getInventory().getCurrentItem()));
            }

        }
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        if (((Boolean) this.safeWalkValue.get()).booleanValue() && !this.shouldGoDown) {
            if (((Boolean) this.airSafeValue.get()).booleanValue() || Scaffold.mc.getThePlayer().getOnGround()) {
                event.setSafeWalk(true);
            }

        }
    }

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        if (((Boolean) this.counterDisplayValue.get()).booleanValue()) {
            this.progress = (float) (System.currentTimeMillis() - this.lastMS) / 100.0F;
            if (this.progress >= 1.0F) {
                this.progress = 1.0F;
            }

            ScaledResolution scaledResolution = new ScaledResolution(Scaffold.mc2);
            String info = this.getBlocksAmount() + " blocks";
            int infoWidth = Fonts.font40.getStringWidth(info);
            int infoWidth2 = Fonts.minecraftFont.getStringWidth(this.getBlocksAmount() + "");

            GlStateManager.translate(0.0F, -14.0F - this.progress * 4.0F, 0.0F);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glColor4f(0.15F, 0.15F, 0.15F, this.progress);
            GL11.glBegin(6);
            GL11.glVertex2d((double) (scaledResolution.getScaledWidth() / 2 - 3), (double) (scaledResolution.getScaledHeight() - 60));
            GL11.glVertex2d((double) (scaledResolution.getScaledWidth() / 2), (double) (scaledResolution.getScaledHeight() - 57));
            GL11.glVertex2d((double) (scaledResolution.getScaledWidth() / 2 + 3), (double) (scaledResolution.getScaledHeight() - 60));
            GL11.glEnd();
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glDisable(2848);
            RenderUtils.drawRoundedRect((float) (scaledResolution.getScaledWidth() / 2 - infoWidth / 2 - 4), (float) (scaledResolution.getScaledHeight() - 60), (float) (scaledResolution.getScaledWidth() / 2 + infoWidth / 2 + 4), (float) (scaledResolution.getScaledHeight() - 74), 2.0F, (new Color(0.15F, 0.15F, 0.15F, this.progress)).getRGB());
            GlStateManager.resetColor();
            Fonts.font35.drawCenteredString(info, (float) (scaledResolution.getScaledWidth() / 2) + 0.1F, (float) (scaledResolution.getScaledHeight() - 70), (new Color(1.0F, 1.0F, 1.0F, 0.8F * this.progress)).getRGB(), false);
            GlStateManager.translate(0.0F, 14.0F + this.progress * 4.0F, 0.0F);
        }

    }

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        if (((Boolean) this.markValue.get()).booleanValue()) {
            for (int i = 0; i < (((String) this.modeValue.get()).equalsIgnoreCase("Expand") ? ((Integer) this.expandLengthValue.get()).intValue() + 1 : 2); ++i) {
                WBlockPos blockPos = new WBlockPos(Scaffold.mc.getThePlayer().getPosX() + (double) (Scaffold.mc.getThePlayer().getHorizontalFacing().equals(Scaffold.classProvider.getEnumFacing(EnumFacingType.WEST)) ? -i : (Scaffold.mc.getThePlayer().getHorizontalFacing().equals(Scaffold.classProvider.getEnumFacing(EnumFacingType.EAST)) ? i : 0)), Scaffold.mc.getThePlayer().getPosY() - (Scaffold.mc.getThePlayer().getPosY() == (double) ((int) Scaffold.mc.getThePlayer().getPosY()) + 0.5D ? 0.0D : 1.0D) - (this.shouldGoDown ? 1.0D : 0.0D), Scaffold.mc.getThePlayer().getPosZ() + (double) (Scaffold.mc.getThePlayer().getHorizontalFacing().equals(Scaffold.classProvider.getEnumFacing(EnumFacingType.NORTH)) ? -i : (Scaffold.mc.getThePlayer().getHorizontalFacing().equals(Scaffold.classProvider.getEnumFacing(EnumFacingType.SOUTH)) ? i : 0)));
                PlaceInfo placeInfo = PlaceInfo.get(blockPos);

                if (BlockUtils.isReplaceable(blockPos) && placeInfo != null) {
                    RenderUtils.drawBlockBox(blockPos, new Color(68, 117, 255, 100), false);
                    break;
                }
            }

        }
    }

    private boolean search(WBlockPos blockPosition, boolean checks) {
        if (!BlockUtils.isReplaceable(blockPosition)) {
            return false;
        } else {
            boolean staticMode = ((String) this.rotationModeValue.get()).equalsIgnoreCase("Static");
            boolean staticPitchMode = staticMode || ((String) this.rotationModeValue.get()).equalsIgnoreCase("StaticPitch");
            boolean staticYawMode = staticMode || ((String) this.rotationModeValue.get()).equalsIgnoreCase("StaticYaw");
            float staticPitch = ((Float) this.staticPitchValue.get()).floatValue();
            float staticYawOffset = ((Float) this.staticYawOffsetValue.get()).floatValue();
            double xzRV = (double) ((Float) this.xzRangeValue.get()).floatValue();
            double xzSSV = this.calcStepSize(xzRV);
            double yRV = (double) ((Float) this.yRangeValue.get()).floatValue();
            double ySSV = this.calcStepSize(yRV);
            double xSearchFace = 0.0D;
            double ySearchFace = 0.0D;
            double zSearchFace = 0.0D;
            WVec3 eyesPos = new WVec3(Scaffold.mc.getThePlayer().getPosX(), Scaffold.mc.getThePlayer().getEntityBoundingBox().getMinY() + (double) Scaffold.mc.getThePlayer().getEyeHeight(), Scaffold.mc.getThePlayer().getPosZ());
            PlaceRotation placeRotation = null;
            EnumFacingType[] aenumfacingtype = EnumFacingType.values();
            int i = aenumfacingtype.length;

            int j;
            EnumFacingType facingType;
            IEnumFacing side;
            WBlockPos neighbor;
            WVec3 dirVec;

            for (j = 0; j < i; ++j) {
                facingType = aenumfacingtype[j];
                side = Scaffold.classProvider.getEnumFacing(facingType);
                neighbor = blockPosition.offset(side);
                if (BlockUtils.canBeClicked(neighbor)) {
                    dirVec = new WVec3(side.getDirectionVec());

                    for (double posVec = 0.5D - xzRV / 2.0D; posVec <= 0.5D + xzRV / 2.0D; posVec += xzSSV) {
                        for (double ySearch = 0.5D - yRV / 2.0D; ySearch <= 0.5D + yRV / 2.0D; ySearch += ySSV) {
                            for (double rotationVector = 0.5D - xzRV / 2.0D; rotationVector <= 0.5D + xzRV / 2.0D; rotationVector += xzSSV) {
                                WVec3 obj = (new WVec3(blockPosition)).addVector(posVec, ySearch, rotationVector);
                                double distanceSqPosVec1 = eyesPos.squareDistanceTo(obj);
                                WVec3 hitVec1 = obj.add(new WVec3(dirVec.getXCoord() * 0.5D, dirVec.getYCoord() * 0.5D, dirVec.getZCoord() * 0.5D));

                                if (!checks || eyesPos.squareDistanceTo(hitVec1) <= 18.0D && distanceSqPosVec1 <= eyesPos.squareDistanceTo(obj.add(dirVec)) && Scaffold.mc.getTheWorld().rayTraceBlocks(eyesPos, hitVec1, false, true, false) == null) {
                                    for (int i = 0; i < (staticYawMode ? 2 : 1); ++i) {
                                        double diffX = staticYawMode && i == 0 ? 0.0D : hitVec1.getXCoord() - eyesPos.getXCoord();
                                        double diffY = hitVec1.getYCoord() - eyesPos.getYCoord();
                                        double diffZ = staticYawMode && i == 1 ? 0.0D : hitVec1.getZCoord() - eyesPos.getZCoord();
                                        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
                                        float pitch = staticPitchMode ? staticPitch : WMathHelper.wrapAngleTo180_float((float) (-Math.toDegrees(Math.atan2(diffY, diffXZ))));
                                        Rotation rotation = new Rotation(WMathHelper.wrapAngleTo180_float((float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F + (staticYawMode ? staticYawOffset : 0.0F)), pitch);
                                        WVec3 rotationVector1 = RotationUtils.getVectorForRotation(rotation);
                                        WVec3 vector1 = eyesPos.addVector(rotationVector1.getXCoord() * 4.0D, rotationVector1.getYCoord() * 4.0D, rotationVector1.getZCoord() * 4.0D);
                                        IMovingObjectPosition obj1 = Scaffold.mc.getTheWorld().rayTraceBlocks(eyesPos, vector1, false, false, true);

                                        if (obj1.getTypeOfHit() == IMovingObjectPosition.WMovingObjectType.BLOCK && obj1.getBlockPos().equals(neighbor)) {
                                            if (placeRotation == null || RotationUtils.getRotationDifference(rotation) < RotationUtils.getRotationDifference(placeRotation.getRotation())) {
                                                placeRotation = new PlaceRotation(new PlaceInfo(neighbor, side.getOpposite(), hitVec1), rotation);
                                            }

                                            xSearchFace = posVec;
                                            ySearchFace = ySearch;
                                            zSearchFace = rotationVector;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (placeRotation == null) {
                return false;
            } else {
                if (!((String) this.rotationModeValue.get()).equalsIgnoreCase("Off")) {
                    if (((Float) this.minTurnSpeedValue.get()).floatValue() < 180.0F) {
                        this.limitedRotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, placeRotation.getRotation(), (float) (Math.random() * (double) (((Float) this.maxTurnSpeedValue.get()).floatValue() - ((Float) this.minTurnSpeedValue.get()).floatValue()) + (double) ((Float) this.minTurnSpeedValue.get()).floatValue()));
                        this.setRotation(this.limitedRotation, ((Integer) this.keepLengthValue.get()).intValue());
                        this.lockRotation = this.limitedRotation;
                        this.facesBlock = false;
                        aenumfacingtype = EnumFacingType.values();
                        i = aenumfacingtype.length;

                        for (j = 0; j < i; ++j) {
                            facingType = aenumfacingtype[j];
                            side = Scaffold.classProvider.getEnumFacing(facingType);
                            neighbor = blockPosition.offset(side);
                            if (BlockUtils.canBeClicked(neighbor)) {
                                dirVec = new WVec3(side.getDirectionVec());
                                WVec3 wvec3 = (new WVec3(blockPosition)).addVector(xSearchFace, ySearchFace, zSearchFace);
                                double distanceSqPosVec = eyesPos.squareDistanceTo(wvec3);
                                WVec3 hitVec = wvec3.add(new WVec3(dirVec.getXCoord() * 0.5D, dirVec.getYCoord() * 0.5D, dirVec.getZCoord() * 0.5D));

                                if (!checks || eyesPos.squareDistanceTo(hitVec) <= 18.0D && distanceSqPosVec <= eyesPos.squareDistanceTo(wvec3.add(dirVec)) && Scaffold.mc.getTheWorld().rayTraceBlocks(eyesPos, hitVec, false, true, false) == null) {
                                    WVec3 wvec31 = RotationUtils.getVectorForRotation(this.limitedRotation);
                                    WVec3 vector = eyesPos.addVector(wvec31.getXCoord() * 4.0D, wvec31.getYCoord() * 4.0D, wvec31.getZCoord() * 4.0D);
                                    IMovingObjectPosition imovingobjectposition = Scaffold.mc.getTheWorld().rayTraceBlocks(eyesPos, vector, false, false, true);

                                    if (imovingobjectposition.getTypeOfHit() == IMovingObjectPosition.WMovingObjectType.BLOCK && imovingobjectposition.getBlockPos().equals(neighbor)) {
                                        this.facesBlock = true;
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        this.setRotation(placeRotation.getRotation(), ((Integer) this.keepLengthValue.get()).intValue());
                        this.lockRotation = placeRotation.getRotation();
                        this.facesBlock = true;
                    }
                }

                this.targetPlace = placeRotation.getPlaceInfo();
                return true;
            }
        }
    }

    private double calcStepSize(double range) {
        double accuracy = (double) ((Integer) this.searchAccuracyValue.get()).intValue();

        accuracy += accuracy % 2.0D;
        return range / accuracy < 0.01D ? 0.01D : range / accuracy;
    }

    private int getBlocksAmount() {
        int amount = 0;

        for (int i = 36; i < 45; ++i) {
            IItemStack itemStack = Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack();

            if (itemStack != null && Scaffold.classProvider.isItemBlock(itemStack.getItem())) {
                IBlock block = itemStack.getItem().asItemBlock().getBlock();
                IItemStack heldItem = Scaffold.mc.getThePlayer().getHeldItem();

                if (heldItem != null && heldItem.equals(itemStack) || !InventoryUtils.BLOCK_BLACKLIST.contains(block) && !Scaffold.classProvider.isBlockBush(block)) {
                    amount += itemStack.getStackSize();
                }
            }
        }

        return amount;
    }

    public String getTag() {
        return (String) this.modeValue.get();
    }

    public void getBestBlocks() {
        if (this.getBlocksAmount() != 0) {
            int i;
            int count;
            int a;

            if (((Boolean) this.picker.get()).booleanValue()) {
                int is = this.getBiggestBlockSlotInv();

                i = this.getBiggestBlockSlotHotbar();
                int item = this.getBiggestBlockSlotHotbar() > 0 ? this.getBiggestBlockSlotHotbar() : this.getBiggestBlockSlotInv();

                count = 42;
                if (i > 0 && is > 0 && Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(is).getHasStack() && Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getHasStack() && Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack().getStackSize() < Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(is).getStack().getStackSize()) {
                    item = is;
                }

                if (this.hotbarContainBlock()) {
                    for (a = 36; a < 45; ++a) {
                        if (Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(a).getHasStack()) {
                            IItem item1 = Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(a).getStack().getItem();

                            if (item1 instanceof IItemBlock) {
                                count = a;
                                break;
                            }
                        }
                    }
                } else {
                    for (a = 36; a < 45; ++a) {
                        if (!Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(a).getHasStack()) {
                            count = a;
                            break;
                        }
                    }
                }

                if (Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(count).getSlotNumber() != item) {
                    this.swap(item, count - 36);
                    Scaffold.mc.getPlayerController().updateController();
                }
            } else if (this.invCheck()) {
                IItemStack iitemstack = (IItemStack) Scaffold.functions.getItemById(261);

                for (i = 9; i < 36; ++i) {
                    if (Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getHasStack()) {
                        IItem iitem = Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack().getItem();

                        count = 0;
                        if (iitem instanceof IItemBlock) {
                            for (a = 36; a < 45; ++a) {
                                if (Scaffold.functions.canAddItemToSlot(Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(a), iitemstack, true)) {
                                    this.swap(i, a - 36);
                                    ++count;
                                    break;
                                }
                            }

                            if (count == 0) {
                                this.swap(i, 7);
                            }
                            break;
                        }
                    }
                }
            }

        }
    }

    private boolean hotbarContainBlock() {
        int i = 36;

        while (i < 45) {
            try {
                IItemStack stack = Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack();

                if (stack != null && stack.getItem() != null && stack.getItem() instanceof IItemBlock) {
                    return true;
                }

                ++i;
            } catch (Exception exception) {
                ;
            }
        }

        return false;
    }

    public int getBiggestBlockSlotHotbar() {
        int slot = -1;
        int size = 0;

        if (this.getBlocksAmount() == 0) {
            return -1;
        } else {
            for (int i = 36; i < 45; ++i) {
                if (Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getHasStack()) {
                    IItem item = Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack().getItem();
                    IItemStack is = Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack();

                    if (item instanceof IItemBlock && is.getStackSize() > size) {
                        size = is.getStackSize();
                        slot = i;
                    }
                }
            }

            return slot;
        }
    }

    protected void swap(int slot, int hotbarNum) {
        Scaffold.mc.getPlayerController().windowClick(Scaffold.mc.getThePlayer().getInventoryContainer().getWindowId(), slot, hotbarNum, 2, Scaffold.mc.getThePlayer());
    }

    private boolean invCheck() {
        for (int i = 36; i < 45; ++i) {
            if (Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getHasStack()) {
                IItem item = Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack().getItem();

                if (item instanceof IItemBlock) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getBiggestBlockSlotInv() {
        int slot = -1;
        int size = 0;

        if (this.getBlocksAmount() == 0) {
            return -1;
        } else {
            for (int i = 9; i < 36; ++i) {
                if (Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getHasStack()) {
                    IItem item = Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack().getItem();
                    IItemStack is = Scaffold.mc.getThePlayer().getInventoryContainer().getSlot(i).getStack();

                    if (item instanceof IItemBlock && is.getStackSize() > size) {
                        size = is.getStackSize();
                        slot = i;
                    }
                }
            }

            return slot;
        }
    }
}

package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoTool;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "Fucker",
    description = "Destroys selected blocks around you. (aka.  IDNuker)",
    category = ModuleCategory.WORLD
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\'\u001a\u0004\u0018\u00010\u00132\u0006\u0010(\u001a\u00020\u0006J\u0010\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0013H\u0002J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0007J\u0010\u00100\u001a\u00020-2\u0006\u0010.\u001a\u000201H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/world/Fucker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "actionValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "blockHitDelay", "", "blockValue", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "currentDamage", "", "getCurrentDamage", "()F", "setCurrentDamage", "(F)V", "instantValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "noHitValue", "oldPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "pos", "getPos", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "setPos", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;)V", "rangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationsValue", "surroundingsValue", "swingValue", "switchTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "switchValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "tag", "", "getTag", "()Ljava/lang/String;", "throughWallsValue", "find", "targetID", "isHitable", "", "blockPos", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class Fucker extends Module {

    private static final BlockValue blockValue;
    private static final ListValue throughWallsValue;
    private static final FloatValue rangeValue;
    private static final ListValue actionValue;
    private static final BoolValue instantValue;
    private static final IntegerValue switchValue;
    private static final BoolValue swingValue;
    private static final BoolValue rotationsValue;
    private static final BoolValue surroundingsValue;
    private static final BoolValue noHitValue;
    @Nullable
    private static WBlockPos pos;
    private static WBlockPos oldPos;
    private static int blockHitDelay;
    private static final MSTimer switchTimer;
    private static float currentDamage;
    public static final Fucker INSTANCE;

    @Nullable
    public final WBlockPos getPos() {
        return Fucker.pos;
    }

    public final void setPos(@Nullable WBlockPos <set-?>) {
        Fucker.pos = <set-?>;
    }

    public final float getCurrentDamage() {
        return Fucker.currentDamage;
    }

    public final void setCurrentDamage(float <set-?>) {
        Fucker.currentDamage = <set-?>;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            Module module;

            if (((Boolean) Fucker.noHitValue.get()).booleanValue()) {
                module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
                if (module == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
                }

                KillAura targetId = (KillAura) module;

                if (targetId.getState() && targetId.getTarget() != null) {
                    return;
                }
            }

            WBlockPos wblockpos;
            WBlockPos wblockpos1;
            label203: {
                int targetId1 = ((Number) Fucker.blockValue.get()).intValue();

                if (Fucker.pos != null) {
                    IExtractedFunctions iextractedfunctions = MinecraftInstance.functions;

                    wblockpos1 = Fucker.pos;
                    if (Fucker.pos == null) {
                        Intrinsics.throwNpe();
                    }

                    IBlock iblock = BlockUtils.getBlock(wblockpos1);

                    if (iblock == null) {
                        Intrinsics.throwNpe();
                    }

                    if (iextractedfunctions.getIdFromBlock(iblock) == targetId1) {
                        wblockpos = Fucker.pos;
                        if (Fucker.pos == null) {
                            Intrinsics.throwNpe();
                        }

                        if (BlockUtils.getCenterDistance(wblockpos) <= ((Number) Fucker.rangeValue.get()).doubleValue()) {
                            break label203;
                        }
                    }
                }

                Fucker.pos = this.find(targetId1);
            }

            if (Fucker.pos == null) {
                Fucker.currentDamage = 0.0F;
            } else {
                wblockpos = Fucker.pos;
                if (Fucker.pos != null) {
                    WBlockPos currentPos = wblockpos;
                    VecRotation vecrotation = RotationUtils.faceBlock(currentPos);

                    if (vecrotation != null) {
                        VecRotation rotations = vecrotation;
                        boolean surroundings = false;
                        IWorldClient iworldclient;

                        if (((Boolean) Fucker.surroundingsValue.get()).booleanValue()) {
                            WVec3 autoTool = thePlayer.getPositionEyes(1.0F);

                            iworldclient = MinecraftInstance.mc.getTheWorld();
                            if (iworldclient == null) {
                                Intrinsics.throwNpe();
                            }

                            IMovingObjectPosition imovingobjectposition = iworldclient.rayTraceBlocks(autoTool, rotations.getVec(), false, false, true);
                            WBlockPos block = imovingobjectposition != null ? imovingobjectposition.getBlockPos() : null;

                            if (block != null && !MinecraftInstance.classProvider.isBlockAir(block)) {
                                if (currentPos.getX() != block.getX() || currentPos.getY() != block.getY() || currentPos.getZ() != block.getZ()) {
                                    surroundings = true;
                                }

                                Fucker.pos = block;
                                wblockpos = Fucker.pos;
                                if (Fucker.pos == null) {
                                    return;
                                }

                                currentPos = wblockpos;
                                vecrotation = RotationUtils.faceBlock(currentPos);
                                if (vecrotation == null) {
                                    return;
                                }

                                rotations = vecrotation;
                            }
                        }

                        if (Fucker.oldPos != null && Intrinsics.areEqual(Fucker.oldPos, currentPos) ^ true) {
                            Fucker.currentDamage = 0.0F;
                            Fucker.switchTimer.reset();
                        }

                        Fucker.oldPos = currentPos;
                        if (Fucker.switchTimer.hasTimePassed((long) ((Number) Fucker.switchValue.get()).intValue())) {
                            if (Fucker.blockHitDelay > 0) {
                                int autoTool2 = Fucker.blockHitDelay;

                                Fucker.blockHitDelay += -1;
                            } else {
                                if (((Boolean) Fucker.rotationsValue.get()).booleanValue()) {
                                    RotationUtils.setTargetRotation(rotations.getRotation());
                                }

                                IPlayerControllerMP iplayercontrollermp;
                                IWorldClient iworldclient1;

                                if (!StringsKt.equals((String) Fucker.actionValue.get(), "destroy", true) && !surroundings) {
                                    if (StringsKt.equals((String) Fucker.actionValue.get(), "use", true)) {
                                        iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
                                        iworldclient1 = MinecraftInstance.mc.getTheWorld();
                                        if (iworldclient1 == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        IItemStack iitemstack = thePlayer.getHeldItem();

                                        if (iitemstack == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        WBlockPos wblockpos2 = Fucker.pos;

                                        if (Fucker.pos == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        if (iplayercontrollermp.onPlayerRightClick(thePlayer, iworldclient1, iitemstack, wblockpos2, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN), new WVec3((double) currentPos.getX(), (double) currentPos.getY(), (double) currentPos.getZ()))) {
                                            if (((Boolean) Fucker.swingValue.get()).booleanValue()) {
                                                thePlayer.swingItem();
                                            }

                                            Fucker.blockHitDelay = 4;
                                            Fucker.currentDamage = 0.0F;
                                            Fucker.pos = (WBlockPos) null;
                                        }
                                    }
                                } else {
                                    module = LiquidBounce.INSTANCE.getModuleManager().get(AutoTool.class);
                                    if (module == null) {
                                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.AutoTool");
                                    }

                                    AutoTool autoTool1 = (AutoTool) module;

                                    if (autoTool1.getState()) {
                                        autoTool1.switchSlot(currentPos);
                                    }

                                    if (((Boolean) Fucker.instantValue.get()).booleanValue()) {
                                        MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK, currentPos, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                                        if (((Boolean) Fucker.swingValue.get()).booleanValue()) {
                                            thePlayer.swingItem();
                                        }

                                        MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK, currentPos, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                                        Fucker.currentDamage = 0.0F;
                                        return;
                                    }

                                    IBlock iblock1 = currentPos.getBlock();

                                    if (iblock1 == null) {
                                        return;
                                    }

                                    IBlock block1 = iblock1;

                                    if (Fucker.currentDamage == 0.0F) {
                                        label215: {
                                            MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK, currentPos, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                                            if (!thePlayer.getCapabilities().isCreativeMode()) {
                                                iworldclient1 = MinecraftInstance.mc.getTheWorld();
                                                if (iworldclient1 == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                IWorld iworld = (IWorld) iworldclient1;
                                                WBlockPos wblockpos3 = Fucker.pos;

                                                if (Fucker.pos == null) {
                                                    Intrinsics.throwNpe();
                                                }

                                                if (block1.getPlayerRelativeBlockHardness(thePlayer, iworld, wblockpos3) < 1.0F) {
                                                    break label215;
                                                }
                                            }

                                            if (((Boolean) Fucker.swingValue.get()).booleanValue()) {
                                                thePlayer.swingItem();
                                            }

                                            iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
                                            wblockpos1 = Fucker.pos;
                                            if (Fucker.pos == null) {
                                                Intrinsics.throwNpe();
                                            }

                                            iplayercontrollermp.onPlayerDestroyBlock(wblockpos1, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN));
                                            Fucker.currentDamage = 0.0F;
                                            Fucker.pos = (WBlockPos) null;
                                            return;
                                        }
                                    }

                                    if (((Boolean) Fucker.swingValue.get()).booleanValue()) {
                                        thePlayer.swingItem();
                                    }

                                    float f = Fucker.currentDamage;
                                    IWorldClient iworldclient2 = MinecraftInstance.mc.getTheWorld();

                                    if (iworldclient2 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    Fucker.currentDamage = f + block1.getPlayerRelativeBlockHardness(thePlayer, (IWorld) iworldclient2, currentPos);
                                    iworldclient = MinecraftInstance.mc.getTheWorld();
                                    if (iworldclient == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    iworldclient.sendBlockBreakProgress(thePlayer.getEntityId(), currentPos, (int) (Fucker.currentDamage * 10.0F) - 1);
                                    if (Fucker.currentDamage >= 1.0F) {
                                        MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK, currentPos, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                                        MinecraftInstance.mc.getPlayerController().onPlayerDestroyBlock(currentPos, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN));
                                        Fucker.blockHitDelay = 4;
                                        Fucker.currentDamage = 0.0F;
                                        Fucker.pos = (WBlockPos) null;
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        WBlockPos wblockpos = Fucker.pos;

        if (Fucker.pos != null) {
            RenderUtils.drawBlockBox(wblockpos, Color.RED, true);
        }
    }

    @Nullable
    public final WBlockPos find(int targetID) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            return null;
        } else {
            IEntityPlayerSP thePlayer = ientityplayersp;
            int radius = (int) ((Number) Fucker.rangeValue.get()).floatValue() + 1;
            double nearestBlockDistance = DoubleCompanionObject.INSTANCE.getMAX_VALUE();
            WBlockPos nearestBlock = (WBlockPos) null;
            int x = radius;
            int i = -radius + 1;

            if (radius >= i) {
                while (true) {
                    int y = radius;
                    int j = -radius + 1;

                    if (radius >= j) {
                        while (true) {
                            int z = radius;
                            int k = -radius + 1;

                            if (radius >= k) {
                                while (true) {
                                    WBlockPos blockPos = new WBlockPos((int) thePlayer.getPosX() + x, (int) thePlayer.getPosY() + y, (int) thePlayer.getPosZ() + z);
                                    IBlock iblock = BlockUtils.getBlock(blockPos);

                                    if (iblock != null) {
                                        IBlock block = iblock;

                                        if (MinecraftInstance.functions.getIdFromBlock(block) == targetID) {
                                            double distance = BlockUtils.getCenterDistance(blockPos);

                                            if (distance <= ((Number) Fucker.rangeValue.get()).doubleValue() && nearestBlockDistance >= distance && (this.isHitable(blockPos) || ((Boolean) Fucker.surroundingsValue.get()).booleanValue())) {
                                                nearestBlockDistance = distance;
                                                nearestBlock = blockPos;
                                            }
                                        }
                                    }

                                    if (z == k) {
                                        break;
                                    }

                                    --z;
                                }
                            }

                            if (y == j) {
                                break;
                            }

                            --y;
                        }
                    }

                    if (x == i) {
                        break;
                    }

                    --x;
                }
            }

            return nearestBlock;
        }
    }

    private final boolean isHitable(WBlockPos blockPos) {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;
            String s = (String) Fucker.throughWallsValue.get();
            boolean eyesPos = false;

            if (s == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                String s1 = s.toLowerCase();

                Intrinsics.checkExpressionValueIsNotNull(s1, "(this as java.lang.String).toLowerCase()");
                s = s1;
                boolean flag;

                switch (s.hashCode()) {
                case -1409235507:
                    if (s.equals("around")) {
                        flag = !BlockUtils.isFullBlock(blockPos.down()) || !BlockUtils.isFullBlock(blockPos.up()) || !BlockUtils.isFullBlock(blockPos.north()) || !BlockUtils.isFullBlock(blockPos.east()) || !BlockUtils.isFullBlock(blockPos.south()) || !BlockUtils.isFullBlock(blockPos.west());
                        return flag;
                    }
                    break;

                case 988024425:
                    if (s.equals("raycast")) {
                        WVec3 eyesPos1 = new WVec3(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + (double) thePlayer.getEyeHeight(), thePlayer.getPosZ());
                        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                        if (iworldclient == null) {
                            Intrinsics.throwNpe();
                        }

                        IMovingObjectPosition movingObjectPosition = iworldclient.rayTraceBlocks(eyesPos1, new WVec3((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D), false, true, false);

                        flag = movingObjectPosition != null && Intrinsics.areEqual(movingObjectPosition.getBlockPos(), blockPos);
                        return flag;
                    }
                }

                flag = true;
                return flag;
            }
        } else {
            return false;
        }
    }

    @NotNull
    public String getTag() {
        return BlockUtils.getBlockName(((Number) Fucker.blockValue.get()).intValue());
    }

    static {
        Fucker fucker = new Fucker();

        INSTANCE = fucker;
        blockValue = new BlockValue("Block", 26);
        throughWallsValue = new ListValue("ThroughWalls", new String[] { "None", "Raycast", "Around"}, "None");
        rangeValue = new FloatValue("Range", 5.0F, 1.0F, 7.0F);
        actionValue = new ListValue("Action", new String[] { "Destroy", "Use"}, "Destroy");
        instantValue = new BoolValue("Instant", false);
        switchValue = new IntegerValue("SwitchDelay", 250, 0, 1000);
        swingValue = new BoolValue("Swing", true);
        rotationsValue = new BoolValue("Rotations", true);
        surroundingsValue = new BoolValue("Surroundings", true);
        noHitValue = new BoolValue("NoHit", false);
        switchTimer = new MSTimer();
    }
}

package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.BlockExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "ChestAura",
    description = "Automatically opens chests around you.",
    category = ModuleCategory.WORLD
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/world/ChestAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chestValue", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "clickedBlocks", "", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getClickedBlocks", "()Ljava/util/List;", "currentBlock", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "rangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "throughWallsValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "visualSwing", "onDisable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidBounce"}
)
public final class ChestAura extends Module {

    private static final FloatValue rangeValue;
    private static final IntegerValue delayValue;
    private static final BoolValue throughWallsValue;
    private static final BoolValue visualSwing;
    private static final BlockValue chestValue;
    private static final BoolValue rotationsValue;
    private static WBlockPos currentBlock;
    private static final MSTimer timer;
    @NotNull
    private static final List clickedBlocks;
    public static final ChestAura INSTANCE;

    @NotNull
    public final List getClickedBlocks() {
        return ChestAura.clickedBlocks;
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (!LiquidBounce.INSTANCE.getModuleManager().get(Blink.class).getState()) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);

            if (module == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
            }

            if (!((KillAura) module).isBlockingChestAura()) {
                IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                if (ientityplayersp == null) {
                    Intrinsics.throwNpe();
                }

                IEntityPlayerSP thePlayer = ientityplayersp;
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                IWorldClient theWorld = iworldclient;

                switch (ChestAura$WhenMappings.$EnumSwitchMapping$0[event.getEventState().ordinal()]) {
                case 1:
                    if (MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.mc.getCurrentScreen())) {
                        ChestAura.timer.reset();
                    }

                    float radius = ((Number) ChestAura.rangeValue.get()).floatValue() + (float) 1;
                    WVec3 eyesPos = new WVec3(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + (double) thePlayer.getEyeHeight(), thePlayer.getPosZ());
                    Map $this$minBy$iv = BlockUtils.searchBlocks((int) radius);
                    boolean $i$f$minBy = false;
                    Map $i$f$minBy1 = (Map) (new LinkedHashMap());
                    boolean iterator$iv$iv = false;
                    boolean minValue$iv$iv = false;
                    Iterator e$iv$iv = $this$minBy$iv.entrySet().iterator();

                    Entry v$iv$iv;
                    boolean $i$a$-filter-ChestAura$onMotion$2;

                    while (e$iv$iv.hasNext()) {
                        v$iv$iv = (Entry) e$iv$iv.next();
                        $i$a$-filter-ChestAura$onMotion$2 = false;
                        if (MinecraftInstance.functions.getIdFromBlock((IBlock) v$iv$iv.getValue()) == ((Number) ChestAura.chestValue.get()).intValue() && !ChestAura.clickedBlocks.contains(v$iv$iv.getKey()) && BlockUtils.getCenterDistance((WBlockPos) v$iv$iv.getKey()) < ((Number) ChestAura.rangeValue.get()).doubleValue()) {
                            $i$f$minBy1.put(v$iv$iv.getKey(), v$iv$iv.getValue());
                        }
                    }

                    $i$f$minBy = false;
                    Map $this$minBy$iv$iv = $i$f$minBy1;

                    $i$f$minBy1 = (Map) (new LinkedHashMap());
                    iterator$iv$iv = false;
                    minValue$iv$iv = false;
                    e$iv$iv = $this$minBy$iv$iv.entrySet().iterator();

                    while (e$iv$iv.hasNext()) {
                        v$iv$iv = (Entry) e$iv$iv.next();
                        $i$a$-filter-ChestAura$onMotion$2 = false;
                        boolean flag;

                        if (((Boolean) ChestAura.throughWallsValue.get()).booleanValue()) {
                            flag = true;
                        } else {
                            WBlockPos blockPos = (WBlockPos) v$iv$iv.getKey();
                            IMovingObjectPosition movingObjectPosition = theWorld.rayTraceBlocks(eyesPos, BlockExtensionKt.getVec(blockPos), false, true, false);

                            flag = movingObjectPosition != null && Intrinsics.areEqual(movingObjectPosition.getBlockPos(), blockPos);
                        }

                        if (flag) {
                            $i$f$minBy1.put(v$iv$iv.getKey(), v$iv$iv.getValue());
                        }
                    }

                    $i$f$minBy = false;
                    Iterable $this$minBy$iv$iv1 = (Iterable) $i$f$minBy1.entrySet();
                    boolean $i$f$minBy2 = false;
                    Iterator iterator$iv$iv1 = $this$minBy$iv$iv1.iterator();
                    Object object;

                    if (!iterator$iv$iv1.hasNext()) {
                        object = null;
                    } else {
                        Object minElem$iv$iv = iterator$iv$iv1.next();

                        if (!iterator$iv$iv1.hasNext()) {
                            object = minElem$iv$iv;
                        } else {
                            Entry minValue$iv$iv1 = (Entry) minElem$iv$iv;
                            boolean e$iv$iv1 = false;
                            double minValue$iv$iv2 = BlockUtils.getCenterDistance((WBlockPos) minValue$iv$iv1.getKey());

                            do {
                                Object e$iv$iv2 = iterator$iv$iv1.next();

                                v$iv$iv = (Entry) e$iv$iv2;
                                boolean $i$a$-minBy-ChestAura$onMotion$3 = false;
                                double v$iv$iv1 = BlockUtils.getCenterDistance((WBlockPos) v$iv$iv.getKey());

                                if (Double.compare(minValue$iv$iv2, v$iv$iv1) > 0) {
                                    minElem$iv$iv = e$iv$iv2;
                                    minValue$iv$iv2 = v$iv$iv1;
                                }
                            } while (iterator$iv$iv1.hasNext());

                            object = minElem$iv$iv;
                        }
                    }

                    ChestAura.currentBlock = (Entry) object != null ? (WBlockPos) ((Entry) object).getKey() : null;
                    if (((Boolean) ChestAura.rotationsValue.get()).booleanValue()) {
                        WBlockPos wblockpos = ChestAura.currentBlock;

                        if (ChestAura.currentBlock == null) {
                            return;
                        }

                        VecRotation vecrotation = RotationUtils.faceBlock(wblockpos);

                        if (vecrotation == null) {
                            return;
                        }

                        RotationUtils.setTargetRotation(vecrotation.getRotation());
                    }
                    break;

                case 2:
                    if (ChestAura.currentBlock != null && ChestAura.timer.hasTimePassed((long) ((Number) ChestAura.delayValue.get()).intValue())) {
                        IPlayerControllerMP iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
                        IWorldClient iworldclient1 = MinecraftInstance.mc.getTheWorld();

                        if (iworldclient1 == null) {
                            Intrinsics.throwNpe();
                        }

                        IItemStack iitemstack = thePlayer.getHeldItem();
                        WBlockPos wblockpos1 = ChestAura.currentBlock;

                        if (ChestAura.currentBlock == null) {
                            Intrinsics.throwNpe();
                        }

                        IEnumFacing ienumfacing = MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN);
                        WBlockPos wblockpos2 = ChestAura.currentBlock;

                        if (ChestAura.currentBlock == null) {
                            Intrinsics.throwNpe();
                        }

                        if (iplayercontrollermp.onPlayerRightClick(thePlayer, iworldclient1, iitemstack, wblockpos1, ienumfacing, BlockExtensionKt.getVec(wblockpos2))) {
                            if (((Boolean) ChestAura.visualSwing.get()).booleanValue()) {
                                thePlayer.swingItem();
                            } else {
                                MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketAnimation());
                            }

                            List list = ChestAura.clickedBlocks;
                            WBlockPos wblockpos3 = ChestAura.currentBlock;

                            if (ChestAura.currentBlock == null) {
                                Intrinsics.throwNpe();
                            }

                            list.add(wblockpos3);
                            ChestAura.currentBlock = (WBlockPos) null;
                            ChestAura.timer.reset();
                        }
                    }
                }

                return;
            }
        }

    }

    public void onDisable() {
        ChestAura.clickedBlocks.clear();
    }

    static {
        ChestAura chestaura = new ChestAura();

        INSTANCE = chestaura;
        rangeValue = new FloatValue("Range", 5.0F, 1.0F, 6.0F);
        delayValue = new IntegerValue("Delay", 100, 50, 200);
        throughWallsValue = new BoolValue("ThroughWalls", true);
        visualSwing = new BoolValue("VisualSwing", true);
        chestValue = new BlockValue("Chest", MinecraftInstance.functions.getIdFromBlock(MinecraftInstance.classProvider.getBlockEnum(BlockType.CHEST)));
        rotationsValue = new BoolValue("Rotations", true);
        timer = new MSTimer();
        boolean flag = false;

        clickedBlocks = (List) (new ArrayList());
    }
}

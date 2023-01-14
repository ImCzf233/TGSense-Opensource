package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
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
import net.ccbluex.liquidbounce.features.module.modules.player.AutoTool;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "Nuker",
    description = "Breaks all blocks around you.",
    category = ModuleCategory.WORLD
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/world/Nuker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackedBlocks", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "Lkotlin/collections/ArrayList;", "blockHitDelay", "", "currentBlock", "hitDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "layerValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "nuke", "nukeDelay", "nukeTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "nukeValue", "priorityValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "radiusValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationsValue", "throughWallsValue", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "validBlock", "", "block", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "Companion", "LiquidBounce"}
)
public final class Nuker extends Module {

    private final FloatValue radiusValue = new FloatValue("Radius", 5.2F, 1.0F, 6.0F);
    private final BoolValue throughWallsValue = new BoolValue("ThroughWalls", false);
    private final ListValue priorityValue = new ListValue("Priority", new String[] { "Distance", "Hardness"}, "Distance");
    private final BoolValue rotationsValue = new BoolValue("Rotations", true);
    private final BoolValue layerValue = new BoolValue("Layer", false);
    private final IntegerValue hitDelayValue = new IntegerValue("HitDelay", 4, 0, 20);
    private final IntegerValue nukeValue = new IntegerValue("Nuke", 1, 1, 20);
    private final IntegerValue nukeDelay = new IntegerValue("NukeDelay", 1, 1, 20);
    private final ArrayList attackedBlocks;
    private WBlockPos currentBlock;
    private int blockHitDelay;
    private TickTimer nukeTimer;
    private int nuke;
    private static float currentDamage;
    public static final Nuker.Companion Companion = new Nuker.Companion((DefaultConstructorMarker) null);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        Module module;

        if (this.blockHitDelay > 0) {
            module = LiquidBounce.INSTANCE.getModuleManager().get(FastBreak.class);
            if (module == null) {
                Intrinsics.throwNpe();
            }

            if (!module.getState()) {
                int thePlayer1 = this.blockHitDelay;

                this.blockHitDelay += -1;
                return;
            }
        }

        this.nukeTimer.update();
        if (this.nukeTimer.hasTimePassed(((Number) this.nukeDelay.get()).intValue())) {
            this.nuke = 0;
            this.nukeTimer.reset();
        }

        this.attackedBlocks.clear();
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp == null) {
            Intrinsics.throwNpe();
        }

        IEntityPlayerSP thePlayer = ientityplayersp;
        Map $this$forEach$iv;
        boolean $dstr$pos$_u24__u24;
        boolean $i$a$-forEach-Nuker$onUpdate$4;
        WVec3 blockVec;
        boolean blockVec1;
        boolean flag;
        IWorldClient iworldclient;

        if (!MinecraftInstance.mc.getPlayerController().isInCreativeMode()) {
            Map $i$f$forEach = BlockUtils.searchBlocks(MathKt.roundToInt(((Number) this.radiusValue.get()).floatValue()) + 1);
            boolean $this$filterTo$iv$iv = false;
            Map $i$f$filterTo = (Map) (new LinkedHashMap());
            boolean element$iv = false;

            $i$a$-forEach-Nuker$onUpdate$4 = false;
            Iterator element$iv$iv = $i$f$forEach.entrySet().iterator();

            while (element$iv$iv.hasNext()) {
                Entry $dstr$pos$block = (Entry) element$iv$iv.next();
                boolean eyesPos = false;
                boolean pos1 = false;
                WBlockPos block = (WBlockPos) $dstr$pos$block.getKey();

                pos1 = false;
                IBlock rayTrace = (IBlock) $dstr$pos$block.getValue();

                if (BlockUtils.getCenterDistance(block) <= ((Number) this.radiusValue.get()).doubleValue() && this.validBlock(rayTrace)) {
                    if (((Boolean) this.layerValue.get()).booleanValue() && (double) block.getY() < thePlayer.getPosY()) {
                        flag = false;
                    } else if (!((Boolean) this.throughWallsValue.get()).booleanValue()) {
                        blockVec = new WVec3(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + (double) thePlayer.getEyeHeight(), thePlayer.getPosZ());
                        WVec3 pos5 = new WVec3((double) block.getX() + 0.5D, (double) block.getY() + 0.5D, (double) block.getZ() + 0.5D);

                        iworldclient = MinecraftInstance.mc.getTheWorld();
                        if (iworldclient == null) {
                            Intrinsics.throwNpe();
                        }

                        IMovingObjectPosition block1 = iworldclient.rayTraceBlocks(blockVec, pos5, false, true, false);

                        flag = block1 != null && Intrinsics.areEqual(block1.getBlockPos(), block);
                    } else {
                        flag = true;
                    }
                } else {
                    flag = false;
                }

                if (flag) {
                    $i$f$filterTo.put($dstr$pos$block.getKey(), $dstr$pos$block.getValue());
                }
            }

            $this$forEach$iv = MapsKt.toMutableMap($i$f$filterTo);

            do {
                Entry entry;
                IWorldClient iworldclient1;
                label285: {
                    String $i$f$filterTo1 = (String) this.priorityValue.get();
                    Object pos;
                    double hardness;
                    WBlockPos safePos;
                    WBlockPos pos2;
                    IBlock block2;
                    double hardness1;
                    WBlockPos safePos1;
                    Iterable $i$a$-forEach-Nuker$onUpdate$41;
                    boolean element$iv$iv1;
                    Iterator $dstr$pos$block1;
                    Entry eyesPos1;
                    double eyesPos2;
                    Object blockVec2;
                    Entry pos6;
                    double pos7;
                    boolean block3;
                    WBlockPos rayTrace1;
                    IBlock block5;
                    boolean block6;
                    Object object;

                    switch ($i$f$filterTo1.hashCode()) {
                    case 181289442:
                        if ($i$f$filterTo1.equals("Hardness")) {
                            $dstr$pos$_u24__u24 = false;
                            $i$a$-forEach-Nuker$onUpdate$41 = (Iterable) $this$forEach$iv.entrySet();
                            element$iv$iv1 = false;
                            $dstr$pos$block1 = $i$a$-forEach-Nuker$onUpdate$41.iterator();
                            if (!$dstr$pos$block1.hasNext()) {
                                object = null;
                            } else {
                                pos = $dstr$pos$block1.next();
                                if (!$dstr$pos$block1.hasNext()) {
                                    object = pos;
                                } else {
                                    eyesPos1 = (Entry) pos;
                                    blockVec1 = false;
                                    block3 = false;
                                    rayTrace1 = (WBlockPos) eyesPos1.getKey();
                                    block3 = false;
                                    block5 = (IBlock) eyesPos1.getValue();
                                    iworldclient1 = MinecraftInstance.mc.getTheWorld();
                                    if (iworldclient1 == null) {
                                        Intrinsics.throwNpe();
                                    }

                                    hardness = (double) block5.getPlayerRelativeBlockHardness(thePlayer, (IWorld) iworldclient1, rayTrace1);
                                    safePos = new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - (double) 1, thePlayer.getPosZ());
                                    eyesPos2 = rayTrace1.getX() == safePos.getX() && safePos.getY() <= rayTrace1.getY() && rayTrace1.getZ() == safePos.getZ() ? DoubleCompanionObject.INSTANCE.getMIN_VALUE() + hardness : hardness;

                                    do {
                                        blockVec2 = $dstr$pos$block1.next();
                                        pos6 = (Entry) blockVec2;
                                        block3 = false;
                                        block6 = false;
                                        pos2 = (WBlockPos) pos6.getKey();
                                        block6 = false;
                                        block2 = (IBlock) pos6.getValue();
                                        iworldclient1 = MinecraftInstance.mc.getTheWorld();
                                        if (iworldclient1 == null) {
                                            Intrinsics.throwNpe();
                                        }

                                        hardness1 = (double) block2.getPlayerRelativeBlockHardness(thePlayer, (IWorld) iworldclient1, pos2);
                                        safePos1 = new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - (double) 1, thePlayer.getPosZ());
                                        pos7 = pos2.getX() == safePos1.getX() && safePos1.getY() <= pos2.getY() && pos2.getZ() == safePos1.getZ() ? DoubleCompanionObject.INSTANCE.getMIN_VALUE() + hardness1 : hardness1;
                                        if (Double.compare(eyesPos2, pos7) < 0) {
                                            pos = blockVec2;
                                            eyesPos2 = pos7;
                                        }
                                    } while ($dstr$pos$block1.hasNext());

                                    object = pos;
                                }
                            }

                            entry = (Entry) object;
                            break label285;
                        }
                        break;

                    case 353103893:
                        if ($i$f$filterTo1.equals("Distance")) {
                            $dstr$pos$_u24__u24 = false;
                            $i$a$-forEach-Nuker$onUpdate$41 = (Iterable) $this$forEach$iv.entrySet();
                            element$iv$iv1 = false;
                            $dstr$pos$block1 = $i$a$-forEach-Nuker$onUpdate$41.iterator();
                            if (!$dstr$pos$block1.hasNext()) {
                                object = null;
                            } else {
                                pos = $dstr$pos$block1.next();
                                if (!$dstr$pos$block1.hasNext()) {
                                    object = pos;
                                } else {
                                    eyesPos1 = (Entry) pos;
                                    blockVec1 = false;
                                    block3 = false;
                                    rayTrace1 = (WBlockPos) eyesPos1.getKey();
                                    block3 = false;
                                    block5 = (IBlock) eyesPos1.getValue();
                                    hardness = BlockUtils.getCenterDistance(rayTrace1);
                                    safePos = new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - (double) 1, thePlayer.getPosZ());
                                    eyesPos2 = rayTrace1.getX() == safePos.getX() && safePos.getY() <= rayTrace1.getY() && rayTrace1.getZ() == safePos.getZ() ? DoubleCompanionObject.INSTANCE.getMAX_VALUE() - hardness : hardness;

                                    do {
                                        blockVec2 = $dstr$pos$block1.next();
                                        pos6 = (Entry) blockVec2;
                                        block3 = false;
                                        block6 = false;
                                        pos2 = (WBlockPos) pos6.getKey();
                                        block6 = false;
                                        block2 = (IBlock) pos6.getValue();
                                        hardness1 = BlockUtils.getCenterDistance(pos2);
                                        safePos1 = new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - (double) 1, thePlayer.getPosZ());
                                        pos7 = pos2.getX() == safePos1.getX() && safePos1.getY() <= pos2.getY() && pos2.getZ() == safePos1.getZ() ? DoubleCompanionObject.INSTANCE.getMAX_VALUE() - hardness1 : hardness1;
                                        if (Double.compare(eyesPos2, pos7) > 0) {
                                            pos = blockVec2;
                                            eyesPos2 = pos7;
                                        }
                                    } while ($dstr$pos$block1.hasNext());

                                    object = pos;
                                }
                            }

                            entry = (Entry) object;
                            break label285;
                        }
                    }

                    return;
                }

                if (entry == null) {
                    return;
                }

                Entry destination$iv$iv = entry;

                element$iv = false;
                WBlockPos $i$f$forEach1 = (WBlockPos) destination$iv$iv.getKey();

                element$iv = false;
                IBlock $this$filterTo$iv$iv1 = (IBlock) destination$iv$iv.getValue();

                if (Intrinsics.areEqual($i$f$forEach1, this.currentBlock) ^ true) {
                    Nuker.currentDamage = 0.0F;
                }

                if (((Boolean) this.rotationsValue.get()).booleanValue()) {
                    VecRotation vecrotation = RotationUtils.faceBlock($i$f$forEach1);

                    if (vecrotation == null) {
                        return;
                    }

                    VecRotation destination$iv$iv1 = vecrotation;

                    RotationUtils.setTargetRotation(destination$iv$iv1.getRotation());
                }

                this.currentBlock = $i$f$forEach1;
                this.attackedBlocks.add($i$f$forEach1);
                module = LiquidBounce.INSTANCE.getModuleManager().getModule(AutoTool.class);
                if (module == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.AutoTool");
                }

                AutoTool destination$iv$iv2 = (AutoTool) module;

                if (destination$iv$iv2.getState()) {
                    destination$iv$iv2.switchSlot($i$f$forEach1);
                }

                if (Nuker.currentDamage == 0.0F) {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK, $i$f$forEach1, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                    iworldclient1 = MinecraftInstance.mc.getTheWorld();
                    if (iworldclient1 == null) {
                        Intrinsics.throwNpe();
                    }

                    if ($this$filterTo$iv$iv1.getPlayerRelativeBlockHardness(thePlayer, (IWorld) iworldclient1, $i$f$forEach1) >= 1.0F) {
                        Nuker.currentDamage = 0.0F;
                        thePlayer.swingItem();
                        MinecraftInstance.mc.getPlayerController().onPlayerDestroyBlock($i$f$forEach1, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN));
                        this.blockHitDelay = ((Number) this.hitDelayValue.get()).intValue();
                        element$iv = false;
                        $this$forEach$iv.remove($i$f$forEach1);
                        int $i$f$filterTo2 = this.nuke++;
                        continue;
                    }
                }

                thePlayer.swingItem();
                float f = Nuker.currentDamage;
                IWorldClient iworldclient2 = MinecraftInstance.mc.getTheWorld();

                if (iworldclient2 == null) {
                    Intrinsics.throwNpe();
                }

                Nuker.currentDamage = f + $this$filterTo$iv$iv1.getPlayerRelativeBlockHardness(thePlayer, (IWorld) iworldclient2, $i$f$forEach1);
                iworldclient = MinecraftInstance.mc.getTheWorld();
                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                iworldclient.sendBlockBreakProgress(thePlayer.getEntityId(), $i$f$forEach1, (int) (Nuker.currentDamage * 10.0F) - 1);
                if (Nuker.currentDamage >= 1.0F) {
                    MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK, $i$f$forEach1, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                    MinecraftInstance.mc.getPlayerController().onPlayerDestroyBlock($i$f$forEach1, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN));
                    this.blockHitDelay = ((Number) this.hitDelayValue.get()).intValue();
                    Nuker.currentDamage = 0.0F;
                }

                return;
            } while (this.nuke < ((Number) this.nukeValue.get()).intValue());
        } else {
            IClassProvider iclassprovider = MinecraftInstance.classProvider;
            IItemStack iitemstack = thePlayer.getHeldItem();

            if (iclassprovider.isItemSword(iitemstack != null ? iitemstack.getItem() : null)) {
                return;
            }

            $this$forEach$iv = BlockUtils.searchBlocks(MathKt.roundToInt(((Number) this.radiusValue.get()).floatValue()) + 1);
            boolean $i$f$forEach2 = false;
            Map destination$iv$iv3 = (Map) (new LinkedHashMap());
            boolean $i$f$filterTo3 = false;

            $dstr$pos$_u24__u24 = false;
            Iterator $i$a$-forEach-Nuker$onUpdate$42 = $this$forEach$iv.entrySet().iterator();

            while ($i$a$-forEach-Nuker$onUpdate$42.hasNext()) {
                Entry element$iv$iv2 = (Entry) $i$a$-forEach-Nuker$onUpdate$42.next();
                boolean pos3 = false;

                blockVec1 = false;
                WBlockPos pos8 = (WBlockPos) element$iv$iv2.getKey();

                blockVec1 = false;
                IBlock block4 = (IBlock) element$iv$iv2.getValue();

                if (BlockUtils.getCenterDistance(pos8) <= ((Number) this.radiusValue.get()).doubleValue() && this.validBlock(block4)) {
                    if (((Boolean) this.layerValue.get()).booleanValue() && (double) pos8.getY() < thePlayer.getPosY()) {
                        flag = false;
                    } else if (!((Boolean) this.throughWallsValue.get()).booleanValue()) {
                        WVec3 eyesPos3 = new WVec3(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + (double) thePlayer.getEyeHeight(), thePlayer.getPosZ());

                        blockVec = new WVec3((double) pos8.getX() + 0.5D, (double) pos8.getY() + 0.5D, (double) pos8.getZ() + 0.5D);
                        iworldclient = MinecraftInstance.mc.getTheWorld();
                        if (iworldclient == null) {
                            Intrinsics.throwNpe();
                        }

                        IMovingObjectPosition rayTrace2 = iworldclient.rayTraceBlocks(eyesPos3, blockVec, false, true, false);

                        flag = rayTrace2 != null && Intrinsics.areEqual(rayTrace2.getBlockPos(), pos8);
                    } else {
                        flag = true;
                    }
                } else {
                    flag = false;
                }

                if (flag) {
                    destination$iv$iv3.put(element$iv$iv2.getKey(), element$iv$iv2.getValue());
                }
            }

            $i$f$forEach2 = false;
            Map $this$filterTo$iv$iv2 = destination$iv$iv3;
            boolean destination$iv$iv4 = false;
            Iterator $i$f$filterTo4 = $this$filterTo$iv$iv2.entrySet().iterator();

            while ($i$f$filterTo4.hasNext()) {
                Entry element$iv1 = (Entry) $i$f$filterTo4.next();

                $i$a$-forEach-Nuker$onUpdate$4 = false;
                boolean $dstr$pos$block2 = false;
                WBlockPos pos4 = (WBlockPos) element$iv1.getKey();

                MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK, pos4, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                thePlayer.swingItem();
                MinecraftInstance.mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK, pos4, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                this.attackedBlocks.add(pos4);
            }
        }

    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        WBlockPos blockPos;

        if (!((Boolean) this.layerValue.get()).booleanValue()) {
            WBlockPos wblockpos = new WBlockPos;
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            double d0 = ientityplayersp.getPosX();
            IEntityPlayerSP ientityplayersp1 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp1 == null) {
                Intrinsics.throwNpe();
            }

            double d1 = ientityplayersp1.getPosY() - (double) 1;
            IEntityPlayerSP ientityplayersp2 = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp2 == null) {
                Intrinsics.throwNpe();
            }

            wblockpos.<init>(d0, d1, ientityplayersp2.getPosZ());
            blockPos = wblockpos;
            IBlock safeBlock = BlockUtils.getBlock(blockPos);

            if (safeBlock != null && this.validBlock(safeBlock)) {
                RenderUtils.drawBlockBox(blockPos, Color.GREEN, true);
            }
        }

        Iterator safeBlock1 = this.attackedBlocks.iterator();

        while (safeBlock1.hasNext()) {
            blockPos = (WBlockPos) safeBlock1.next();
            RenderUtils.drawBlockBox(blockPos, Color.RED, true);
        }

    }

    private final boolean validBlock(IBlock block) {
        return !MinecraftInstance.classProvider.isBlockAir(block) && !MinecraftInstance.classProvider.isBlockLiquid(block) && !MinecraftInstance.classProvider.isBlockBedrock(block);
    }

    public Nuker() {
        boolean flag = false;
        ArrayList arraylist = new ArrayList();

        this.attackedBlocks = arraylist;
        this.nukeTimer = new TickTimer();
    }

    @Metadata(
        mv = { 1, 1, 16},
        bv = { 1, 0, 3},
        k = 1,
        d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"},
        d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/world/Nuker$Companion;", "", "()V", "currentDamage", "", "getCurrentDamage", "()F", "setCurrentDamage", "(F)V", "LiquidBounce"}
    )
    public static final class Companion {

        public final float getCurrentDamage() {
            return Nuker.currentDamage;
        }

        public final void setCurrentDamage(float <set-?>) {
            Nuker.currentDamage = <set-?>;
        }

        private Companion() {}

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

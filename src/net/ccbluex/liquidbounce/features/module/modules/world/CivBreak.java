package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.ClickBlockEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "CivBreak",
    description = "Allows you to break blocks instantly.",
    category = ModuleCategory.WORLD
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/world/CivBreak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "airResetValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "enumFacing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "range", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rangeResetValue", "rotationsValue", "visualSwingValue", "onBlockClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickBlockEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidBounce"}
)
public final class CivBreak extends Module {

    private WBlockPos blockPos;
    private IEnumFacing enumFacing;
    private final FloatValue range = new FloatValue("Range", 5.0F, 1.0F, 6.0F);
    private final BoolValue rotationsValue = new BoolValue("Rotations", true);
    private final BoolValue visualSwingValue = new BoolValue("VisualSwing", true);
    private final BoolValue airResetValue = new BoolValue("Air-Reset", true);
    private final BoolValue rangeResetValue = new BoolValue("Range-Reset", true);

    @EventTarget
    public final void onBlockClick(@NotNull ClickBlockEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IClassProvider iclassprovider = MinecraftInstance.classProvider;
        WBlockPos wblockpos = event.getClickedBlock();
        IBlock iblock;

        if (wblockpos != null) {
            WBlockPos wblockpos1 = wblockpos;
            IClassProvider iclassprovider1 = iclassprovider;
            boolean flag = false;
            boolean flag1 = false;
            boolean $i$a$-let-CivBreak$onBlockClick$1 = false;
            IBlock iblock1 = BlockUtils.getBlock(wblockpos1);

            iclassprovider = iclassprovider1;
            iblock = iblock1;
        } else {
            iblock = null;
        }

        if (!iclassprovider.isBlockBedrock(iblock)) {
            wblockpos = event.getClickedBlock();
            if (wblockpos != null) {
                this.blockPos = wblockpos;
                IEnumFacing ienumfacing = event.getWEnumFacing();

                if (ienumfacing != null) {
                    this.enumFacing = ienumfacing;
                    IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    IClassProvider iclassprovider2 = MinecraftInstance.classProvider;
                    ICPacketPlayerDigging.WAction icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK;
                    WBlockPos wblockpos2 = this.blockPos;

                    if (this.blockPos == null) {
                        Intrinsics.throwNpe();
                    }

                    IEnumFacing ienumfacing1 = this.enumFacing;

                    if (this.enumFacing == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue(iclassprovider2.createCPacketPlayerDigging(icpacketplayerdigging_waction, wblockpos2, ienumfacing1));
                    iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                    iclassprovider2 = MinecraftInstance.classProvider;
                    icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK;
                    wblockpos2 = this.blockPos;
                    if (this.blockPos == null) {
                        Intrinsics.throwNpe();
                    }

                    ienumfacing1 = this.enumFacing;
                    if (this.enumFacing == null) {
                        Intrinsics.throwNpe();
                    }

                    iinethandlerplayclient.addToSendQueue(iclassprovider2.createCPacketPlayerDigging(icpacketplayerdigging_waction, wblockpos2, ienumfacing1));
                }
            }
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        WBlockPos wblockpos = this.blockPos;

        if (this.blockPos != null) {
            WBlockPos pos = wblockpos;

            if ((!((Boolean) this.airResetValue.get()).booleanValue() || !MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(pos))) && (!((Boolean) this.rangeResetValue.get()).booleanValue() || BlockUtils.getCenterDistance(pos) <= ((Number) this.range.get()).doubleValue())) {
                if (!MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(pos)) && BlockUtils.getCenterDistance(pos) <= ((Number) this.range.get()).doubleValue()) {
                    switch (CivBreak$WhenMappings.$EnumSwitchMapping$0[event.getEventState().ordinal()]) {
                    case 1:
                        if (((Boolean) this.rotationsValue.get()).booleanValue()) {
                            VecRotation vecrotation = RotationUtils.faceBlock(pos);

                            if (vecrotation == null) {
                                return;
                            }

                            RotationUtils.setTargetRotation(vecrotation.getRotation());
                        }
                        break;

                    case 2:
                        if (((Boolean) this.visualSwingValue.get()).booleanValue()) {
                            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

                            if (ientityplayersp == null) {
                                Intrinsics.throwNpe();
                            }

                            ientityplayersp.swingItem();
                        } else {
                            MinecraftInstance.mc.getNetHandler().addToSendQueue((IPacket) MinecraftInstance.classProvider.createCPacketAnimation());
                        }

                        IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                        IClassProvider iclassprovider = MinecraftInstance.classProvider;
                        ICPacketPlayerDigging.WAction icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK;
                        WBlockPos wblockpos1 = this.blockPos;

                        if (this.blockPos == null) {
                            Intrinsics.throwNpe();
                        }

                        IEnumFacing ienumfacing = this.enumFacing;

                        if (this.enumFacing == null) {
                            Intrinsics.throwNpe();
                        }

                        iinethandlerplayclient.addToSendQueue(iclassprovider.createCPacketPlayerDigging(icpacketplayerdigging_waction, wblockpos1, ienumfacing));
                        iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                        iclassprovider = MinecraftInstance.classProvider;
                        icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK;
                        wblockpos1 = this.blockPos;
                        if (this.blockPos == null) {
                            Intrinsics.throwNpe();
                        }

                        ienumfacing = this.enumFacing;
                        if (this.enumFacing == null) {
                            Intrinsics.throwNpe();
                        }

                        iinethandlerplayclient.addToSendQueue(iclassprovider.createCPacketPlayerDigging(icpacketplayerdigging_waction, wblockpos1, ienumfacing));
                        IPlayerControllerMP iplayercontrollermp = MinecraftInstance.mc.getPlayerController();
                        WBlockPos wblockpos2 = this.blockPos;

                        if (this.blockPos == null) {
                            Intrinsics.throwNpe();
                        }

                        IEnumFacing ienumfacing1 = this.enumFacing;

                        if (this.enumFacing == null) {
                            Intrinsics.throwNpe();
                        }

                        iplayercontrollermp.clickBlock(wblockpos2, ienumfacing1);
                    }

                }
            } else {
                this.blockPos = (WBlockPos) null;
            }
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        WBlockPos wblockpos = this.blockPos;

        if (this.blockPos != null) {
            RenderUtils.drawBlockBox(wblockpos, Color.RED, true);
        }
    }
}

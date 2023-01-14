package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
    name = "SpeedMine",
    description = "faq",
    category = ModuleCategory.WORLD
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0012\u0010\u0017\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0018H\u0007R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0019"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/player/SpeedMine;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getBlockPos", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "setBlockPos", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;)V", "bzs", "", "bzx", "", "facing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "getFacing", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "setFacing", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;)V", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class SpeedMine extends Module {

    private boolean bzs;
    private float bzx;
    @Nullable
    private WBlockPos blockPos;
    @Nullable
    private IEnumFacing facing;

    @Nullable
    public final WBlockPos getBlockPos() {
        return this.blockPos;
    }

    public final void setBlockPos(@Nullable WBlockPos <set-?>) {
        this.blockPos = <set-?>;
    }

    @Nullable
    public final IEnumFacing getFacing() {
        return this.facing;
    }

    public final void setFacing(@Nullable IEnumFacing <set-?>) {
        this.facing = <set-?>;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();

        if (MinecraftInstance.classProvider.isCPacketPlayerDigging(packet)) {
            IPacket packets = (IPacket) packet.asCPacketPlayerDigging();

            if (packet.asCPacketPlayerDigging().getAction() == ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK) {
                this.bzs = true;
                this.blockPos = packets.asCPacketPlayerDigging().getPosition();
                this.facing = packet.asCPacketPlayerDigging().getFacing();
                this.bzx = 0.0F;
            } else if (packet.asCPacketPlayerDigging().getAction() == ICPacketPlayerDigging.WAction.ABORT_DESTROY_BLOCK || packet.asCPacketPlayerDigging().getAction() == ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK) {
                this.bzs = false;
                this.blockPos = (WBlockPos) null;
                this.facing = (IEnumFacing) null;
            }
        }

    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        if (MinecraftInstance.mc.getPlayerController().extendedReach()) {
            MinecraftInstance.mc.getPlayerController().setBlockHitDelay(0);
        } else if (this.bzs) {
            IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

            if (iworldclient == null) {
                Intrinsics.throwNpe();
            }

            WBlockPos wblockpos = this.blockPos;

            if (this.blockPos == null) {
                Intrinsics.throwNpe();
            }

            IBlock block = iworldclient.getBlockState(wblockpos).getBlock();
            float f = this.bzx;
            IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            IWorldClient iworldclient1 = MinecraftInstance.mc.getTheWorld();

            if (iworldclient1 == null) {
                Intrinsics.throwNpe();
            }

            IWorld iworld = (IWorld) iworldclient1;
            WBlockPos wblockpos1 = this.blockPos;

            if (this.blockPos == null) {
                Intrinsics.throwNpe();
            }

            this.bzx = f + (float) ((double) block.getPlayerRelativeBlockHardness(ientityplayersp, iworld, wblockpos1) * 1.4D);
            if (this.bzx >= 1.0F) {
                iworldclient = MinecraftInstance.mc.getTheWorld();
                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                wblockpos = this.blockPos;
                Block block = Blocks.AIR;

                Intrinsics.checkExpressionValueIsNotNull(Blocks.AIR, "Blocks.AIR");
                iworldclient.setBlockState(wblockpos, block.getDefaultState(), 11);
                IINetHandlerPlayClient iinethandlerplayclient = MinecraftInstance.mc.getNetHandler();
                IClassProvider iclassprovider = MinecraftInstance.classProvider;
                ICPacketPlayerDigging.WAction icpacketplayerdigging_waction = ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK;
                WBlockPos wblockpos2 = this.blockPos;

                if (this.blockPos == null) {
                    Intrinsics.throwNpe();
                }

                IEnumFacing ienumfacing = this.facing;

                if (this.facing == null) {
                    Intrinsics.throwNpe();
                }

                iinethandlerplayclient.addToSendQueue(iclassprovider.createCPacketPlayerDigging(icpacketplayerdigging_waction, wblockpos2, ienumfacing));
                this.bzx = 0.0F;
                this.bzs = false;
            }
        }

    }
}

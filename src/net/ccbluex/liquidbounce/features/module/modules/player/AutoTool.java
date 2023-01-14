package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.ClickBlockEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AutoTool",
    description = "Automatically selects the best tool in your inventory to mine a block.",
    category = ModuleCategory.PLAYER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t¨\u0006\n"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoTool;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickBlockEvent;", "switchSlot", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "LiquidBounce"}
)
public final class AutoTool extends Module {

    @EventTarget
    public final void onClick(@NotNull ClickBlockEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        WBlockPos wblockpos = event.getClickedBlock();

        if (wblockpos != null) {
            this.switchSlot(wblockpos);
        }
    }

    public final void switchSlot(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        float bestSpeed = 1.0F;
        int bestSlot = -1;
        IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

        if (iworldclient == null) {
            Intrinsics.throwNpe();
        }

        IIBlockState blockState = iworldclient.getBlockState(blockPos);
        int i = 0;

        IEntityPlayerSP ientityplayersp;

        for (byte b0 = 8; i <= b0; ++i) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            IItemStack iitemstack = ientityplayersp.getInventory().getStackInSlot(i);

            if (iitemstack != null) {
                IItemStack item = iitemstack;
                float speed = item.getStrVsBlock(blockState);

                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestSlot = i;
                }
            }
        }

        if (bestSlot != -1) {
            ientityplayersp = MinecraftInstance.mc.getThePlayer();
            if (ientityplayersp == null) {
                Intrinsics.throwNpe();
            }

            ientityplayersp.getInventory().setCurrentItem(bestSlot);
        }

    }
}

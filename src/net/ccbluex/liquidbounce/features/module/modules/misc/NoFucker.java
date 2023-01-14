package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.Fucker;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BlockValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "NoFucker",
    description = "CNM",
    category = ModuleCategory.MISC
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/misc/NoFucker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blockValue", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "pos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "targetId", "", "getTargetId", "()I", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class NoFucker extends Module {

    private final BlockValue blockValue = new BlockValue("Block", 26);
    private final int targetId;
    private WBlockPos pos;

    public final int getTargetId() {
        return this.targetId;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        label27: {
            Intrinsics.checkParameterIsNotNull(event, "event");
            if (this.pos != null) {
                IExtractedFunctions iextractedfunctions = MinecraftInstance.functions;
                WBlockPos wblockpos = this.pos;

                if (this.pos == null) {
                    Intrinsics.throwNpe();
                }

                IBlock iblock = BlockUtils.getBlock(wblockpos);

                if (iblock == null) {
                    Intrinsics.throwNpe();
                }

                if (iextractedfunctions.getIdFromBlock(iblock) == this.targetId) {
                    WBlockPos wblockpos1 = Fucker.INSTANCE.getPos();

                    if (wblockpos1 == null) {
                        Intrinsics.throwNpe();
                    }

                    if (BlockUtils.getCenterDistance(wblockpos1) <= (double) 7) {
                        break label27;
                    }
                }
            }

            this.pos = Fucker.INSTANCE.find(this.targetId);
        }

        LiquidBounce.INSTANCE.getHud().addNotification(new Notification("NoFucker", "已标记您的床", NotifyType.SUCCESS, 0, 0, 24, (DefaultConstructorMarker) null));
    }

    public NoFucker() {
        this.targetId = ((Number) this.blockValue.get()).intValue();
    }
}

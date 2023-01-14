package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "BlockWalk",
    description = "Allows you to walk on non-fullblock blocks.",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/BlockWalk;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "cobwebValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "snowValue", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "LiquidBounce"}
)
public final class BlockWalk extends Module {

    private final BoolValue cobwebValue = new BoolValue("Cobweb", true);
    private final BoolValue snowValue = new BoolValue("Snow", true);

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (((Boolean) this.cobwebValue.get()).booleanValue() && Intrinsics.areEqual(event.getBlock(), MinecraftInstance.classProvider.getBlockEnum(BlockType.WEB)) || ((Boolean) this.snowValue.get()).booleanValue() && Intrinsics.areEqual(event.getBlock(), MinecraftInstance.classProvider.getBlockEnum(BlockType.SNOW_LAYER))) {
            event.setBoundingBox(MinecraftInstance.classProvider.createAxisAlignedBB((double) event.getX(), (double) event.getY(), (double) event.getZ(), (double) event.getX() + 1.0D, (double) event.getY() + 1.0D, (double) event.getZ() + 1.0D));
        }

    }
}

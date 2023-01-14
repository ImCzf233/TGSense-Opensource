package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AutoBreak",
    description = "Automatically breaks the block you are looking at.",
    category = ModuleCategory.WORLD
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/world/AutoBreak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class AutoBreak extends Module {

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.mc.getObjectMouseOver() != null) {
            IMovingObjectPosition imovingobjectposition = MinecraftInstance.mc.getObjectMouseOver();

            if (imovingobjectposition == null) {
                Intrinsics.throwNpe();
            }

            if (imovingobjectposition.getBlockPos() != null && MinecraftInstance.mc.getTheWorld() != null) {
                IKeyBinding ikeybinding = MinecraftInstance.mc.getGameSettings().getKeyBindAttack();
                IWorldClient iworldclient = MinecraftInstance.mc.getTheWorld();

                if (iworldclient == null) {
                    Intrinsics.throwNpe();
                }

                IMovingObjectPosition imovingobjectposition1 = MinecraftInstance.mc.getObjectMouseOver();

                if (imovingobjectposition1 == null) {
                    Intrinsics.throwNpe();
                }

                WBlockPos wblockpos = imovingobjectposition1.getBlockPos();

                if (wblockpos == null) {
                    Intrinsics.throwNpe();
                }

                ikeybinding.setPressed(Intrinsics.areEqual(iworldclient.getBlockState(wblockpos).getBlock(), MinecraftInstance.classProvider.getBlockEnum(BlockType.AIR)) ^ true);
                return;
            }
        }

    }

    public void onDisable() {
        if (!MinecraftInstance.mc.getGameSettings().isKeyDown(MinecraftInstance.mc.getGameSettings().getKeyBindAttack())) {
            MinecraftInstance.mc.getGameSettings().getKeyBindAttack().setPressed(false);
        }

    }
}

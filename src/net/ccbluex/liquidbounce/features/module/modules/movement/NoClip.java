package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "NoClip",
    description = "Allows you to freely move through walls (A sandblock has to fall on your head).",
    category = ModuleCategory.MOVEMENT
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoClip;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class NoClip extends Module {

    public void onDisable() {
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            ientityplayersp.setNoClip(false);
        }

    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP ientityplayersp = MinecraftInstance.mc.getThePlayer();

        if (ientityplayersp != null) {
            IEntityPlayerSP thePlayer = ientityplayersp;

            thePlayer.setNoClip(true);
            thePlayer.setFallDistance(0.0F);
            thePlayer.setOnGround(false);
            thePlayer.getCapabilities().setFlying(false);
            thePlayer.setMotionX(0.0D);
            thePlayer.setMotionY(0.0D);
            thePlayer.setMotionZ(0.0D);
            float speed = 0.32F;

            thePlayer.setJumpMovementFactor(speed);
            if (MinecraftInstance.mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                thePlayer.setMotionY(thePlayer.getMotionY() + (double) speed);
            }

            if (MinecraftInstance.mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                thePlayer.setMotionY(thePlayer.getMotionY() - (double) speed);
            }

        }
    }
}

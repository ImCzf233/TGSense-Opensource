package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Ghost;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
    name = "AutoRespawn",
    description = "Automatically respawns you after dying.",
    category = ModuleCategory.PLAYER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoRespawn;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "instantValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidBounce"}
)
public final class AutoRespawn extends Module {

    private final BoolValue instantValue = new BoolValue("Instant", true);

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.mc.getThePlayer();

        if (thePlayer != null) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(Ghost.class);

            if (module == null) {
                Intrinsics.throwNpe();
            }

            if (!module.getState()) {
                boolean flag;

                if (((Boolean) this.instantValue.get()).booleanValue()) {
                    flag = thePlayer.getHealth() == 0.0F || thePlayer.isDead();
                } else {
                    label31: {
                        if (MinecraftInstance.classProvider.isGuiGameOver(MinecraftInstance.mc.getCurrentScreen())) {
                            IGuiScreen iguiscreen = MinecraftInstance.mc.getCurrentScreen();

                            if (iguiscreen == null) {
                                Intrinsics.throwNpe();
                            }

                            if (iguiscreen.asGuiGameOver().getEnableButtonsTimer() >= 20) {
                                flag = true;
                                break label31;
                            }
                        }

                        flag = false;
                    }
                }

                if (flag) {
                    thePlayer.respawnPlayer();
                    MinecraftInstance.mc.displayGuiScreen((IGuiScreen) null);
                }

                return;
            }
        }

    }
}

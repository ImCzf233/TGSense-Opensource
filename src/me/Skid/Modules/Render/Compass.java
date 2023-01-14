package me.Skid.Modules.Render;

import kotlin.Metadata;
import me.Skid.utils.CompassUtil;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

@ModuleInfo(
    name = "JelloCompass",
    description = "Show the compass for u.",
    category = ModuleCategory.RENDER
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0003¨\u0006\u0007"},
    d2 = { "Lme/Skid/Modules/Render/Compass;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onRender2D", "", "e", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "LiquidBounce"}
)
public final class Compass extends Module {

    @EventTarget
    private final void onRender2D(Render2DEvent e) {
        CompassUtil cpass = new CompassUtil(325.0F, 325.0F, 1.0F, 2, true);
        ScaledResolution sc = new ScaledResolution(Minecraft.getMinecraft());

        cpass.draw(sc);
    }
}

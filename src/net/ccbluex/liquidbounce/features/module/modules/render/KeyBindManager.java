package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.gui.GuiScreen;

@ModuleInfo(
    name = "KeyBindManager",
    description = "按键绑定管理",
    category = ModuleCategory.RENDER,
    keyBind = 184,
    canEnable = false
)
@Metadata(
    mv = { 1, 1, 16},
    bv = { 1, 0, 3},
    k = 1,
    d1 = { "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"},
    d2 = { "Lnet/ccbluex/liquidbounce/features/module/modules/render/KeyBindManager;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onEnable", "", "LiquidBounce"}
)
public final class KeyBindManager extends Module {

    public void onEnable() {
        MinecraftInstance.mc2.displayGuiScreen((GuiScreen) LiquidBounce.INSTANCE.getKeyBindManager());
    }
}

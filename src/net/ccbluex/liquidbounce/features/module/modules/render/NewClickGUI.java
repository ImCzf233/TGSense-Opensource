package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.client.clickgui.HaloClickGui;

@ModuleInfo(
    name = "NewClickGUI",
    description = "更好的ClickGUI",
    category = ModuleCategory.RENDER,
    canEnable = false,
    keyBind = 23
)
public class NewClickGUI extends Module {

    public void onEnable() {
        NewClickGUI.mc2.displayGuiScreen(new HaloClickGui());
        super.onEnable();
    }
}

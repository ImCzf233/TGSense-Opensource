package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.FloatValue;

@ModuleInfo(
    name = "Animations",
    description = "Animation for blocking.",
    category = ModuleCategory.RENDER
)
public class Animations extends Module {

    public static FloatValue itemPosX = new FloatValue("itemPosX", 0.21F, -1.0F, 1.0F);
    public static FloatValue itemPosY = new FloatValue("itemPosY", 0.1F, -1.0F, 1.0F);
    public static FloatValue itemPosZ = new FloatValue("itemPosZ", 0.07F, -1.0F, 1.0F);
    public static FloatValue Scale = new FloatValue("Scale", 1.03F, 0.0F, 2.0F);
}

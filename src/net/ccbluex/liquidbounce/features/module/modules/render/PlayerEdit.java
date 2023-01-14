package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;

@ModuleInfo(
    name = "PlayerEdit",
    description = "Edit the player.",
    category = ModuleCategory.PLAYER
)
public class PlayerEdit extends Module {

    public static FloatValue playerSizeValue = new FloatValue("PlayerSize", 0.5F, 0.01F, 5.0F);
    public static BoolValue editPlayerSizeValue = new BoolValue("EditPlayerSize", true);
}

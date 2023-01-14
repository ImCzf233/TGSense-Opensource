package net.ccbluex.liquidbounce.features.module.modules.render;

import me.cn.hanabi.gui.cloudmusic.ui.MusicPlayerUI;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;

@ModuleInfo(
    name = "MusicPlayer",
    description = "网易云播放器",
    category = ModuleCategory.RENDER,
    canEnable = false
)
public class MusicPlayer extends Module {

    public void onEnable() {
        MusicPlayer.mc2.displayGuiScreen(new MusicPlayerUI());
        super.onEnable();
    }
}

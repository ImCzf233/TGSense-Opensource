package net.ccbluex.liquidbounce.ui.client.clickgui.style;

import net.ccbluex.liquidbounce.ui.client.clickgui.Panel;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ButtonElement;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

public abstract class Style extends MinecraftInstance {

    public abstract void drawPanel(int i, int j, Panel panel);

    public abstract void drawDescription(int i, int j, String s);

    public abstract void drawButtonElement(int i, int j, ButtonElement buttonelement);

    public abstract void drawModuleElement(int i, int j, ModuleElement moduleelement);
}

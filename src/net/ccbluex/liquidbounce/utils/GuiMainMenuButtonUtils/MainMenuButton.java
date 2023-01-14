package net.ccbluex.liquidbounce.utils.GuiMainMenuButtonUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class MainMenuButton extends GuiButton {

    public MainMenuButton(int buttonId, int x, int y, int width, int height, String buttonText) {
        super(buttonId, x, y, width, height, buttonText);
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}
}

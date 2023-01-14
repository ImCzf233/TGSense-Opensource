package net.ccbluex.liquidbounce.ui.client.clickgui.elements;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class ModuleElement extends ButtonElement {

    private final Module module;
    private boolean showSettings;
    private float settingsWidth = 0.0F;
    private boolean wasPressed;
    public int slowlySettingsYPos;
    public int slowlyFade;

    public ModuleElement(Module module) {
        super((String) null);
        this.displayName = module.getName();
        this.module = module;
    }

    public void drawScreen(int mouseX, int mouseY, float button) {
        LiquidBounce.clickGui.style.drawModuleElement(mouseX, mouseY, this);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY) && this.isVisible()) {
            this.module.toggle();
            ModuleElement.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
        }

        if (mouseButton == 1 && this.isHovering(mouseX, mouseY) && this.isVisible()) {
            this.showSettings = !this.showSettings;
            ModuleElement.mc.getSoundHandler().playSound("gui.button.press", 1.0F);
        }

    }

    public Module getModule() {
        return this.module;
    }

    public boolean isShowSettings() {
        return this.showSettings;
    }

    public void setShowSettings(boolean showSettings) {
        this.showSettings = showSettings;
    }

    public boolean isntPressed() {
        return !this.wasPressed;
    }

    public void updatePressed() {
        this.wasPressed = Mouse.isButtonDown(0);
    }

    public float getSettingsWidth() {
        return this.settingsWidth;
    }

    public void setSettingsWidth(float settingsWidth) {
        this.settingsWidth = settingsWidth;
    }
}

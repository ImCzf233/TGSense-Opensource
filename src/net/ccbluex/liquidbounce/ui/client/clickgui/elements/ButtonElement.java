package net.ccbluex.liquidbounce.ui.client.clickgui.elements;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ButtonElement extends Element {

    protected String displayName;
    protected int color = 16777215;
    public int hoverTime;

    public ButtonElement(String displayName) {
        this.createButton(displayName);
    }

    public void createButton(String displayName) {
        this.displayName = displayName;
    }

    public void drawScreen(int mouseX, int mouseY, float button) {
        LiquidBounce.clickGui.style.drawButtonElement(mouseX, mouseY, this);
        super.drawScreen(mouseX, mouseY, button);
    }

    public int getHeight() {
        return 16;
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + 16;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }
}

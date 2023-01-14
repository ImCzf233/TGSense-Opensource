package me.Skid.ui;

import java.awt.Color;
import me.Skid.ui.Fonts.FontManager;
import net.ccbluex.liquidbounce.utils.render.Colors;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class Button {

    String name;
    int x;
    int y;
    GuiScreen guiScreen;
    public boolean isHovered = false;
    public float hoverAni = 0.0F;
    String icon;

    public Button(String name, GuiScreen guiScreen, String icon) {
        this.name = name;
        this.guiScreen = guiScreen;
        this.icon = icon;
    }

    public void draw(int x, int y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;
        this.isHovered = this.isHovered((float) x, (float) y, (float) (x + 120), (float) (y + 18), mouseX, mouseY);
        this.hoverAni = (float) RenderUtils.getAnimationState((double) this.hoverAni, this.isHovered ? 255.0D : 0.0D, 500.0D);
        float finalAni = clampValue(this.hoverAni / 100.0F, 0.0F, 1.0F);

        RenderUtils.drawRect((float) (x - 1), (float) (y - 1), (float) (x + 121), (float) (y + 19), new Color(64, 64, 64));
        RenderUtils.drawShadow(x - 1, y - 1, 122, 20);
        RenderUtils.drawShadow(x - 1, y - 1, 122, 20);
        if (this.hoverAni > 1.0F) {
            RenderUtils.drawRect((float) (x - 1), (float) (y - 1), (float) (x + 121), (float) (y + 19), new Color(64, 64, 64));
            RenderUtils.drawShadow(x - 1, y - 1, 122, 20);
            RenderUtils.drawShadow(x - 1, y - 1, 122, 20);
        } else {
            RenderUtils.drawRect((float) (x - 1), (float) (y - 1), (float) (x + 121), (float) (y + 19), new Color(64, 64, 64));
            RenderUtils.drawShadow(x - 1, y - 1, 122, 20);
            RenderUtils.drawShadow(x - 1, y - 1, 122, 20);
        }

        RenderUtils.drawRoundRect((float) x, (float) y, (float) (x + 120), (float) (y + 18), (new Color(22, 22, 22, 255)).getRGB());
        FontManager.SFREGULAR18.drawCenteredStringWithShadow(this.name, (float) x + 60.0F, (float) y + 9.0F - FontManager.SFREGULAR18.getHeight(this.name) / 2.0F, Colors.WHITE.c);
    }

    public void onClick() {
        if (this.isHovered) {
            if (this.guiScreen == null) {
                Minecraft.getMinecraft().shutdown();
            } else {
                Minecraft.getMinecraft().displayGuiScreen(this.guiScreen);
            }
        }

    }

    public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float) mouseX >= x && (float) mouseX <= x2 && (float) mouseY >= y && (float) mouseY <= y2;
    }

    public static float clampValue(float value, float floor, float cap) {
        return value < floor ? floor : Math.min(value, cap);
    }
}

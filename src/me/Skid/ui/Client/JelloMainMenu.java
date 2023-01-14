package me.Skid.ui.Client;

import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import me.Skid.utils.DrawUtils;
import net.ccbluex.liquidbounce.injection.backend.ClassProviderImpl;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class JelloMainMenu extends GuiScreen {

    private float animatedMouseX;
    private float animatedMouseY;
    private boolean hovered;
    private double val;
    private double max;

    public void initGui() {
        this.animatedMouseX = 0.0F;
        this.animatedMouseY = 0.0F;
        this.val = 0.0D;
        this.max = 8.0D;
        super.initGui();
    }

    public void updateScreen() {
        double scaleVal = 2.0D;

        if (this.hovered) {
            if (this.val < 8.0D || this.val < 0.0D) {
                this.val += scaleVal;
            }
        } else if (this.val == this.max || this.val > this.max) {
            this.val -= scaleVal;
        }

        super.updateScreen();
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("langya/jellomenu.png"));
        DrawUtils.drawModalRectWithCustomSizedTexture(-this.animatedMouseX / 1.43F, -this.animatedMouseY / 10.15F, 0.0F, 0.0F, (double) this.width * 1.7D, (double) this.height * 1.1D, (double) this.width * 1.7D, (double) this.height * 1.1D);
        Fonts.fontSFUI35.drawString("©Jello Client made by leakedPVP", 4.0F, (float) this.height - 12.0F, (new Color(255, 255, 255, 200)).getRGB(), true);
        double posX = (double) (this.width / 2 - 153);
        byte idk = 64;
        String[] astring = new String[] { "singleplayer", "multiplayer", "connect", "settings", "alt"};
        int i = astring.length;

        for (int j = 0; j < i; ++j) {
            String name = astring[j];
            boolean hovered = DrawUtils.isHovered(posX + 8.0D, (double) (this.height / 2 + 13), posX + 56.0D, (double) (this.height / 2 + 61), mouseX, mouseY);
            boolean hoveAndClicked = hovered && Mouse.isButtonDown(0);
            double daVal = this.val;
            boolean nameuh = name.equals("singleplayer") || name.equals("multiplayer") || name.equals("connect") || name.equals("settings") || name.equals("alt");
            double x = posX + (hovered ? (nameuh ? -(daVal / 2.0D) : 0.0D) : 0.0D);
            double y = (double) (this.height / 2 + 5) + (hovered ? (nameuh ? -daVal : 0.0D) : 0.0D);
            double wi = (double) idk + (hovered ? (nameuh ? daVal : 0.0D) : 0.0D);
            double he = (double) idk + (hovered ? (nameuh ? daVal : 0.0D) : 0.0D);

            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GL11.glDepthMask(false);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GlStateManager.color(hoveAndClicked ? 0.9F : 1.0F, hoveAndClicked ? 0.9F : 1.0F, hoveAndClicked ? 0.9F : 1.0F, 1.0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("langya/mainmenu/" + name + ".png"));
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            DrawUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0D, 0.0D, wi, he, wi, he);
            GL11.glDepthMask(true);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
            if (hovered) {
                Fonts.fontSFUI35.drawString(name, (float) (posX + 32.0D), (float) (this.height / 2 + 75), (new Color(255, 255, 255, 180)).getRGB(), false);
                this.hovered = true;
            } else {
                this.hovered = false;
            }

            posX += 61.0D;
        }

        this.animatedMouseX = (float) mouseX;
        this.animatedMouseY = (float) mouseY;
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        double posX = (double) (this.width / 2) - 152.5D;
        String[] astring = new String[] { "singleplayer", "multiplayer", "connect", "settings", "alt"};
        int i = astring.length;

        for (int j = 0; j < i; ++j) {
            String name = astring[j];

            if (DrawUtils.isHovered(posX + 8.0D, (double) (this.height / 2 + 13), posX + 56.0D, (double) (this.height / 2 + 61), mouseX, mouseY) && mouseButton == 0) {
                byte b0 = -1;

                switch (name.hashCode()) {
                case -1664825047:
                    if (name.equals("singleplayer")) {
                        b0 = 0;
                    }
                    break;

                case 96681:
                    if (name.equals("alt")) {
                        b0 = 4;
                    }
                    break;

                case 562356570:
                    if (name.equals("multiplayer")) {
                        b0 = 1;
                    }
                    break;

                case 951351530:
                    if (name.equals("connect")) {
                        b0 = 2;
                    }
                    break;

                case 1434631203:
                    if (name.equals("settings")) {
                        b0 = 3;
                    }
                }

                switch (b0) {
                case 0:
                    MinecraftInstance.mc2.displayGuiScreen(new GuiWorldSelection(this));
                    break;

                case 1:
                    this.mc.displayGuiScreen(new GuiMultiplayer(this));
                    break;

                case 2:
                    try {
                        Desktop.getDesktop().browse(new URI("http://anrantoday.shop/"));
                    } catch (URISyntaxException urisyntaxexception) {
                        urisyntaxexception.printStackTrace();
                    }
                    break;

                case 3:
                    this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                    break;

                case 4:
                    MinecraftInstance.mc.displayGuiScreen(ClassProviderImpl.INSTANCE.wrapGuiScreen(new GuiAltManager()));
                }
            }

            posX += 61.0D;
        }

    }
}

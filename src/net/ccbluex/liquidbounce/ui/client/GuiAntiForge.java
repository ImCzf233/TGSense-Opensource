package net.ccbluex.liquidbounce.ui.client;

import java.io.IOException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.ui.font.Fonts;

public class GuiAntiForge extends WrappedGuiScreen {

    private final IGuiScreen prevGui;
    private IGuiButton enabledButton;
    private IGuiButton fmlButton;
    private IGuiButton proxyButton;
    private IGuiButton payloadButton;

    public GuiAntiForge(IGuiScreen prevGui) {
        this.prevGui = prevGui;
    }

    public void initGui() {
        this.representedScreen.getButtonList().add(this.enabledButton = GuiAntiForge.classProvider.createGuiButton(1, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 35, "Enabled (" + (AntiForge.enabled ? "On" : "Off") + ")"));
        this.representedScreen.getButtonList().add(this.fmlButton = GuiAntiForge.classProvider.createGuiButton(2, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 50 + 25, "Block FML (" + (AntiForge.blockFML ? "On" : "Off") + ")"));
        this.representedScreen.getButtonList().add(this.proxyButton = GuiAntiForge.classProvider.createGuiButton(3, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 50 + 50, "Block FML Proxy Packet (" + (AntiForge.blockProxyPacket ? "On" : "Off") + ")"));
        this.representedScreen.getButtonList().add(this.payloadButton = GuiAntiForge.classProvider.createGuiButton(4, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 50 + 75, "Block Payload Packets (" + (AntiForge.blockPayloadPackets ? "On" : "Off") + ")"));
        this.representedScreen.getButtonList().add(GuiAntiForge.classProvider.createGuiButton(0, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 55 + 100 + 5, "Back"));
    }

    public void actionPerformed(IGuiButton button) {
        switch (button.getId()) {
        case 0:
            GuiAntiForge.mc.displayGuiScreen(this.prevGui);
            break;

        case 1:
            AntiForge.enabled = !AntiForge.enabled;
            this.enabledButton.setDisplayString("Enabled (" + (AntiForge.enabled ? "On" : "Off") + ")");
            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
            break;

        case 2:
            AntiForge.blockFML = !AntiForge.blockFML;
            this.fmlButton.setDisplayString("Block FML (" + (AntiForge.blockFML ? "On" : "Off") + ")");
            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
            break;

        case 3:
            AntiForge.blockProxyPacket = !AntiForge.blockProxyPacket;
            this.proxyButton.setDisplayString("Block FML Proxy Packet (" + (AntiForge.blockProxyPacket ? "On" : "Off") + ")");
            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
            break;

        case 4:
            AntiForge.blockPayloadPackets = !AntiForge.blockPayloadPackets;
            this.payloadButton.setDisplayString("Block Payload Packets (" + (AntiForge.blockPayloadPackets ? "On" : "Off") + ")");
            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
        }

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.representedScreen.drawBackground(0);
        Fonts.fontBold180.drawCenteredString("AntiForge", (float) ((int) ((float) this.representedScreen.getWidth() / 2.0F)), (float) ((int) ((float) this.representedScreen.getHeight() / 8.0F + 5.0F)), 4673984, true);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (1 == keyCode) {
            GuiAntiForge.mc.displayGuiScreen(this.prevGui);
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }
}

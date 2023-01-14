package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.io.IOException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.lwjgl.input.Keyboard;

public class GuiChangeName extends WrappedGuiScreen {

    private final GuiAltManager prevGui;
    private IGuiTextField name;
    private String status;

    public GuiChangeName(GuiAltManager gui) {
        this.prevGui = gui;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.representedScreen.getButtonList().add(GuiChangeName.classProvider.createGuiButton(1, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 96, "Change"));
        this.representedScreen.getButtonList().add(GuiChangeName.classProvider.createGuiButton(0, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 120, "Back"));
        this.name = GuiChangeName.classProvider.createGuiTextField(2, Fonts.font40, this.representedScreen.getWidth() / 2 - 100, 60, 200, 20);
        this.name.setFocused(true);
        this.name.setText(GuiChangeName.mc.getSession().getUsername());
        this.name.setMaxStringLength(16);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.representedScreen.drawBackground(0);
        RenderUtils.drawRect(30, 30, this.representedScreen.getWidth() - 30, this.representedScreen.getHeight() - 30, Integer.MIN_VALUE);
        Fonts.font40.drawCenteredString("Change Name", (float) this.representedScreen.getWidth() / 2.0F, 34.0F, 16777215);
        Fonts.font40.drawCenteredString(this.status == null ? "" : this.status, (float) this.representedScreen.getWidth() / 2.0F, (float) this.representedScreen.getHeight() / 4.0F + 84.0F, 16777215);
        this.name.drawTextBox();
        if (this.name.getText().isEmpty() && !this.name.isFocused()) {
            Fonts.font40.drawCenteredString("§7Username", (float) this.representedScreen.getWidth() / 2.0F - 74.0F, 66.0F, 16777215);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(IGuiButton button) throws IOException {
        switch (button.getId()) {
        case 0:
            GuiChangeName.mc.displayGuiScreen(this.prevGui.representedScreen);
            break;

        case 1:
            if (this.name.getText().isEmpty()) {
                this.status = "§cEnter a name!";
                return;
            }

            if (!this.name.getText().equalsIgnoreCase(GuiChangeName.mc.getSession().getUsername())) {
                this.status = "§cJust change the upper and lower case!";
                return;
            }

            GuiChangeName.mc.setSession(GuiChangeName.classProvider.createSession(this.name.getText(), GuiChangeName.mc.getSession().getPlayerId(), GuiChangeName.mc.getSession().getToken(), GuiChangeName.mc.getSession().getSessionType()));
            LiquidBounce.eventManager.callEvent(new SessionEvent());
            this.status = "§aChanged name to §7" + this.name.getText() + "§c.";
            this.prevGui.status = this.status;
            GuiChangeName.mc.displayGuiScreen(this.prevGui.representedScreen);
        }

    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (1 == keyCode) {
            GuiChangeName.mc.displayGuiScreen(this.prevGui.getRepresentedScreen());
        } else {
            if (this.name.isFocused()) {
                this.name.textboxKeyTyped(typedChar, keyCode);
            }

            super.keyTyped(typedChar, keyCode);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.name.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void updateScreen() {
        this.name.updateCursorCounter();
        super.updateScreen();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }
}

package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.TabUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.lwjgl.input.Keyboard;

public class GuiDirectLogin extends WrappedGuiScreen {

    private final IGuiScreen prevGui;
    private IGuiButton loginButton;
    private IGuiButton clipboardLoginButton;
    private IGuiTextField username;
    private IGuiTextField password;
    private String status = "§7Idle...";

    public GuiDirectLogin(GuiAltManager gui) {
        this.prevGui = gui.representedScreen;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.getRepresentedScreen().getButtonList().add(this.loginButton = GuiDirectLogin.classProvider.createGuiButton(1, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 72, "Login"));
        this.getRepresentedScreen().getButtonList().add(this.clipboardLoginButton = GuiDirectLogin.classProvider.createGuiButton(2, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 96, "Clipboard Login"));
        this.getRepresentedScreen().getButtonList().add(GuiDirectLogin.classProvider.createGuiButton(0, this.getRepresentedScreen().getWidth() / 2 - 100, this.getRepresentedScreen().getHeight() / 4 + 120, "Back"));
        this.username = GuiDirectLogin.classProvider.createGuiTextField(2, Fonts.font40, this.getRepresentedScreen().getWidth() / 2 - 100, 60, 200, 20);
        this.username.setFocused(true);
        this.username.setMaxStringLength(Integer.MAX_VALUE);
        this.password = GuiDirectLogin.classProvider.createGuiPasswordField(3, Fonts.font40, this.getRepresentedScreen().getWidth() / 2 - 100, 85, 200, 20);
        this.password.setMaxStringLength(Integer.MAX_VALUE);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.getRepresentedScreen().drawBackground(0);
        RenderUtils.drawRect(30, 30, this.getRepresentedScreen().getWidth() - 30, this.getRepresentedScreen().getHeight() - 30, Integer.MIN_VALUE);
        Fonts.font40.drawCenteredString("Direct Login", (float) this.getRepresentedScreen().getWidth() / 2.0F, 34.0F, 16777215);
        Fonts.font35.drawCenteredString(this.status == null ? "" : this.status, (float) this.getRepresentedScreen().getWidth() / 2.0F, (float) this.getRepresentedScreen().getHeight() / 4.0F + 60.0F, 16777215);
        this.username.drawTextBox();
        this.password.drawTextBox();
        if (this.username.getText().isEmpty() && !this.username.isFocused()) {
            Fonts.font40.drawCenteredString("§7Username / E-Mail", (float) this.getRepresentedScreen().getWidth() / 2.0F - 55.0F, 66.0F, 16777215);
        }

        if (this.password.getText().isEmpty() && !this.password.isFocused()) {
            Fonts.font40.drawCenteredString("§7Password", (float) this.getRepresentedScreen().getWidth() / 2.0F - 74.0F, 91.0F, 16777215);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(IGuiButton button) throws IOException {
        if (button.getEnabled()) {
            switch (button.getId()) {
            case 0:
                GuiDirectLogin.mc.displayGuiScreen(this.prevGui);
                break;

            case 1:
                if (this.username.getText().isEmpty()) {
                    this.status = "§cYou have to fill in both fields!";
                    return;
                }

                this.loginButton.setEnabled(false);
                this.clipboardLoginButton.setEnabled(false);
                (new Thread(() -> {
                    // $FF: Couldn't be decompiled
                })).start();
                break;

            case 2:
                try {
                    String e = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    String[] args = e.split(":", 2);

                    if (!e.contains(":") || args.length != 2) {
                        this.status = "§cInvalid clipboard data. (Use: E-Mail:Password)";
                        return;
                    }

                    this.loginButton.setEnabled(false);
                    this.clipboardLoginButton.setEnabled(false);
                    (new Thread(() -> {
                        // $FF: Couldn't be decompiled
                    })).start();
                } catch (UnsupportedFlavorException unsupportedflavorexception) {
                    this.status = "§cClipboard flavor unsupported!";
                    ClientUtils.getLogger().error("Failed to read data from clipboard.", unsupportedflavorexception);
                } catch (IOException ioexception) {
                    this.status = "§cUnknown error! (See log)";
                    ClientUtils.getLogger().error(ioexception);
                }
            }

        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        switch (keyCode) {
        case 1:
            GuiDirectLogin.mc.displayGuiScreen(this.prevGui);
            return;

        case 15:
            TabUtils.tab(new IGuiTextField[] { this.username, this.password});
            return;

        case 28:
            this.actionPerformed(this.loginButton);
            return;

        default:
            if (this.username.isFocused()) {
                this.username.textboxKeyTyped(typedChar, keyCode);
            }

            if (this.password.isFocused()) {
                this.password.textboxKeyTyped(typedChar, keyCode);
            }

            super.keyTyped(typedChar, keyCode);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.username.mouseClicked(mouseX, mouseY, mouseButton);
        this.password.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
        super.updateScreen();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }
}

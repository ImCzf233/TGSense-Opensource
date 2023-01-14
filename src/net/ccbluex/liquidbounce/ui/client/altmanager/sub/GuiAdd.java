package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.TabUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.lwjgl.input.Keyboard;

public class GuiAdd extends WrappedGuiScreen {

    private final GuiAltManager prevGui;
    private IGuiButton addButton;
    private IGuiButton clipboardButton;
    private IGuiTextField username;
    private IGuiTextField password;
    private String status = "§7Idle...";

    public GuiAdd(GuiAltManager gui) {
        this.prevGui = gui;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.representedScreen.getButtonList().add(this.addButton = GuiAdd.classProvider.createGuiButton(1, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 72, "Add"));
        this.representedScreen.getButtonList().add(this.clipboardButton = GuiAdd.classProvider.createGuiButton(2, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 96, "Clipboard"));
        this.representedScreen.getButtonList().add(GuiAdd.classProvider.createGuiButton(0, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 120, "Back"));
        this.username = GuiAdd.classProvider.createGuiTextField(2, Fonts.font40, this.representedScreen.getWidth() / 2 - 100, 60, 200, 20);
        this.username.setFocused(true);
        this.username.setMaxStringLength(Integer.MAX_VALUE);
        this.password = GuiAdd.classProvider.createGuiPasswordField(3, Fonts.font40, this.representedScreen.getWidth() / 2 - 100, 85, 200, 20);
        this.password.setMaxStringLength(Integer.MAX_VALUE);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.representedScreen.drawBackground(0);
        RenderUtils.drawRect(30, 30, this.representedScreen.getWidth() - 30, this.representedScreen.getHeight() - 30, Integer.MIN_VALUE);
        Fonts.font40.drawCenteredString("Add Account", (float) this.representedScreen.getWidth() / 2.0F, 34.0F, 16777215);
        Fonts.font35.drawCenteredString(this.status == null ? "" : this.status, (float) this.representedScreen.getWidth() / 2.0F, (float) this.representedScreen.getHeight() / 4.0F + 60.0F, 16777215);
        this.username.drawTextBox();
        this.password.drawTextBox();
        if (this.username.getText().isEmpty() && !this.username.isFocused()) {
            Fonts.font40.drawCenteredString("§7Username / E-Mail", (float) (this.representedScreen.getWidth() / 2 - 55), 66.0F, 16777215);
        }

        if (this.password.getText().isEmpty() && !this.password.isFocused()) {
            Fonts.font40.drawCenteredString("§7Password", (float) (this.representedScreen.getWidth() / 2 - 74), 91.0F, 16777215);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(IGuiButton button) throws IOException {
        if (button.getEnabled()) {
            switch (button.getId()) {
            case 0:
                GuiAdd.mc.displayGuiScreen(this.prevGui.representedScreen);
                break;

            case 1:
                if (LiquidBounce.fileManager.accountsConfig.accountExists(this.username.getText())) {
                    this.status = "§cThe account has already been added.";
                } else {
                    this.addAccount(this.username.getText(), this.password.getText());
                }
                break;

            case 2:
                try {
                    String e = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    String[] accountData = e.split(":", 2);

                    if (!e.contains(":") || accountData.length != 2) {
                        this.status = "§cInvalid clipboard data. (Use: E-Mail:Password)";
                        return;
                    }

                    this.addAccount(accountData[0], accountData[1]);
                } catch (UnsupportedFlavorException unsupportedflavorexception) {
                    this.status = "§cClipboard flavor unsupported!";
                    ClientUtils.getLogger().error("Failed to read data from clipboard.", unsupportedflavorexception);
                }
            }

        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        switch (keyCode) {
        case 1:
            GuiAdd.mc.displayGuiScreen(this.prevGui.representedScreen);
            return;

        case 15:
            TabUtils.tab(new IGuiTextField[] { this.username, this.password});
            return;

        case 28:
            this.actionPerformed(this.addButton);
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
    }

    private void addAccount(String name, String password) {
        if (LiquidBounce.fileManager.accountsConfig.accountExists(name)) {
            this.status = "§cThe account has already been added.";
        } else {
            this.addButton.setEnabled(false);
            this.clipboardButton.setEnabled(false);
            MinecraftAccount account = new MinecraftAccount(name, password);

            (new Thread(() -> {
                // $FF: Couldn't be decompiled
            })).start();
        }
    }
}

package net.ccbluex.liquidbounce.ui.client.tools;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.TabUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import org.lwjgl.input.Keyboard;

public class GuiPortScanner extends WrappedGuiScreen {

    private final IGuiScreen prevGui;
    private final List ports = new ArrayList();
    private IGuiTextField hostField;
    private IGuiTextField minPortField;
    private IGuiTextField maxPortField;
    private IGuiTextField threadsField;
    private IGuiButton buttonToggle;
    private boolean running;
    private String status = "§7Waiting...";
    private String host;
    private int currentPort;
    private int maxPort;
    private int minPort;
    private int checkedPort;

    public GuiPortScanner(IGuiScreen prevGui) {
        this.prevGui = prevGui;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.hostField = GuiPortScanner.classProvider.createGuiTextField(0, Fonts.font40, this.representedScreen.getWidth() / 2 - 100, 60, 200, 20);
        this.hostField.setFocused(true);
        this.hostField.setMaxStringLength(Integer.MAX_VALUE);
        this.hostField.setText("localhost");
        this.minPortField = GuiPortScanner.classProvider.createGuiTextField(1, Fonts.font40, this.representedScreen.getWidth() / 2 - 100, 90, 90, 20);
        this.minPortField.setMaxStringLength(5);
        this.minPortField.setText(String.valueOf(1));
        this.maxPortField = GuiPortScanner.classProvider.createGuiTextField(2, Fonts.font40, this.representedScreen.getWidth() / 2 + 10, 90, 90, 20);
        this.maxPortField.setMaxStringLength(5);
        this.maxPortField.setText(String.valueOf('\uffff'));
        this.threadsField = GuiPortScanner.classProvider.createGuiTextField(3, Fonts.font40, this.representedScreen.getWidth() / 2 - 100, 120, 200, 20);
        this.threadsField.setMaxStringLength(Integer.MAX_VALUE);
        this.threadsField.setText(String.valueOf(500));
        this.representedScreen.getButtonList().add(this.buttonToggle = GuiPortScanner.classProvider.createGuiButton(1, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 95, this.running ? "Stop" : "Start"));
        this.representedScreen.getButtonList().add(GuiPortScanner.classProvider.createGuiButton(0, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 120, "Back"));
        this.representedScreen.getButtonList().add(GuiPortScanner.classProvider.createGuiButton(2, this.representedScreen.getWidth() / 2 - 100, this.representedScreen.getHeight() / 4 + 155, "Export"));
        super.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.representedScreen.drawBackground(0);
        Fonts.font40.drawCenteredString("Port Scanner", (float) this.representedScreen.getWidth() / 2.0F, 34.0F, 16777215);
        Fonts.font35.drawCenteredString(this.running ? "§7" + this.checkedPort + " §8/ §7" + this.maxPort : (this.status == null ? "" : this.status), (float) this.representedScreen.getWidth() / 2.0F, (float) this.representedScreen.getHeight() / 4.0F + 80.0F, 16777215);
        this.buttonToggle.setDisplayString(this.running ? "Stop" : "Start");
        this.hostField.drawTextBox();
        this.minPortField.drawTextBox();
        this.maxPortField.drawTextBox();
        this.threadsField.drawTextBox();
        Fonts.font40.drawString("§c§lPorts:", 2, 2, Color.WHITE.hashCode());
        List list = this.ports;

        synchronized (this.ports) {
            int i = 12;
            Iterator iterator = this.ports.iterator();

            while (true) {
                if (!iterator.hasNext()) {
                    break;
                }

                Integer integer = (Integer) iterator.next();

                Fonts.font35.drawString(String.valueOf(integer), 2, i, Color.WHITE.hashCode());
                i += Fonts.font35.getFontHeight();
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(IGuiButton button) throws IOException {
        switch (button.getId()) {
        case 0:
            GuiPortScanner.mc.displayGuiScreen(this.prevGui);
            break;

        case 1:
            if (this.running) {
                this.running = false;
            } else {
                this.host = this.hostField.getText();
                if (this.host.isEmpty()) {
                    this.status = "§cInvalid host";
                    return;
                }

                try {
                    this.minPort = Integer.parseInt(this.minPortField.getText());
                } catch (NumberFormatException numberformatexception) {
                    this.status = "§cInvalid min port";
                    return;
                }

                try {
                    this.maxPort = Integer.parseInt(this.maxPortField.getText());
                } catch (NumberFormatException numberformatexception1) {
                    this.status = "§cInvalid max port";
                    return;
                }

                int i;

                try {
                    i = Integer.parseInt(this.threadsField.getText());
                } catch (NumberFormatException numberformatexception2) {
                    this.status = "§cInvalid threads";
                    return;
                }

                this.ports.clear();
                this.currentPort = this.minPort - 1;
                this.checkedPort = this.minPort;

                for (int j = 0; j < i; ++j) {
                    (new Thread(() -> {
                        // $FF: Couldn't be decompiled
                    })).start();
                }

                this.running = true;
            }

            this.buttonToggle.setDisplayString(this.running ? "Stop" : "Start");
            break;

        case 2:
            File selectedFile = MiscUtils.saveFileChooser();

            if (selectedFile == null || selectedFile.isDirectory()) {
                return;
            }

            try {
                if (!selectedFile.exists()) {
                    selectedFile.createNewFile();
                }

                FileWriter e = new FileWriter(selectedFile);

                e.write("Portscan\r\n");
                e.write("Host: " + this.host + "\r\n\r\n");
                e.write("Ports (" + this.minPort + " - " + this.maxPort + "):\r\n");
                Iterator iterator = this.ports.iterator();

                while (iterator.hasNext()) {
                    Integer integer = (Integer) iterator.next();

                    e.write(integer + "\r\n");
                }

                e.flush();
                e.close();
                JOptionPane.showMessageDialog((Component) null, "Exported successfully!", "Port Scanner", 1);
            } catch (Exception exception) {
                exception.printStackTrace();
                MiscUtils.showErrorPopup("Error", "Exception class: " + exception.getClass().getName() + "\nMessage: " + exception.getMessage());
            }
        }

        super.actionPerformed(button);
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (1 == keyCode) {
            GuiPortScanner.mc.displayGuiScreen(this.prevGui);
        } else {
            if (15 == keyCode) {
                TabUtils.tab(new IGuiTextField[] { this.hostField, this.minPortField, this.maxPortField});
            }

            if (!this.running) {
                if (this.hostField.isFocused()) {
                    this.hostField.textboxKeyTyped(typedChar, keyCode);
                }

                if (this.minPortField.isFocused() && !Character.isLetter(typedChar)) {
                    this.minPortField.textboxKeyTyped(typedChar, keyCode);
                }

                if (this.maxPortField.isFocused() && !Character.isLetter(typedChar)) {
                    this.maxPortField.textboxKeyTyped(typedChar, keyCode);
                }

                if (this.threadsField.isFocused() && !Character.isLetter(typedChar)) {
                    this.threadsField.textboxKeyTyped(typedChar, keyCode);
                }

                super.keyTyped(typedChar, keyCode);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.hostField.mouseClicked(mouseX, mouseY, mouseButton);
        this.minPortField.mouseClicked(mouseX, mouseY, mouseButton);
        this.maxPortField.mouseClicked(mouseX, mouseY, mouseButton);
        this.threadsField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void updateScreen() {
        this.hostField.updateCursorCounter();
        this.minPortField.updateCursorCounter();
        this.maxPortField.updateCursorCounter();
        this.threadsField.updateCursorCounter();
        super.updateScreen();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.running = false;
        super.onGuiClosed();
    }
}
